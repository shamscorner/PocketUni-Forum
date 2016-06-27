package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by shamim on 18-Jun-16.
 */
public class CarouselPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private CarouselLayout cur = null;
    private CarouselLayout next = null;
    private Context context;
    private FragmentManager fm;
    private float scale;
    int pCount = 0;

    public CarouselPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others

        try {
            if (position == NoticeEvents.FIRST_PAGE)
                scale = NoticeEvents.SMALL_SCALE;
            else
                scale = NoticeEvents.SMALL_SCALE;

            position = position % NoticeEvents.count;

        } catch (Exception e) {
            // TODO: handle exception
        }

        return CarouselFragment.newInstance(context, position, scale);
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            count = NoticeEvents.count * NoticeEvents.LOOPS;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        /*
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                cur = getRootView(position);
                next = getRootView(position + 1);

                cur.setScaleBoth(NoticeEvents.BIG_SCALE - NoticeEvents.DIFF_SCALE * positionOffset);
                next.setScaleBoth(NoticeEvents.SMALL_SCALE + NoticeEvents.DIFF_SCALE * positionOffset);

                pCount++;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private CarouselLayout getRootView(int position) {
        return (CarouselLayout) fm.findFragmentByTag(this.getFragmentTag(position)).getView().findViewById(R.id.root);
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + NoticeEvents.ctx.getId() + ":" + position;
    }
}
