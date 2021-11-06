package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WatchedFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    static WatchedFragment wf = null;
    static boolean c = false;
    List<MovieClass> movieList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_not_watched, container, false);
        manager = new LinearLayoutManager(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(manager);
        movieList.clear();
        wf = this;

        for ( int i = 0; i < MainActivity.movieListId.size(); i++ ){
            if( MainActivity.movieListId.get(i).getStatus() == 2 ){
                movieList.add(MainActivity.movieListId.get(i));
            }
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, this);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemListener(getContext(), recyclerView ,new RecyclerItemListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        c = true;
                        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                        intent.putExtra("movieId", movieList.get(position).getId());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {}
                })
        );

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {}

    public void reload() {

        movieList.clear();

        for ( int i = 0; i < MainActivity.movieListId.size(); i++ ){
            if( MainActivity.movieListId.get(i).getStatus() == 2 ){
                movieList.add(MainActivity.movieListId.get(i));
            }
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, this);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
