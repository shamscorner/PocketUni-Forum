package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.PrefValue;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.RoutineReciever;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.RoutineUpcomingNotifyReciever;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.UploadToServerSqlite;
import com.shamsapp.shamscorner.com.pocketuni_forum.settings.Settings;
import com.shamsapp.shamscorner.com.pocketuni_forum.sqlite_manager.DBHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Dashboard extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static final String LOGINPREF = "loginpref" ;
    private PrefValue ob;
    public static final String ROUTINE = "pref_routine";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

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

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_manage) {
                    Intent intent = new Intent(getApplicationContext(), Settings.class);
                    startActivity(intent);
                }
                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        ob = new PrefValue(this);
        insertIntoRoutienPref();
        startServiceForRoutine();
        startServiceForRoutineNotify();
    }

    private void startServiceForRoutine() {
        // this section for the increment of the routine day
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(Dashboard.this, RoutineReciever.class);
        PendingIntent pendingIntentRoutine = PendingIntent.getBroadcast(Dashboard.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentRoutine);
    }

    private void insertIntoRoutienPref() {
        sharedpreferences = getSharedPreferences(ROUTINE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(sharedpreferences.getString("VACATION", "").equals("")){
            editor.putString("VACATION", "");
        }
        if(sharedpreferences.getString("HOLIDAYS", "").equals("")){
            editor.putString("HOLIDAYS", "Thursday-Friday-");
        }
        if(sharedpreferences.getString("TODAY", "").equals("")){
            editor.putString("TODAY", "A");
        }
        editor.commit();
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

        Intent myIntent = new Intent(Dashboard.this, RoutineUpcomingNotifyReciever.class);
        PendingIntent pendingIntentRoutine = PendingIntent.getBroadcast(Dashboard.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentRoutine);
    }


    public void logout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("USERNAME", "");
        editor.putString("PASSWORD", "");
        editor.putString("TYPE", "");
        editor.commit();
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

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
}
