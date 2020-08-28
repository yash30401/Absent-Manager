package com.yashveer.attendencemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class AlarmReciever extends BroadcastReceiver {

    SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
public void func(Context context){

    sharedPreferences=context.getSharedPreferences("com.yashveer.attendencemanager",0);
    editor=sharedPreferences.edit();

    editor.remove("FirstTime2");
    editor.commit();

    sqLiteDatabase=context.openOrCreateDatabase("com.yashveer.attendencemanager2",0,null);
    String sql="DELETE FROM absentstudentsName";

    sqLiteDatabase.execSQL(sql);
}

    @Override
    public void onReceive(Context context, Intent intent) {

    func(context);


    }
}