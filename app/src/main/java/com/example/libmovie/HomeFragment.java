package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Collections;

public class HomeFragment extends Fragment implements View.OnClickListener {
    InTheaterFragment it = new InTheaterFragment();
    ComingSoonFragment cs = new ComingSoonFragment();
    TopRatedFragment tr = new TopRatedFragment();
    MostPopularFragment mp = new MostPopularFragment();
    FloatingActionButton sort;
    int curr = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.sort_param = 1;

        boolean t1=true,t2=false,t3=false,t4=false;

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_home);
        ViewPager2 pager2 = view.findViewById(R.id.viewpager_home);

        sort = (FloatingActionButton) view.findViewById(R.id.button_sort);

        sort.setOnClickListener(this);

        FragmentAdapter adapter = new FragmentAdapter(
                getActivity().getSupportFragmentManager(),
                getLifecycle(),
                tabLayout.getTabCount(),
                it,
                cs,
                tr,
                mp);

        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
                curr = tab.getPosition();
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

    @Override
    public void onClick(View view) {
        MainActivity.sort_param = MainActivity.sort_param*-1;
        if(MainActivity.sort_param==0)MainActivity.sort_param=-1;
       switch (curr) {
                    case 0:
                        it.reload();
                        break;
                    case 1:
                        cs.reload();
                        break;
                    case 2:
                        tr.reload();
                        break;
                    case 3:
                        mp.reload();
                        break;
                }
        if(MainActivity.sort_param == 1){
            sort.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), R.drawable.icon_az));
        }
        else{
            sort.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), R.drawable.icon_za));
        }
    }
}
