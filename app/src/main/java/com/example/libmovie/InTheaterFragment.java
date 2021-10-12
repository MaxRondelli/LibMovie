package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InTheaterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_theater, container, false);

        ListView lw = (ListView) view.findViewById(R.id.list);

        ListAdapter la;
        la = new ListAdapter(getActivity().getBaseContext(),MainActivity.movieList);
        lw.setAdapter(la);

        return view;
    }
}
