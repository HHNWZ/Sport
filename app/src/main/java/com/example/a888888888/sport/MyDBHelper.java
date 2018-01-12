package com.example.a888888888.sport;

/**
 * Created by CCBDA on 2017/12/24.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper{

    public MyDBHelper(Context context, String name,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE main.exp " +
                "(name VARCHAR, " +
                "run INTEGER , " +
                "walk INTEGER, " +
                "air INTEGER, "+
                "push INTEGER, "+
                "sit INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

    }
}
