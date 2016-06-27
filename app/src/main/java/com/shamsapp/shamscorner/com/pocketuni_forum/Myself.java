package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by shamim on 06-Jun-16.
 */
public class Myself extends Fragment {

    ImageView imgProfilePicture;
    TextView email, phone, gender, nationality, fathersName, mothersName, presentAddr, parmanentAddr, regSession, rank,
            dateOfBirth, joiningDate;
    TextView name, username, semester, section, department;
    Button btnSend, btnCall;
    public static final String LOGINPREF = "loginpref" ;
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.myself, container, false);

        imgProfilePicture = (ImageView)android.findViewById(R.id.profile_picture);

        email = (TextView)android.findViewById(R.id.email);
        phone = (TextView)android.findViewById(R.id.phone);
        gender = (TextView)android.findViewById(R.id.gender);
        nationality = (TextView)android.findViewById(R.id.nationality);
        fathersName = (TextView)android.findViewById(R.id.fathers_name);
        mothersName = (TextView)android.findViewById(R.id.mothers_name);
        presentAddr = (TextView)android.findViewById(R.id.present_addr);
        parmanentAddr = (TextView)android.findViewById(R.id.parmanent_addr);
        regSession = (TextView)android.findViewById(R.id.reg_session);
        rank = (TextView)android.findViewById(R.id.rank);
        dateOfBirth = (TextView)android.findViewById(R.id.date_of_birth);
        joiningDate = (TextView)android.findViewById(R.id.joining_date);

        name = (TextView)android.findViewById(R.id.full_name);
        semester = (TextView)android.findViewById(R.id.year_and_semester);
        username = (TextView)android.findViewById(R.id.roll_no);
        section = (TextView)android.findViewById(R.id.section);
        department = (TextView)android.findViewById(R.id.department);

        btnSend = (Button)android.findViewById(R.id.send_btn);
        btnCall = (Button)android.findViewById(R.id.btn_call);

        // set the sharedpreferences
        sharedpreferences = getContext().getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);
        String username_text = sharedpreferences.getString("USERNAME", "");

        if(!username_text.equals("")){
            new InfoActivity(getContext(), imgProfilePicture, btnSend, btnCall, email, phone, gender, nationality, fathersName, mothersName, presentAddr,
                    parmanentAddr, regSession, rank, dateOfBirth, joiningDate, name, semester, username, section, department).execute(username_text);
        }

        return android;
    }
}
