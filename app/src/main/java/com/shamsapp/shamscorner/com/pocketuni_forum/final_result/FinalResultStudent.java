package com.shamsapp.shamscorner.com.pocketuni_forum.final_result;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.NetworkAvailability;
import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.attendance.SqlInfoCheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FinalResultStudent extends AppCompatActivity {

    private Button[] btn = new Button[8];
    private int[] btnId = {
            R.id.semester_1,
            R.id.semester_2,
            R.id.semester_3,
            R.id.semester_4,
            R.id.semester_5,
            R.id.semester_6,
            R.id.semester_7,
            R.id.semester_8
    };
    private Context context;
    public static final String LOGINPREF = "loginpref";
    private SharedPreferences sharedpreferences;
    private String username_text, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_student);

        context = this;

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        // initiate those buttons
        for(int i = 0; i < 8; i++){
            btn[i] = (Button)findViewById(btnId[i]);
            btn[i].setVisibility(View.INVISIBLE);
        }

        // make the button available if exist
        if(new NetworkAvailability(context).isNetworkAvailable()){
            new uploadToServerButton(btn).execute();
        }

        // make those button fires event
        for(int i = 0; i < 8; i++){
            final int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(new NetworkAvailability(context).isNetworkAvailable()){
                        new uploadToServer().execute(""+(finalI+1));
                    }
                }
            });
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
                semester = arg[0];
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/final_result_semester.php";
                String data = URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");
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
            //Log.d("ResultDetailFinal", result);
            Intent intent = new Intent(context, FinalResultStudentDetailsShow.class);
            intent.putExtra("ID", semester);
            intent.putExtra("RESULT", result);
            startActivity(intent);
            progressDialog.dismiss();
        }
    }

    private class uploadToServerButton extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        private Button[] btn;
        public uploadToServerButton(Button[] btn) {
            this.btn = btn;
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/get_semester.php";
                String data = URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(username_text, "UTF-8");

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
            int len = Integer.parseInt(result);

            for(int i = 0; i < len; i++){
                if(i == len-1){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn[i].setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_default));
                    }
                }
                btn[i].setVisibility(View.VISIBLE);
            }

            progressDialog.dismiss();
        }
    }
}
