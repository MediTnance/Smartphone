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
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.application.GestionSmartphone;

/**
 *
 * @author jeremie
 */
public class InterventionActivity extends Activity {

    Button buttonIntervention;
    Button buttonCancelIntervention;
    EditText hourEditText;
    EditText priceEditText;
    EditText commentEditText;
    String id = "";
    GestionSmartphone smartphoneManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.intervention);
        smartphoneManager = new GestionSmartphone(this);
        buttonIntervention = (Button) findViewById(R.id.buttonIntervention);
        buttonCancelIntervention = (Button)findViewById(R.id.buttonCancelIntervention);
        hourEditText = (EditText) findViewById(R.id.hourEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        JSONObject jsono = smartphoneManager.getInterventionById(id);
        try {
            if (jsono.getString("end").equals("null"))
                hourEditText.setText("");
            else
                hourEditText.setText(jsono.getString("end").substring(11, 16));
            priceEditText.setText(jsono.getString("cost"));
            commentEditText.setText(jsono.getString("annotations"));
            
        } catch (JSONException ex) {
            smartphoneManager.showDialog("Erreur", "Impossible de récupérer les données sur le serveur");
        }


        buttonIntervention.setOnClickListener(new View.OnClickListener()      {

            public void onClick(View v) {
                String cost = priceEditText.getText().toString();
                String comment = commentEditText.getText().toString();
                String hour = hourEditText.getText().toString();
                if (cost.isEmpty() || comment.isEmpty() || hour.isEmpty()) {
                    smartphoneManager.showDialog("Erreur", "Veuillez remplir tous les champs");
                } else {

                    if (!hour.matches("[0-9]{1,2}:[0-9]{2}")) {
                        smartphoneManager.showDialog("Erreur", "Veuillez une heure valide (format XX:XX)");
                    } else {
                        try {
                            Integer.parseInt(cost);
                            if(!smartphoneManager.updateIntervention(id, cost, comment, hour))
                                smartphoneManager.showDialog("Erreur", "Pas de réseau, les changements prendont effet dès que le réseau sera disponible");
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } catch (NumberFormatException nfe) {
                            smartphoneManager.showDialog("Erreur", "Veuillez un coût valide");
                        }
                    }
                }
            }
        });
        
        buttonCancelIntervention.setOnClickListener(new View.OnClickListener()      {

            public void onClick(View v) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
            }
        });

    }
}