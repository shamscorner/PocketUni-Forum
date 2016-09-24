package com.shamsapp.shamscorner.com.pocketuni_forum.intro;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shamim on 23-Sep-16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // shared pref file name
    private static final String PREF_NAME = "loginpref";

    // is this first time launch
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_ALWAYS_LAUNCH = "IsAlwaysLaunch";

    public PrefManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setAlwaysLaunch(boolean isAlways){
        editor.putBoolean(IS_ALWAYS_LAUNCH, isAlways);
        editor.commit();
    }

    public boolean isAlwaysLaunch(){
        return pref.getBoolean(IS_ALWAYS_LAUNCH, false);
    }

    /*
    public void setAttenAgain(boolean atten){
        editor.putBoolean("AttenAgain", atten);
        editor.commit();
    }
    public boolean isAttenAvailable(){
        return pref.getBoolean("AttenAgain", true);
    }
    */
}
