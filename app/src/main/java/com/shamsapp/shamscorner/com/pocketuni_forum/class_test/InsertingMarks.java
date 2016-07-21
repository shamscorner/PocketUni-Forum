package com.shamsapp.shamscorner.com.pocketuni_forum.class_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SqlInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class InsertingMarks extends AppCompatActivity {

    private String semester, section, department, courseId, series, status;
    private LayoutInflater inflater;
    private LinearLayout mainContainer;
    private int noStudent, ctNo;
    private View v;
    private Button btnDone;
    private TextView tvError;
    private ArrayList<EditText> marksList = new ArrayList<>();
    private ArrayList<String> rollNOs = new ArrayList<>();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        btnDone = (Button) findViewById(R.id.btn_insert_done);
        tvError = (TextView) findViewById(R.id.tvErrorCT);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            semester = extras.getString("SEMESTER");
            section = extras.getString("SECTION");
            department = extras.getString("DEPARTMENT");
            courseId = extras.getString("COURSEID");
            ctNo = Integer.parseInt(extras.getString("CTNO"));
            series = extras.getString("SERIES");
            status = extras.getString("STATUS");
        }

        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(InsertingMarks.this);
        mainContainer = (LinearLayout) findViewById(R.id.main_container);

        new uploadToServer().execute();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide the keyboard
                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < noStudent; i++) {
                    new CTMarksInserted(context, i, noStudent, tvError, status).execute("" + ctNo, rollNOs.get(i), courseId, series, semester, section, department, marksList.get(i).getText().toString().trim());
                }
            }
        });
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
            noStudent = value.length;

            for (int i = 0; i < noStudent; i++) {
                v = inflater.inflate(R.layout.marks_input, null);
                TextView textView = (TextView) v.findViewById(R.id.tv_roll);
                textView.setText(value[i]);
                rollNOs.add(value[i]);
                EditText editText = (EditText) v.findViewById(R.id.edt_marks);
                if(status.equals("edit")){
                    new SqlInfo(context, editText, "").execute(section, "marks", "ct_marks",
                            "ct_no = "+ctNo+" and roll_no = '"+value[i]+"' and course_id = '"+courseId+"' and semester = "+semester+" and section");
                }
                marksList.add(editText);
                mainContainer.addView(v);
            }
            progressDialog.dismiss();
        }
    }
}