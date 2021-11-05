package com.example.libmovie;

import android.content.Context;
import android.text.Layout;
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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    private List<MovieClass> movieList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private int layout;

    SearchRecyclerViewAdapter(Context context, List<MovieClass> movieList, ItemClickListener clickListener, int layout) {
        this.inflater = LayoutInflater.from(context);
        this.movieList = movieList;
        this.clickListener = clickListener;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(layout, parent, false), clickListener, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieClass movie = movieList.get(position);

        holder.name.setText(movie.getTitle());
        new DownloadImageTask(holder.image).execute(movie.getImageUrl());

        //Animation
        holder.container.setAnimation(AnimationUtils.loadAnimation(inflater.getContext(), R.anim.scale_up_animation));
        //holder.image.setAnimation(AnimationUtils.loadAnimation(inflater.getContext(), R.anim.fade_transition_animation));
        //holder.container.setAnimation(AnimationUtils.loadAnimation(inflater.getContext(), R.anim.fade_transition_animation));
    }

    @Override
    public int getItemCount() { return movieList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout container;
        TextView name = (TextView) itemView.findViewById(R.id.name);
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
