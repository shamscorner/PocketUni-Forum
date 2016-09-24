package com.shamsapp.shamscorner.com.pocketuni_forum.attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SpinnerItemAddition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class StudentAtten extends AppCompatActivity {

    private String type = null;
    private Spinner semester, courseId;
    public static final String LOGINPREF = "loginpref";
    SharedPreferences sharedpreferences;
    private String username_text, semesterText, courseIdText;
    private LayoutInflater inflater;
    private Context context;
    private View v, viewDialog;
    private LinearLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_result_input);

        context = this;

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        semester = (Spinner) findViewById(R.id.sp_semester);
        courseId = (Spinner) findViewById(R.id.sp_course_id);

        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(StudentAtten.this);

        viewDialog = inflater.inflate(R.layout.ct_marks_dialog_show, null);

        // set those text for this dialog
        TextView tvTitle, tvLeft, tvRight;
        tvTitle = (TextView)viewDialog.findViewById(R.id.tv_title_dialog);
        tvTitle.setText("Result : Attendance");
        tvLeft = (TextView)viewDialog.findViewById(R.id.tv_left_title);
        tvLeft.setText("Cycle-Day");
        tvRight = (TextView)viewDialog.findViewById(R.id.tv_right_title);
        tvRight.setText("Status");

        mainContainer = (LinearLayout) viewDialog.findViewById(R.id.container_holder_ct_show);

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
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mAttenMarkShowDialog.php";
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semesterText, "UTF-8");
                data += "&" + URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseIdText, "UTF-8");

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
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            View v = inflater.inflate(R.layout.atten_student_result_panel_main, null);
            TextView tvRoll, tvPercent;
            String percent;
            percent = result;
            tvRoll = (TextView) v.findViewById(R.id.roll_no);
            tvPercent = (TextView) v.findViewById(R.id.percent);
            tvRoll.setText(username_text);
            tvPercent.setText(percent + "%");
            LinearLayout holder = (LinearLayout) v.findViewById(R.id.present_mark_holder);
            new uploadToServerForThisRoll(holder).execute();
            mainContainer.addView(v);
            progressDialog.dismiss();
        }
    }

    private class uploadToServerForThisRoll extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        private LinearLayout holder;

        public uploadToServerForThisRoll(LinearLayout holder) {
            this.holder = holder;
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mAttenListForEachRollStudent.php";
                String data = URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseIdText, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semesterText, "UTF-8");
                data += "&" + URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");

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
            int loop = value.length / 2;
            int step = 0;
            for (int i = 0; i < loop; i++) {
                View v = inflater.inflate(R.layout.atten_show_for_student, null);

                TextView tv_day = (TextView)v.findViewById(R.id.day_cycle);
                ImageView img_check = (ImageView)v.findViewById(R.id.check_atten);

                tv_day.setText(value[step]);
                step++;
                String atten = value[step];
                if (atten.equals("1")) {
                    img_check.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
                } else if (atten.equals("0")) {
                    img_check.setImageDrawable(context.getResources().getDrawable(R.drawable.cross));
                }
                holder.addView(v);
                step++;
            }

            progressDialog.dismiss();
        }
    }
}
