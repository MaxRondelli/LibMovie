package com.example.libmovie;

public class MovieClass {
    public String name;
    public int imageId;
    int e = 0;
    boolean out;
    boolean isTopRated;
    boolean isMostPopular;

    MovieClass(String name, int imageId,boolean out,boolean isTopRated,boolean isMostPopular) {
        this.name = name;
        this.imageId = imageId;
        this.out = out;
        this.isTopRated = isTopRated;
        this.isMostPopular = isMostPopular;
    }

    int francesi_208=0;

    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
