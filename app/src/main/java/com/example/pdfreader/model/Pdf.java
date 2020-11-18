package com.example.pdfreader.model;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "PDFTable")
public class Pdf {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int _id;
    @ColumnInfo
    private String uri;
    @ColumnInfo
    private String name;

    public Pdf(){
    }
    public Pdf(Uri uri, String name){
        this.uri= String.valueOf(uri);
        this.name=name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
