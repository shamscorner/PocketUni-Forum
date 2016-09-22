package com.shamsapp.shamscorner.com.pocketuni_forum.routine;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shamim on 29-Jul-16.
 */
public class PrefValue {
    public static final String ROUTINE = "pref_routine";
    public static final String KEY_TODAY = "TODAY";
    public static final String KEY_CYCLE = "CYCLE";
    public static final String KEY_VACATION = "VACATION";
    public static final String KEY_HOLIDAYS = "HOLIDAYS";
    public static final String NOTIFY = "NOTIFY_RING";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private String today, vacation, holidays, rington;
    private int cycle;

    public PrefValue(Context context){
        this.context = context;
        sharedpreferences = context.getSharedPreferences(ROUTINE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }
    public String getToday(){
        today = sharedpreferences.getString(KEY_TODAY, "");
        return today;
    }
    public int getCycle(){
        cycle = sharedpreferences.getInt(KEY_CYCLE, 0);
        return cycle;
    }
    public String getVacationText(){
        vacation = sharedpreferences.getString(KEY_VACATION, "");
        return vacation;
    }
    public String getHolidayText(){
        holidays = sharedpreferences.getString(KEY_HOLIDAYS, "");
        return holidays;
    }
    public String getRingOn(){
        rington = sharedpreferences.getString(NOTIFY, "");
        return rington;
    }
    public String getNotHour(){
        return sharedpreferences.getString("NOT_HOUR", "");
    }
    public String getNotMin(){
        return sharedpreferences.getString("NOT_MIN", "");
    }
    public String getNotAmPm(){
        return sharedpreferences.getString("NOT_AM_PM", "");
    }

    public void saveNotHour(String value){
        editor.putString("NOT_HOUR", value);
        editor.commit();
    }
    public void saveNotMin(String value){
        editor.putString("NOT_MIN", value);
        editor.commit();
    }
    public void saveNotAmPm(String value){
        editor.putString("NOT_AM_PM", value);
        editor.commit();
    }

    public void saveToday(String value){
        editor.putString(KEY_TODAY, value);
        editor.commit();
    }
    public void saveCycle(int value){
        editor.putInt(KEY_CYCLE, value);
        editor.commit();
    }
    public void saveVacationText(String value){
        editor.putString(KEY_VACATION, value);
        editor.commit();
    }
    public void saveHolidayText(String value){
        editor.putString(KEY_HOLIDAYS, value);
        editor.commit();
    }
    public void setRingOn(String val){
        editor.putString(NOTIFY, val);
        editor.commit();
    }
    public void saveBeforeNotHour(String value){
        editor.putString("BEFORE_NOT_HOUR", value);
        editor.commit();
    }
    public String getBeforeNotHour(){
        return sharedpreferences.getString("BEFORE_NOT_HOUR", "");
    }
    public void saveBeforeNotMin(String value){
        editor.putString("BEFORE_NOT_MIN", value);
        editor.commit();
    }
    public String getBeforeNotMin(){
        return sharedpreferences.getString("BEFORE_NOT_MIN", "");
    }
    public void saveCount(int value){
        editor.putInt("COUNT", value);
        editor.commit();
    }
    public int getCount(){
        return sharedpreferences.getInt("COUNT", 0);
    }

}
