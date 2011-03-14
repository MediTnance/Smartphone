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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.DAO.ClientDAO;
import org.me.smartphone.util.MessageBox;

/**
 *
 * @author jeremie
 */
public class ClientActivity extends Activity {

    Button button1;
    TextView firstnameText;
    EditText firstnameEditText;
    TextView lastnameText;
    EditText lastnameEditText;
    ClientDAO client = new ClientDAO();
//    MessageBox messageBox = new MessageBox();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.client);

        this.firstnameText = (TextView) findViewById(R.id.firstnameText);
        this.firstnameEditText = (EditText) findViewById(R.id.fistnameEditText);
        this.lastnameText = (TextView) findViewById(R.id.lastnameText);
        this.lastnameEditText = (EditText) findViewById(R.id.lastnameEditText);
        button1 = (Button) findViewById(R.id.button1);



        button1.setOnClickListener(new View.OnClickListener()  {

            public void onClick(View v) {
                String firstName = firstnameEditText.getText().toString();
                String lastName = lastnameEditText.getText().toString();
                if (firstName.equals("") || lastName.equals("")) {
                    Show("Erreur", "Veuillez remplir tous les champs");
                } else {
                    JSONArray res = client.getClientByName(firstName, lastName);
                    if (res == null) {
                        Show("Erreur", "Client inexistant");
                    } else {
                        String clientI = "";
                        try {
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject json_data = res.getJSONObject(i);
                                clientI += json_data.getString("firstName") + " " + json_data.getString("lastName") + " " + json_data.getString("address") + "\n";
                            }
                            TextView clientText = (TextView)findViewById(R.id.clientText);
                            clientText.setText(clientI);
                        } catch (JSONException ex) {
                            Logger.getLogger(Meditnance.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        });
    }

    public void Show(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()   {

            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });
        alertDialog.show();
    }
}