package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager.DBHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shamim on 28-Jul-16.
 */
public class RoutineToday {
    private DBHelper dbHelper;
    private Context context;
    private String day;

    private String sql = "CREATE TABLE IF NOT EXISTS \"time_slot\" (\"time\" VARCHAR NOT NULL ,\"day\" VARCHAR NOT NULL " +
            ",\"course_id\" VARCHAR NOT NULL, \"title\" VARCHAR NOT NULL ,\"classroom_id\" " +
            "VARCHAR NOT NULL ,\"teacher\" VARCHAR NOT NULL )";
    /*
    public static final String COL_TIME = "time";
    public static final String COL_DAY = "day";
    public static final String COL_COURSE_ID = "course_id";
    public static final String COL_title = "title";
    public static final String COL_CLASSROOM_ID = "classroom_id";
    public static final String COL_TEACHER = "teacher";
    */
    private LayoutInflater inflate;

    public RoutineToday(final Context context, LayoutInflater inflaterToday, LinearLayout layoutHolder, String day){
        this.context = context;
        this.day = day;
        this.inflate = inflaterToday;
        dbHelper = new DBHelper(context, "core.db", "time_slot", sql);

        final ArrayList<String> courseId, title, time;
        courseId = dbHelper.getAllData("course_id", "SELECT * FROM time_slot WHERE day = '"+day+"'");
        title = dbHelper.getAllData("title", "SELECT * FROM time_slot WHERE day = '"+day+"'");
        time = dbHelper.getAllData("time", "SELECT * FROM time_slot WHERE day = '"+day+"'");

        //Log.d("ValueCorecourse", ""+courseId.size());

        for(int i = 0; i < courseId.size(); i++){
            View todayView = inflaterToday.inflate(R.layout.routine_today_details, null);
            // get those all instances
            TextView tvCourseId = (TextView)todayView.findViewById(R.id.tv_routine_course_id);
            TextView tvTitle = (TextView)todayView.findViewById(R.id.tv_routine_course_title);
            TextView tvTime = (TextView)todayView.findViewById(R.id.tv_routine_course_time);
            //set the text
            tvCourseId.setText(courseId.get(i));
            tvTitle.setText(title.get(i));
            tvTime.setText(time.get(i));

            ImageView ongoing = (ImageView)todayView.findViewById(R.id.imageView);
            String[] timeval = time.get(i).split("-");
            float currentTime = getCurrentTime();
            float ongoingTimeStart = getTimeFromText(timeval[0], "\\.", true);
            float ongoingTimeEnd = getTimeFromText(timeval[1], "\\.", true);

            //Toast.makeText(context, "Current : "+currentTime+" ongoing: "+ongoingTimeStart+" ongoing end: "+ongoingTimeEnd, Toast.LENGTH_LONG).show();

            if(currentTime < ongoingTimeStart){
                // upcoming
                ongoing.setImageResource(R.drawable.check);
            }else if(currentTime >= ongoingTimeStart && currentTime < ongoingTimeEnd){
                // going
                ongoing.setImageResource(R.drawable.going);
            }else if(currentTime > ongoingTimeEnd){
                // over
                ongoing.setImageResource(R.drawable.cross);
            }

            final int thisi = i;

            final ToggleButton tg = (ToggleButton)todayView.findViewById(R.id.toggleButton);
            tg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefValue ob = new PrefValue(context);
                    String val = ob.getRingOn();
                    if(tg.isChecked()){
                        // disable the notification ringtone
                        val = val + courseId.get(thisi) + "-";
                        ob.setRingOn(val);
                    }else{
                        // enable the notification ringtone
                        String[] valarr = val.split("-");
                        String s = "";
                        for (int j = 0; j < valarr.length; j++){
                            if(valarr[j].equals(courseId.get(thisi))){
                                continue;
                            }
                            s = s + valarr[j] + "-";
                        }
                        ob.setRingOn(s);
                    }
                }
            });

            // add the final view
            layoutHolder.addView(todayView);

            todayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(time.get(thisi), courseId.get(thisi), title.get(thisi));
                }
            });
        }
    }
    private float getTimeFromText(String s, String delimeter, boolean b){
        String[] arr = s.split(delimeter);
        int hour, min;
        hour = Integer.parseInt(arr[0]);
        if(b && hour < 8){
            hour += 12;
        }
        min = Integer.parseInt(arr[1]);
        return hour+((float)min/60);
    }
    private float getCurrentTime(){
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calander.getTime());
        return getTimeFromText(time, ":", false);
    }
    private void showDialog(String... arg){
        View v = inflate.inflate(R.layout.routine_today_show, null);
        TextView time, courseID, title, assignment, homework, ct;
        time = (TextView)v.findViewById(R.id.time);
        courseID = (TextView)v.findViewById(R.id.course_id);
        title = (TextView)v.findViewById(R.id.title);
        assignment = (TextView)v.findViewById(R.id.assignment);
        homework = (TextView)v.findViewById(R.id.homework);
        ct = (TextView)v.findViewById(R.id.classtest);

        time.setText(arg[0]);
        courseID.setText(arg[1]);
        title.setText(arg[2]);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(v)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
