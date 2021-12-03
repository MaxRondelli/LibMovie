package com.example.libmovie;

import android.graphics.Paint;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RatingDAO {
    @Query("Select * FROM RatingClass")
    List<RatingClass> getMovieList();
    @Insert
    void insertMovie(RatingClass join);
    @Update
    void updateMovie(RatingClass join);
    @Delete
    void deleteMovie(RatingClass join);
    @Query("Delete From RatingClass")
    public void deleteTable();
}
