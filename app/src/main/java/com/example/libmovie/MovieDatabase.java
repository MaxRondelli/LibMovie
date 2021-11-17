package com.example.libmovie;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = JoinClass.class, exportSchema = false,version = 4)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "movie_db";
    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();

        }
        return instance;
    }

    public abstract MovieDAO movieDAO();
}

