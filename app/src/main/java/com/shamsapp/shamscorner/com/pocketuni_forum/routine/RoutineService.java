package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.Dashboard;
import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SplashScreen;
import com.shamsapp.shamscorner.com.pocketuni_forum.intro.PrefManager;

import java.util.Calendar;

/**
 * Created by shamim on 29-Jul-16.
 */
public class RoutineService extends Service {

    private PrefValue ob;

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

        String[] holiday = ob.getHolidayText().split("-");
        //Log.d("Increment Day", "Today week: "+getTodayOfWeek()+" | holiday0: "+holiday[0]+" | holiday1: "+holiday[1]);
        if(!getTodayOfWeek().equals(holiday[0]) && !getTodayOfWeek().equals(holiday[1])){
            Calendar today = Calendar.getInstance();
            // Set the hour to midnight up to the millisecond
            today.set(Calendar.HOUR_OF_DAY, 12);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.AM_PM,Calendar.AM);
            // compute currenttime - midnight to obtain the milliseconds of today
            long milliseconds = System.currentTimeMillis()-today.getTimeInMillis();
            if(milliseconds < 10){

                if(ob.getVacationText().equals("")){
                    ob.saveToday(incrementDay(ob.getToday()));
                }else{
                    String[] vacations = ob.getVacationText().split("//");
                    //long fromTime = convertCurrentTimeMills(vacations[0]);
                    //long toTime = convertCurrentTimeMills(vacations[1]);
                    long current = System.currentTimeMillis();

                    if(current < convertCurrentTimeMills(vacations[0]) || current > convertCurrentTimeMills(vacations[1])){
                        if(ob.getToday().equals("A")){
                            int cycle = ob.getCycle();
                            cycle++;
                            ob.saveCycle(cycle);
                        }
                        ob.saveToday(incrementDay(ob.getToday()));
                        // set the attendance true again
                        //new PrefManager(getApplicationContext()).setAttenAgain(true);
                    }
                }
                notifyPush();
            }
        }
    }
    private void notifyPush(){
        NotificationManager mManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(getApplicationContext(), Dashboard.class);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(true);
        builder.setTicker("");
        builder.setContentTitle("Routine updated");
        builder.setContentText("Today is day - "+ ob.getToday());
        builder.setSmallIcon(R.drawable.clock);
        builder.setContentIntent(pendingNotificationIntent);
        // check for the api version that support or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setSubText("Refresh Please...");   //API level 16
            builder.build();
        }
        Notification myNotication = builder.getNotification();
        mManager.notify(11, myNotication);
    }

    private long convertCurrentTimeMills(String s) {
        String[] time = s.split("-");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(time[2]), Integer.parseInt(time[1]), Integer.parseInt(time[0]));
        return calendar.getTimeInMillis();
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

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
