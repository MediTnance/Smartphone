/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 *
 * @author jeremie
 */
public class MessageBox extends Activity {

    public void Show(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()   {

            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });
        alertDialog.show();
    }
}
