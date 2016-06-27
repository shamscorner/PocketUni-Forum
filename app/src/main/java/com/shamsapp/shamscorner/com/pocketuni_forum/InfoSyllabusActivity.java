package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by shamim on 12-Jun-16.
 */
public class InfoSyllabusActivity extends AsyncTask<String, Void, String> {
    private String courseId, title, credit;
    private Context context;
    private TextView tvTheory, tvSessional;
    private WebView tvSyllabus;
    ProgressDialog progressDialog;

    public InfoSyllabusActivity(Context context, WebView tvSyllabus, TextView... views){
        this.context = context;
        this.tvSyllabus = tvSyllabus;
        this.tvTheory = views[0];
        this.tvSessional = views[1];
    }

    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }
    @Override
    protected String doInBackground(String... arg) {
        try{
            courseId = arg[0];
            title = arg[1];
            credit = arg[2];
            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mSyllabus.php";

            String data = URLEncoder.encode("course_id", "UTF-8") + "=" + URLEncoder.encode(courseId, "UTF-8");
            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
            data += "&" + URLEncoder.encode("credit", "UTF-8") + "=" + URLEncoder.encode(credit, "UTF-8");

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
    protected void onPostExecute(String result) {
        final String[] value = result.split("//");
        //set all the text of the views
        tvTheory.setText(value[0]);
        tvSessional.setText(value[1]);
        //now work with the webview
        String htmlText = " %s ";
        tvSyllabus.loadData(String.format(htmlText, "<html>" + " <head></head>"
                + " <body style=\"text-align:justify;color:#000000;background:#e75d5d;font-size: 18px;font-family: 'Lucida Sans Unicode', 'Lucida Grande', sans-serif;\">"
                +value[2]+"</body></html>"), "text/html", "utf-8");

        progressDialog.dismiss();
    }
}
