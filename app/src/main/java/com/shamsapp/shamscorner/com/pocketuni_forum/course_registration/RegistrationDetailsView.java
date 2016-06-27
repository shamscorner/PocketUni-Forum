package com.shamsapp.shamscorner.com.pocketuni_forum.course_registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 05-Jun-16.
 */
public class RegistrationDetailsView extends AsyncTask<String, Void, String> {

    private TextView tvError;
    private Context context;
    public static final String LOGINPREF = "loginpref" ;
    String username, password;
    SharedPreferences sharedpreferences;
    ProgressDialog progressDialog;

    public RegistrationDetailsView(Context context, TextView tvError){
        this.context = context;
        this.tvError = tvError;
    }

    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... arg) {
        try{
            username = arg[0];
            password = arg[1];

            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mlogin.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null){
                sb.append(line);
                break;
            }
            return sb.toString();

        }catch(Exception e){
            Toast.makeText(context,"Error: " + e.getMessage() , Toast.LENGTH_LONG);
            return new String("Error: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){
        String[] value = result.split("//");

        progressDialog.dismiss();
    }
}
