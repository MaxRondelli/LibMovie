package com.example.libmovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DirectorSearchFragment extends Fragment implements SearchView.OnQueryTextListener {
        ListAdapterD la;
        View view;
        View top;
        ListView lw;
        SearchView search;
        DirectorSearchFragment(View top){
            this.top = top;
        }

        @Nullable
        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            view = inflater.inflate(R.layout.fragment_director_search, container, false);

            lw = (ListView) view.findViewById(R.id.list);
            search = (SearchView) top.findViewById(R.id.search_bar);
            search.setOnQueryTextListener(this);

            List<DirectorClass> tmp = new ArrayList<>();
            for (int i = 0; i < MainActivity.directorList.size(); i++) {
                if (MainActivity.directorList.get(i).name.toLowerCase().contains(SearchFragment.filter)  || SearchFragment.filter.isEmpty()) {
                    tmp.add(MainActivity.directorList.get(i));
                }
            }

            la = new ListAdapterD(getActivity().getBaseContext(),tmp);
            lw.setAdapter(la);
            return view;
        }

    public void reload() {
        lw = (ListView) view.findViewById(R.id.list);
        search= (SearchView) top.findViewById(R.id.search_bar);
        search.setOnQueryTextListener(this);
        ListView lw = (ListView) view.findViewById(R.id.list);
        ListAdapterD la;
        List<DirectorClass> tmp = new ArrayList<>();
        for(int i=0; i<MainActivity.directorList.size(); i++) {
            if(MainActivity.directorList.get(i).name.toLowerCase().contains(SearchFragment.filter) || SearchFragment.filter.isEmpty()) {
                tmp.add(MainActivity.directorList.get(i));
            }
        }
        la = new ListAdapterD(getActivity().getBaseContext(), tmp);
        lw.setAdapter(la);
    }


    @Override
    public boolean onQueryTextSubmit (String s){
        return false;
    }

    @Override
    public boolean onQueryTextChange (String s){
        s = s.toLowerCase();
        ListView lw = (ListView) view.findViewById(R.id.list);
        ListAdapterD la;
        List<DirectorClass> tmp = new ArrayList<>();
        for (int i = 0; i < MainActivity.directorList.size(); i++) {
            if (MainActivity.directorList.get(i).name.toLowerCase().contains(s)) {
                tmp.add(MainActivity.directorList.get(i));
            }
        }
        la = new ListAdapterD(getActivity().getBaseContext(), tmp);
        lw.setAdapter(la);
        SearchFragment.filter = s.toLowerCase();
        return false;
    }
}
