package com.example.libmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapterA extends ArrayAdapter<ActorClass> {

    public ListAdapterA(Context context, List<ActorClass> items){
        super(context,R.layout.listitemdesign,items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitemdesign,null);
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        ActorClass movie = getItem(position);

        title.setText(movie.name);
        image.setImageResource(movie.imageId);

        return view;
    }

}
