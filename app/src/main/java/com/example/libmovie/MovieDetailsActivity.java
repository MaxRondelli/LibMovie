package com.example.libmovie;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("movieId");

        // Colorazione del bottone all'apertura dell'activity
        boolean check = false;
        int index = -1;

        for (int i = 0; i < MainActivity.movieListId.size(); i++) {
            if ( MainActivity.movieListId.get(i).equals(id) ){
                check = true;
                index = i;
                break;
            }
        }

        if (check) {
            switch (MainActivity.movieListId.get(index).getStatus()) {
                case 0:
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                    floatingActionButton.setImageResource(R.drawable.icon_add);
                    break;
                case 1:
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F1C40F")));
                    floatingActionButton.setImageResource(R.drawable.icon_check);
                    break;
                case 2:
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#229954")));
                    floatingActionButton.setImageResource(R.drawable.icon_check);
                    break;
            }
        }

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

                //Aggiunta immagine sfondo
                ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
                new DownloadImageTask(backdrop).execute(MainActivity.IMG_URL + results.getBackdrop_path());

                //Aggiunta titolo
                TextView title = (TextView) findViewById(R.id.title);
                title.setText(results.getTitle());

                //Aggiunta descrizione
                TextView description = (TextView) findViewById(R.id.description);
                description.setText("Overview:\n" + results.getOverview());

                // Aggiunta in libreria ( Watched / Not Watched / Remove it)
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean check = false;
                        int index = -1;

                        for (int i = 0; i < MainActivity.movieListId.size(); i++) {
                            if ( MainActivity.movieListId.get(i).equals(id) ){
                                check = true;
                                index = i;
                                break;
                            }
                        }

                        if (check == true) {
                            MainActivity.movieListId.get(index).setStatus((MainActivity.movieListId.get(index).getStatus() + 1 ) % 3 );
                            // Status 2: il film è stato visto
                            if (MainActivity.movieListId.get(index).getStatus() == 2) floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#229954")));
                            // Status 0: il film è stato rimosso
                            if (MainActivity.movieListId.get(index).getStatus() == 0) {
                                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                                MainActivity.movieListId.remove(index);
                                floatingActionButton.setImageResource(R.drawable.icon_add);
                            }
                        } else {
                            // Status 1: il film è stato aggiunto in libreria
                            MovieClass movieClass = new MovieClass(results.getTitle(), results.getOverview(), results.getRelease_date(), MainActivity.IMG_URL + results.getPoster_path(), results.getVote_average(), null, results.getId());
                            movieClass.setStatus(movieClass.getStatus() + 1);
                            MainActivity.movieListId.add(movieClass);
                            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F1C40F")));
                            floatingActionButton.setImageResource(R.drawable.icon_check);
                        }

                        WatchedFragment w = WatchedFragment.wf;
                        NotWatchedFragment nw  = NotWatchedFragment.wf;
                        if (WatchedFragment.c) {
                            if (w != null) w.reload();
                            nw.reload();
                            WatchedFragment.c = false;
                        }
                    }
                });
                // Fine aggiunta il libreria ( Watched / Not Watched / Remove it )
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {}
        });
    }

}
