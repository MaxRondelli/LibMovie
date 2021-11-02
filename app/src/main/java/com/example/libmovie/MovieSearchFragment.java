package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSearchFragment extends Fragment implements SearchView.OnQueryTextListener, RecyclerViewAdapter.ItemClickListener {
    List<MovieClass> movieList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    View view;
    String query;
    View searchBar;
    SearchView search;

    private RecyclerViewAdapter.ItemClickListener listener;

    MovieSearchFragment(View searchBar) {
        this.searchBar = searchBar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        manager = new LinearLayoutManager(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_movie);
        recyclerView.setLayoutManager(manager);
        search = (SearchView) searchBar.findViewById(R.id.search_bar);
        search.setOnQueryTextListener(this);

        movieList.clear();

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

                            movieList.add(new MovieClass(results2.getTitle(), results2.getOverview(), results2.getRelease_date(),MainActivity.IMG_URL + results2.getPoster_path(), results2.getVote_average(), gen_list));

                            recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, MovieSearchFragment.this);
                            recyclerViewAdapter.notifyDataSetChanged();
                            recyclerViewAdapter.setClickListener(MovieSearchFragment.this);
                            recyclerView.setAdapter(recyclerViewAdapter);

                            recyclerView.addOnItemTouchListener(
                                    new RecyclerItemListener(getContext(), recyclerView ,new RecyclerItemListener.OnItemClickListener() {
                                        @Override public void onItemClick(View view, int position) {
                                            System.err.println("ciao -> " + position + " " + movieList.get(position).getTitle());
                                        }

                                        @Override public void onLongItemClick(View view, int position) {
                                            // do whatever
                                        }
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



        return view;
    }

    public void reload() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_movie);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, this);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(view.getContext(), movieList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_movie);
        RecyclerViewAdapter recyclerViewAdapter;

        List<MovieClass> movieList = new ArrayList<>();

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().toLowerCase().contains(s)) {

            }
        }


        return false;
    }
}
