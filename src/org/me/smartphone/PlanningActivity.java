/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.DAO.InterventionDAO;
import org.me.smartphone.DAO.ClientDAO;

/**
 *
 * @author jeremie
 */
public class PlanningActivity extends Activity {

    private InterventionDAO intervention = new InterventionDAO();
    private ClientDAO clientDAO = new ClientDAO();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.planning);
        TableLayout table = (TableLayout) findViewById(R.id.TableLayout);

        JSONArray res = intervention.getInterventionsByDate(Calendar.getInstance().getTime());
        
        if (res != null) {
            try {
                for (int i = 0; i < res.length(); i++) {
                    JSONObject json_data = res.getJSONObject(i);
                    TableRow row = new TableRow(this);
                    TextView date = new TextView(this);
                    date.setText(json_data.getString("begin").substring(11, 16));
                    TextView employee = new TextView(this);
                    employee.setText(json_data.getString("employee"));
                    TextView client = new TextView(this);
                    client.setText(clientDAO.getClientFullNameById(json_data.getString("client")));
                    row.addView(date);
                    row.addView(employee);
                    row.addView(client);
                    // add the TableRow to the TableLayout
                    table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                }
            } catch (JSONException ex) {
                Logger.getLogger(Meditnance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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