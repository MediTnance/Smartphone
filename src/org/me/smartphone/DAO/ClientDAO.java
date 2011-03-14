/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.DAO;

import android.util.Log;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.util.MessageBox;
import org.me.smartphone.webservice.Reseau;

/**
 *
 * @author jeremie
 */
public class ClientDAO {

    private static final String TAG = "Debug of Meditnance Project";
//    private MessageBox messageBox = new MessageBox();

    public JSONArray getClientByName(String firstName, String lastName) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("firstName", firstName));
        nameValuePairs.add(new BasicNameValuePair("lastName", lastName));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/clientDataManager/getClientByName.php");

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch (JSONException e) {
            return null;
        }
    }
    
    public String getClientFullNameById(String id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/clientDataManager/getClientById.php");

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
                JSONObject json_data = jArray.getJSONObject(0);
                String fullname = json_data.getString("firstName") + " " + json_data.getString("lastName");
            return fullname;
        } catch (JSONException e) {
            return null;
        }
    }
}