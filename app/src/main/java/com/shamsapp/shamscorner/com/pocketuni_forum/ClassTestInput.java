package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

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
