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
import org.me.smartphone.webservice.Reseau;
/**
 *
 * @author jeremie
 */
public class PieceDAO {
    
    Activity parent;

    public PieceDAO(Activity p) {
        parent = p;
    }

    public JSONArray getAll() {
        String result = Reseau.webServiceResponse(null, "http://10.0.2.2/smartphone/pieceDataManager/getAll.php");

        //parse json data
        try {
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch (JSONException e) {
            return null;
        }
    }
    
    public boolean orderPiece(String piece) {
        String[] split = piece.split(" ");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("piece", piece));
//        nameValuePairs.add(new BasicNameValuePair("ref", split[1]));
        if (Reseau.ping("http://10.0.2.2")) {
        Reseau.webServiceResponse(nameValuePairs, "http://10.0.2.2/smartphone/pieceDataManager/sendPiece.php");
            return true;
        } else {
            String data = "piece:" + piece + "\n";
            if (Reseau.WriteSettings(parent, data, "piece.txt")) {
                new Thread()  {

                    @Override
                    public void run() {
                        Reseau.listeningNetwork("http://10.0.2.2", parent, "piece.txt", "http://10.0.2.2/smartphone/pieceDataManager/sendPiece.php");
                    }
                }.start();
            }
            return false;
        }
        
//        try {
//            JSONArray jArray = new JSONArray(result);
//            JSONObject j = jArray.getJSONObject(0);
//            if(j.getString("reour").equals("FALSE"))
//                return false;
//            else
//                return true;
//        } catch (JSONException e) {
//            return false;
//        }
    }
}
