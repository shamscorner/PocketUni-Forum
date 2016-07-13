package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by shamim on 13-Jul-16.
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    /**
     * when token refresh, start service to get new token
     */
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
