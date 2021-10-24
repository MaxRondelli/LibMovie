package com.example.libmovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListAdapter extends ArrayAdapter<MovieClass> {

    public ListAdapter(Context context, List<MovieClass> items){
        super(context,R.layout.listitemdesign,items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitemdesign,null);
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView genere = (TextView) view.findViewById(R.id.genre);
        TextView release_date = (TextView) view.findViewById(R.id.release_date);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        MovieClass movie = getItem(position);


        InputStream in = null;
        Bitmap bitmap = null;


        new DownLoadImageTask(image).execute(movie.url);

        image.setImageBitmap(bitmap);
        title.setText(movie.name);
        description.setText(movie.description);

        String genre = "";

        if(movie.genre!=null){
            for(int i=0; i<movie.genre.size(); i++){
                genre = genre + movie.genre.get(i);
                if(i!=movie.genre.size()-1)genre+= ", ";
            }
        }

        genere.setText("Genre: " + genre);
        release_date.setText("Release date: " + movie.release_date);

        return view;
    }

}
