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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.routine.InfoCourseActivityRoutine;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.RoutineToday;
import com.shamsapp.shamscorner.com.pocketuni_forum.routine.UploadToServerSqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shamim on 06-Jun-16.
 */
public class Routine extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public static final String LOGINPREF = "loginpref";
    public static final String ROUTINE = "pref_routine";
    public static final String SYLLABUS_ID = "syllabus_id";
    SharedPreferences sharedpreferences;
    TextView tvToday;
    private String today;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.routine, container, false);
        //get the list view
        expandableListView = (ExpandableListView) android.findViewById(R.id.overall_routine_view);
        tvToday = (TextView)android.findViewById(R.id.tv_today_day);
        sharedpreferences = getContext().getSharedPreferences(ROUTINE, Context.MODE_PRIVATE);
        today = sharedpreferences.getString("TODAY", "");
        tvToday.setText("Today is "+today+" day");
        LinearLayout layoutTodayHolder = (LinearLayout)android.findViewById(R.id.routine_today_holder);

        //preparing list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        sharedpreferences = getContext().getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        final String username_text = sharedpreferences.getString("USERNAME", "");
        final String type = sharedpreferences.getString("TYPE", "");

        // this is the routine today section
        //call the update to sqlite data base section
        new UploadToServerSqlite(getContext(), layoutTodayHolder, today).execute(username_text, type);// this have to change

        new InfoCourseActivityRoutine(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, type, "A");
        new InfoCourseActivityRoutine(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, type, "B");
        new InfoCourseActivityRoutine(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, type, "C");
        new InfoCourseActivityRoutine(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, type, "D");
        new InfoCourseActivityRoutine(getContext(), expandableListView, listAdapter, listDataHeader, listDataChild).execute(username_text, type, "E");

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
