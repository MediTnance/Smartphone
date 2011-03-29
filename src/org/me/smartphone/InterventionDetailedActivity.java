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
import android.widget.TextView;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.application.GestionSmartphone;

/**
 *
 * @author jeremie
 */
public class InterventionDetailedActivity extends Activity {

    TextView typeText;
    TextView clientText;
    TextView employeeText;
    TextView beginText;
    TextView endText;
    TextView costText;
    TextView annotationsText;
    Button buttonBack;
    Button buttonFurnituresClient;
    String id;
    GestionSmartphone smartphoneManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.interventiondetailed);
        smartphoneManager = new GestionSmartphone(this);
        typeText = (TextView) findViewById(R.id.typeText);
        clientText = (TextView) findViewById(R.id.clientText);
        employeeText = (TextView) findViewById(R.id.employeeText);
        beginText = (TextView) findViewById(R.id.beginText);
        endText = (TextView) findViewById(R.id.endText);
        costText = (TextView) findViewById(R.id.costText);
        annotationsText = (TextView) findViewById(R.id.annotationsText);
        buttonBack = (Button) findViewById(R.id.buttonBack2);
        buttonFurnituresClient = (Button) findViewById(R.id.buttonFurnitureClient);

        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        final JSONObject jsono = smartphoneManager.getInterventionById(id);

        try {
            if (jsono.getString("end").equals("null")) {
                endText.append("");
            } else {
                endText.append(jsono.getString("end"));
            }

            if (jsono.getString("nature").equals("1")) {
                typeText.append("Réparation");
            } else {
                typeText.append("Maintenance");
            }

            costText.append(jsono.getString("cost"));
            clientText.setText(bundle.getString("client"));
            employeeText.setText(bundle.getString("employee"));
            beginText.append(jsono.getString("begin"));
            annotationsText.append(jsono.getString("annotations"));

        } catch (JSONException ex) {
            smartphoneManager.showDialog("Erreur", "Impossible de récupérer les données sur le serveur");
        }

        buttonBack.setOnClickListener(new View.OnClickListener()     {

            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttonFurnituresClient.setOnClickListener(new View.OnClickListener()     {

            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", jsono.getString("client"));
                    Intent intent = new Intent(v.getContext(), ListFurnitureActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                } catch (JSONException ex) {
                    Logger.getLogger(InterventionDetailedActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
