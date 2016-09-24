package com.shamsapp.shamscorner.com.pocketuni_forum.final_result;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

public class FinalResultStudent extends AppCompatActivity {

    private Button[] btn = new Button[8];
    private int[] btnId = {
            R.id.semester_1,
            R.id.semester_2,
            R.id.semester_3,
            R.id.semester_4,
            R.id.semester_5,
            R.id.semester_6,
            R.id.semester_7,
            R.id.semester_8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_student);

        // initiate those buttons
        for(int i = 0; i < 8; i++){
            btn[i] = (Button)findViewById(btnId[i]);
        }

        // make those button fires event
        for(int i = 0; i < 8; i++){
            final int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), FinalResultStudentDetailsShow.class);
                    intent.putExtra("ID", finalI +1);
                    startActivity(intent);
                }
            });
        }
    }
}
