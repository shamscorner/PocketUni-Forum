package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ShamimH on 21-May-16.
 */
public class SplashScreen extends AppCompatActivity {

    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //set the content view for this activity
        setContentView(R.layout.splash_screen);

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
}
