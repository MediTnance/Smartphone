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
import org.me.smartphone.DAO.InterventionDAO;
import org.me.smartphone.util.MessageBox;

/**
 *
 * @author jeremie
 */
public class InterventionActivity extends Activity {

    Button buttonIntervention;
    EditText hourEditText;
    EditText priceEditText;
    EditText commentEditText;
    String id = "";
    private InterventionDAO interventionDAO = new InterventionDAO();
    MessageBox messageBox = new MessageBox();
    Activity _this;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.intervention);
        Log.d("RAAAAAAAAAAAAAAAAAh", "hu");
        _this = this;
        buttonIntervention = (Button) findViewById(R.id.buttonIntervention);
        hourEditText = (EditText) findViewById(R.id.hourEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        Log.d("RAAAAAAAAAAAAAAAAAh", "huDDD");
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        Log.d("RAAAAAAAAAAAAAAAAAh", id);

        buttonIntervention.setOnClickListener(new View.OnClickListener()     {

            public void onClick(View v) {
                String cost = priceEditText.getText().toString();
                String comment = commentEditText.getText().toString();
                String hour = hourEditText.getText().toString();
                if (cost.isEmpty() || comment.isEmpty() || hour.isEmpty()) {
                    messageBox.Show("Erreur", "Veuillez remplir tous les champs", _this);
                } else {
                    try {
                        Integer testParse = Integer.parseInt(cost);
                        interventionDAO.updateIntervention(id, cost, comment);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (NumberFormatException nfe) {
                        messageBox.Show("Erreur", "Veuillez un coût valide", _this);
                    }
                }
            }
        });

    }
}