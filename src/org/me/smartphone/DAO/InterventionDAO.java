/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.DAO;

import android.app.Activity;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    Activity parent;

    public InterventionDAO(Activity p) {
        parent = p;
    }

    public JSONArray getInterventionsByDate(Date date) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String t = dateFormat.format(date);
        nameValuePairs.add(new BasicNameValuePair("begin", t));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/interventionDataManager/getInterventionByDate.php");
        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject getInterventionById(String id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", id));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/interventionDataManager/getInterventionById.php");
        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            JSONObject json_data = jArray.getJSONObject(0);
            return json_data;
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean updateIntervention(String id, String cost, String comment, String hour) {
        String result = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.split(":")[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(hour.split(":")[1]));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end = dateFormat.format(cal.getTime());

        nameValuePairs.add(new BasicNameValuePair("cost", cost));
        nameValuePairs.add(new BasicNameValuePair("annotations", comment));
        nameValuePairs.add(new BasicNameValuePair("id", id));
        nameValuePairs.add(new BasicNameValuePair("end", end));

        if (Reseau.ping("http://10.0.2.2")) {
            result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/interventionDataManager/updateIntervention.php");
            return true;
        } else {
//            Reseau.clearFile(parent, "intervention.txt");
//            return true;
            String data = "cost:" + cost + "/id:" + id + "/end:" + end + "/annotations:" + comment + "\n";
            if (Reseau.WriteSettings(parent, data, "intervention.txt")) {
                new Thread()  {

                    @Override
                    public void run() {
                        Reseau.listeningNetwork("http://10.0.2.2", parent, "intervention.txt", "http://10.0.2.2/smartphone/interventionDataManager/updateIntervention.php");
                    }
                }.start();
            } else {
                return false;
            }
            return true;
        }
    }
}
