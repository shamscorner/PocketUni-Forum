package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shamim on 30-Jul-16.
 */
public class RoutineUpcomingNotifyReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, RoutineUpcomingNotify.class);
        context.startService(service);
    }
}
