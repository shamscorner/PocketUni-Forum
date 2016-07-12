package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;

import com.shamsapp.shamscorner.com.pocketuni_forum.contacts.ContactOtherSection;
import com.shamsapp.shamscorner.com.pocketuni_forum.contacts.ContactStudentSection;
import com.shamsapp.shamscorner.com.pocketuni_forum.contacts.ContactTeacherSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamim on 06-Jun-16.
 */
public class Contact extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.contact, container, false);

        viewPager = (ViewPager) android.findViewById(R.id.viewpager_contact);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) android.findViewById(R.id.tabs_contact);
        tabLayout.setupWithViewPager(viewPager);

        return android;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new ContactStudentSection(), "Student");
        adapter.addFragment(new ContactTeacherSection(), "Teacher");
        adapter.addFragment(new ContactOtherSection(), "Others");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
