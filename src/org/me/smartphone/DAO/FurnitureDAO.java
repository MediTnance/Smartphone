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
import org.me.smartphone.webservice.Reseau;
/**
 *
 * @author jeremie
 */
public class FurnitureDAO {

    public JSONArray getFurnituresByClient(String id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("client", id));

        String result = Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/furnitureDataManager/getFurnituresByClient.php");
        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch (Exception e) {
            return null;
        }
    }
}
