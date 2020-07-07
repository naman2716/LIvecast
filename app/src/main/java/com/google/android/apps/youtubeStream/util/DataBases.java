package com.google.android.apps.youtubeStream.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.util.Log;

public class DataBases extends SQLiteOpenHelper {

    public static final String DataBase_Name = "quota_db";
    public static final String Table_Name = "sessionTable";
    public static final String ID = "sessionId";
    public static final String Start_time = "sessionStartTime";
    public static final String End_time = "sessionEndTime";
    public static final String quota = "sessionQuota";

    public DataBases(@Nullable Context context) {
        super(context, DataBase_Name, null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE "+ Table_Name + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Start_time + " DATETIME," + End_time + " DATETIME," + quota + " INTEGER" +")";
        db.execSQL(create_table);
//        DATETIME DEFAULT CURRENT_TIMESTAMP
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }

    public void insertData(String sTime, String eTime, Integer sessionCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Start_time, sTime);
        contentValues.put(End_time, eTime);
        contentValues.put(quota, sessionCost);
        db.insert(Table_Name, null, contentValues);
    }

    public void updateData(String eTime, Integer addCost){
        Log.i("tag","Update function");
        SQLiteDatabase db = this.getReadableDatabase();
        String updateQuery = "UPDATE "+Table_Name+ " SET "+ End_time + " = '" + eTime + "'," + quota + " = " + addCost + " WHERE " + ID + " = (SELECT MAX(" + ID + ") FROM " + Table_Name + ")";
        db.execSQL(updateQuery);
    }
}


