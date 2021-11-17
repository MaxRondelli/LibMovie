package com.example.libmovie;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Person.class, exportSchema = false,version = 2)
public abstract class PersonDatabase extends RoomDatabase {
    private static final String DB_NAME = "person_db";
    private static PersonDatabase instance;

    public static synchronized PersonDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();

        }
        return instance;
    }

    public abstract PersonDAO personDAO();
}
