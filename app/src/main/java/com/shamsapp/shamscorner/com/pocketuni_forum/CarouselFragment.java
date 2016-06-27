package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.shamsapp.shamscorner.com.pocketuni_forum.course_registration.CourseRegistration;

/**
 * Created by shamim on 18-Jun-16.
 */
public class CarouselFragment extends Fragment {
    TextView tvHeader, subject, code, name, date;
    Button btnClick;
    private int position;

    public static Fragment newInstance(Context context, int pos, float scale) {

        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, CarouselFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout fragmentLL = (LinearLayout) inflater.inflate(R.layout.mf, container, false);
        int pos = this.getArguments().getInt("pos");
        position = pos;

        //this is the component of that layout section
        LinearLayout iv = (LinearLayout) fragmentLL.findViewById(R.id.pagerContiner);
        iv.setLayoutParams(layoutParams);

        CarouselLayout root = (CarouselLayout) fragmentLL.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        //Log.d("Position", ""+pos);


        // this is the layout component section
        tvHeader = (TextView) fragmentLL.findViewById(R.id.header);
        subject = (TextView) fragmentLL.findViewById(R.id.subject);
        code = (TextView) fragmentLL.findViewById(R.id.code);
        name = (TextView) fragmentLL.findViewById(R.id.name);
        date = (TextView) fragmentLL.findViewById(R.id.date);

        btnClick = (Button) fragmentLL.findViewById(R.id.click);

        switch (pos) {
            case 3:
                // this is the class test
                setTextComponent("Exam", "Class Test - 1", "CSE 101", "Computer Programming", "01/01/2016");
                break;
            case 4:
                // this is the class test result
                setTextComponent("Result", "Class Test - 1", "CSE 101", "Computer Programming", "Marks - 20");
                break;
            case 2:
                // this is the Lab
                setTextComponent("Experimental", "Lab - 1", "CSE 102", "Sessional based on CSE 101", "01/01/2016");
                break;
            case 1:
                // this is the lab test result
                setTextComponent("Result", "Lab Test - 1", "CSE 102", "Sessional based on CSE 101", "Marks - 10");
                break;
            case 8:
                // this is the quiz section
                setTextComponent("Final", "Quiz", "CSE 101", "Computer Programming", "01/01/2016");
                break;
            case 0:
                // this is the board viva test
                setTextComponent("Final", "Board Viva", "CSE 101", "Computer Programming", "01/01/2016");
                break;
            case 5:
                // this is the assignment
                setTextComponent("Home Work", "Assignment", "CSE 101", "Computer Programming", "01/01/2016");
                break;
            case 6:
                // this is the assignment result
                setTextComponent("Home Work", "Assignment", "CSE 101", "Computer Programming", "Marks - 5");
                break;
            case 9:
                // this is the semester final exam test
                setTextComponent("Exam", "Semester Final", "1st year 1st semester", "", "01/01/2016");
                break;
            case 7:
                // this is the semester final test
                setTextComponent("Result", "Semester Final", "1st year 1st semester", "GPA : 3.75", "CGPA : 3.77");
                break;
            case 10:
                // this is the course registration section
                setTextComponent("Administrator", "Course Registration", "1st year 1st semester", "Total Credit - 20.05", "01/01/2016");
                break;
            case 11:
                // this is the exam fees
                setTextComponent("Administrator", "Exam Fee", "1st year 1st semester", "Amount - 400", "01/01/2016");
                break;
            case 12:
                // this is the department fees
                setTextComponent("Administrator", "Department Fee", "1st year 1st semester", "Amount - 400", "01/01/2016");
                break;
            case 13:
                // this is the residence fees
                setTextComponent("Administrator", "Residential Fee", "January - 2016", "Amount - 230", "01/01/2016");
                break;
        }

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 3:
                        // this is the class test
                        break;
                    case 4:
                        // this is the class test result
                        break;
                    case 2:
                        // this is the Lab
                        break;
                    case 1:
                        // this is the lab test result
                        break;
                    case 8:
                        // this is the quiz section
                        break;
                    case 0:
                        // this is the board viva test
                        break;
                    case 5:
                        // this is the assignment
                        break;
                    case 6:
                        // this is the assignment result
                        break;
                    case 9:
                        // this is the semester final exam test
                        break;
                    case 7:
                        // this is the semester final test
                        break;
                    case 10:
                        // this is the course registration section
                        Intent intent = new Intent(getContext(), CourseRegistration.class);
                        startActivity(intent);
                        break;
                    case 11:
                        // this is the exam fees
                        break;
                    case 12:
                        // this is the department fees
                        break;
                    case 13:
                        // this is the residence fees
                        break;
                }
            }
        });

        return fragmentLL;
    }

    private void setTextComponent(String... arg) {
        tvHeader.setText(arg[0]);
        subject.setText(arg[1]);
        code.setText(arg[2]);
        name.setText(arg[3]);
        date.setText(arg[4]);
    }
}
