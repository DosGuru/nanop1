package com.example.android.p1;

/**
 * Created by dave_ on 9/18/2015.
 */
public class MovieItem {
    private String imageUrl;
    private String title;


    private String movieId;


    public MovieItem(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMovieId() { return movieId; }

    public void setMovieId(String movieId) {this.movieId = movieId; }

}
