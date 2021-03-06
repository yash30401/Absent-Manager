package com.yashveer.attendencemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataHelper extends SQLiteOpenHelper {

    private static final String dbName="com.yashveer.attendencemanager";
    private static final int versionOfDb=1;

    public MyDataHelper(Context context){
        super(context,dbName,null,versionOfDb);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE IF NOT EXISTS studentsName (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT) ";
        db.execSQL(sql);



    }

    public void insertData(String name,SQLiteDatabase database){
        ContentValues values=new ContentValues();
        values.put("name",name);
        database.insert("studentsName",null,values);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
