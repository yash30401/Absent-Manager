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


import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;





public class Main2Activity extends AppCompatActivity {


    String []studentCount;

    SQLiteDatabase sqLiteDatabase;


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

        sqLiteDatabase=myDataHelper.getWritableDatabase();

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

            //ListView Onclick Starts from here

                absentStudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        myDataHelper.absentinsertData(parent.getItemAtPosition(position).toString(),sqLiteDatabase);

                        Toast.makeText(Main2Activity.this,"Student "+parent.getItemAtPosition(position).toString()+" Added to absent List",Toast.LENGTH_SHORT).show();

                    }
                });

            //ListVeiw on click ends Here



       }


    //on create Ends Here----------------------------------------------------------------------------------------------------------------


    //Button Next Click listner Starts from Here
    public void next(View view){
        Intent i=new Intent(Main2Activity.this,absentStudent.class);
        startActivity(i);
    }

    //Button Next click Listner ends Here







}

