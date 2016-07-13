package com.shamsapp.shamscorner.com.pocketuni_forum.contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
public class ContactStudentSection extends Fragment {

    String type;
    LinearLayout mainContainer;

    public ContactStudentSection() {
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
        new handleServer().execute("student");

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

            int count = 0, loop = 0;
            while(loop < (value.length/3)){
                // inflate a xml file in a non activity class
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.contact_details, null);

                TextView name = (TextView)layout.findViewById(R.id.contact_name);
                TextView deptName = (TextView)layout.findViewById(R.id.contact_dept);
                TextView extra = (TextView)layout.findViewById(R.id.contact_rank);

                name.setText(""+value[count]);
                count++;
                deptName.setText(""+value[count]);
                count++;
                extra.setText(""+value[count]);
                count++;

                mainContainer.addView(layout);
                loop++;
            }

            pd.hide();
            pd.dismiss();
        }
    }
}
