package com.shamsapp.shamscorner.com.pocketuni_forum.attendance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.class_test.PreviousMarks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 21-Sep-16.
 */
public class AttenInserted extends AsyncTask<String, Void, String> {

    private String rollNo, courseId, semester, atten, status, series, section, department;
    private ProgressDialog progressDialog;
    private Context context;
    private int finalValue, noStudent;
    private TextView tvError;

    AttenInserted(Context context, int finalValue, int noStudent, TextView tvError, String status){
        this.context = context;
        this.finalValue = finalValue;
        this.noStudent = noStudent;
        this.tvError = tvError;
        this.status = status;
    }

    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... arg) {
        try{
            rollNo = arg[0];
            courseId = arg[1];
            semester = arg[2];
            atten = arg[3];
            series = arg[4];
            section = arg[5];
            department = arg[6];

            // this link has to change for the attendance
            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mct_marks.php";
            String data = URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(rollNo, "UTF-8");
            data += "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseId, "UTF-8");
            data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");
            data += "&" + URLEncoder.encode("atten", "UTF-8") + "=" + URLEncoder.encode(atten, "UTF-8");
            data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");

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
            return new String("Error: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){
        tvError.setText(result);
        if(finalValue == noStudent-1){
            //send a push notification here...
            new uploadToServer().execute();

            Intent intent = new Intent(context, PreviousAtten.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("SEMESTER", semester);
            intent.putExtra("SECTION", section);
            intent.putExtra("COURSEID", courseId);
            intent.putExtra("DEPARTMENT", department);
            intent.putExtra("SERIES", series);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
        progressDialog.dismiss();
    }
    public class uploadToServer extends AsyncTask<String, Void, String> {
        public uploadToServer() {
        }

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                // this link has to change for the attendance
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/sendNotificationUserCt.php";
                String data = URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseId, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");

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
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}
