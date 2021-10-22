package com.example.libmovie;

public class MovieClass {
    public String name;
    public int imageId;
    int e = 0;
    boolean out;
    boolean isTopRated;
    boolean isMostPopular;
    String url;

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

    int francesi_308=0;

    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
