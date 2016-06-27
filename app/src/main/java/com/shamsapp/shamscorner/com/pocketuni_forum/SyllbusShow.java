package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SyllbusShow extends AppCompatActivity {

    public static final String SYLLABUS_ID = "syllabus_id";
    private String syllabusID, username;
    private TextView tvTheory, tvSessional;
    WebView tvSyllabus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syllabus_show);

        //initialize all views
        tvTheory = (TextView)findViewById(R.id.theory);
        tvSessional = (TextView)findViewById(R.id.sessional);
        tvSyllabus = (WebView)findViewById(R.id.tv_syllabus);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            syllabusID = extras.getString(SYLLABUS_ID);
            username = extras.getString("USERNAME");
        }

        // split the string
        String[] value = syllabusID.split("-");
        new InfoSyllabusActivity(this, tvSyllabus, tvTheory, tvSessional).execute(value[0].trim(), value[1].trim(), value[2].trim());
    }
}
