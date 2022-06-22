package com.example.some.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.some.fragment.FragmentInfo;
import com.example.some.fragment.FragmentList;
import com.example.some.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentList();
            case 1:
                return new FragmentInfo();
            case 2:
                return new FragmentSearch();
            default:
                return new FragmentList();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}