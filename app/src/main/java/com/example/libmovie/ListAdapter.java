package com.example.libmovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.InputStream;
import java.util.List;

public class ListAdapter extends ArrayAdapter<MovieClass> {
    Animation scaleUp;

    public ListAdapter(Context context, List<MovieClass> items){
        super(context,R.layout.listitemdesign,items);
        //scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up_fast);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitemdesign, null);
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView genere = (TextView) view.findViewById(R.id.genre);
        TextView release_date = (TextView) view.findViewById(R.id.release_date);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        MovieClass movie = getItem(position);

        InputStream in = null;
        Bitmap bitmap = null;

        new DownloadImageTask(image).execute(movie.getImageUrl());

        image.setImageBitmap(bitmap);
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());

        String genre = "";

        if(movie.getGenres()!=null){
            for(int i=0; i<movie.getGenres().size(); i++){
                genre = genre + movie.getGenres().get(i);
                if(i!=movie.getGenres().size()-1)genre+= ", ";
            }
        }

        genere.setText("Genre: " + genre);
        release_date.setText("Release date: " + movie.getReleaseDate());

        CardView cv = (CardView) view.findViewById(R.id.cardview);
        cv.startAnimation(scaleUp);

        return view;
    }
}
