package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeContentFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    List<MovieClass> movieList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    View view;
    String query;

    private RecyclerViewAdapter.ItemClickListener listener;

    HomeContentFragment(String query) {
        this.query = query;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_content, container, false);
        manager = new LinearLayoutManager(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(manager);
        movieList.clear();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = myInterface.listOfMovies(query, MainActivity.API_KEY, MainActivity.LANGUAGE, MainActivity.PAGE, MainActivity.REGION);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);

                    Call<MovieDetails> call1 = myInterface.listOfMovies(curr.getId(), MainActivity.API_KEY);
                    call1.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            MovieDetails results2 = response.body();

                            List<MovieDetails.GenresDTO> genre = results2.getGenres();
                            List<String> gen_list = new ArrayList<>();
                            for (int j = 0; j < genre.size(); j++) {
                                gen_list.add(genre.get(j).getName());
                            }

                            movieList.add(new MovieClass(results2.getTitle(), results2.getOverview(), results2.getRelease_date(),MainActivity.IMG_URL + results2.getPoster_path(), results2.getVote_average(), gen_list, results2.getId()));

                            recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, HomeContentFragment.this);
                            recyclerViewAdapter.notifyDataSetChanged();
                            recyclerViewAdapter.setClickListener(HomeContentFragment.this);
                            recyclerView.setAdapter(recyclerViewAdapter);

                            recyclerView.addOnItemTouchListener(
                                    new RecyclerItemListener(getContext(), recyclerView ,new RecyclerItemListener.OnItemClickListener() {
                                        @Override public void onItemClick(View view, int position) {
                                            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                                            intent.putExtra("movieId", movieList.get(position).getId());
                                            startActivity(intent);
                                        }

                                        @Override public void onLongItemClick(View view, int position) {}
                                    })
                            );
                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, HomeContentFragment.this);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setClickListener(HomeContentFragment.this);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    public void sort(){
        for (int i = 0; i < movieList.size(); i++) {
            int min = i;
            for (int j = i + 1; j < movieList.size(); j++){
                if (MainActivity.sort_param == -1 && movieList.get(min).getTitle().compareTo(movieList.get(j).getTitle()) > 0) {
                    min = j;
                }
                if (MainActivity.sort_param == 1 && movieList.get(min).getTitle().compareTo(movieList.get(j).getTitle()) < 0) {
                    min = j;
                }
            }
            Collections.swap(movieList, i, min);
        }
    }

    public void reload() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        sort();

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, this);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(view.getContext(), movieList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}
