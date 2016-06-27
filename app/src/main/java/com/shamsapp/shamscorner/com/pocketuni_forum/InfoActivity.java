package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 05-Jun-16.
 */
public class InfoActivity extends AsyncTask<String, Void, String> {

    private Context context;
    String username;
    TextView email, phone, gender, nationality, fathersName, mothersName, presentAddr, parmanentAddr, regSession, rank,
            dateOfBirth, joiningDate;
    TextView name, tvusername, semester, section, department;
    ImageView profilePicture;
    Button btnSend, btnCall;
    ProgressDialog progressDialog;

    public InfoActivity(Context context, ImageView imgView, Button btnSend, Button btnCall, TextView... views){
        this.context = context;
        email = views[0];
        phone = views[1];
        gender = views[2];
        nationality = views[3];
        fathersName = views[4];
        mothersName = views[5];
        presentAddr = views[6];
        parmanentAddr = views[7];
        regSession = views[8];
        rank = views[9];
        dateOfBirth = views[10];
        joiningDate = views[11];
        name = views[12];
        semester = views[13];
        tvusername = views[14];
        section = views[15];
        department = views[16];
        profilePicture = imgView;
        this.btnSend = btnSend;
        this.btnCall = btnCall;
    }

    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... arg) {
        try{
            username = arg[0];

            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mInfo.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

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
            Toast.makeText(context,"Error: " + e.getMessage() , Toast.LENGTH_LONG);
            return new String("Error: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){
        final String[] value = result.split("//");

        tvusername.setText(username);
        String fullname = value[1] + " " + value[2];
        name.setText(fullname);
        gender.setText(value[3]);
        dateOfBirth.setText(value[4]);
        nationality.setText(value[5]);
        email.setText(value[6]);
        phone.setText(value[7]);
        fathersName.setText(value[8]);
        mothersName.setText(value[9]);
        joiningDate.setText(value[12]);
        presentAddr.setText(value[17]+", "+value[18]+", "+value[19]+", "+value[20]);
        parmanentAddr.setText(value[13]+", "+value[14]+", "+value[15]+", "+value[16]);
        if(value[11].equals("student")){
            regSession.setText(value[21]+ "/" + value[22]);
            semester.setText(getYear(Integer.parseInt(value[24])) +" "+ getSemesterPost(Integer.parseInt(value[24])));
            section.setText("Section - "+(value[25]).toUpperCase());
        }else if(value[11].equals("teacher")){
            regSession.setVisibility(View.INVISIBLE);
            semester.setVisibility(View.INVISIBLE);
            section.setVisibility(View.INVISIBLE);
        }
        if(value[11].equals("student")){
            rank.setVisibility(View.INVISIBLE);
        }else if(value[11].equals("teacher")){
            rank.setText(value[26]);
        }
        if(value[11].equals("student")){
            department.setText(value[23]);
        }else if(value[11].equals("teacher")){
            department.setText(value[27]);
        }
        new LoadImageFromUrl(profilePicture).execute("http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/"+value[10]);

        //handle the send email
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {""};
                String[] CC = {""};
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",value[6], null));
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.putExtra(Intent.EXTRA_CC, CC);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject...");
                intent.putExtra(Intent.EXTRA_TEXT, "Your Message...");

                try{
                    context.startActivity(Intent.createChooser(intent, "Email via:"));
                }catch (Exception e){
                    Toast.makeText(context, "There is no email client installed", Toast.LENGTH_SHORT);
                }
            }
        });

        //handle the call
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_CALL);
                in.setData(Uri.parse("tel:" +value[7]));
                try{
                    context.startActivity(in);
                }catch(Exception e){
                    Toast.makeText(context, "Call can not taken", Toast.LENGTH_SHORT);
                }
            }
        });
        progressDialog.dismiss();
    }
    private String getSemesterPost(int id){
        switch (id){
            case 1:
                return "1st semester";
            case 2:
                return "2nd semester";
            case 3:
                return "3rd semester";
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return id+"th semester";
        }
        return null;
    }
    private String getYear(int id){
        switch (id){
            case 1:
            case 2:
                return "1st year";
            case 3:
            case 4:
                return "2nd year";
            case 5:
            case 6:
                return "3rd year";
            case 7:
            case 8:
                return "4rth year";
        }
        return null;
    }
}
