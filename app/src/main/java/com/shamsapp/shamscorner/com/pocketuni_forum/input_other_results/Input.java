package com.shamsapp.shamscorner.com.pocketuni_forum.input_other_results;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

/**
 * Created by shamim on 08-Oct-16.
 */
public class Input extends TabActivity {

    private String inputType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_test_input);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            inputType = extras.getString("INPUT_TYPE");
        }

        Intent intentTeacherInput = new Intent(this, TeacherInput.class);
        intentTeacherInput.putExtra("INPUT_TYPE", inputType);

        Intent intentPreviousInput = new Intent(this, PreviousInput.class);
        intentPreviousInput.putExtra("INPUT_TYPE", inputType);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec insertSpec = tabHost.newTabSpec("input");
        insertSpec.setIndicator(inputType, getResources().getDrawable(R.drawable.tab_hover));
        insertSpec.setContent(intentTeacherInput);

        TabHost.TabSpec previousSpec = tabHost.newTabSpec("previous_input");
        previousSpec.setIndicator("Previous-Input", getResources().getDrawable(R.drawable.tab_hover));
        previousSpec.setContent(intentPreviousInput);

        tabHost.addTab(insertSpec);
        tabHost.addTab(previousSpec);
    }
}
