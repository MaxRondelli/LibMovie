package com.example.libmovie;

import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
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

public class MovieSearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchRecyclerViewAdapter.ItemClickListener {
    List<MovieClass> movieList = new ArrayList<>();
    SearchRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    GridLayoutManager manager;
    View view;
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
        manager = new GridLayoutManager(view.getContext(), 3);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_movie);
        recyclerView.setLayoutManager(manager);
        search = (SearchView) searchBar.findViewById(R.id.search_bar);

        search.setOnQueryTextListener(this);

        movieList.clear();

        return view;
    }

    public void reload() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_movie);

        recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchdesign);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
        search.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(view.getContext(), movieList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        s = s.toLowerCase();

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

        Call<MovieResults> call = myInterface.listOfMovies("movie", MainActivity.API_KEY, s);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                movieList.clear();
                MovieResults results = response.body();
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);

                    movieList.add(new MovieClass(curr.getTitle(), "", "", MainActivity.IMG_URL + curr.getPoster_path(), 0, null, curr.getId()));
                    recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity().getBaseContext(), movieList, MovieSearchFragment.this, R.layout.listsearchdesign);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.setClickListener(MovieSearchFragment.this);
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
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();

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

        Call<MovieResults> call = myInterface.listOfMovies("movie", MainActivity.API_KEY, s);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                movieList.clear();
                MovieResults results = response.body();
                if (results == null) return;
                List<MovieResults.ResultsDTO> listOfMovies = results.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {
                    MovieResults.ResultsDTO curr = listOfMovies.get(i);

                    movieList.add(new MovieClass(curr.getTitle(), "", "", MainActivity.IMG_URL + curr.getPoster_path(), 0, null, curr.getId()));
                    recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity().getBaseContext(), movieList, MovieSearchFragment.this, R.layout.listsearchdesign);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.setClickListener(MovieSearchFragment.this);
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
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return false;
    }
}
