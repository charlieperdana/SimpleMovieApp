package com.example.charliesiagian.movieapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.charliesiagian.movieapp.R;
import com.example.charliesiagian.movieapp.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.GridViewHolder> {
    private ArrayList<Movie> mData = new ArrayList<>();

    public void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_grid, viewGroup, false);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, int i) {
        gridViewHolder.bind(mData.get(i));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView title;
        TextView year;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            title = itemView.findViewById(R.id.overview_title);
            year = itemView.findViewById(R.id.overview_year);
        }

        void bind(Movie movierItems) {
            Glide.with(itemView.getContext())
                    .load(movierItems.getPhoto())
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgPhoto);

            title.setText(movierItems.getTitle());
            year.setText(movierItems.getYear());
        }
    }
}
