/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.application;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.me.smartphone.DAO.ClientDAO;
import org.me.smartphone.DAO.EmployeeDAO;
import org.me.smartphone.DAO.FurnitureDAO;
import org.me.smartphone.DAO.InterventionDAO;
import org.me.smartphone.R;

/**
 *
 * @author jeremie
 */
public class GestionSmartphone {

    Activity parent;
    private InterventionDAO interventionDAO;
    private ClientDAO clientDAO;
    private EmployeeDAO employeeDAO;
    private FurnitureDAO furnitureDAO;

    public GestionSmartphone(Activity p) {
        parent = p;
        interventionDAO = new InterventionDAO(p);
        clientDAO = new ClientDAO(p);
        employeeDAO = new EmployeeDAO();
        furnitureDAO = new FurnitureDAO();
    }

    public ArrayList<ArrayList<HashMap<String, String>>> calculerPlanning(Activity a) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<ArrayList<HashMap<String, String>>> listItem = new ArrayList<ArrayList<HashMap<String, String>>>();

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.SUNDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
                break;

            case Calendar.TUESDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
                break;

            case Calendar.WEDNESDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 2);
                break;

            case Calendar.THURSDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 3);
                break;

            case Calendar.FRIDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 4);
                break;

            case Calendar.SATURDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 5);
                break;
        }
        int nb_null = 0;
        for (int i = 0; i < 5; i++) {
            JSONArray res = this.interventionDAO.getInterventionsByDate(calendar.getTime());
            ArrayList<HashMap<String, String>> jour = new ArrayList<HashMap<String, String>>();

            if (res != null) {
                try {
                    for (int j = 0; j < res.length(); j++) {
                        JSONObject json_data = res.getJSONObject(j);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("heure", "Heure : " + json_data.getString("begin").substring(11, 16));
                        map.put("employe", "Employé : " + employeeDAO.getEmployeeFullNameById(json_data.getString("employee")));
                        map.put("client", "Client : " + clientDAO.getClientFullNameById(json_data.getString("client")));
                        map.put("idIntervention", json_data.getString("id"));
                        jour.add(map);

                    }
                } catch (JSONException ex) {
                    Logger.getLogger(GestionSmartphone.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                nb_null++;
            }
            listItem.add(jour);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);

        }
        if (nb_null == 5) {
            return null;
        }
        return listItem;
    }

    public Calendar getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.SUNDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
                break;

            case Calendar.TUESDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
                break;

            case Calendar.WEDNESDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 2);
                break;

            case Calendar.THURSDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 3);
                break;

            case Calendar.FRIDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 4);
                break;

            case Calendar.SATURDAY:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 5);
                break;
        }
        return calendar;
    }

    public JSONObject getInterventionById(String id) {
        return this.interventionDAO.getInterventionById(id);
    }

    public JSONArray getClientByName(String firstName, String lastName) {
        return clientDAO.getClientByName(firstName, lastName);
    }

    public JSONObject getClientById(String id) {
        return this.clientDAO.getClientById(id);
    }

    public ArrayList<HashMap<String, String>> getFunituresByClient(String clientId) {
        JSONArray jsono = furnitureDAO.getFurnituresByClient(clientId);
        ArrayList<HashMap<String, String>> furnitures = new ArrayList<HashMap<String, String>>();

        if (jsono != null) {
            try {
                for (int j = 0; j < jsono.length(); j++) {
                    JSONObject json_data = jsono.getJSONObject(j);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("client", "Matériels du client : " + clientDAO.getClientFullNameById(clientId));
                    if (!json_data.getString("type").equals("null")) {
                        map.put("type", "Type : " + json_data.getString("type"));
                    }
                    if (!json_data.getString("verification").equals("null")) {
                        map.put("verif", "Vérification : " + json_data.getString("verification"));
                    }
                    map.put("brand", "Marque : " + json_data.getString("brand"));
                    map.put("ref", "Référence : " + json_data.getString("ref"));
                    if (!json_data.getString("lastIntervention").equals("null")) {
                        map.put("last", "Dernière intervention : " + json_data.getString("lastIntervention"));
                    }
                    furnitures.add(map);

                }
            } catch (JSONException ex) {
                Logger.getLogger(GestionSmartphone.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null;
        }
        Log.d("RRRRRRRRRRRRRRRRRRRRRRRRRRR", "hu");
        return furnitures;
    }
}
