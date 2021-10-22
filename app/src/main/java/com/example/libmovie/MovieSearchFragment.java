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
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MovieSearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    ListAdapter la;
    View view;
    View top;
    ListView lw;
    SearchView search;
    MovieSearchFragment(View top){
        this.top = top;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_search, container, false);


        lw = (ListView) view.findViewById(R.id.list);
        search = (SearchView) top.findViewById(R.id.search_bar);
        search.setOnQueryTextListener(this);
        List<MovieClass> tmp = new ArrayList<>();
        for (int i = 0; i < MainActivity.movieList.size(); i++) {
            if (MainActivity.movieList.get(i).name.toLowerCase().contains(SearchFragment.filter) || SearchFragment.filter.isEmpty()) {
                tmp.add(MainActivity.movieList.get(i));
            }
        }



        for(int i=0; i<MainActivity.movieList.size(); i++){
            System.out.println(MainActivity.movieList.get(i).name);
        }


        la = new ListAdapter(getActivity().getBaseContext(),tmp);
        lw.setAdapter(la);


        /*Collections.sort(mo, new Comparator<MovieClass>() {
            public int compare(MovieClass o1, MovieClass o2) {
                return o1.name.toString().compareTo(o2.name.toString());
            }
        });*/

        return view;
    }




    public void reload(){
        lw = (ListView) view.findViewById(R.id.list);
        search= (SearchView) top.findViewById(R.id.search_bar);
        search.setOnQueryTextListener(this);
        ListView lw = (ListView) view.findViewById(R.id.list);
        ListAdapter la;
        List<MovieClass> tmp = new ArrayList<>();
        for(int i=0; i<MainActivity.movieList.size(); i++){
            if(MainActivity.movieList.get(i).name.toLowerCase().toLowerCase().contains(SearchFragment.filter) || SearchFragment.filter.isEmpty()){
                tmp.add(MainActivity.movieList.get(i));
            }
        }
        la = new ListAdapter(getActivity().getBaseContext(), tmp);
        lw.setAdapter(la);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        ListView lw = (ListView) view.findViewById(R.id.list);
        ListAdapter la;
        List<MovieClass> tmp = new ArrayList<>();
        for(int i=0; i<MainActivity.movieList.size(); i++){
            if(MainActivity.movieList.get(i).name.toLowerCase().contains(s)){
                tmp.add(MainActivity.movieList.get(i));
            }
        }
        la = new ListAdapter(getActivity().getBaseContext(), tmp);
        lw.setAdapter(la);
        SearchFragment.filter = s.toLowerCase();
        return false;
    }
}
