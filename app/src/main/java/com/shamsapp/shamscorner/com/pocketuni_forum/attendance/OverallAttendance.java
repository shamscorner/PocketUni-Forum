package com.shamsapp.shamscorner.com.pocketuni_forum.attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.NetworkAvailability;
import com.shamsapp.shamscorner.com.pocketuni_forum.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class OverallAttendance extends AppCompatActivity {

    private String semester, courseId;
    private Context context;
    private LinearLayout mainContainer;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            semester = extras.getString("SEMESTER");
            courseId = extras.getString("COURSEID");
        }

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(OverallAttendance.this);

        mainContainer = (LinearLayout)findViewById(R.id.main_container);

        if(new NetworkAvailability(getApplicationContext()).isNetworkAvailable()){
            new uploadToServer().execute();
        }
    }

    private class uploadToServer extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        public uploadToServer() {
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mOverallAttenShowTab.php";
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
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            String[] value = result.split("//");
            int loop = value.length/2;
            int step = 0;

            for(int i = 0; i < loop; i++){
                View v = inflater.inflate(R.layout.atten_details_show_overall, null);
                TextView tvRoll, tvPercent;
                String roll, percent;
                roll = value[step];
                percent = value[++step];
                tvRoll = (TextView)v.findViewById(R.id.roll_no);
                tvPercent = (TextView)v.findViewById(R.id.percent);
                tvRoll.setText(roll);
                tvPercent.setText(percent+"%");
                LinearLayout holder = (LinearLayout)v.findViewById(R.id.present_mark_holder);
                new uploadToServerForEachRoll(holder).execute(roll);
                mainContainer.addView(v);
                step++;
            }

            progressDialog.dismiss();
        }
    }

    private class uploadToServerForEachRoll extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        private String rollNo;
        private LinearLayout holder;
        public uploadToServerForEachRoll(LinearLayout holder) {
            this.holder = holder;
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                rollNo = arg[0];
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mOverallAttenListForEachRoll.php";
                String data = URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseId, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");
                data += "&" + URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(rollNo, "UTF-8");

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
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            String[] value = result.split("//");

            for(int i = 0; i < value.length; i++){
                ImageView imageView = new ImageView(context);
                String atten = value[i];
                if(atten.equals("1")){
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
                }else if(atten.equals("0")){
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cross));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                imageView.setLayoutParams(params);
                holder.addView(imageView);
            }

            progressDialog.dismiss();
        }
    }

}
