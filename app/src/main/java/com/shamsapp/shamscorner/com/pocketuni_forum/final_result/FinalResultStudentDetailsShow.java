package com.shamsapp.shamscorner.com.pocketuni_forum.final_result;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

public class FinalResultStudentDetailsShow extends AppCompatActivity {

    private int semesterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_student_details_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Snackbar snackbar = Snackbar.make(view, "Credit Registered : 20.25\nCredit" +
                            " Earned : 20.25\nCumulative Earned Credit : 83.25\nGPA : 3.75\nCGPA : 3.56", Snackbar.LENGTH_INDEFINITE);
                    // floating action button-does-not-come-down-when-dismissing-snackbar-solved
                    snackbar.getView().addOnAttachStateChangeListener( new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow( View v ) {

                        }

                        @Override
                        public void onViewDetachedFromWindow( View v ) {
                            fab.setTranslationY( 0 );
                        }
                    });
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // hide and dismiss the snack bar
                            snackbar.dismiss();
                        }
                    });
                    // make the snack bar multiline
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5); // set the line because snackbar default support only 2 lines
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.bk_main)); // set the text color
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.off_white)); // set the background color of snackbar view
                    snackbar.show();
                }
            });
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            semesterID = extras.getInt("ID");
        }

        TextView tvSemsterId = (TextView) findViewById(R.id.tv_semester_result_final);
        tvSemsterId.setText("Result For Semester - " + semesterID);
    }

}
