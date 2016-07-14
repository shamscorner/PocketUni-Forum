package com.shamsapp.shamscorner.com.pocketuni_forum;

/**
 * Created by shamim on 12-Jun-16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SqlInfo extends AsyncTask<String, Void, String> {

    private Context context;
    private String extra;
    private TextView tvText;

    public SqlInfo(Context context, TextView tvText, String extra){
        this.context = context;
        this.tvText = tvText;
        this.extra = extra;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg) {
        try{
            String username = arg[0];
            String select = arg[1];
            String table = arg[2];
            String where = arg[3];
            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mInfoSql.php";
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
            Toast.makeText(context,"Error: " + e.getMessage() , Toast.LENGTH_LONG).show();
            return new String("Error: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){
        tvText.setText(extra + result);
        Log.d("Error - ", result);
    }
}
