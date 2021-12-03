package com.example.libmovie;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {
    boolean hasrating = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("movieId");



        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);

        MovieDatabase mdb = MovieDatabase.getInstance(this);
        RatingDatabase rdb = RatingDatabase.getInstance(this);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<RatingClass> list_rating = rdb.ratingDAO().getMovieList();

                for(int i=0; i<list_rating.size(); i++){
                    if(LoginActivity.loggedUser.equals(list_rating.get(i).nickname) && list_rating.get(i).movie_id==id){
                        rb.setRating((float)list_rating.get(i).rating);
                        hasrating = true;
                    }
                }

            }
        });


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<JoinClass> listmovie = mdb.movieDAO().getMovieList();

                System.err.println(listmovie.size());
                for(int i=0; i<listmovie.size(); i++){
                    if(LoginActivity.loggedUser.equals(listmovie.get(i).nickname))
                        System.out.println(i + "->" + listmovie.get(i).movie_id);
                }
            }
        });

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<RatingClass> list_rating = rdb.ratingDAO().getMovieList();
                        if(!hasrating){
                            rdb.ratingDAO().insertMovie(new RatingClass(LoginActivity.loggedUser,id,rating));
                        }
                        else{
                            rdb.ratingDAO().updateMovie(new RatingClass(LoginActivity.loggedUser,id,rating));
                        }
                    }
                });


            }

        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);

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

        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
        floatingActionButton.setImageResource(R.drawable.icon_add);

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
                description.setText(results.getOverview());

                //Aggiunta anno uscita
                TextView year = (TextView) findViewById(R.id.year);
                year.setText(results.getRelease_date() + " \u2022");

                //Aggiunta durata
                TextView runtime = (TextView) findViewById(R.id.runtime);
                runtime.setText(results.getRuntime() + " min");

                //Aggiunta genere
                TextView genres = (TextView) findViewById(R.id.genre);
                String tmp = "";
                for (int i = 0; i < results.getGenres().size(); i++) {
                    tmp += results.getGenres().get(i).getName().trim();
                    if (i < 2) tmp += ", ";
                }
                genres.setText(tmp + " \u2022 ");

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<JoinClass> listmovie = mdb.movieDAO().getMovieList();

                        for(int i=0; i<listmovie.size(); i++){
                            if(LoginActivity.loggedUser.equals(listmovie.get(i).nickname) && results.getId()==listmovie.get(i).movie_id){
                                switch(listmovie.get(i).status){
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

                        }
                    }
                });


            // Aggiunta in libreria ( Watched / Not Watched / Remove it)
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MovieDatabase mdb = MovieDatabase.getInstance(MovieDetailsActivity.this);
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                List<JoinClass> listmovie = mdb.movieDAO().getMovieList();

                                for(int i=0; i<listmovie.size(); i++) {
                                    if (LoginActivity.loggedUser.equals(listmovie.get(i).nickname) && results.getId() == listmovie.get(i).movie_id) {
                                        mdb.movieDAO().updateMovie(new JoinClass(LoginActivity.loggedUser,results.getId(),(listmovie.get(i).status+1)%3));
                                        switch((listmovie.get(i).status+1)%3){
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
                                        return;
                                    }
                                }
                                mdb.movieDAO().insertMovie(new JoinClass(LoginActivity.loggedUser,results.getId(),1));
                                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F1C40F")));
                                floatingActionButton.setImageResource(R.drawable.icon_check);
                            }
                        });
                    }
                });
                // Fine aggiunta il libreria ( Watched / Not Watched / Remove it )
            }


            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {}
        });
    }

}
