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
        TextView director = (TextView) view.findViewById(R.id.director);
        TextView release_date = (TextView) view.findViewById(R.id.release_date);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        MovieClass movie = getItem(position);

        title.setText(movie.name);

        InputStream in = null;
        Bitmap bitmap = null;

        /*if(!movie.name.isEmpty()) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            URL url = null;
                            try {
                                url = new URL(movie.url);
                                image.setImageBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }*/

        new DownLoadImageTask(image).execute(movie.url);

        image.setImageBitmap(bitmap);
        //image.setImageResource(movie.imageId);
        description.setText(movie.name);
        director.setText("Director: " + movie.name);
        release_date.setText("Release date: " + movie.name);

        return view;
    }

}
