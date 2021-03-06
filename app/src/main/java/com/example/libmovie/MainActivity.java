package com.example.libmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.graphics.Movie;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationBarView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static int sort_param = 0;

    //Test aggiunta in libreria
    public static List<MovieClass> movieListId = new ArrayList<>();
    //Fine test

    public static String BASE_URL = "https://api.themoviedb.org";
    public static String API_KEY = "c1180ea0157a385a1b0a30ba3183e640";
    public static int PAGE = 1;
    public static String REGION = "US";
    static String LANGUAGE = "en-US";
    static String IMG_URL = "https://image.tmdb.org/t/p/w500";
    static OutputStreamWriter ratfile;

    Fragment selectedFragment = new HomeFragment() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("rating.txt", MODE_PRIVATE);
            ratfile= new OutputStreamWriter(fOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        PersonDatabase appDB = PersonDatabase.getInstance(this);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
            }
        });



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
