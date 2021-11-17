package com.example.libmovie;

import android.graphics.Paint;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("Select * FROM joinclass")
    List<JoinClass> getMovieList();
    @Insert
    void insertMovie(JoinClass join);
    @Update
    void updateMovie(JoinClass join);
    @Delete
    void deleteMovie(JoinClass join);
    @Query("Delete From joinclass")
    public void deleteTable();
}
