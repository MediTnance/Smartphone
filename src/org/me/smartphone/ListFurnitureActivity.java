/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import org.me.smartphone.application.GestionSmartphone;

/**
 *
 * @author jeremie
 */
public class ListFurnitureActivity extends Activity {

    TextView clientFurnitureText;
    TextView typeFurnitureText;
    TextView verifFurnitureText;
    TextView brandFurnitureText;
    TextView refFurnitureText;
    TextView lastInterventionFurnitureText;
    ListView furnituresList;
    Button buttonBack;
    GestionSmartphone smartphoneManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listfurniture);
        buttonBack = (Button) findViewById(R.id.buttonBack3);
        smartphoneManager = new GestionSmartphone(this);
        furnituresList = (ListView) findViewById(R.id.listviewFurnitures);

        Bundle bundle = this.getIntent().getExtras();
        String clientId = bundle.getString("id");
        ArrayList<HashMap<String, String>> list = smartphoneManager.getFunituresByClient(clientId);
        
        if (list == null) {
            smartphoneManager.showDialog("Erreur", "Aucun matériel pour ce client");
            finish();
        } else {
            SimpleAdapter mSchedule = new SimpleAdapter(getBaseContext(), list, R.layout.furnitureitem,
                    new String[]{"client", "type", "verif", "brand", "ref", "last"},
                    new int[]{R.id.clientFurnitureText, R.id.typeFurnitureText, R.id.verifFurnitureText, R.id.brandFurnitureText, R.id.refFurnitureText, R.id.lastFurnitureText});
            furnituresList.setAdapter(mSchedule);
        }
        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
