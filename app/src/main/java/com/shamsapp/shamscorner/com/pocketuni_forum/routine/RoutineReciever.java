package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shamim on 29-Jul-16.
 */
public class RoutineReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, RoutineService.class);
        context.startService(service);
    }
}
