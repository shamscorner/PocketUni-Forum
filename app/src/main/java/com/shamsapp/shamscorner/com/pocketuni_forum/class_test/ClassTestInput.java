package com.shamsapp.shamscorner.com.pocketuni_forum.class_test;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

public class ClassTestInput extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_input);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec insertSpec = tabHost.newTabSpec("InsertMarks");
        insertSpec.setIndicator("Insert Marks", getResources().getDrawable(R.drawable.tab_hover));
        insertSpec.setContent(new Intent(this, InsertMarks.class));

        TabHost.TabSpec previousSpec = tabHost.newTabSpec("PreviousMarks");
        previousSpec.setIndicator("Previous Marks", getResources().getDrawable(R.drawable.tab_hover));
        previousSpec.setContent(new Intent(this, PreviousMarks.class));

        tabHost.addTab(insertSpec);
        tabHost.addTab(previousSpec);
    }
}
