package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by shamim on 06-Jun-16.
 */
public class NoticeEvents extends Fragment {

    View android;

    public final static int LOOPS = 1000;
    public static int FIRST_PAGE; // = count*LOOPS/2;
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.8f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    public CarouselPagerAdapter adapter;
    public ViewPager pager;

    public static NoticeEvents ctx;

    /***
     * variables for the View
     */
    public static int count = 14;
    public static int currentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        android = inflater.inflate(R.layout.events, container, false);

        ctx = this;

        // this is the section for the view pager
        pager = (ViewPager) android.findViewById(R.id.carousel);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int pageMargin = 0;
        pageMargin = (int) ((metrics.widthPixels / 4));
        pager.setPageMargin(-pageMargin);

        try {
            adapter = new CarouselPagerAdapter(getContext(), getFragmentManager());
            pager.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            FIRST_PAGE = count * LOOPS / 2;

            pager.setOnPageChangeListener(adapter);
            // Set current item to the middle page so we can fling to both
            // directions left and right
            pager.setCurrentItem(FIRST_PAGE); // FIRST_PAGE
            // pager.setFocusableInTouchMode(true);
            pager.setOffscreenPageLimit(3);
            // Set margin for pages as a negative number, so a part of next and
            // previous pages will be showed

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return android;
    }
}
