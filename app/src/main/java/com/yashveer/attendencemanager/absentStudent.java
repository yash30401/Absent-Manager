package com.yashveer.attendencemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class absentStudent extends AppCompatActivity {


    String []studentCount;

    SQLiteDatabase sqLiteDatabase2;

    Cursor c;



    ListView absentListview;

    ArrayAdapter<String> absentListAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_student);

        sharedPreferences = getSharedPreferences("com.yashveer.attendencemanager", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        sqLiteDatabase2=this.openOrCreateDatabase("com.yashveer.attendencemanager2",MODE_PRIVATE,null);


        c=sqLiteDatabase2.rawQuery("SELECT * FROM absentstudentsName",null);

        absentListview=findViewById(R.id.absentListView);

        studentCount=new String[c.getCount()];

        absentListAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,studentCount);

        absentListview.setAdapter(absentListAdapter);



        c.moveToFirst();


         for(int i=0;i<studentCount.length;i++){
            studentCount[i]=c.getString(1);
            c.moveToNext();
        }

        String currentTime = new SimpleDateFormat("hh:mm:ss:a", Locale.getDefault()).format(new Date());

        do{
            String sql="DELETE FROM absentstudentsName";
            sqLiteDatabase2.execSQL(sql);
        }while (currentTime=="12:00:00:AM");



        do{
            editor.remove("FirstTime2");
            editor.commit();
        }while (currentTime=="12:00:00:AM");





    }
}