package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shamim on 06-Jun-16.
 */
public class Semester extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public static final String LOGINPREF = "loginpref" ;
    public static final String SYLLABUS_ID = "syllabus_id";
    SharedPreferences sharedpreferences;
    TextView tvSemester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.semester, container, false);
        //get the list view
        expandableListView = (ExpandableListView)android.findViewById(R.id.semester_view);
        tvSemester = (TextView)android.findViewById(R.id.tv_semester);

        //preparing list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        sharedpreferences = getContext().getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        final String username_text = sharedpreferences.getString("USERNAME", "");
        final String type = sharedpreferences.getString("TYPE", "");

        //set the text for the tvSemester
        if(type.equals("student")){
            new SqlInfo(getContext(), tvSemester, "Semester - ").execute(username_text, "semester_id", "student", "roll_no");
        }else if(type.equals("teacher")){
            tvSemester.setText("At this time...");
        }

        if(!username_text.equals("") && type.equals("student")){
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "1");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "2");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "3");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "4");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "5");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "6");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "7");
            new InfoCourseActivity(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "8");
        }else if(!username_text.equals("") && type.equals("teacher")){
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "1");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "2");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "3");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "4");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "5");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "6");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "7");
            new InfoCourseActivityTeacher(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, "8");
        }

        // Listview Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String s = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                Intent intent = new Intent(getContext(), SyllbusShow.class);
                intent.putExtra(SYLLABUS_ID, s);
                intent.putExtra("USERNAME", username_text);
                startActivity(intent);
                return false;
            }
        });

        return android;
    }
}
