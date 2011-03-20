/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.webservice;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.net.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

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
        Log.d("RAAAAAAAAAH", "te");
        try {
            fOut = context.openFileOutput(path, Context.MODE_PRIVATE);
            Log.d("RAAAAAAAAAH", "tDDFe");
            osw = new OutputStreamWriter(fOut);
            Log.d("RAAAAAAAAAH", "tedsd");
            osw.write(data);
            Log.d("RAAAAAAAAAH", "teddee");
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

    public static void ecrire(String nomFic, String texte) {

        try {
            URL url = new URL(nomFic);
            Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "pouet");
//            Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "e : " + url.getUserInfo());
            Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR",  "a : " + url.getContent().toString());
            
       HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
       
       urlConn.setRequestMethod("POST"); 
//       Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR",  "b : " + String.valueOf(urlConn.getLastModified()));
       urlConn.setDoInput(true); 
       urlConn.setDoOutput(true); 
       urlConn.connect();
//       

       
//       DataOutputStream os = new DataOutputStream(urlConn.getOutputStream());
//       os.writeChars(texte);
//       os.flush();
//       os.close();
       
//       PrintWriter pw = new PrintWriter(urlConn.getOutputStream());
       
//       pw.write(texte);
//       pw.println(texte); 
//       pw.close();
//            FileWriter fw = new FileWriter(nomFic, false);
       OutputStreamWriter fw = new OutputStreamWriter(urlConn.getOutputStream());
Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", texte);
        fw.write(texte);
        fw.flush();
        
        Log.d("FAKEEEEE", String.valueOf(urlConn.getResponseCode()));
        Log.d("FAKEEEEE", urlConn.getResponseMessage());
        fw.close();
        urlConn.disconnect();
//               String result = "";
//        InputStream is = urlConn.getInputStream();
//       BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            is.close();
//
//            result = sb.toString();
//            Log.d("TEST",result);
        
            // le BufferedWriter output auquel on donne comme argument le FileWriter fw cree juste au dessus
//            BufferedWriter output = new BufferedWriter(fw);
Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "iuijhndfsdsd");
            //on marque dans le fichier ou plutot dans le BufferedWriter qui sert comme un tampon(stream)
//            output.write("fgfs");
            //on peut utiliser plusieurs fois methode write
Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "iuijhsddsdsdsddsn");
//            output.flush();
            //ensuite flush envoie dans le fichier, ne pas oublier cette methode pour le BufferedWriter

//            output.close();
            //et on le ferme
        } catch (Exception ioe) {
            Log.d("Erreur", ioe.getMessage());
        }

    }
    
    public static void sendData(String data) {

    HttpParams p=new BasicHttpParams();
    p.setParameter("name", data);

  //Instantiate an HttpClient
  HttpClient client = new DefaultHttpClient(p);

  //Instantiate a GET HTTP method
  try {
      Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "iuijhsddsdsdsddsn");
        HttpResponse response=client.execute(new HttpGet("http://10.0.2.2/smartphone/pieceDataManager/sendPiece.php"));
        Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "iuijhsddsdsdsddsn");
        InputStream is=response.getEntity().getContent();
        Log.d("ErreurRRRRRRRRRRRRRRRRRRRRRR", "iuijhsddsdsdsddsn");
        //You can convert inputstream to a string with: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
} catch (ClientProtocolException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
} catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
}

}
}
