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
import org.me.smartphone.DAO.InterventionDAO;
import org.me.smartphone.util.MessageBox;

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
    private InterventionDAO interventionDAO;
    MessageBox messageBox = new MessageBox();
    Activity _this;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.intervention);
        _this = this;
        interventionDAO = new InterventionDAO(this);
        buttonIntervention = (Button) findViewById(R.id.buttonIntervention);
        buttonCancelIntervention = (Button)findViewById(R.id.buttonCancelIntervention);
        hourEditText = (EditText) findViewById(R.id.hourEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        JSONObject jsono = interventionDAO.getInterventionById(id);
        try {
            if (jsono.getString("end").equals("null"))
                hourEditText.setText("");
            else
                hourEditText.setText(jsono.getString("end").substring(11, 16));
            priceEditText.setText(jsono.getString("cost"));
            commentEditText.setText(jsono.getString("annotations"));
            
        } catch (JSONException ex) {
            messageBox.Show("Erreur", "Impossible de récupérer les données sur le serveur", _this);
        }


        buttonIntervention.setOnClickListener(new View.OnClickListener()      {

            public void onClick(View v) {
                String cost = priceEditText.getText().toString();
                String comment = commentEditText.getText().toString();
                String hour = hourEditText.getText().toString();
                if (cost.isEmpty() || comment.isEmpty() || hour.isEmpty()) {
                    messageBox.Show("Erreur", "Veuillez remplir tous les champs", _this);
                } else {

                    if (!hour.matches("[0-9]{1,2}:[0-9]{2}")) {
                        messageBox.Show("Erreur", "Veuillez une heure valide (format XX:XX)", _this);
                    } else {
                        try {
                            Integer.parseInt(cost);
                            if(!interventionDAO.updateIntervention(id, cost, comment, hour))
                                messageBox.Show("Erreur","Mise à jour impossible",_this);
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } catch (NumberFormatException nfe) {
                            messageBox.Show("Erreur", "Veuillez un coût valide", _this);
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