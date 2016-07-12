package com.shamsapp.shamscorner.com.pocketuni_forum.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

/**
 * Created by shamim on 13-Jul-16.
 */
public class ContactTeacherSection extends Fragment {
    public ContactTeacherSection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contact_teacher, container, false);
    }
}
