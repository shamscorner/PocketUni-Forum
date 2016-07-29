package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.RoutineReciever;

import java.util.Calendar;

/**
 * Created by ShamimH on 21-May-16.
 */
public class SplashScreen extends AppCompatActivity {

    //private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //set the content view for this activity
        setContentView(R.layout.splash_screen);

        /*
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){

                }else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                }else{

                }
            }
        };
        */

        //check status of google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode){
            //check type of error
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                Toast.makeText(getApplicationContext(), "Google play service is not available in this device!", Toast.LENGTH_LONG).show();
                //so notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            }else{
                Toast.makeText(getApplicationContext(), "This device does not support for Google play service!", Toast.LENGTH_LONG).show();
            }
        }else{
            //start service
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }

        // set the sharedpreferences
        sharedpreferences = getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);

        // Now play the splash screen first
        Thread timer = new Thread(){
            public void run(){
                try{
                    int timer=0;
                    while(timer<3000) {
                        sleep(100);
                        timer = timer + 100;
                    }
                    if(sharedpreferences.getString("USERNAME", "").equals("") && sharedpreferences.getString("PASSWORD", "").equals("")){
                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                        startActivity(intent);
                    }

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }
        };
        timer.start();
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
    */
}
