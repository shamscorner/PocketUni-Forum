package com.shamsapp.shamscorner.com.pocketuni_forum.attendance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;
import com.shamsapp.shamscorner.com.pocketuni_forum.SpinnerItemAddition;
import com.shamsapp.shamscorner.com.pocketuni_forum.class_test.InsertingMarks;

public class TeacherAtten extends AppCompatActivity {

    private Spinner spSemester, spDepartment, spCourseID, spSection;
    private EditText seriesFrom, seriesTo;
    private String username_text;
    private static final String LOGINPREF = "loginpref";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_atten);

        // get the username
        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        // initiate all initials
        spSemester = (Spinner) findViewById(R.id.sp_semester);
        spDepartment = (Spinner) findViewById(R.id.sp_department);
        spCourseID = (Spinner) findViewById(R.id.sp_course_id);
        spSection = (Spinner) findViewById(R.id.sp_section);
        seriesFrom = (EditText) findViewById(R.id.series_from);
        seriesTo = (EditText) findViewById(R.id.series_to);

        // add contents for all spinner
        new SpinnerItemAddition(this, spSemester).execute(username_text, "distinct semester", "teaches", "username");
        new SpinnerItemAddition(this, spSection).execute(username_text, "distinct section", "teaches", "username");
        new SpinnerItemAddition(this, spCourseID).execute(username_text, "distinct course_id", "teaches", "username");
        new SpinnerItemAddition(this, spDepartment).execute(username_text, "distinct dept_name", "teaches, course", "teaches.course_id = course.course_id and username");
    }

    public void ctGetStarted(View view) {
        //hide the keyboard
        try{
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch(Exception e){
            e.printStackTrace();
        }

        if ("".equals(seriesFrom.getText().toString().trim()) || "".equals(seriesTo.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Some Contents are missing", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("Data all set correctly");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), InsertingAtten.class);
                            intent.putExtra("STATUS", "insert");
                            intent.putExtra("SEMESTER", spSemester.getSelectedItem().toString());
                            intent.putExtra("SECTION", spSection.getSelectedItem().toString());
                            intent.putExtra("COURSEID", spCourseID.getSelectedItem().toString());
                            intent.putExtra("DEPARTMENT", spDepartment.getSelectedItem().toString());
                            intent.putExtra("SERIES", seriesFrom.getText().toString().trim() + "-" + seriesTo.getText().toString().trim());
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }
}
