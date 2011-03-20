/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.smartphone.DAO;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.me.smartphone.webservice.Reseau;
/**
 *
 * @author jeremie
 */
public class PieceDAO {

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
}
