package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shamim on 23-Aug-16.
 */
public class RoutineUpcomingBeforeNotifyRecieve extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, RoutineUpcomingBeforeNotify.class);
        context.startService(service);
    }
}
