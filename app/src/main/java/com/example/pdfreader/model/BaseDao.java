package com.example.pdfreader.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao()
public interface BaseDao {

    @Query("SELECT * FROM PDFTable")
    List<Pdf> getAll();

    @Insert
    void insertPdf(Pdf pdf);
}
