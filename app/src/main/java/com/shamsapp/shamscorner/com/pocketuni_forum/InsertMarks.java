package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InsertMarks extends AppCompatActivity {

    private Spinner spSemester, spDepartment, spCourseID, spSection;
    public static final String LOGINPREF = "loginpref";
    SharedPreferences sharedpreferences;
    private EditText ctNo, seriesFrom, seriesTo;
    String username_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_marks);

        // set and get all the instances
        spSemester = (Spinner) findViewById(R.id.sp_semester);
        spDepartment = (Spinner) findViewById(R.id.sp_department);
        spCourseID = (Spinner) findViewById(R.id.sp_course_id);
        spSection = (Spinner) findViewById(R.id.sp_section);
        ctNo = (EditText) findViewById(R.id.ct_no);
        seriesFrom = (EditText) findViewById(R.id.series_from);
        seriesTo = (EditText) findViewById(R.id.series_to);

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        //add the item for these spinner
        new SpinnerItemAddition(this, spSemester).execute(username_text, "distinct semester", "teaches", "username");
        new SpinnerItemAddition(this, spSection).execute(username_text, "distinct section", "teaches", "username");
        new SpinnerItemAddition(this, spCourseID).execute(username_text, "distinct course_id", "teaches", "username");
        new SpinnerItemAddition(this, spDepartment).execute(username_text, "distinct dept_name", "teaches, course", "teaches.course_id = course.course_id and username");
    }

    public void ctGetStarted(View view) {
        if ("".equals(ctNo.getText().toString().trim()) || "".equals(seriesFrom.getText().toString().trim())
                || "".equals(seriesTo.getText().toString().trim())) {
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
                            Intent intent = new Intent(getApplicationContext(), InsertingMarks.class);
                            intent.putExtra("CTNO", ctNo.getText().toString().trim());
                            intent.putExtra("ROLLNO", username_text);
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