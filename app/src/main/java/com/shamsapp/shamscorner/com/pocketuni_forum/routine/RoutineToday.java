package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager.DBHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

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

    public RoutineToday(final Context context, LayoutInflater inflaterToday, LinearLayout layoutHolder, String day){
        this.context = context;
        this.day = day;
        dbHelper = new DBHelper(context, "core.db", "time_slot", sql);

        ArrayList<String> courseId, title, time;
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
            // add the final view
            layoutHolder.addView(todayView);

            todayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "success", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
