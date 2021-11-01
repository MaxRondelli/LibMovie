package com.example.libmovie;

import android.graphics.Movie;

import java.util.List;

public class MovieClass {
    private String title;
    private String description;
    private String releaseDate;
    private String imageUrl;
    private double voteAverage;
    List<String> genres;

    MovieClass(String title, String description, String releaseDate, String imageUrl, double voteAverage, List<String> genres) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
        this.voteAverage = voteAverage;
        this.genres = genres;
    }

    //Getter methods
    String getTitle() {return title;}
    String getDescription() {return description;}
    String getReleaseDate() {return releaseDate;}
    String getImageUrl() {return imageUrl;}
    double getVoteAverage() {return voteAverage;}
    List<String> getGenres() {return genres;}
}
