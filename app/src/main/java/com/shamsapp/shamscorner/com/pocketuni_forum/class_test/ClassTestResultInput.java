package com.shamsapp.shamscorner.com.pocketuni_forum.class_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
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
    private String username_text, semesterText, courseIdText;
    private LayoutInflater inflater;

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

    public void ctGetStartedStudent(View view) {
        semesterText = semester.getSelectedItem().toString();
        courseIdText = courseId.getSelectedItem().toString();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Get the layout inflater
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(ClassTestResultInput.this);

        alertDialogBuilder.setView(inflater.inflate(R.layout.ct_marks_dialog_show, null))
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
/*

select ct_no, marks from ct_marks where roll_no = '123086' and semester = 1 and course_id = 'CSE 100'

 */