package com.shamsapp.shamscorner.com.pocketuni_forum.class_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

public class PreviousMarksShowTab extends AppCompatActivity {

    LinearLayout mainContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_marks_show_tab);

        mainContainer = (LinearLayout)findViewById(R.id.mark_show_holder);
    }
}
