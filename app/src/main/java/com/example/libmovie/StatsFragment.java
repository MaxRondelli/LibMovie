package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsFragment extends Fragment {
    List<MovieClass> movieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        MovieDatabase mdb = MovieDatabase.getInstance(getContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            int notWatched = 0;
            int watched = 0;
            int minutesWatched = 0;
            int minutesNotWatched = 0;

            @Override
            public void run() {
                List<JoinClass> listMovie = mdb.movieDAO().getMovieList();

                Retrofit retrofit = new Retrofit.Builder().
                        baseUrl(MainActivity.BASE_URL).
                        addConverterFactory(GsonConverterFactory.create()).
                        build();
                ApiInterface myInterface = retrofit.create(ApiInterface.class);

                for (int i = 0; i < listMovie.size(); i++) {
                    if (LoginActivity.loggedUser.equals(listMovie.get(i).nickname) && listMovie.get(i).status == 1) {
                        notWatched++;

                        Call<MovieDetails> call = myInterface.listOfMovies(listMovie.get(i).movie_id, MainActivity.API_KEY);

                        call.enqueue(new Callback<MovieDetails>() {
                            @Override
                            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                                MovieDetails results = response.body();
                                minutesNotWatched += results.getRuntime();

                                System.out.println(results.getRuntime());
                            }

                            @Override
                            public void onFailure(Call<MovieDetails> call, Throwable t) {
                            }
                        });
                    }

                    if (LoginActivity.loggedUser.equals(listMovie.get(i).nickname) && listMovie.get(i).status == 2) {
                        watched++;

                        Call<MovieDetails> call = myInterface.listOfMovies(listMovie.get(i).movie_id, MainActivity.API_KEY);

                        call.enqueue(new Callback<MovieDetails>() {
                            @Override
                            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                                MovieDetails results = response.body();
                                minutesWatched += results.getRuntime();

                                System.out.println(results.getRuntime());
                            }

                            @Override
                            public void onFailure(Call<MovieDetails> call, Throwable t) {
                            }
                        });
                    }
                }

                System.out.println(notWatched);
                System.out.println(watched);
            }
        });

        TextView watchedMovieNumber = (TextView) view.findViewById(R.id.watched_movies);
        TextView notWatchedMovieNumber = (TextView) view.findViewById(R.id.movies_to_watch);

        return view;
    }
}
