package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        ListView l;
        ArrayAdapter adapter;
        l = (ListView)view.findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1,
                MainActivity.array);
        l.setAdapter(adapter);


        return view;
    }
}
