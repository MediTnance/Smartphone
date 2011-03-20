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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
    
    GestionSmartphone smartphoneManager = new GestionSmartphone();
    ArrayList<ArrayList<HashMap<String, String>>> planning;
    ArrayList<String> listIds = new ArrayList<String>();    
    ListView liste;
    Context context;
//    Button map;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        liste = (ListView) findViewById(R.id.listviewperso);
        
        planning = this.smartphoneManager.calculerPlanning(this);
        if (planning != null) {
            
            spinner.setOnItemSelectedListener(new OnItemSelectedListener()   {

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    
                    ArrayList<HashMap<String, String>> jour = null;
                    switch (position) {
                        case 0:
                            jour = planning.get(0);
                            for (HashMap<String, String> h : jour) {
                                listIds.add(h.get("id"));
                            }
                            break;
                        
                        case 1:
                            jour = planning.get(1);
                            break;
                        
                        case 2:
                            jour = planning.get(2);
                            break;
                        
                        case 3:
                            jour = planning.get(3);
                            break;
                        
                        case 4:
                            jour = planning.get(4);
                            break;
                        
                        case 5:
                            jour = planning.get(5);
                            break;
                    }
                    SimpleAdapter mSchedule = new SimpleAdapter(context, jour, R.layout.affichageitem,
                            new String[]{"heure", "employe", "client"}, new int[]{R.id.heure, R.id.employe, R.id.client});
                    
                    liste.setAdapter(mSchedule);
                }
                
                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });

//            View.OnClickListener handler = new View.OnClickListener()   {

//                public void onClick(View v) {
//                    Log.d("RAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH", "test");
//                    switch (v.getId()) {
//                        case R.id.buttonMap:
//                            Log.d("RAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH", "test");
//                            Show("test", "e");
//                            break;
//                            
//                            case R.id.listviewperso:
//                            Log.d("RAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH", "test");
//                            Show("test", "e");
//                            break;
//                    }
//                }
//            };
//
//            findViewById(R.id.buttonMap).setOnClickListener(handler);  

//            liste.setOnItemClickListener(new OnItemClickListener()  {
//                @Override
//                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//                    Log.d("RAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH", "test");
//                    HashMap<String, String> map = (HashMap<String, String>) liste.getItemAtPosition(position);
//                    Show("test",map.get("employe"));
//                }
//            });
//            findViewById(R.id.listviewperso).setOnClickListener(handler);
        }
        
    }
    
    public void mapHandler(View target) {
        Log.d("RAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH", "LOLtest");
        Intent intent = new Intent(target.getContext(), MapRouteActivity.class);
        startActivityForResult(intent, 0);
    }
    
    public void updateInterventionHandler(View target) {
        Bundle bundle = new Bundle();
        bundle.putString("id", listIds.get(0));
        Intent intent = new Intent(target.getContext(), InterventionActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }
    
    public void detailsInterventionHandler(View target) {
    }
    
    public void Show(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()    {
            
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });
        alertDialog.show();
    }
}
