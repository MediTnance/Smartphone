/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.webservice;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author jeremie
 */
public class Reseau {

    public static String webServiceResponse(ArrayList<NameValuePair> nameValuePairs, String requestPath) {
        String result = "";
        InputStream is = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(requestPath);
            if (nameValuePairs != null) {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        //convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        return result;
    }

    public static boolean WriteSettings(Context context, String data, String path) {

        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try {

            fOut = context.openFileOutput(path, Context.MODE_APPEND);
            osw = new OutputStreamWriter(fOut);
            osw.append(data);
            osw.flush();

        } catch (Exception e) {
            return false;
        } finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public static String ReadSettings(Context context, String path, String match) {
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        String data = null;
        boolean found = false;

        try {
            fIn = context.openFileInput(path);
            isr = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader(isr);
            String line;
            while (((line = buffreader.readLine()) != null) && !found) {
                if (line.contains(match)) {
                    found = true;
                    data = line;
                }
            }
            isr.close();
            fIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ArrayList<NameValuePair> ReadSettings(Context context, String path) {
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try {
            fIn = context.openFileInput(path);
            isr = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader(isr);
            String line = null;
            while ((line = buffreader.readLine()) != null) {
                String[] fields = line.split("/");
                for (String s : fields) {
                    String[] d = new String[2];
                    d = s.split(":");
                    nameValuePairs.add(new BasicNameValuePair(d[0], d[1]));
                }
            }
            isr.close();
            fIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameValuePairs;
    }

    public static boolean deleteFile(String path) {
        try {
            File file = new File(path);
            file.delete();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void clearFile(Context context, String path) {
        try {
            FileOutputStream fOut = context.openFileOutput(path, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write("");
            osw.flush();
            osw.close();
        } catch (IOException ex) {
            Logger.getLogger(Reseau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean ping(String host) {
        URL url;
        try {
            url = new URL(host);

            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestProperty("User-Agent", "Android Application");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
            urlc.connect();
            if (urlc.getResponseCode() == 200) {
                return true;
            }
        } catch (MalformedURLException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return false;
    }

    public static boolean listeningNetwork(final String address, final Context context, String file, String scriptPath) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<ArrayList<NameValuePair>> f = (Future<ArrayList<NameValuePair>>) es.submit(new checkInterventionFile(address, context, file));
        try {
            ArrayList<NameValuePair> value = f.get();
            Reseau.webServiceResponse(value, scriptPath);
            Reseau.clearFile(context, file);
            return true;
        } catch (InterruptedException ex) {
            return false;
        } catch (ExecutionException ex) {
            return false;
        }
    }
}

class checkInterventionFile implements Callable<ArrayList<NameValuePair>> {

    private String address;
    private Context context;
    private String file;

    public checkInterventionFile(String address, Context context, String file) {
        this.address = address;
        this.context = context;
        this.file = file;
    }

    public ArrayList<NameValuePair> call() {
        while (!Reseau.ping(address)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Reseau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<NameValuePair> data = Reseau.ReadSettings(context, file);
        return data;
    }
}
