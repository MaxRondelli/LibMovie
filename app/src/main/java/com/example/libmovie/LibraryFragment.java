package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.tabs.TabLayout;

public class LibraryFragment extends Fragment {
    boolean t1 = true, t2 = false;
    static int layout = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_library);
        ViewPager2 pager2 = view.findViewById(R.id.viewpager_library);

        NotWatchedFragment notWatchedFragment = new NotWatchedFragment();
        WatchedFragment watchedFragment = new WatchedFragment();

        // INIZIO CODICE MENU LIBRERIA
        FloatingActionMenu floatingActionMenu = view.findViewById(R.id.fab_menu);
        FloatingActionButton layoutButton = floatingActionMenu.findViewById(R.id.layout_style);
        FloatingActionButton orderButton = floatingActionMenu.findViewById(R.id.order_by);

        layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout = 1 - layout;
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        if (t1) notWatchedFragment.reload();
                        else t1 = true;
                        break;
                    case 1:
                        if (t2) watchedFragment.reload();
                        else t2 = true;
                        break;
                }
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.sort_param = -1 * MainActivity.sort_param;
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        if (t1) notWatchedFragment.sort();
                        else t1 = true;
                        break;
                    case 1:
                        if (t2) watchedFragment.sort();
                        else t2 = true;
                        break;
                }
            }
        });
        // FINE CODICE MENU LIBRERIA

        FragmentAdapter adapter = new FragmentAdapter(
                getActivity().getSupportFragmentManager(),
                getLifecycle(),
                tabLayout.getTabCount(),
                notWatchedFragment,
                watchedFragment
        );

        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        MainActivity.sort_param = 1;
                        //if (t1) ;// notWatchedFragment.reload();
                        //else t1 = true;
                        break;
                    case 1:
                        MainActivity.sort_param = 1;
                        //if (t2);// watchedFragment.reload();
                        //else t2 = true;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
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
