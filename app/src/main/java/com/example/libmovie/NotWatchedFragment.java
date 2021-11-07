package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotWatchedFragment extends Fragment implements LibraryRecyclerViewAdapter.ItemClickListener {
    static NotWatchedFragment wf = null;
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

        movieList.clear();
        wf = this;

        for (int i = 0; i < MainActivity.movieListId.size(); i++){
            if (MainActivity.movieListId.get(i).getStatus() == 1){
                movieList.add(MainActivity.movieListId.get(i));
            }
        }

        switch (LibraryFragment.layout) {
            case 0:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchdesign);
                break;
            case 1:
                libraryRecyclerViewAdapter = new LibraryRecyclerViewAdapter(getActivity().getBaseContext(), movieList, this, R.layout.listsearchactor);
                break;
        }
        libraryRecyclerViewAdapter.notifyDataSetChanged();
        libraryRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(libraryRecyclerViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemListener(getContext(), recyclerView ,new RecyclerItemListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        WatchedFragment.c=true;
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

    public void reload() {
        movieList.clear();

        for ( int i = 0; i < MainActivity.movieListId.size(); i++ ){
            if( MainActivity.movieListId.get(i).getStatus() == 1 ){
                movieList.add(MainActivity.movieListId.get(i));
            }
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
}
