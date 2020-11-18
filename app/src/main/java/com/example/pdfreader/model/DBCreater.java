package com.example.pdfreader.model;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class DBCreater {
    private RoomBase base;
    private Context mContext;
    private static DBCreater mDBCreater;
    private DBCreater(Context context){
        this.mContext=context;
        base= Room.databaseBuilder(mContext,RoomBase.class,"CreateTable").allowMainThreadQueries().build();
    }

    public List<Pdf> getPDFs(){
        return base.getBaseDao().getAll();
    }

    public void insertBook(Pdf pdf){
        base.getBaseDao().insertPdf(pdf);
    }

    public static DBCreater getInstance(Context context){
        if (mDBCreater==null){
            mDBCreater=new DBCreater(context);
        }
        return mDBCreater;
    }
}
