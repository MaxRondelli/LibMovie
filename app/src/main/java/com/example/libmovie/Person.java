package com.example.libmovie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String nickname;
    @ColumnInfo(name = "password")
    public String password;

    Person(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }


    @NonNull
    public String getNickname() {
        return nickname;
    }

    public void setNickname(@NonNull String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
