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

public class ActorSearchFragment extends Fragment implements SearchView.OnQueryTextListener, RecyclerViewAdapter.ItemClickListener {
    List<MovieClass> movieList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    View view;
    String query;
    View searchBar;
    SearchView search;

    private RecyclerViewAdapter.ItemClickListener listener;

    ActorSearchFragment(View searchBar) {
        this.searchBar = searchBar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_actor_search, container, false);
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

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, this);
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
                if(results==null)return;
                List<PeopleClass.ResultsDTO> listOfMovies = results.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {
                    PeopleClass.ResultsDTO curr = listOfMovies.get(i);

                    movieList.add(new MovieClass(curr.getName(), "", "", MainActivity.IMG_URL + curr.getProfile_path(), 0, null, curr.getId()));


                    recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, ActorSearchFragment.this);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.setClickListener(ActorSearchFragment.this);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemListener(getContext(), recyclerView, new RecyclerItemListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    System.err.println("ciao -> " + position + " " + movieList.get(position).getTitle());
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {
                                    // do whatever
                                }
                            })
                    );
                }
            }

            @Override
            public void onFailure(Call<PeopleClass> call, Throwable t) {

            }


        });




        return false;
    }
}
