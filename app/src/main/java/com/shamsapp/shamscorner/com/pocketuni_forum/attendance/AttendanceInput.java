package com.shamsapp.shamscorner.com.pocketuni_forum.attendance;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

/**
 * Created by shamim on 22-Sep-16.
 */
public class AttendanceInput extends TabActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_input);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec insertSpec = tabHost.newTabSpec("attendance_input");
        insertSpec.setIndicator("Attendance", getResources().getDrawable(R.drawable.tab_hover));
        insertSpec.setContent(new Intent(this, TeacherAtten.class));

        TabHost.TabSpec previousSpec = tabHost.newTabSpec("previous_input");
        previousSpec.setIndicator("Previous-Input", getResources().getDrawable(R.drawable.tab_hover));
        previousSpec.setContent(new Intent(this, PreviousAttenShowTab.class));

        tabHost.addTab(insertSpec);
        tabHost.addTab(previousSpec);
    }
}
