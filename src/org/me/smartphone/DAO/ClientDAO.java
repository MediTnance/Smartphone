/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.DAO;

import android.app.Activity;
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

    Activity parent;

    public ClientDAO(Activity p) {
        parent = p;
    }

    public JSONArray getClientByName(String firstName, String lastName) {
        String result = null;
        if (Reseau.ping("http://10.0.2.2")) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("firstName", firstName));
            nameValuePairs.add(new BasicNameValuePair("lastName", lastName));

            result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/clientDataManager/getClientByName.php");
//            if(result != null);

            Reseau.WriteSettings(parent, result, "client.txt");
        } else {
            String match = "\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\"";
            result = Reseau.ReadSettings(parent, "client.txt", match);
        }
        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getClientById(String id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/clientDataManager/getClientById.php");

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            JSONObject jsono = jArray.getJSONObject(0);
            return jsono;
        } catch (JSONException e) {
            return null;
        }
    }

    public String getClientFullNameById(String id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/clientDataManager/getClientFullNameById.php");

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