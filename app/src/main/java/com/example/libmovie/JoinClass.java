package com.example.libmovie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "JoinClass" , primaryKeys = {"nickname", "movie_id"})
public class JoinClass {
    @NonNull
    public String nickname;
    @NonNull
    public int movie_id;
    @NonNull
    public int status;

    JoinClass(String nickname, int movie_id,int status){
        this.nickname = nickname;
        this.movie_id = movie_id;
        this.status = status;
    }

}
