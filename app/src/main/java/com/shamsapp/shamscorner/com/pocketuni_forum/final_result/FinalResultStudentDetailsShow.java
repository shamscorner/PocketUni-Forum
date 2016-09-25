package com.shamsapp.shamscorner.com.pocketuni_forum.final_result;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FinalResultStudentDetailsShow extends AppCompatActivity {

    private int semesterID;
    private Context context;
    public static final String LOGINPREF = "loginpref";
    private SharedPreferences sharedpreferences;
    private String username_text, result_text;
    private String[] value;
    private LinearLayout mainContainer;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_student_details_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            semesterID = extras.getInt("ID");
            result_text = extras.getString("RESULT");
        }

        // make text for the snakbar to show
        value = result_text.split("//");
        result_text = "Credit Registered : " + value[0] + "\nCredit Earned : "+ value[1]
                +"\nCumulative Earned Credit : "+ value[2] +"\nGPA : "+ value[3] +"\nCGPA : "+ value[4] +"";

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // this result will something like this
                    //Credit Registered : 20.25 Credit" + " Earned : 20.25 Cumulative Earned Credit : 83.25 GPA : 3.75 CGPA : 3.56
                    final Snackbar snackbar = Snackbar.make(view, result_text, Snackbar.LENGTH_INDEFINITE);
                    // floating action button-does-not-come-down-when-dismissing-snackbar-solved
                    snackbar.getView().addOnAttachStateChangeListener( new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow( View v ) {

                        }

                        @Override
                        public void onViewDetachedFromWindow( View v ) {
                            fab.setTranslationY( 0 );
                        }
                    });
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // hide and dismiss the snack bar
                            snackbar.dismiss();
                        }
                    });
                    // make the snack bar multiline
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5); // set the line because snackbar default support only 2 lines
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.bk_main)); // set the text color
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.off_white)); // set the background color of snackbar view
                    snackbar.show();
                }
            });
        }

        TextView tvSemsterId = (TextView) findViewById(R.id.tv_semester_result_final);
        if (tvSemsterId != null) {
            tvSemsterId.setText("Result For Semester - " + semesterID+1);
        }

        mainContainer = (LinearLayout) findViewById(R.id.main_container);

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(FinalResultStudentDetailsShow.this);

        if(new NetworkAvailability(context).isNetworkAvailable()){
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
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/final_result_details_view.php";
                String data = URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");
                data += "&" + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(""+semesterID, "UTF-8");

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
            //Log.d("ResultDetailFinal", result);

            String[] value_detail = result.split("//");
            int loop = value_detail.length / 4;
            int step = 0;

            for(int i = 0; i < loop; i++) {
                View v = inflater.inflate(R.layout.final_result_show_for_student, null);
                TextView courseID = (TextView) v.findViewById(R.id.course_id);
                TextView title = (TextView) v.findViewById(R.id.course_title);
                TextView credit = (TextView) v.findViewById(R.id.tv_course_credit);
                TextView grade = (TextView) v.findViewById(R.id.course_grade);

                courseID.setText(value_detail[step]);
                step++;
                title.setText(value_detail[step]);
                step++;
                credit.setText(value_detail[step]);
                step++;
                grade.setText(value_detail[step]);
                step++;

                mainContainer.addView(v);

                // make a separator
                View view = new View(context);
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.bk_for));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                //params.setMargins(0, 2, 0, 2);
                view.setLayoutParams(params);
                mainContainer.addView(view);
            }

            // finally set the footer
            TextView footer = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(25, 40, 25, 40);
            footer.setLayoutParams(params);
            footer.setText(result_text);
            footer.setTextColor(Color.BLACK);
            footer.setTextSize(16);
            footer.setGravity(Gravity.CENTER);
            mainContainer.addView(footer);

            progressDialog.dismiss();
        }
    }
}
