package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.ExpandableListAdapter;

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
public class InfoCourseActivityRoutine extends AsyncTask<String, Void, String> {
    private Context context;
    private String day;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    ProgressDialog progressDialog;

    public InfoCourseActivityRoutine(Context context, ExpandableListView expandableListView, ExpandableListAdapter listAdapter, List<String> listDataHeader, HashMap<String, List<String>> listDataChild){
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
            String username = arg[0];
            String type = arg[1];
            day = arg[2];
            String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mroutine.php";

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
            data += "&" + URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8");

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
            Toast.makeText(context,"Error: " + e.getMessage() , Toast.LENGTH_LONG).show();
            return new String("Error: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){
        final String[] value = result.split("//");
        // this is for input output test
        //Toast.makeText(context, ""+value.length , Toast.LENGTH_LONG).show();

        List<String> child_list = new ArrayList<>();
        int i = 0, step = -1;
        int loop = (value.length)/3;
        while(i<loop){
            child_list.add(value[++step] + " - " + value[++step] + " - " + value[++step]);
            i++;
        }
        listDataHeader.add(day + " - Day");
        listDataChild.put(listDataHeader.get(getPosition(day)), child_list);

        listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

        //setting the list adapter
        expandableListView.setAdapter(listAdapter);
        progressDialog.dismiss();

    }
    private int getPosition(String s){
        switch (s){
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
        }
        return 0;
    }
}
