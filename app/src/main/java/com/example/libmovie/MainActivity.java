package com.example.libmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static int sort_param = 0;
    static List<MovieClass> movieList = new ArrayList<>();
    static List<ActorClass> actorList = new ArrayList<>();
    static List<DirectorClass> directorList = new ArrayList<>();
    public static String BASE_URL = "https://api.themoviedb.org";
    public static String API_KEY = "c1180ea0157a385a1b0a30ba3183e640";
    public static int PAGE = 1;
    public static String REGION = "IT";
    static String LANGUAGE = "it-IT";
    static String img_url = "https://image.tmdb.org/t/p/w500";
    Fragment selectedFragment = new HomeFragment() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();


        NavigationBarView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @SuppressLint("NonConstantResourceId")
    private final NavigationBarView.OnItemSelectedListener navListener =
            item -> {
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
