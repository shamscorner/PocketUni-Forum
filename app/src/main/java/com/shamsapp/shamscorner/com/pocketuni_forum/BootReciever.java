package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.PrefValue;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.RoutineReciever;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.RoutineUpcomingNotifyReciever;

import java.util.Calendar;

/**
 * Created by shamim on 02-Aug-16.
 */
public class BootReciever extends BroadcastReceiver {
    private Context context;
    private PrefValue ob;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            // set all the alarm in here...
            this.context = context;
            ob = new PrefValue(this.context);
            startServiceForRoutine();
            startServiceForRoutineNotify();
        }
    }
    private void startServiceForRoutine() {
        // this section for the increment of the routine day
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(context, RoutineReciever.class);
        PendingIntent pendingIntentRoutine = PendingIntent.getBroadcast(context, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentRoutine);
    }
    private void startServiceForRoutineNotify() {
        // this section for the class notification of the routine day

        Calendar calendar = Calendar.getInstance();
        String hour = ob.getNotHour();
        String min = ob.getNotMin();

        if(hour.equals("")){
            hour = "10";
        }
        if(min.equals("")){
            min = "00";
        }

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(min));
        //calendar.set(Calendar.HOUR_OF_DAY, 2);
        //calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);
        //calendar.set(Calendar.AM_PM,Calendar.PM);

        String ampm = ob.getNotAmPm();
        if(ampm.equals("AM")){
            calendar.set(Calendar.AM_PM,Calendar.AM);
        }else if(ampm.equals("PM")){
            calendar.set(Calendar.AM_PM,Calendar.PM);
        }else{
            calendar.set(Calendar.AM_PM,Calendar.PM);
        }

        Intent myIntent = new Intent(context, RoutineUpcomingNotifyReciever.class);
        PendingIntent pendingIntentRoutine = PendingIntent.getBroadcast(context, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentRoutine);
    }
}
