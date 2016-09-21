package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shamsapp.shamscorner.com.pocketuni_forum.Dashboard;
import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shamim on 23-Aug-16.
 */
public class RoutineUpcomingBeforeNotify extends Service {

    private PrefValue ob;
    private DBHelper dbHelper;
    private String sql = "CREATE TABLE IF NOT EXISTS \"time_slot\" (\"time\" VARCHAR NOT NULL ,\"day\" VARCHAR NOT NULL " +
            ",\"course_id\" VARCHAR NOT NULL, \"title\" VARCHAR NOT NULL ,\"classroom_id\" " +
            "VARCHAR NOT NULL ,\"teacher\" VARCHAR NOT NULL )";
    private String todayDay;
    private int hour, min, count, len;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        dbHelper = new DBHelper(getApplicationContext(), "core.db", "time_slot", sql);
        ob = new PrefValue(getApplicationContext());
        todayDay = ob.getToday();

        // get all the courses from the database
        ArrayList<String> courseId, title, time;
        courseId = dbHelper.getAllData("course_id", "SELECT * FROM time_slot WHERE day = '" + todayDay + "'");
        title = dbHelper.getAllData("title", "SELECT * FROM time_slot WHERE day = '" + todayDay + "'");
        time = dbHelper.getAllData("time", "SELECT * FROM time_slot WHERE day = '" + todayDay + "'");

        len = courseId.size();

        String hourS, minS;
        hourS = ob.getBeforeNotHour();
        minS = ob.getBeforeNotMin();
        if(hourS.equals("")){
            hour = 7;
        }else{
            hour = Integer.parseInt(hourS);
        }
        if(minS.equals("")){
            min = 45;
        }else{
            min = Integer.parseInt(minS);
        }

        // here is the main stuff coding
        String[] holiday = ob.getHolidayText().split("-");
        if(!getTodayOfWeek().equals(holiday[0]) && !getTodayOfWeek().equals(holiday[1])){
            Calendar today = Calendar.getInstance();

            today.set(Calendar.HOUR_OF_DAY, hour);
            today.set(Calendar.MINUTE, min);
            today.set(Calendar.SECOND, 0);
            if(getAMPM(hour).equals("AM")){
                today.set(Calendar.AM_PM,Calendar.AM);
            }else if(getAMPM(hour).equals("PM")){
                today.set(Calendar.AM_PM,Calendar.PM);
            }

            long milliseconds = System.currentTimeMillis()-today.getTimeInMillis();
            if(milliseconds < 10){
                count = ob.getCount();
                if(ob.getVacationText().equals("")){
                    // perform the notification for each of these classes
                    if(count < len){
                        notifyPush(courseId.get(count), title.get(count), time.get(count), count + 30);
                        saveAll();
                    }
                }else{
                    String[] vacations = ob.getVacationText().split("//");
                    long current = System.currentTimeMillis();

                    if(current < convertCurrentTimeMills(vacations[0]) || current > convertCurrentTimeMills(vacations[1])){
                        // perform the notification for each of these classes
                        if(count < len){
                            notifyPush(courseId.get(count), title.get(count), time.get(count), count + 30);
                            saveAll();
                        }
                    }
                }
            }
        }
    }

    private void saveAll(){
        count++;
        ob.saveCount(count);


    }

    private long convertCurrentTimeMills(String s) {
        String[] time = s.split("-");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(time[2]), Integer.parseInt(time[1]), Integer.parseInt(time[0]));
        return calendar.getTimeInMillis();
    }

    private String getTodayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day){
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }
        return "";
    }

    private String getAMPM(int hour){
        if(hour == 12){
            return "PM";
        }else if(hour < 6){
            return "PM";
        }else{
            return "AM";
        }
    }

    private void notifyPush(String courseID, String courseTitle, String time, int id) {
        NotificationManager mManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(getApplicationContext(), Dashboard.class); // have to change with a custom layout

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(true);
        builder.setTicker("");
        builder.setContentTitle("Your Upcoming Class");
        builder.setContentText(courseID + " - " + courseTitle);
        builder.setSmallIcon(R.drawable.clock);
        builder.setContentIntent(pendingNotificationIntent);
        // check for the api version that support or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setSubText(""+time);   //API level 16
            builder.build();
        }

        Notification myNotication = builder.getNotification();
        mManager.notify(id, myNotication);
    }
}
