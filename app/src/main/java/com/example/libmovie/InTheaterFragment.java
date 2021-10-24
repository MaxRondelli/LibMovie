package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class InTheaterFragment extends Fragment {
    List<MovieClass> tmp = new ArrayList<>();
    ListAdapter la;
    View view;
    ListView lw;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_theater, container, false);

        lw = (ListView) view.findViewById(R.id.list);

        tmp.clear();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = myInterface.listOfMovies("now_playing", MainActivity.API_KEY, MainActivity.LANGUAGE, MainActivity.PAGE, MainActivity.REGION);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();


                for(int i=0; i<listOfMovies.size(); i++){
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);


                    Call<MovieDetails> call1 = myInterface.listOfMovies(curr.getId(),MainActivity.API_KEY);
                    call1.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            MovieDetails results2 = response.body();
                            List<MovieDetails.GenresDTO> genre = results2.getGenres();
                            List<String> gen_list = new ArrayList<>();
                            for(int j=0; j<genre.size(); j++){
                                gen_list.add(genre.get(j).getName());
                            }
                            tmp.add(new MovieClass(results2.getTitle(),MainActivity.img_url + results2.getPoster_path(),results2.getRelease_date(),results2.getOverview(),gen_list));


                            la = new ListAdapter(getActivity().getBaseContext(),tmp);
                            la.notifyDataSetChanged();
                            lw.setAdapter(la);

                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {

                        }
                    });


                }



            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });


        la = new ListAdapter(getActivity().getBaseContext(),tmp);
        la.notifyDataSetChanged();
        lw.setAdapter(la);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getActivity(), "" + position + " - " + tmp.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });
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
        lw = (ListView) view.findViewById(R.id.list);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = myInterface.listOfMovies("now_playing", MainActivity.API_KEY, MainActivity.LANGUAGE, MainActivity.PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();
                MovieResults.ResultsDTO firstMovie = listOfMovies.get(0);

                for(int i=0; i<listOfMovies.size(); i++){
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);
                    tmp.add(new MovieClass(curr.getTitle(),MainActivity.img_url + curr.getPoster_path(),curr.getRelease_date()));
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
