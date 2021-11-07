package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleSearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchRecyclerViewAdapter.ItemClickListener {
    List<MovieClass> movieList = new ArrayList<>();
    SearchRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    View view;
    View searchBar;
    SearchView search;

    private HomeRecyclerViewAdapter.ItemClickListener listener;

    PeopleSearchFragment(View searchBar) {
        this.searchBar = searchBar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_people_search, container, false);
        manager = new LinearLayoutManager(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_actor);
        recyclerView.setLayoutManager(manager);
        search = (SearchView) searchBar.findViewById(R.id.search_bar);

        search.setOnQueryTextListener(this);

        movieList.clear();

        return view;
    }

    public void reload() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_actor);

        recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchactor);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
        search.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(view.getContext(), movieList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        s = s.toLowerCase();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_actor);
        recyclerView.setLayoutManager(manager);
        search = (SearchView) searchBar.findViewById(R.id.search_bar);
        search.setOnQueryTextListener(this);

        movieList.clear();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<PeopleClass> call = myInterface.listOfPerson("person", MainActivity.API_KEY, s);

        call.enqueue(new Callback<PeopleClass>() {
            @Override
            public void onResponse(Call<PeopleClass> call, Response<PeopleClass> response) {
                movieList.clear();
                PeopleClass results = response.body();
                List<PeopleClass.ResultsDTO> listOfPeople = results.getResults();

                for (int i = 0; i < listOfPeople.size(); i++) {
                    PeopleClass.ResultsDTO curr = listOfPeople.get(i);

                    movieList.add(new MovieClass(curr.getName(), "", "", MainActivity.IMG_URL + curr.getProfile_path(), 0, null, curr.getId()));
                    recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity().getBaseContext(), movieList, PeopleSearchFragment.this, R.layout.listsearchactor);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.setClickListener(PeopleSearchFragment.this);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemListener(getContext(), recyclerView ,new RecyclerItemListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(getContext(), PeopleDetailsActivity.class);
                                    intent.putExtra("personId", movieList.get(position).getId());
                                    startActivity(intent);
                                }

                                @Override public void onLongItemClick(View view, int position) {}
                            })
                    );
                }
            }

            @Override
            public void onFailure(Call<PeopleClass> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search_actor);
        recyclerView.setLayoutManager(manager);
        search = (SearchView) searchBar.findViewById(R.id.search_bar);
        search.setOnQueryTextListener(this);

        movieList.clear();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MainActivity.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<PeopleClass> call = myInterface.listOfPerson("person", MainActivity.API_KEY, s);

        call.enqueue(new Callback<PeopleClass>() {
            @Override
            public void onResponse(Call<PeopleClass> call, Response<PeopleClass> response) {
                movieList.clear();
                PeopleClass results = response.body();
                if (results == null) return;
                List<PeopleClass.ResultsDTO> listOfPeople = results.getResults();

                for (int i = 0; i < listOfPeople.size(); i++) {
                    PeopleClass.ResultsDTO curr = listOfPeople.get(i);

                    movieList.add(new MovieClass(curr.getName(), "", "", MainActivity.IMG_URL + curr.getProfile_path(), 0, null, curr.getId()));
                    recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity().getBaseContext(), movieList, PeopleSearchFragment.this, R.layout.listsearchactor);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.setClickListener(PeopleSearchFragment.this);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemListener(getContext(), recyclerView ,new RecyclerItemListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(getContext(), PeopleDetailsActivity.class);
                                    intent.putExtra("personId", movieList.get(position).getId());
                                    startActivity(intent);
                                }

                                @Override public void onLongItemClick(View view, int position) {}
                            })
                    );
                }
            }

            @Override
            public void onFailure(Call<PeopleClass> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return false;
    }
}
