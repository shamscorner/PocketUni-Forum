package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class InsertingMarks extends AppCompatActivity {

    private String semester, section, department, courseId, username, series;
    private LayoutInflater inflater;
    private LinearLayout mainContainer;
    private int noStudent, ctNo, from = 1, to;
    private View v;
    private Button btnDone;
    private TextView tvError;
    //private EditText[] editTexts = new EditText[200];
    private ArrayList<EditText> marksList = new ArrayList<>();
    private ArrayList<String> rollNOs = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnDone = (Button)findViewById(R.id.btn_insert_done);
        tvError = (TextView)findViewById(R.id.tvErrorCT);

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
            username = extras.getString("ROLLNO");
            semester = extras.getString("SEMESTER");
            section = extras.getString("SECTION");
            department = extras.getString("DEPARTMENT");
            courseId = extras.getString("COURSEID");
            ctNo = Integer.parseInt(extras.getString("CTNO"));
            series = extras.getString("SERIES");
        }

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(InsertingMarks.this);
        mainContainer = (LinearLayout)findViewById(R.id.main_container);

        /*
        if(semester.equals("a")){
            from = 1;
        }else if(semester.equals("b")){
            from = 1 + (student-1);
        }else if(semester.equals("c")){
            from = 1 + (student*2 - 1);
        }
        to = from + student;
        */

        new uploadToServer().execute();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < noStudent; i++){
                    //Log.d("val",(marksList.get(i)).getText().toString().trim());
                    new CTMarksInserted(getApplicationContext(), tvError).execute(""+ctNo, rollNOs.get(i), courseId, series, semester, section, department, marksList.get(i).getText().toString().trim());
                }
            }
        });
    }


    public class uploadToServer extends AsyncTask<String, Void, String> {

        public uploadToServer(){}

        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(getApplicationContext(), "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try{
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mHandleCTMarks.php";
                String data = URLEncoder.encode("series", "UTF-8") + "=" + URLEncoder.encode(series, "UTF-8");
                data += "&" + URLEncoder.encode("dept_name", "UTF-8") + "=" + URLEncoder.encode(department, "UTF-8");
                data += "&" + URLEncoder.encode("semester_id", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");
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
                Toast.makeText(getApplicationContext(),"Error: " + e.getMessage() , Toast.LENGTH_LONG);
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            String[] value = result.split("//");
            Log.d("marks_value", result);
            noStudent = value.length;

            for(int i = 0; i < noStudent; i++){
                v = inflater.inflate(R.layout.marks_input, null);
                TextView textView = (TextView)v.findViewById(R.id.tv_roll);
                textView.setText(value[i]);
                rollNOs.add(value[i]);
                //editTexts[i] = (EditText)v.findViewById(R.id.edt_marks);
                marksList.add((EditText)v.findViewById(R.id.edt_marks));
                mainContainer.addView(v);
            }
            //progressDialog.dismiss();
        }
    }

}
