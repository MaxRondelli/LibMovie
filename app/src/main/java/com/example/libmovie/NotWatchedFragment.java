package com.example.libmovie;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotWatchedFragment extends Fragment implements LibraryRecyclerViewAdapter.ItemClickListener {
    static NotWatchedFragment wf = null;
    static boolean c = false;
    List<MovieClass> movieList = new ArrayList<>();
    LibraryRecyclerViewAdapter libraryRecyclerViewAdapter;
    RecyclerView recyclerView;
    GridLayoutManager gridManager;
    LinearLayoutManager linearManager;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_not_watched, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {}

    public void sort() {
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

        switch (LibraryFragment.layout) {
            case 0:
                gridManager = new GridLayoutManager(view.getContext(), 3);
                recyclerView.setLayoutManager(gridManager);
                break;
            case 1:
                linearManager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(linearManager);
                break;
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        switch (LibraryFragment.layout) {
            case 0:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchdesign);
                break;
            case 1:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchactor);
                break;
        }
        libraryRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(libraryRecyclerViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        movieList.clear();
        MovieDatabase mdb = MovieDatabase.getInstance(getContext());


        switch (LibraryFragment.layout) {
            case 0:
                gridManager = new GridLayoutManager(view.getContext(), 3);
                recyclerView.setLayoutManager(gridManager);
                break;
            case 1:
                linearManager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(linearManager);
                break;
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        switch (LibraryFragment.layout) {
            case 0:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchdesign);
                break;
            case 1:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchactor);
                break;
        }
        libraryRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(libraryRecyclerViewAdapter);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<JoinClass> listmovie = mdb.movieDAO().getMovieList();
                List<Integer> id_list = new ArrayList<>();
                for (int i = 0; i < listmovie.size(); i++) {
                    if (LoginActivity.loggedUser.equals(listmovie.get(i).nickname) && listmovie.get(i).status == 1) {
                        id_list.add(listmovie.get(i).movie_id);
                    }
                }
                for (int i = 0; i < id_list.size(); i++) {
                    Retrofit retrofit = new Retrofit.Builder().
                            baseUrl(MainActivity.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
                    ApiInterface myInterface = retrofit.create(ApiInterface.class);

                    Call<MovieDetails> call = myInterface.listOfMovies(id_list.get(i), MainActivity.API_KEY);

                    call.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            MovieDetails results = response.body();


                            movieList.add(new MovieClass(results.getTitle(), results.getOverview(), results.getRelease_date(),MainActivity.IMG_URL + results.getPoster_path(), results.getVote_average(), null, results.getId()));

                            libraryRecyclerViewAdapter.notifyDataSetChanged();
                            libraryRecyclerViewAdapter.setClickListener(NotWatchedFragment.this);
                            recyclerView.setAdapter(libraryRecyclerViewAdapter);

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
                        public void onFailure(Call<MovieDetails> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }

    public void reload() {
        movieList.clear();
        MovieDatabase mdb = MovieDatabase.getInstance(getContext());


        switch (LibraryFragment.layout) {
            case 0:
                gridManager = new GridLayoutManager(view.getContext(), 3);
                recyclerView.setLayoutManager(gridManager);
                break;
            case 1:
                linearManager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(linearManager);
                break;
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        switch (LibraryFragment.layout) {
            case 0:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchdesign);
                break;
            case 1:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchactor);
                break;
        }
        libraryRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(libraryRecyclerViewAdapter);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<JoinClass> listmovie = mdb.movieDAO().getMovieList();
                List<Integer> id_list = new ArrayList<>();
                for (int i = 0; i < listmovie.size(); i++) {
                    if (LoginActivity.loggedUser.equals(listmovie.get(i).nickname) && listmovie.get(i).status == 1) {
                        id_list.add(listmovie.get(i).movie_id);
                    }
                }
                for (int i = 0; i < id_list.size(); i++) {
                    Retrofit retrofit = new Retrofit.Builder().
                            baseUrl(MainActivity.BASE_URL).
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
                    ApiInterface myInterface = retrofit.create(ApiInterface.class);

                    Call<MovieDetails> call = myInterface.listOfMovies(id_list.get(i), MainActivity.API_KEY);

                    call.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            MovieDetails results = response.body();


                            movieList.add(new MovieClass(results.getTitle(), results.getOverview(), results.getRelease_date(),MainActivity.IMG_URL + results.getPoster_path(), results.getVote_average(), null, results.getId()));

                            libraryRecyclerViewAdapter.notifyDataSetChanged();
                            libraryRecyclerViewAdapter.setClickListener(NotWatchedFragment.this);
                            recyclerView.setAdapter(libraryRecyclerViewAdapter);

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
                        public void onFailure(Call<MovieDetails> call, Throwable t) {
                        }
                    });
                }
            }
        });

    }
}
