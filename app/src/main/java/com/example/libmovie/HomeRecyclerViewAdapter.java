package com.example.libmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    private List<MovieClass> movieList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    HomeRecyclerViewAdapter(Context context, List<MovieClass> movieList, ItemClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        this.movieList = movieList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.listitemdesign, parent, false), clickListener, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieClass movie = movieList.get(position);

        holder.title.setText(movie.getTitle());
        holder.description.setText(movie.getDescription());
        holder.releaseDate.setText("Release date: " + movie.getReleaseDate());
        new DownloadImageTask(holder.image).execute(movie.getImageUrl());

        //Printing genres list
        String genres = "";
        if (movie.getGenres() != null) {
            for (int i = 0; i < movie.getGenres().size(); i++) {
                genres = genres + movie.getGenres().get(i);
                if(i != movie.getGenres().size() - 1) genres += ", ";
            }
        }
        holder.genres.setText("Genres: " + genres);

        //Animation
        holder.image.setAnimation(AnimationUtils.loadAnimation(inflater.getContext(), R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(inflater.getContext(), R.anim.fade_transition_animation));
    }

    @Override
    public int getItemCount() { return movieList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout container;
        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView description = (TextView) itemView.findViewById(R.id.description);
        TextView genres = (TextView) itemView.findViewById(R.id.genre);
        TextView releaseDate = (TextView) itemView.findViewById(R.id.release_date);
        ImageView image = (ImageView) itemView.findViewById(R.id.image);
        ItemClickListener clickListener;
        Context context;

        ViewHolder(View itemView, ItemClickListener clickListener, Context context) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
