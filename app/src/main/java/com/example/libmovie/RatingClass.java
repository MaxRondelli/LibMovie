package com.example.libmovie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RatingClass" , primaryKeys = {"nickname", "movie_id"})
public class RatingClass {
    @NonNull
    public String nickname;
    @NonNull
    public int movie_id;
    @NonNull
    public double rating;

    RatingClass(String nickname, int movie_id,double rating){
        this.nickname = nickname;
        this.movie_id = movie_id;
        this.rating = rating;
    }

}