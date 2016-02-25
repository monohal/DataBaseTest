package com.example.hal.databasetest;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by HAL on 2016/02/20.
 */
public class DataBase extends Application {

    SQLiteDatabase db;

    public static final String DATABASE_NAME = "TestDataBase";
    public static final String TABLE_NAME = "TestDataBaseTable";
    public static final Integer DATABASE_VERSION = 1;

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String AGE = "age";

    public void DBWritableOpen(Context context) {
        DataBaseHelper dbh = new DataBaseHelper(context);

        try {
            db = dbh.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e("OUTPUT", "WritableDatabase Open 失敗");
        }
    }

    public void DBReadableOpen(Context context, String dbName, int version) {
        DataBaseHelper dbh = new DataBaseHelper(context);

        try {
            db = dbh.getReadableDatabase();
        } catch (SQLiteException e) {
            Log.e("OUTPUT", "ReadableDatabase Open 失敗");
        }
    }

    public void DBSave(String name, int age) {
        ContentValues values = new ContentValues();

        values.put("NAME", name);
        values.put("AGE", age);

        db.insert(TABLE_NAME, null, values);
    }

    public Cursor TableQuery() {
        return db.rawQuery("select *  from " + TABLE_NAME, null);
    }

    public void DBDelete(int id) {
        db.delete(TABLE_NAME, ID + "=" + id, null);
    }

    public void DBUndo() {
        int lastid;
        final String MY_QUERY = "SELECT last_insert_rowid()";

        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        lastid = cur.getInt(0);
        cur.close();

        DBDelete(lastid);
    }

    public String getName(int pos) {
        return null;
    }

}
