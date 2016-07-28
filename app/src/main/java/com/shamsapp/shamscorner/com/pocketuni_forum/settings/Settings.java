package com.shamsapp.shamscorner.com.pocketuni_forum.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.class_test.InsertingMarks;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.UploadToServerSqlite;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private Spinner spToday;
    private LayoutInflater inflater;
    private String today, vacation, holidays;
    public static final String ROUTINE = "pref_routine";
    public static final String LOGINPREF = "loginpref";

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private int status;
    private String from, to;
    private boolean isSpinnerTouched = false;

    private String username_text, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");
        type = sharedpreferences.getString("TYPE", "");

        sharedpreferences = getSharedPreferences(ROUTINE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // get all the instances
        spToday = (Spinner) findViewById(R.id.st_day_today);

        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(Settings.this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.day_list));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spToday.setAdapter(dataAdapter);

        spToday.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });

        spToday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerTouched) {
                    today = spToday.getSelectedItem().toString();
                    editor.putString("TODAY", today);
                    //Toast.makeText(getApplicationContext(), today, Toast.LENGTH_LONG).show();
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void setVacation(View view) {
        status = 0;
        View v = inflater.inflate(R.layout.calender_view, null);
        final TextView tv = (TextView) v.findViewById(R.id.title);
        tv.setText("From");
        final CalendarView calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(v)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                status++;
                if (status == 1) {
                    from = dayOfMonth + "-" + month + "-" + year;
                    tv.setText("To");
                } else if (status == 2) {
                    to = dayOfMonth + "-" + month + "-" + year;

                    editor.putString("VACATION", from + "//" + to);
                    //Toast.makeText(getApplicationContext(), from+"//"+to, Toast.LENGTH_LONG).show();
                    editor.commit();
                    alertDialog.dismiss();
                }
            }
        });
    }

    public void setHoliday(View view) {
        View v = inflater.inflate(R.layout.week_view, null);
        final RadioButton rdsat = (RadioButton) v.findViewById(R.id.radsat);
        final RadioButton rdsun = (RadioButton) v.findViewById(R.id.radsun);
        final RadioButton rdmon = (RadioButton) v.findViewById(R.id.radmon);
        final RadioButton rdtue = (RadioButton) v.findViewById(R.id.radtue);
        final RadioButton rdwed = (RadioButton) v.findViewById(R.id.radwed);
        final RadioButton rdthu = (RadioButton) v.findViewById(R.id.radthus);
        final RadioButton rdfri = (RadioButton) v.findViewById(R.id.radfri);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(v)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // handle the holiday section
                        holidays = "";
                        insertHoliday(rdsat);
                        insertHoliday(rdsun);
                        insertHoliday(rdmon);
                        insertHoliday(rdtue);
                        insertHoliday(rdwed);
                        insertHoliday(rdthu);
                        insertHoliday(rdfri);

                        editor.putString("HOLIDAYS", holidays);
                        //Toast.makeText(getApplicationContext(), holidays, Toast.LENGTH_LONG).show();
                        editor.commit();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void insertHoliday(RadioButton radioButton) {
        String s = "";
        if (radioButton.isChecked()) {
            s = radioButton.getText().toString();
        }
        if (!s.equals("")) {
            holidays = holidays + s + "-";
        }
    }

    public void refreshWhole(View view) {
        //View v = inflater.inflate(R.layout.routine, null);
        //LinearLayout layoutTodayHolder = (LinearLayout)v.findViewById(R.id.routine_today_holder);
        //new UploadToServerSqlite(this, layoutTodayHolder, today).execute(username_text, type);

        // refresh the routine fragment class
        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentByTag("routine_tag");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
}
