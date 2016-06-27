package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shamim on 11-Jun-16.
 */
public class InfoCourseActivityTeacher extends AsyncTask<String, Void, String> {
    private Context context;
    private String username, semester;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    ProgressDialog progressDialog;

    public InfoCourseActivityTeacher(Context context, ExpandableListView expandableListView, ExpandableListAdapter listAdapter, List<String> listDataHeader, HashMap<String, List<String>> listDataChild){
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
        this.expandableListView = expandableListView;
        this.listAdapter = listAdapter;
    }
    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }
    @Override
    protected String doInBackground(String... arg) {
        try{
            username = arg[0];
            semester = arg[1];
            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mcourse_te.php";

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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

        List<String> semester_list = new ArrayList<>();
        int i = 0, step = 0;
        int loop = (value.length)/3;
        while(i<loop){
            semester_list.add(value[step] + " - " + value[step+1] +" - "+value[step+2]);
            step+=3;
            i++;
        }
        listDataHeader.add(getSemesterText(Integer.parseInt(semester))+" Semester");
        listDataChild.put(listDataHeader.get((Integer.parseInt(semester)) - 1), semester_list);

        listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

        //setting the list adapter
        expandableListView.setAdapter(listAdapter);
        progressDialog.dismiss();

    }
    private String getSemesterText(int id){
        String str = "";
        switch (id){
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return id+"th";
        }
        return str;
    }
}
