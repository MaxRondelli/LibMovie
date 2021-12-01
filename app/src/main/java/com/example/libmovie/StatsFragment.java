package com.example.libmovie;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StatsFragment extends Fragment {

    static int watched=0;
    static int notWatched=0;
    List<MovieClass> movieList = new ArrayList<>();
    List<String> gen_list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        MovieDatabase mdb = MovieDatabase.getInstance(getContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            //int notWatched = 0;
            //int watched = 0;
            int minutesWatched = 0;
            int minutesNotWatched = 0;

            @Override
            public void run() {
                gen_list.clear();
                watched=0;
                notWatched=0;
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

                                TextView minutes_to_watch = (TextView) view.findViewById(R.id.minutes_to_watch);

                                minutes_to_watch.setText(String.valueOf(minutesNotWatched));

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

                                List<MovieDetails.GenresDTO> genre = results.getGenres();
                                for (int j = 0; j < genre.size(); j++) {
                                    gen_list.add(genre.get(j).getName());
                                }

                                new Handler(Looper.getMainLooper()).post(
                                        new Runnable() {
                                            public void run() {
                                                PieChart pieChart;

                                                pieChart = view.findViewById(R.id.piechart);
                                                pieChart.clearChart();

                                                TextView tt = view.findViewById(R.id.lista);
                                                tt.setText("");

                                                for(int i=0; i<gen_list.size(); i++){
                                                    boolean posso = true;
                                                    for(int j=i-1; j>=0; j--){
                                                        if(gen_list.get(i).equals(gen_list.get(j))){
                                                            posso = false;
                                                            break;
                                                        }
                                                    }
                                                    if(!posso)continue;
                                                    int cont = 0;
                                                    for(int j=i; j<gen_list.size(); j++){
                                                        if(gen_list.get(i).equals(gen_list.get(j)))cont++;
                                                    }

                                                    Random rand = new Random();

                                                    tt.setText(tt.getText()  +  "\n" +  gen_list.get(i));

                                                    pieChart.addPieSlice(
                                                            new PieModel(
                                                                    gen_list.get(i),
                                                                    (float)((float)cont/(float)gen_list.size()*100.0),
                                                                    Color.parseColor("#" + String.valueOf(rand.nextInt(10)) + String.valueOf(rand.nextInt(10))  + String.valueOf(rand.nextInt(10)) + String.valueOf(rand.nextInt(10)) + String.valueOf(rand.nextInt(10)) + String.valueOf(rand.nextInt(10)))
                                                            ));

                                                }
                                            }
                                        }
                                );


                                minutesWatched += results.getRuntime();

                                System.out.println(results.getRuntime());

                                TextView minutes_watched = (TextView) view.findViewById(R.id.watched_minutes);

                                minutes_watched.setText(String.valueOf(minutesWatched));
                            }

                            @Override
                            public void onFailure(Call<MovieDetails> call, Throwable t) {
                            }
                        });
                    }

                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                public void run() {

                                    TextView watchedMovieNumber = (TextView) view.findViewById(R.id.watched_movies);
                                    TextView notWatchedMovieNumber = (TextView) view.findViewById(R.id.movies_to_watch);

                                    watchedMovieNumber.setText(String.valueOf(watched));
                                    notWatchedMovieNumber.setText(String.valueOf(notWatched));
                                }
                            }
                    );
                }

                System.out.println(notWatched);
                System.out.println(watched);
            }
        });

        return view;
    }
}
