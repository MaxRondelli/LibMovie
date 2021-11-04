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

public class PeopleDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("personId");
        System.out.println(id);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<PeopleDetailsClass> call = myInterface.PeopleDetails(id, MainActivity.API_KEY, MainActivity.LANGUAGE);

        call.enqueue(new Callback<PeopleDetailsClass>() {
            @Override
            public void onResponse(Call<PeopleDetailsClass> call, Response<PeopleDetailsClass> response) {
                PeopleDetailsClass results = response.body();

                //Aggiunta immagine sfondo
                try {
                    ImageView profile = (ImageView) findViewById(R.id.profile);
                    new DownloadImageTask(profile).execute(MainActivity.IMG_URL + results.getProfile_path());
                } catch (Exception e) {
                    System.out.println(results.getProfile_path());
                }


                //Aggiunta titolo
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(results.getName());

                //Aggiunta biografia
                TextView biography = (TextView) findViewById(R.id.biography);
                biography.setText(results.getBiography());
            }

            @Override
            public void onFailure(Call<PeopleDetailsClass> call, Throwable t) {}
        });
    }
}
