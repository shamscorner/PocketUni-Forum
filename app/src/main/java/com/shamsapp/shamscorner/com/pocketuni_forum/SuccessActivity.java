package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    private String result, successText = "";
    private TextView successView;
    private ImageView imgMark;
    private AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            result = extras.getString("RESULT");
            successText = extras.getString("SUCCESS_TEXT");
        }

        successView = (TextView)findViewById(R.id.success_text);
        successView.setText(""+successText);
        imgMark = (ImageView) findViewById(R.id.success_mark);
        frameAnimation = (AnimationDrawable) imgMark.getBackground();
        frameAnimation.start();
        if(result.equals("uploaded")){
            imgMark.setImageResource(R.drawable.tickmark);
        }else if(result.equals("failed")){
            imgMark.setImageResource(R.drawable.crossmark);
        }

    }

    public void clickOkForSuccess(View view) {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
