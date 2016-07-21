package com.shamsapp.shamscorner.com.pocketuni_forum.class_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SpinnerItemAddition;

/**
 * Created by shamim on 19-Jun-16.
 */
public class ClassTestResultInput extends AppCompatActivity {
    private String type = null;
    private Spinner semester, courseId;
    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;
    String username_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_result_input);

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            type = extras.getString("TYPE");
        }

        semester = (Spinner)findViewById(R.id.sp_semester);
        courseId = (Spinner)findViewById(R.id.sp_course_id);

        new SpinnerItemAddition(this, semester).execute(username_text, "distinct semester", "course_takes", "roll_no");
        new SpinnerItemAddition(this, courseId).execute(username_text, "distinct course_id", "course_takes", "roll_no");
    }
}
