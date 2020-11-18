package com.example.pdfreader.model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Pdf.class}, version = 1)
public abstract class RoomBase extends RoomDatabase{
    public abstract BaseDao getBaseDao();
}
