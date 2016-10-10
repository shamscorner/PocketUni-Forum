package com.shamsapp.shamscorner.com.pocketuni_forum.input_other_results;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SpinnerItemAddition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 10-Oct-16.
 */
public class Result extends AppCompatActivity {
    private String type = null;
    private Spinner semester, courseId;
    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;
    private String username_text, semesterText, courseIdText;
    private LayoutInflater inflater;
    private Context context;
    private View v, viewDialog;
    private LinearLayout mainContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_result_input);
        context = this;

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            type = extras.getString("TYPE");
        }

        semester = (Spinner)findViewById(R.id.sp_semester);
        courseId = (Spinner)findViewById(R.id.sp_course_id);

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(Result.this);

        viewDialog = inflater.inflate(R.layout.ct_marks_dialog_show, null);
        mainContainer = (LinearLayout)viewDialog.findViewById(R.id.container_holder_ct_show);

        new SpinnerItemAddition(this, semester).execute(username_text, "distinct semester", "course_takes", "roll_no");
        new SpinnerItemAddition(this, courseId).execute(username_text, "distinct course_id", "course_takes", "roll_no");
    }

    public void ctGetStartedStudent(View view) {
        semesterText = semester.getSelectedItem().toString();
        courseIdText = courseId.getSelectedItem().toString();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(viewDialog)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

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
                // this link has to change
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mCtMarkShowDialog.php";
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semesterText, "UTF-8");
                data += "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseIdText, "UTF-8");
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
            int count = value.length/2;
            int step = 0;

            for(int i = 0; i < count; i++){
                v = inflater.inflate(R.layout.ct_show_student_details, null);
                TextView textView = (TextView)v.findViewById(R.id.tv_ct_no);
                textView.setText(value[step]);
                TextView textView_marks = (TextView)v.findViewById(R.id.tv_ct_mark);
                textView_marks.setText(value[++step]);
                step++;
                mainContainer.addView(v);
            }
            progressDialog.dismiss();
        }
    }
}
/*

select ct_no, marks from ct_marks where roll_no = '123086' and semester = 1 and course_id = 'CSE 100'

 */