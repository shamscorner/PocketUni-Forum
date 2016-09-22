package com.shamsapp.shamscorner.com.pocketuni_forum.attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
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

/**
 * Created by shamim on 22-Sep-16.
 */
public class PreviousAttenShowTab extends AppCompatActivity {
    private LinearLayout mainContainer;
    private Context context;

    public static final String LOGINPREF = "loginpref" ;
    private SharedPreferences sharedpreferences;

    private String username_text = null;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_marks_show_tab);

        context = this;

        sharedpreferences = getApplicationContext().getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(PreviousAttenShowTab.this);

        mainContainer = (LinearLayout)findViewById(R.id.mark_show_holder);

        if(new NetworkAvailability(getApplicationContext()).isNetworkAvailable()){
            new uploadToServer().execute();
        }
    }

    public class uploadToServer extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        public uploadToServer() {
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mSelectCtInfoForShowTab.php";
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");

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
            //Log.d("Found", result);
            int loop = value.length/5;
            int step = 0;
            for(int i = 0; i<loop; i++){
                View v = inflater.inflate(R.layout.activity_previous_mark_show_details, null);
                String ctNo, courseId;

                TextView ctNO = (TextView)v.findViewById(R.id.ct_no);
                TextView course_id = (TextView)v.findViewById(R.id.course_id);
                TextView title = (TextView)v.findViewById(R.id.course_title);
                TextView credit = (TextView)v.findViewById(R.id.credit);
                TextView date = (TextView)v.findViewById(R.id.date_time);
                LinearLayout touchingContent = (LinearLayout)v.findViewById(R.id.touchingContent);

                ctNO.setText(value[step]);
                ctNo = value[step];
                step++;
                course_id.setText(value[step]);
                courseId = value[step];
                step++;
                title.setText(value[step]);
                step++;
                credit.setText(value[step]);
                step++;
                date.setText(value[step]);
                step++;

                mainContainer.addView(v);

                final String finalCtNo = ctNo;
                final String finalCourseId = courseId;

                touchingContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new uploadToServerInfo().execute(finalCtNo, finalCourseId);
                        progressDialog.dismiss();
                    }
                });
            }

            progressDialog.dismiss();
        }
    }

    public class uploadToServerInfo extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        private String ctNO, courseId;

        public uploadToServerInfo() {
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                ctNO = arg[0];
                courseId = arg[1];
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mSelectInfoForPreview.php";
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");
                data += "&" + URLEncoder.encode("ct_no", "UTF-8") + "=" + URLEncoder.encode(ctNO, "UTF-8");
                data += "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseId, "UTF-8");

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

            Intent intent = new Intent(getApplicationContext(), PreviousAtten.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("CTNO", ctNO);
            intent.putExtra("SEMESTER", value[0]);
            intent.putExtra("SECTION", value[1]);
            intent.putExtra("COURSEID", courseId);
            intent.putExtra("DEPARTMENT", value[2]);
            intent.putExtra("SERIES", value[3]);
            context.startActivity(intent);
            progressDialog.dismiss();
        }
    }
}
