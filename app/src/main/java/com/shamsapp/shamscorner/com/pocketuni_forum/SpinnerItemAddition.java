package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamim on 15-Jun-16.
 */
public class SpinnerItemAddition extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    Context context;
    Spinner spinner;

    public SpinnerItemAddition(Context context, Spinner spinner){
        this.context = context;
        this.spinner = spinner;
    }

    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... arg) {
        try{
            String username = arg[0];
            String select = arg[1];
            String table = arg[2];
            String where = arg[3];

            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mspinner.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("select", "UTF-8") + "=" + URLEncoder.encode(select, "UTF-8");
            data += "&" + URLEncoder.encode("table", "UTF-8") + "=" + URLEncoder.encode(table, "UTF-8");
            data += "&" + URLEncoder.encode("where", "UTF-8") + "=" + URLEncoder.encode(where, "UTF-8");

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
        //the main work will be done in here
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < value.length; i++){
            list.add(""+value[i]);
        }

        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(semesterAdapter);

        progressDialog.dismiss();
    }
}
