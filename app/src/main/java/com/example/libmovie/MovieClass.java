package com.example.libmovie;

import java.util.List;

public class MovieClass {
    public String name;
    public String description;
    public int imageId;
    int e = 0;
    boolean out;
    boolean isTopRated;
    boolean isMostPopular;
    String url;
    String release_date;
    List<String> genre;

    MovieClass(String name, int imageId,boolean out,boolean isTopRated,boolean isMostPopular) {
        this.name = name;
        this.imageId = imageId;
        this.out = out;
        this.isTopRated = isTopRated;
        this.isMostPopular = isMostPopular;
    }

    MovieClass(String name, String url){
        this.name = name;
        this.url = url;
        this.imageId = 0;
        this.out = true;
        this.isTopRated = true;
        this.isMostPopular = true;
    }

    MovieClass(String name, String url,String release_date){
        this.name = name;
        this.url = url;
        this.imageId = 0;
        this.out = true;
        this.isTopRated = true;
        this.isMostPopular = true;
        this.release_date = release_date;
    }
    MovieClass(String name, String url,String release_date,String description, List<String> genre){
        this.name = name;
        this.url = url;
        this.imageId = 0;
        this.out = true;
        this.isTopRated = true;
        this.isMostPopular = true;
        this.description = description;
        this.release_date = release_date;
        this.genre = genre;
    }

    int francesi_308=0;

    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
