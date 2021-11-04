package com.example.libmovie;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("movieId");

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
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {}
        });
    }
}
