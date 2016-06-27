package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InsertingMarks extends AppCompatActivity {

    private String semester, section, department, courseId, rollNo;
    private LayoutInflater inflater;
    private LinearLayout mainContainer;
    private int student, ctNo;
    private View v;
    private Button btnDone;
    private TextView tvError;
    //private EditText[] editTexts = new EditText[200];
    private ArrayList<EditText> marksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnDone = (Button)findViewById(R.id.btn_insert_done);
        tvError = (TextView)findViewById(R.id.tvErrorCT);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            rollNo = extras.getString("ROLLNO");
            semester = extras.getString("SEMESTER");
            section = extras.getString("SECTION");
            department = extras.getString("DEPARTMENT");
            courseId = extras.getString("COURSEID");
            student = Integer.parseInt(extras.getString("STUDENT"));
            ctNo = Integer.parseInt(extras.getString("CTNO"));
        }

        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(InsertingMarks.this);
        mainContainer = (LinearLayout)findViewById(R.id.main_container);

        for(int i = 1; i <= student; i++){
            v = inflater.inflate(R.layout.marks_input, null);
            ((TextView)v.findViewById(R.id.tv_roll)).setText(""+i);
            //editTexts[i] = (EditText)v.findViewById(R.id.edt_marks);
            marksList.add((EditText)v.findViewById(R.id.edt_marks));
            mainContainer.addView(v);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < student; i++){
                    //Log.d("val",(marksList.get(i)).getText().toString().trim());
                    new CTMarksInserted(getApplicationContext(), tvError).execute(""+ctNo, rollNo, courseId, semester, section, marksList.get(i).getText().toString().trim());
                }
            }
        });
    }

}
