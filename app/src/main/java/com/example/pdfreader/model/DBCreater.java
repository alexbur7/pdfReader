package com.example.pdfreader.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class DBCreater {
    private RoomBase base;
    private static Context mContext;
    private static DBCreater mDBCreater;
    private DBCreater(){
        base= Room.databaseBuilder(mContext,RoomBase.class,"CreateTable").allowMainThreadQueries().build();
    }

    public List<Pdf> getPDFs(){
        return base.getBaseDao().getAll();
    }

    public void writeToBase(Uri uri){
        String name = uri.getLastPathSegment();
        List<Pdf> pdfList=base.getBaseDao().getAll();
        for (Pdf pdf:pdfList){
            Log.d("TUT",pdf.getUri());
            if (pdf.getUri().equals(uri.toString())) return;
        }
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        base.getBaseDao().insertPdf(new Pdf(uri,name));
    }

    public static DBCreater getInstance(Context context){
        mContext=context;
        if (mDBCreater==null){
            mDBCreater=new DBCreater();
        }
        return mDBCreater;
    }
}
