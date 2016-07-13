package com.shamsapp.shamscorner.com.pocketuni_forum.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamsapp.shamscorner.com.pocketuni_forum.InfoActivity;
import com.shamsapp.shamscorner.com.pocketuni_forum.R;

public class ContactDetailsView extends AppCompatActivity {

    ImageView imgProfilePicture;
    TextView email, phone, gender, nationality, fathersName, mothersName, presentAddr, parmanentAddr, regSession, rank,
            dateOfBirth, joiningDate;
    TextView name, username, semester, section, department;
    Button btnSend, btnCall;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("USERNAME");
        }

        imgProfilePicture = (ImageView)findViewById(R.id.profile_picture);

        email = (TextView)findViewById(R.id.email);
        phone = (TextView)findViewById(R.id.phone);
        gender = (TextView)findViewById(R.id.gender);
        nationality = (TextView)findViewById(R.id.nationality);
        fathersName = (TextView)findViewById(R.id.fathers_name);
        mothersName = (TextView)findViewById(R.id.mothers_name);
        presentAddr = (TextView)findViewById(R.id.present_addr);
        parmanentAddr = (TextView)findViewById(R.id.parmanent_addr);
        regSession = (TextView)findViewById(R.id.reg_session);
        rank = (TextView)findViewById(R.id.rank);
        dateOfBirth = (TextView)findViewById(R.id.date_of_birth);
        joiningDate = (TextView)findViewById(R.id.joining_date);

        name = (TextView)findViewById(R.id.full_name);
        semester = (TextView)findViewById(R.id.year_and_semester);
        username = (TextView)findViewById(R.id.roll_no);
        section = (TextView)findViewById(R.id.section);
        department = (TextView)findViewById(R.id.department);

        btnSend = (Button)findViewById(R.id.send_btn);
        btnCall = (Button)findViewById(R.id.btn_call);

        new InfoActivity(this, imgProfilePicture, btnSend, btnCall, email, phone, gender, nationality, fathersName, mothersName, presentAddr,
                parmanentAddr, regSession, rank, dateOfBirth, joiningDate, name, semester, username, section, department).execute("123086");
    }
}
