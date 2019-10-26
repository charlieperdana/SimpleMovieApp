package com.example.charliesiagian.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    private String id;
    private String photo;
    private String title;
    private String genre;
    private String synopsis;
    private String duration;
    private String directors;
    private String year;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.genre);
        dest.writeString(this.synopsis);
        dest.writeString(this.duration);
        dest.writeString(this.directors);
        dest.writeString(this.year);
        dest.writeString(this.type);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = in.readString();
        this.photo = in.readString();
        this.title = in.readString();
        this.genre = in.readString();
        this.synopsis = in.readString();
        this.duration = in.readString();
        this.directors = in.readString();
        this.year = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public Movie(JSONObject object) {


        try {
            String titleMovie = object.getString("Title");
            String filePhoto = object.getString("Poster");
            String yearRelease = object.getString("Year");
            String type = object.getString("Type");
            String id = object.getString("imdbID");
//            String releaseDate = object.getString("Released");
//            String duration = object.getString("Runtime");
//            String genre = object.getString("Genre");
//            String production = object.getString("Production");
//            String overview = object.getString("Plot");


            this.photo = filePhoto;
            this.title = titleMovie;
            this.id = id;
            this.type = type;
            this.year = yearRelease;
//            this.duration = duration;
//            this.genre = genre;
//            this.directors = production;
//            this.synopsis = overview;
//
//            if(releaseDate!=null){
//                this.year = releaseDate;
//            }else{
//                this.year = yearRelease;
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie(Movie movie){
        this.genre = movie.getGenre();
        this.duration = movie.getDuration();
        this.directors = movie.getDirectors();
        this.synopsis = movie.getSynopsis();
    }
}
