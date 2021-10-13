package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    static String filter="";
    boolean t1=true,t2=false,t3=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_search);
        ViewPager2 pager2 = view.findViewById(R.id.viewpager_search);

        MovieSearchFragment ms = new MovieSearchFragment(view);
        ActorSearchFragment as = new ActorSearchFragment(view);
        DirectorSearchFragment ds = new DirectorSearchFragment(view);

        FragmentAdapter adapter = new FragmentAdapter(
                getActivity().getSupportFragmentManager(),
                getLifecycle(),
                tabLayout.getTabCount(),
                ms,
                as,
                ds
        );

        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        if(t1)ms.reload();
                        else t1=true;
                        break;
                    case 1:
                        if(t2)as.reload();
                        else t2=true;
                    break;
                    case 2:
                        if(t3)ds.reload();
                        else t3 = true;
                    break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return view;
    }
}
