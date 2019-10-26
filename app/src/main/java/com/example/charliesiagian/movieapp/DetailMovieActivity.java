package com.example.charliesiagian.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.charliesiagian.movieapp.adapter.ItemClickSupport;
import com.example.charliesiagian.movieapp.adapter.MovieAdapter;
import com.example.charliesiagian.movieapp.model.Movie;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";


    private ArrayList<Movie> movies;

    ImageView imgDetail;
    TextView genreDetail, yearDetail, durationDetail, directorDetail, synopsisDetail;

    private Movie movieDetail;



    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String idMovie = movie.getId();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(movie.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imgDetail = findViewById(R.id.toolbarImgMovie);
        genreDetail = findViewById(R.id.tv_genre_detail);
        yearDetail = findViewById(R.id.tv_detail_year);
        durationDetail = findViewById(R.id.tv_duration_detail);
        directorDetail = findViewById(R.id.tv_director_detail);
        synopsisDetail = findViewById(R.id.tv_synopsis_detail);


        progressBar = findViewById(R.id.progressBarDetail);


        Glide.with(this)
                .load(movie.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(imgDetail);


        if(movie.getType().equals("series")){
            FloatingActionButton floatIcon = (FloatingActionButton) findViewById(R.id.floating_icon);
            floatIcon.setImageResource(R.drawable.icon_tv_show);
        }


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.setDetailMovie(idMovie);
        mainViewModel.getMovies().observe(this, getMovies);


    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> moviesItems) {
            movieDetail = new Movie();
            movieDetail = moviesItems.get(0);

            if (moviesItems != null) {

                genreDetail.setText(movieDetail.getGenre());
                durationDetail.setText(movieDetail.getDuration());
                directorDetail.setText(movieDetail.getDirectors());
                synopsisDetail.setText(movieDetail.getSynopsis());

                showLoading(false);
            }else{
                genreDetail.setText(movieDetail.getGenre());
                durationDetail.setText(movieDetail.getDuration());
                directorDetail.setText(movieDetail.getDirectors());
                synopsisDetail.setText(movieDetail.getSynopsis());

                showLoading(true);
            }




        }

    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);

        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
