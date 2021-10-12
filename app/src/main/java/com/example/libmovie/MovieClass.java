package com.example.libmovie;

public class MovieClass {
    public String name;
    public int imageId;
    int e = 0;

    MovieClass(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
