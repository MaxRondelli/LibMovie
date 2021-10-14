package com.example.libmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<MovieClass> movieList = new ArrayList<>();
    static List<ActorClass> actorList = new ArrayList<>();
    static List<DirectorClass> directorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        up = 0;
        movieList.add(new MovieClass("Le ali della libertà", R.drawable.icon_android, true, true, true));
        movieList.add(new MovieClass("Il padrino", R.drawable.icon_android, true, true, true));
        movieList.add(new MovieClass("Il padrino - Parte II", R.drawable.icon_android, false, true, false));
        movieList.add(new MovieClass("Il cavaliere oscuro", R.drawable.icon_android, false, false, true));

        actorList.add(new ActorClass("Ciccio Marino", R.drawable.icon_android));
        actorList.add(new ActorClass("Ciccio Top", R.drawable.icon_android));
        actorList.add(new ActorClass("Ciccio Stop", R.drawable.icon_android));

        directorList.add(new DirectorClass("Simone Basile", R.drawable.icon_android));
        directorList.add(new DirectorClass("Simone Top", R.drawable.icon_android));
        directorList.add(new DirectorClass("Simone Stop", R.drawable.icon_android));


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
