package com.example.libmovie;

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

        for ( int i = 0; i < MainActivity.movieListId.size(); i++ ){
            if( MainActivity.movieListId.get(i).getStatus() == 2 ){
                movieList.add(MainActivity.movieListId.get(i));
            }
        }

        System.out.println(movieList);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity().getBaseContext(), movieList, this);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
