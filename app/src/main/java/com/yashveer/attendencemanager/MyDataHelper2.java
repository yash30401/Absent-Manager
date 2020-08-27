package com.yashveer.attendencemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataHelper2 extends SQLiteOpenHelper {

    private static final String dbName="com.yashveer.attendencemanager2";
    private static final int versionOfDb=1;

    public MyDataHelper2(Context context){
        super(context,dbName,null,versionOfDb);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE absentstudentsName (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT) ";
        db.execSQL(sql);



    }

    public void insertData(String name,SQLiteDatabase database){
        ContentValues values=new ContentValues();
        values.put("name",name);
        database.insert("absentstudentsName",null,values);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
