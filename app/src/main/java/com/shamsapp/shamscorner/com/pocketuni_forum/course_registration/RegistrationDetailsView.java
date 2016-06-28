package com.shamsapp.shamscorner.com.pocketuni_forum.course_registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 05-Jun-16.
 */
public class RegistrationDetailsView extends AsyncTask<String, Void, String> {

    private Context context;
    String rollNo;
    ProgressDialog progressDialog;
    LinearLayout regMainContainer;

    public RegistrationDetailsView(Context context, LinearLayout regMainContainer){
        this.context = context;
        this.regMainContainer = regMainContainer;
    }

    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    @Override
    protected String doInBackground(String... arg) {
        try{
            rollNo = arg[0];

            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mCourse_reg_details.php";
            String data = URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(rollNo, "UTF-8");

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
        String[] value = result.split("//");
        Log.d("Length", ""+value.length);

        int count = 0, loop = 0;
        while(loop < (value.length/3)){
            // inflate a xml file in a non activity class
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.registration_details, null);

            TextView courseId = (TextView)layout.findViewById(R.id.tv_course_id);
            TextView title = (TextView)layout.findViewById(R.id.tv_course_title);
            TextView credit = (TextView)layout.findViewById(R.id.tv_course_credit);

            courseId.setText(""+value[count]);
            count++;
            title.setText(""+value[count]);
            count++;
            credit.setText(""+value[count]);
            count++;

            regMainContainer.addView(layout);
            loop++;
        }

        progressDialog.dismiss();
    }
}
