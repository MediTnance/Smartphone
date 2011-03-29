/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

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
        
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, PlanningActivity.class);
        spec = tabHost.newTabSpec("planning").setIndicator("Planning").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ClientActivity.class);
        spec = tabHost.newTabSpec("clients").setIndicator("Clients").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, PieceActivity.class);
        spec = tabHost.newTabSpec("pieces").setIndicator("Pièces").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}