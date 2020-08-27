package com.yashveer.attendencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class absentStudent extends AppCompatActivity {


    String []studentCount;

    SQLiteDatabase sqLiteDatabase2;

    Cursor c;



    ListView absentListview;

    ArrayAdapter<String> absentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_student);

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







    }
}