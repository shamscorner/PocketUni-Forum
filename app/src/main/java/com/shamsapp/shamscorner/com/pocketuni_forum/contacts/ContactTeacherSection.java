package com.shamsapp.shamscorner.com.pocketuni_forum.contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SqlInfoData;
import com.shamsapp.shamscorner.com.pocketuni_forum.SuccessActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 13-Jul-16.
 */
public class ContactTeacherSection extends Fragment {

    String type;
    LinearLayout mainContainer;

    public ContactTeacherSection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View android =  inflater.inflate(R.layout.contact_student, container, false);

        mainContainer = (LinearLayout)android.findViewById(R.id.contact_holder);
        new handleServer().execute("teacher");

        return android;
    }

    public class handleServer extends AsyncTask<String, Void, String> {

        private ProgressDialog pd = new ProgressDialog(getContext());

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            try{
                type = arg[0];

                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mHandleContact.php";
                String data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");

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
                Toast.makeText(getContext(),"Error: " + e.getMessage() , Toast.LENGTH_LONG);
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            final String[] value = result.split("//");
            LinearLayout[] th = new LinearLayout[value.length/4+1];

            int count = 0, loop = 0;
            String email, phone, username;
            while(loop < (value.length/6)){
                // inflate a xml file in a non activity class
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.contact_details, null);

                TextView name = (TextView)layout.findViewById(R.id.contact_name);
                TextView deptName = (TextView)layout.findViewById(R.id.contact_dept);
                TextView extra = (TextView)layout.findViewById(R.id.contact_rank);
                th[loop] = (LinearLayout)layout.findViewById(R.id.details_touch);
                Button btnSend = (Button)layout.findViewById(R.id.btn_contact_email);
                Button btnCall = (Button)layout.findViewById(R.id.btn_contact_call);

                name.setText(""+value[count]);
                count++;
                deptName.setText(""+value[count]);
                count++;
                extra.setText(""+value[count]);
                count++;
                username = value[count];
                count++;
                email = value[count];
                count++;
                phone = value[count];
                count++;

                mainContainer.addView(layout);

                final String finalUsername = username;
                th[loop].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ContactDetailsView.class);
                        intent.putExtra("USERNAME", ""+ finalUsername);
                        startActivity(intent);
                    }
                });

                //handle the send email
                final String finalEmail = email;
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] TO = {""};
                        String[] CC = {""};

                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",""+ finalEmail, null));
                        intent.putExtra(Intent.EXTRA_EMAIL, TO);
                        intent.putExtra(Intent.EXTRA_CC, CC);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject...");
                        intent.putExtra(Intent.EXTRA_TEXT, "Your Message...");

                        try{
                            startActivity(Intent.createChooser(intent, "Email via:"));
                        }catch (Exception e){
                            Toast.makeText(getContext(), "There is no email client installed", Toast.LENGTH_SHORT);
                        }
                    }
                });

                //handle the call
                final String finalPhone = phone;
                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_CALL);
                        in.setData(Uri.parse("tel:" + finalPhone));
                        try{
                            startActivity(in);
                        }catch(Exception e){
                            Toast.makeText(getContext(), "Call can not taken", Toast.LENGTH_SHORT);
                        }
                    }
                });

                loop++;
            }

            pd.hide();
            pd.dismiss();
        }
    }
}
