package com.example.libmovie;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    private final int numOfTabs;
    private final Fragment[] fragments;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int numOfTabs, Fragment... fragments) {
        super(fragmentManager, lifecycle);
        this.numOfTabs = numOfTabs;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return fragments[0];
            case 1:
                return fragments[1];
            case 2:
                return fragments[2];
            case 3:
                return fragments[3];
        }
        return fragments[0];
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }
}
