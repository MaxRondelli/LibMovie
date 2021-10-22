package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComingSoonFragment extends Fragment {
    List<MovieClass> tmp = new ArrayList<>();
    ListAdapter la;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        ListView lw = (ListView) view.findViewById(R.id.list);

        tmp.clear();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = myInterface.listOfMovies("upcoming", MainActivity.API_KEY, MainActivity.LANGUAGE, MainActivity.PAGE, MainActivity.REGION);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();
                MovieResults.ResultsDTO firstMovie = listOfMovies.get(0);

                for(int i=0; i<listOfMovies.size(); i++){
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);
                    tmp.add(new MovieClass(curr.getTitle(),MainActivity.img_url + curr.getPoster_path()));
                }

                la = new ListAdapter(getActivity().getBaseContext(),tmp);
                la.notifyDataSetChanged();
                lw.setAdapter(la);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });


        la = new ListAdapter(getActivity().getBaseContext(),tmp);
        la.notifyDataSetChanged();
        lw.setAdapter(la);

        return view;
    }

    public void sorta(){
        for(int i=0; i<tmp.size(); i++){
            int min = i;
            for(int j=i+1; j<tmp.size(); j++){
                if(MainActivity.sort_param==-1 && tmp.get(min).name.compareTo(tmp.get(j).name)>0){
                    min = j;
                }
                if(MainActivity.sort_param==1 && tmp.get(min).name.compareTo(tmp.get(j).name)<0){
                    min = j;
                }
            }
            Collections.swap(tmp,i,min);
        }
    }

    public void reload(){

        tmp.clear();
        ListView lw = (ListView) view.findViewById(R.id.list);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = myInterface.listOfMovies("upcoming", MainActivity.API_KEY, MainActivity.LANGUAGE, MainActivity.PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();
                MovieResults.ResultsDTO firstMovie = listOfMovies.get(0);

                for(int i=0; i<listOfMovies.size(); i++){
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);
                    tmp.add(new MovieClass(curr.getTitle(),MainActivity.img_url + curr.getPoster_path()));
                }

                sorta();

                la = new ListAdapter(getActivity().getBaseContext(),tmp);
                la.notifyDataSetChanged();
                lw.setAdapter(la);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

        System.out.println(tmp.size());


    }
}
