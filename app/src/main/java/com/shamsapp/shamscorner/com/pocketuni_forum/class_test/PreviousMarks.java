package com.shamsapp.shamscorner.com.pocketuni_forum.class_test;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class PreviousMarks extends AppCompatActivity {

    private LayoutInflater inflater;
    private LinearLayout mainContainer;

    private String semester, section, department, courseId, series, ctNo;
    private int noStudent;

    private View v;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_marks);
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

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(PreviousMarks.this);
        mainContainer = (LinearLayout)findViewById(R.id.main_container);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            semester = extras.getString("SEMESTER");
            section = extras.getString("SECTION");
            department = extras.getString("DEPARTMENT");
            courseId = extras.getString("COURSEID");
            ctNo = extras.getString("CTNO");
            series = extras.getString("SERIES");
        }

        new uploadToServer().execute();
    }

    public void marksEdit(View view) {
        //update all the marks
        Intent intent = new Intent(getApplicationContext(), InsertingMarks.class);
        intent.putExtra("STATUS", "edit");
        intent.putExtra("CTNO", ctNo);
        intent.putExtra("SEMESTER", semester);
        intent.putExtra("SECTION", section);
        intent.putExtra("COURSEID", courseId);
        intent.putExtra("DEPARTMENT", department);
        intent.putExtra("SERIES", series);
        startActivity(intent);
    }

    public void marksCancel(View view) {
        //cancel all the marks
    }

    public class uploadToServer extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        public uploadToServer(){
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try{
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mHandleCTShow.php";
                String data = URLEncoder.encode("series", "UTF-8") + "=" + URLEncoder.encode(series, "UTF-8");
                data += "&" + URLEncoder.encode("dept_name", "UTF-8") + "=" + URLEncoder.encode(department, "UTF-8");
                data += "&" + URLEncoder.encode("semester_id", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");
                data += "&" + URLEncoder.encode("section", "UTF-8") + "=" + URLEncoder.encode(section, "UTF-8");
                data += "&" + URLEncoder.encode("ct_no", "UTF-8") + "=" + URLEncoder.encode(ctNo, "UTF-8");
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
                while((line = reader.readLine()) != null){
                    sb.append(line);
                    break;
                }
                return sb.toString();

            }catch(Exception e){
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            String[] value = result.split("//");
            Log.d("ValueResult", result);
            noStudent = value.length/2;
            int step = 0;

            for(int i = 0; i < noStudent; i++){
                v = inflater.inflate(R.layout.marks_show, null);
                TextView textView = (TextView)v.findViewById(R.id.tv_roll);
                textView.setText(value[step]);
                //editTexts[i] = (EditText)v.findViewById(R.id.edt_marks);
                TextView textView_marks = (TextView)v.findViewById(R.id.tv_marks);
                textView_marks.setText(value[++step]);
                step++;
                mainContainer.addView(v);
            }
            progressDialog.dismiss();
        }
    }
}
