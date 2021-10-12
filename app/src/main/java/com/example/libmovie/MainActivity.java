package com.example.libmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Movie;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<MovieClass> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList.add(new MovieClass("film1", R.drawable.icon_android));
        movieList.add(new MovieClass("film2", R.drawable.icon_account));
        movieList.add(new MovieClass("film3", R.drawable.icon_android));
        movieList.add(new MovieClass("film4", R.drawable.icon_search));


        NavigationBarView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @SuppressLint("NonConstantResourceId")
    private final NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = new HomeFragment() ;

                switch(item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.library:
                        selectedFragment = new LibraryFragment();
                        break;

                    case R.id.search:
                        selectedFragment = new SearchFragment();
                        break;

                    case R.id.stats:
                        selectedFragment = new StatsFragment();
                        break;

                   case R.id.account:
                        selectedFragment = new AccountFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            };
}