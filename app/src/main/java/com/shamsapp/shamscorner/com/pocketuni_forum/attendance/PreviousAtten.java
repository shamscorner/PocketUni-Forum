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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.class_test.InsertingMarks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class PreviousAtten extends AppCompatActivity {

    private LayoutInflater inflater;
    private LinearLayout mainContainer;

    private String semester, section, department, courseId, series, day;
    private int noStudent, cycle;

    private View v;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_atten);
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
        inflater = LayoutInflater.from(PreviousAtten.this);
        mainContainer = (LinearLayout)findViewById(R.id.main_container);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            semester = extras.getString("SEMESTER");
            section = extras.getString("SECTION");
            department = extras.getString("DEPARTMENT");
            courseId = extras.getString("COURSEID");
            series = extras.getString("SERIES");
            day = extras.getString("DAY");
            cycle = extras.getInt("CYCLE");
        }

        new uploadToServer().execute();
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
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mHandlePercentageShow.php";
                String data = URLEncoder.encode("cycle", "UTF-8") + "=" + URLEncoder.encode(""+cycle, "UTF-8");
                data += "&" + URLEncoder.encode("semester_id", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");
                data += "&" + URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8");
                data += "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseId, "UTF-8");
                data += "&" + URLEncoder.encode("section", "UTF-8") + "=" + URLEncoder.encode(section, "UTF-8");

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
            noStudent = value.length/2;
            int step = 0;

            for(int i = 0; i < noStudent; i++){
                v = inflater.inflate(R.layout.atten_input, null);
                TextView textView = (TextView)v.findViewById(R.id.tv_roll);
                textView.setText(value[step]);
                //editTexts[i] = (EditText)v.findViewById(R.id.edt_marks);
                CheckBox checkBox = (CheckBox)v.findViewById(R.id.check_atten);
                step++;
                if(value[step].equals("1")){
                    checkBox.setChecked(true);
                }else if(value[step].equals("0")){
                    checkBox.setChecked(false);
                }
                checkBox.setClickable(false);
                step++;
                mainContainer.addView(v);
            }
            progressDialog.dismiss();
        }
    }

    public void marksEdit(View view) {
        //update all the marks
        Intent intent = new Intent(getApplicationContext(), InsertingAtten.class);
        intent.putExtra("STATUS", "edit");
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
}
