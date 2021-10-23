package com.example.libmovie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.PathInterpolator;
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
    final float from = 1.0f;
    final float to = 1.3f;
    //boolean isActive = true;

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
        if(MainActivity.sort_param==0) MainActivity.sort_param=-1;

        //creating the path using coordinates of the animation
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.5f,1.3f);
        path.lineTo(0.75f, 0.8f);
        path.lineTo(1.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(path);

        //create the first animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(sort, getView().SCALE_X, from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(sort, View.SCALE_Y,  from, to);
        ObjectAnimator translationZ = ObjectAnimator.ofFloat(sort, View.TRANSLATION_Z, from, to);

        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(scaleX, scaleY, translationZ);
        set1.setDuration(100);
        set1.setInterpolator(new AccelerateInterpolator());

        /*set1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //sort.setImageResource(isActive ? R.drawable.heart_active : R.drawable.heart_passive);
                //sort.setBackgroundTintList(ColorStateList.valueOf(isActive ? colorActive : colorPassive));
                //sort.setImageDrawable(ContextCompat.getDrawable(getContext(),  MainActivity.sort_param > 0 ? R.drawable.icon_za: R.drawable.icon_az));
                isActive = !isActive;
            }
        });*/

        //creating animation to settle back
        ObjectAnimator scaleXBack = ObjectAnimator.ofFloat(sort, View.SCALE_X, to, from);
        ObjectAnimator scaleYBack = ObjectAnimator.ofFloat(sort, View.SCALE_Y, to, from);
        ObjectAnimator translationZBack = ObjectAnimator.ofFloat(sort, View.TRANSLATION_Z, to, from);

        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(scaleXBack, scaleYBack, translationZBack);
        set2.setDuration(300);
        set2.setInterpolator(pathInterpolator);

        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(set1, set2);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                sort.setEnabled(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                sort.setEnabled(false);
            }
        });

        /*sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.start();
            }
        });*/

        set.start();

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
