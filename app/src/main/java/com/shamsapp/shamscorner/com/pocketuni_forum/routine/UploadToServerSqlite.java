package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager.DBHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 28-Jul-16.
 */
public class UploadToServerSqlite extends AsyncTask<String, Void, String> {

    private ProgressDialog progressDialog;
    private Context context;
    private String day;
    private static final String sql = "CREATE TABLE IF NOT EXISTS \"time_slot\" (\"time\" VARCHAR NOT NULL ,\"day\" VARCHAR NOT NULL " +
            ",\"course_id\" VARCHAR NOT NULL, \"title\" VARCHAR NOT NULL ,\"classroom_id\" " +
            "VARCHAR NOT NULL ,\"teacher\" VARCHAR NOT NULL )";
    private LinearLayout layoutTodayHolder;

    public UploadToServerSqlite(Context context, LinearLayout layoutTodayHolder, String day) {
        this.context = context;
        this.layoutTodayHolder = layoutTodayHolder;
        this.day = day;
    }

    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... arg) {
        try {
            String username = arg[0];
            String type = arg[1];
            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mUploadSqliteDb.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");

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
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();

        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return new String("Error: " + e.getMessage());
        }
    }

    protected void onPostExecute(String result) {
        String[] value = result.split("//");
        DBHelper dbHelper = new DBHelper(context, "core.db", "time_slot", sql);
        dbHelper.deleteAll();
        //Log.d("ValueCore", ""+dbHelper.numberOfRows());
        String[] name = {"time", "day", "course_id", "title", "classroom_id", "teacher"};

        int i = 0;
        int step = -1;
        while(i < value.length/6){
            dbHelper.insert(name, value[++step], value[++step], value[++step], value[++step], value[++step], value[++step]);
            i++;
        }
        //Log.d("ValueCore", ""+dbHelper.numberOfRows());
        LayoutInflater inflaterToday = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        new RoutineToday(context, inflaterToday, layoutTodayHolder, day);
        progressDialog.dismiss();
    }
}