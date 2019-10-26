package com.example.charliesiagian.movieapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.util.Log;

import com.example.charliesiagian.movieapp.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.API_KEY;

    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();


    void setMovie() {

        AsyncHttpClient client = new AsyncHttpClient();

        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "http://www.omdbapi.com/?apikey="+ API_KEY+ "&s=charlie";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("Search");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listItems.add(movieItems);
                    }
                    listMovie.postValue(listItems);

                    Log.d("sukses","sukseskok");
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                Log.d("sukses","sukseskok");
            }
        });
    }

    void setDetailMovie(String idMovie) {

        AsyncHttpClient client = new AsyncHttpClient();

        final Movie movie = new Movie();

        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "http://www.omdbapi.com/?apikey="+ API_KEY+ "&i="+idMovie+"&plot=full";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    String duration = responseObject.getString("Runtime");
                    String genre = responseObject.getString("Genre");
                    String type = responseObject.getString("Type");
                    String production;
                    if(type.equals("series")){
                        production = responseObject.getString("Writer");
                    }else{
                        production = responseObject.getString("Production");
                    }

                    String overview = responseObject.getString("Plot");


                    Movie movieItems = new Movie(movie);
                    movieItems.setGenre(genre);
                    movieItems.setDuration(duration);
                    movieItems.setDirectors(production);
                    movieItems.setSynopsis(overview);
                    listItems.add(movieItems);

                    listMovie.postValue(listItems);

                    Log.d("sukses", "sukseskok");
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                Log.d("sukses","sukseskok");
            }
        });
    }

    void setMovieSearch(String query) {

        AsyncHttpClient client = new AsyncHttpClient();

        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "http://www.omdbapi.com/?apikey="+ API_KEY+ "&s="+query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("Search");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listItems.add(movieItems);
                    }
                    listMovie.postValue(listItems);

                    Log.d("sukses","sukseskok");
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                Log.d("sukses","sukseskok");
            }
        });
    }

    void setMoviePage(String query, int page){
        AsyncHttpClient client = new AsyncHttpClient();

        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "http://www.omdbapi.com/?apikey="+ API_KEY+ "&s="+query+"&p="+page;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("Search");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listItems.add(movieItems);
                    }
                    listMovie.postValue(listItems);

                    Log.d("sukses","sukseskok");
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                Log.d("sukses","sukseskok");
            }
        });
    }

    LiveData<ArrayList<Movie>> getMovies() {
        return listMovie;
    }

}
