/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jeremie
 */
public class Meditnance extends TabActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec; // reusable tabspec for each tab
        Intent intent;

        intent = new Intent().setClass(this, PlanningActivity.class);
        spec = tabHost.newTabSpec("planning").setIndicator("Planning").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ClientActivity.class);
        spec = tabHost.newTabSpec("clients").setIndicator("Clients").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, PlanningActivity.class);
        spec = tabHost.newTabSpec("interventions").setIndicator("Interventions").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ClientActivity.class);
        spec = tabHost.newTabSpec("pieces").setIndicator("Pièces").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        
    }
//    @Override
//    public void onCreate(Bundle icicle) {
//        super.onCreate(icicle);
//        TextView tv = new TextView(this);
//        
//        ClientDataManager client = new ClientDataManager();
//        JSONObject res = client.getClient("Foo", "Bar");
//        
//        
//        String clientI = null;
//        try {
//            clientI = res.getString("firstName") + " " + res.getString("lastName") + " " + res.getString("address");
//        } catch (JSONException ex) {
//            Logger.getLogger(Meditnance.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        tv.setText(clientI);
//        setContentView(tv);  
//    }
    
    public void MessageBox2(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Are you sure?");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
              // here you can add functions
           }
        });
        alertDialog.show();
    }
}