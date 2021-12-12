package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOError;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        MovieDatabase mdb = MovieDatabase.getInstance(getContext());
        PersonDatabase appDB = PersonDatabase.getInstance(getContext());

        TextView usernameText = (TextView) view.findViewById(R.id.username);
        usernameText.setText("Hello @" + LoginActivity.loggedUser + "!");

        //Log out
        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button ranking = (Button) view.findViewById(R.id.ranking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RankingActivity.class);
                startActivity(intent);
            }
        });

        //Change region
        Button changeRegion = (Button) view.findViewById(R.id.change_region);
        changeRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeRegionActivity.class);
                startActivity(intent);
            }
        });

        // Edit password code
        EditText changePassword = (EditText) view.findViewById(R.id.edit_password);
        Button confirmChange = (Button) view.findViewById(R.id.confirm);

        confirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = changePassword.getText().toString();
                changePassword.setText("");

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Person> p = appDB.personDAO().getPersonList();
                        int tmp = 0;

                        for(int i = 0; i < p.size(); i++){
                            if(LoginActivity.loggedUser.equals(p.get(i).getNickname())){
                                System.out.println(p.get(i).getPassword());

                                p.get(i).setPassword(newPassword);
                                tmp = i;

                                break;
                            }
                        }

                        System.out.println(p.get(tmp).getPassword());
                        appDB.personDAO().updatePerson(p.get(tmp));
                    }
                });
            }
        });

        //Aggiunta immagine sfondo
        int min = 0;
        int max = Integer.MAX_VALUE;
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(112) + 2;

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MovieDetails> call = myInterface.listOfMovies(randomNumber, MainActivity.API_KEY);

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails results = response.body();
                ImageView backdrop = (ImageView) view.findViewById(R.id.backdrop);
                try {
                    new DownloadImageTask(backdrop).execute(MainActivity.IMG_URL + results.getBackdrop_path());
                } catch (Exception e ){
                    new DownloadImageTask(backdrop).execute(MainActivity.IMG_URL + "/cCvp5Sni75agCtyJkNOMapORUQV.jpg");
                }

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });

        return view;
    }
}
