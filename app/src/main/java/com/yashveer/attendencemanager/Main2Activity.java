package com.yashveer.attendencemanager;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    String studentCount[];

    SQLiteDatabase sqLiteDatabase;
    SQLiteDatabase sqLiteDatabase2;

    Cursor c;



    MyDataHelper myDataHelper;

    ListView absentStudentListView;

    ArrayAdapter<String> absentStudentArrayAdapter;


    //onCreate Starts From her------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        myDataHelper=new MyDataHelper(this);

        sqLiteDatabase2=myDataHelper.getWritableDatabase();

        sqLiteDatabase=this.openOrCreateDatabase("com.yashveer.attendencemanager",MODE_PRIVATE,null);

        c=sqLiteDatabase.rawQuery("SELECT * FROM studentsName",null);

        studentCount=new String[c.getCount()];

        absentStudentListView=findViewById(R.id.absentStudentListView);


         absentStudentArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,studentCount);

         absentStudentListView.setAdapter(absentStudentArrayAdapter);


        c.moveToFirst();



            for(int i=0;i<studentCount.length;i++){


                studentCount[i]=c.getString(1);

                c.moveToNext();
            }




       }


    //on create Ends Here----------------------------------------------------------------------------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
       switch (id){
            case R.id.next:
                Intent i=new Intent(Main2Activity.this,absentStudent.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

