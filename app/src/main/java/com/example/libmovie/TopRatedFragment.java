package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TopRatedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);

        ListView lw = (ListView) view.findViewById(R.id.list);

        ListAdapter la;

        List<MovieClass> tmp = new ArrayList<>();
        for (int i = 0; i < MainActivity.movieList.size(); i++) {
            if (MainActivity.movieList.get(i).isTopRated) {
                tmp.add(MainActivity.movieList.get(i));
            }
        }

        la = new ListAdapter(getActivity().getBaseContext(),tmp);
        lw.setAdapter(la);

        return view;
    }
}
