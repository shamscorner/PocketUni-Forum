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

public class InsertMarks extends AppCompatActivity {

    private Spinner spSemester, spDepartment, spCourseID, spSection;
    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;
    private EditText noStudent, ctNo;
    String username_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_marks);

        // set and get all the instances
        spSemester = (Spinner)findViewById(R.id.sp_semester);
        spDepartment = (Spinner)findViewById(R.id.sp_department);
        spCourseID = (Spinner)findViewById(R.id.sp_course_id);
        spSection = (Spinner)findViewById(R.id.sp_section);
        noStudent = (EditText)findViewById(R.id.no_of_student);
        ctNo = (EditText)findViewById(R.id.ct_no);

        sharedpreferences = this.getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        username_text = sharedpreferences.getString("USERNAME", "");

        //add the item for these spinner
        new SpinnerItemAddition(this, spSemester).execute(username_text, "distinct semester", "teaches", "username");
        new SpinnerItemAddition(this, spSection).execute(username_text, "distinct section", "teaches", "username");
        new SpinnerItemAddition(this, spCourseID).execute(username_text, "distinct course_id", "teaches", "username");
        new SpinnerItemAddition(this, spDepartment).execute(username_text, "distinct dept_name", "teaches, course", "teaches.course_id = course.course_id and username");
    }

    public void ctGetStarted(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Data all set correctly");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure ?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if(noStudent.getText().toString().trim().equals("")){
                            Toast.makeText(getApplicationContext(), "Please enter the number of student", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(getApplicationContext(), InsertingMarks.class);
                            intent.putExtra("CTNO", ctNo.getText().toString().trim());
                            intent.putExtra("ROLLNO", username_text);
                            intent.putExtra("SEMESTER", spSemester.getSelectedItem().toString());
                            intent.putExtra("SECTION", spSemester.getSelectedItem().toString());
                            intent.putExtra("COURSEID", spSemester.getSelectedItem().toString());
                            intent.putExtra("DEPARTMENT", spSemester.getSelectedItem().toString());
                            intent.putExtra("STUDENT", noStudent.getText().toString().trim());
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
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