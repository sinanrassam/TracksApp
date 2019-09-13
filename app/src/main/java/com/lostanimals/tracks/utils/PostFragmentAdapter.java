package com.lostanimals.tracks.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PostFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;

    public PostFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int index) {
        return this.mFragments.get(index);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }
}
