package com.example.libmovie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDAO {
    @Query("Select * FROM person")
    List<Person> getPersonList();
    @Insert
    void insertPerson(Person person);
    @Update
    void updatePerson(Person person);
    @Delete
    void deletePerson(Person person);
    @Query("Delete From person")
    public void deleteTable();
}
