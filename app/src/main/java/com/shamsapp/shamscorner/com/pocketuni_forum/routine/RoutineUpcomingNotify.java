package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.Dashboard;
import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager.DBHelper;

import java.util.ArrayList;

/**
 * Created by shamim on 29-Jul-16.
 */
public class RoutineUpcomingNotify extends Service {

    private PrefValue ob;
    private DBHelper dbHelper;
    private String sql = "CREATE TABLE IF NOT EXISTS \"time_slot\" (\"time\" VARCHAR NOT NULL ,\"day\" VARCHAR NOT NULL " +
            ",\"course_id\" VARCHAR NOT NULL, \"title\" VARCHAR NOT NULL ,\"classroom_id\" " +
            "VARCHAR NOT NULL ,\"teacher\" VARCHAR NOT NULL )";
    private String today;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }
    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        ob = new PrefValue(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext(), "core.db", "time_slot", sql);
        today = ob.getToday();

        // here is the main stuff coding
        ArrayList<String> courseId, title, time;
        courseId = dbHelper.getAllData("course_id", "SELECT * FROM time_slot WHERE day = '"+incrementDay(today)+"'");
        title = dbHelper.getAllData("title", "SELECT * FROM time_slot WHERE day = '"+incrementDay(today)+"'");
        time = dbHelper.getAllData("time", "SELECT * FROM time_slot WHERE day = '"+incrementDay(today)+"'");

        for(int i = 0; i < courseId.size(); i++){
            notifyPush(courseId.get(i), title.get(i), time.get(i), i+20);
        }
    }
    private String incrementDay(String s){
        switch (s){
            case "A":
                return "B";
            case "B":
                return "C";
            case "C":
                return "D";
            case "D":
                return "E";
            case "E":
                return "A";
        }
        return null;
    }
    private void notifyPush(String courseID, String courseTitle, String time, int id){
        NotificationManager mManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(getApplicationContext(), Dashboard.class); // have to change with a custom layout

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(true);
        builder.setTicker("");
        builder.setContentTitle("Your Upcoming Classes");
        builder.setContentText(courseID + " - " + courseTitle);
        builder.setSmallIcon(R.drawable.clock);
        builder.setContentIntent(pendingNotificationIntent);
        builder.setSubText(time);   //API level 16
        builder.build();

        Notification myNotication = builder.getNotification();
        mManager.notify(id, myNotication);
    }
}
