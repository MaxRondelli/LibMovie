package com.example.libmovie;

public class MovieClass {
    public String name;
    public int imageId;

    String provaCiccio = "rrr";

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
