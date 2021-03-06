package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shamsapp.shamscorner.com.pocketuni_forum.attendance.AttendanceInput;
import com.shamsapp.shamscorner.com.pocketuni_forum.attendance.StudentAtten;
import com.shamsapp.shamscorner.com.pocketuni_forum.attendance.TeacherAtten;
import com.shamsapp.shamscorner.com.pocketuni_forum.class_test.ClassTestInput;
import com.shamsapp.shamscorner.com.pocketuni_forum.class_test.ClassTestResultInput;
import com.shamsapp.shamscorner.com.pocketuni_forum.final_result.FinalResultStudent;
import com.shamsapp.shamscorner.com.pocketuni_forum.input_other_results.Input;
import com.shamsapp.shamscorner.com.pocketuni_forum.input_other_results.Result;

/**
 * Created by shamim on 06-Jun-16.
 */
public class Results extends Fragment {
    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;
    View resultView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedpreferences = getContext().getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        final String type = sharedpreferences.getString("TYPE", "");

        Log.d("TYpe", type);

        if(type.equals("teacher")){
            resultView = inflater.inflate(R.layout.results, container, false);
        }else if(type.equals("student")){
            resultView = inflater.inflate(R.layout.result_student, container, false);
        }

        Button btnCT = (Button)resultView.findViewById(R.id.btn_ct_input);
        btnCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("teacher")){
                    Intent intent = new Intent(getContext(), ClassTestInput.class);
                    startActivity(intent);
                }else if(type.equals("student")){
                    Intent intent = new Intent(getContext(), ClassTestResultInput.class);
                    intent.putExtra("TYPE", "ct");
                    startActivity(intent);
                }
            }
        });

        Button btnAtten = (Button)resultView.findViewById(R.id.button3);
        btnAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("teacher")){
                    Intent intent = new Intent(getContext(), AttendanceInput.class);
                    startActivity(intent);
                }else if(type.equals("student")){
                    Intent intent = new Intent(getContext(), StudentAtten.class);
                    startActivity(intent);
                }
            }
        });

        Button btnFinalResult = (Button)resultView.findViewById(R.id.button2);
        btnFinalResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("teacher")){
                    Intent intent = new Intent(getContext(), Input.class);
                    intent.putExtra("INPUT_TYPE", "Final-Result");
                    startActivity(intent);
                }else if(type.equals("student")){
                    Intent intent = new Intent(getContext(), FinalResultStudent.class);
                    startActivity(intent);
                }
            }
        });

        Button btnQuiz = (Button)resultView.findViewById(R.id.button5);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("teacher")){
                    Intent intent = new Intent(getContext(), Input.class);
                    intent.putExtra("INPUT_TYPE", "Quiz");
                    startActivity(intent);
                }else if(type.equals("student")){
                    Intent intent = new Intent(getContext(), Result.class);
                    intent.putExtra("TYPE", "ct");
                    startActivity(intent);
                }
            }
        });

        Button btnviva = (Button)resultView.findViewById(R.id.button8);
        btnviva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("teacher")){
                    Intent intent = new Intent(getContext(), Input.class);
                    intent.putExtra("INPUT_TYPE", "Board-Viva");
                    startActivity(intent);
                }else if(type.equals("student")){
                    Intent intent = new Intent(getContext(), Result.class);
                    intent.putExtra("TYPE", "viva");
                    startActivity(intent);
                }
            }
        });

        Button btnLabFinal = (Button)resultView.findViewById(R.id.button7);
        btnLabFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("teacher")){
                    Intent intent = new Intent(getContext(), Input.class);
                    intent.putExtra("INPUT_TYPE", "Lab-Final");
                    startActivity(intent);
                }else if(type.equals("student")){
                    Intent intent = new Intent(getContext(), Result.class);
                    intent.putExtra("TYPE", "lab_final");
                    startActivity(intent);
                }
            }
        });

        return resultView;
    }
}
