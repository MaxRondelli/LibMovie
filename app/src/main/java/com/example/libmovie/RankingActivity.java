package com.example.libmovie;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<MovieClass> movieList = new ArrayList<>();
    RankRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        movieList.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        manager = new LinearLayoutManager(getBaseContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_ranking);
        recyclerView.setLayoutManager(manager);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                RatingDatabase rdb = RatingDatabase.getInstance(getBaseContext());
                List<RatingClass> ratingClass = rdb.ratingDAO().getMovieList();

                for(int i=0; i<ratingClass.size(); i++){
                    int id = ratingClass.get(i).movie_id;
                    double rating = ratingClass.get(i).rating;

                    Retrofit retrofit = new Retrofit.Builder().
                            baseUrl(MainActivity.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
                    ApiInterface myInterface = retrofit.create(ApiInterface.class);

                    Call<MovieDetails> call = myInterface.listOfMovies(id, MainActivity.API_KEY);

                    call.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            MovieDetails results = response.body();
                            movieList.add(new MovieClass(results.getTitle(), String.valueOf(rating), "", MainActivity.IMG_URL + results.getPoster_path(), 0, null, results.getId()));
                            recyclerViewAdapter = new RankRecyclerViewAdapter(getBaseContext(), movieList, "aa", R.layout.listranking);
                            recyclerViewAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(recyclerViewAdapter);
                        }


                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {}
                    });
                    System.out.println(ratingClass.get(i).movie_id + " " +  ratingClass.get(i).rating);
                }

            }
        });
    }
}

