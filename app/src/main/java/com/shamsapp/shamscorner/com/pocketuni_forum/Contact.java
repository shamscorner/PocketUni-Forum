package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by shamim on 06-Jun-16.
 */
public class Contact extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.contact, container, false);
        ((TextView) android.findViewById(R.id.textView)).setText("Contact");
        return android;
    }
}
