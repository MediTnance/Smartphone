/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import org.me.smartphone.application.GestionSmartphone;

/**
 *
 * @author jeremie
 */
public class PlanningActivity extends Activity {

    GestionSmartphone smartphoneManager;
    ArrayList<ArrayList<HashMap<String, String>>> planning;
    ListView liste;
    Context context;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        smartphoneManager = new GestionSmartphone(this);
        setContentView(R.layout.planning);
        context = this.getBaseContext();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Calendar cal = this.smartphoneManager.getFirstDayOfWeek();

        String[] dates = new String[5];
        SimpleDateFormat sd;
        for (int i = 0; i < 5; i++) {
            sd = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.FRANCE);
            dates[i] = sd.format(cal.getTime());
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
        liste = (ListView) findViewById(R.id.listviewperso);
        
        planning = this.smartphoneManager.calculerPlanning(this);
        if (planning != null) {

            spinner.setOnItemSelectedListener(new OnItemSelectedListener()    {

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    ArrayList<HashMap<String, String>> jour = null;
                    jour = planning.get(position);
                    SimpleAdapter mSchedule = new SimpleAdapter(context, jour, R.layout.affichageitem,
                            new String[]{"heure", "employe", "client", "idIntervention"}, 
                            new int[]{R.id.heure, R.id.employe, R.id.client, R.id.idIntervention});
                    liste.setAdapter(mSchedule);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });
        }
    }

    public void mapHandler(View target) {
        LinearLayout vwParentRow = (LinearLayout)target.getParent().getParent();
        TextView id = (TextView)vwParentRow.getChildAt(3);
        Bundle bundle = new Bundle();
        bundle.putString("id", id.getText().toString());
        Intent intent = new Intent(target.getContext(), MapRouteActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    public void updateInterventionHandler(View target) {
        LinearLayout vwParentRow = (LinearLayout)target.getParent().getParent();
        TextView id = (TextView)vwParentRow.getChildAt(3);
        Bundle bundle = new Bundle();
        bundle.putString("id", id.getText().toString());
        Intent intent = new Intent(target.getContext(), InterventionActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    public void detailsInterventionHandler(View target) {
        LinearLayout vwParentRow = (LinearLayout)target.getParent().getParent();
        TextView employee = (TextView)vwParentRow.getChildAt(1);
        TextView client = (TextView)vwParentRow.getChildAt(2);
        TextView id = (TextView)vwParentRow.getChildAt(3);
        Bundle bundle = new Bundle();
        bundle.putString("id", id.getText().toString());
        bundle.putString("employee", employee.getText().toString());
        bundle.putString("client", client.getText().toString());
        Intent intent = new Intent(target.getContext(), InterventionDetailedActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    public void Show(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()     {

            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });
        alertDialog.show();
    }
}
