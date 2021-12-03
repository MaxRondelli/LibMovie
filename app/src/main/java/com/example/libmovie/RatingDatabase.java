package com.example.libmovie;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = RatingClass.class, exportSchema = false,version = 1)
public abstract class RatingDatabase extends RoomDatabase {
    private static final String DB_NAME = "rating_db";
    private static RatingDatabase instance;

    public static synchronized RatingDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), RatingDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();

        }
        return instance;
    }

    public abstract RatingDAO ratingDAO();
}
