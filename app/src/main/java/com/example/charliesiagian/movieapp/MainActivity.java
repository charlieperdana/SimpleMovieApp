package com.example.charliesiagian.movieapp;

import android.app.Activity;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.example.charliesiagian.movieapp.adapter.EndlessOnScrollListener;
import com.example.charliesiagian.movieapp.adapter.ItemClickSupport;
import com.example.charliesiagian.movieapp.adapter.MovieAdapter;
import com.example.charliesiagian.movieapp.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private RecyclerView rvMovie;
    private ArrayList<Movie> movies;

    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    static String searchText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        rvMovie = findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);

        rvMovie.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovie.setAdapter(adapter);



        progressBar = findViewById(R.id.progressBar);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.setMovie();
        mainViewModel.getMovies().observe(this, getMovies);


//        if(searchText!=null){
//            rvMovie.addOnScrollListener(scrollData(2));
//        }



    }


    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> moviesItems) {
            if (moviesItems != null) {
                adapter.setData(moviesItems);
                showLoading(false);


            }else{
                adapter.setData(moviesItems);
                showLoading(true);
            }

            movies = new ArrayList<>();
            movies.addAll(moviesItems);

            ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    showDetailMovie(movies.get(position));
                }
            });


        }

    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);

        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showDetailMovie(Movie movie){
        Intent moveDetailMovie = new Intent(this, DetailMovieActivity.class);
        moveDetailMovie.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        startActivity(moveDetailMovie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        MenuItem item = menu.findItem(R.id.search_menu);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                mainViewModel.setMovieSearch(query);
                showLoading(true);
                hideKeyboard();
                searchText = query;

                rvMovie.addOnScrollListener(scrollData(2));

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }

        });

        return true;

    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    private EndlessOnScrollListener scrollData(final int page) {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                Log.d("load berhasil", "load");
                mainViewModel.setMoviePage(searchText, page);
            }
        };
    }
}
