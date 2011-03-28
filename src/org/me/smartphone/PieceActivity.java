/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.DAO.PieceDAO;
import org.me.smartphone.util.MessageBox;

/**
 *
 * @author jeremie
 */
public class PieceActivity extends Activity {

    PieceDAO pieceDAO;
    MessageBox messageBox = new MessageBox();
    Button button;
    Spinner spinner;
    Activity _this;
    int idOrder;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        idOrder = 0;
        setContentView(R.layout.piece);
        pieceDAO = new PieceDAO(this);
        spinner = (Spinner) findViewById(R.id.spinnerPiece);
        button = (Button) findViewById(R.id.buttonCommand);
        JSONArray res = pieceDAO.getAll();
        _this = this;

        if (res == null) {
            spinner.setEnabled(false);
        } else {
            String[] pieces = new String[res.length()];
            for (int i = 0; i < res.length(); i++) {
                try {
                    JSONObject json_data = res.getJSONObject(i);
                    pieces[i] = json_data.getString("provider") + " " + json_data.getString("reference");
                } catch (JSONException ex) {
                    Logger.getLogger(PieceActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pieces);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            button.setOnClickListener(new View.OnClickListener()   {

                public void onClick(View v) {
                    idOrder++;
                    pieceDAO.orderPiece(String.valueOf(idOrder)+ " " + spinner.getSelectedItem().toString());
                }
            });
        }
    }
}
