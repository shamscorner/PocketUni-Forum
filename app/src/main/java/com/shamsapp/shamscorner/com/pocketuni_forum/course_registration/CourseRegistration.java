package com.shamsapp.shamscorner.com.pocketuni_forum.course_registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

/**
 * Created by shamim on 24-Jun-16.
 */
public class CourseRegistration extends AppCompatActivity {

    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;
    String username = "";

    LinearLayout regMainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_reg_panel);

        // set the sharedpreferences
        sharedpreferences = getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);

        //get the username from the sharedpreference
        username = sharedpreferences.getString("USERNAME", "");

        regMainLayout = (LinearLayout)findViewById(R.id.reg_main_container);

        // call the course details class
        new RegistrationDetailsView(this, regMainLayout).execute(username);
    }
}
