package com.example.hal.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HAL on 2016/02/24.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context c) {
        super(c, DataBase.DATABASE_NAME, null, DataBase.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DataBase.TABLE_NAME + " ( "
                        + DataBase.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + DataBase.NAME + " TEXT, "
                        + DataBase.AGE + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DataBase.TABLE_NAME);
        onCreate(db);
    }
}
