/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.DAO;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.webservice.Reseau;

/**
 *
 * @author jeremie
 */
public class InterventionDAO {

    private ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    public JSONArray getInterventionsByDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String t = dateFormat.format(date);
        this.nameValuePairs.add(new BasicNameValuePair("begin", t));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/interventionDataManager/getInterventionByDate.php");
        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch (JSONException e) {
            return null;
        }

    }
}
