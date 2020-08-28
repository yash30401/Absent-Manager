package com.yashveer.attendencemanager;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;


import android.os.Handler;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;





public class Main2Activity extends AppCompatActivity {


    String []studentCount;

    SQLiteDatabase sqLiteDatabase;
    SQLiteDatabase sqLiteDatabase2;



    Cursor c;





    MyDataHelper2 myDataHelper2;

    ListView absentStudentListView;

    ArrayAdapter<String> absentStudentArrayAdapter;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Button nextButton;




    //onCreate Starts From her------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nextButton=findViewById(R.id.nextButton);

        sharedpreferences = getSharedPreferences("com.yashveer.attendencemanager", MODE_PRIVATE);
        editor = sharedpreferences.edit();

        myDataHelper2=new MyDataHelper2(this);

        sqLiteDatabase2=myDataHelper2.getWritableDatabase();

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

                        myDataHelper2.insertData(parent.getItemAtPosition(position).toString(),sqLiteDatabase2);
                        nextButton.setVisibility(View.VISIBLE);
                        Toast.makeText(Main2Activity.this,"Student "+parent.getItemAtPosition(position).toString()+" Added to absent List",Toast.LENGTH_SHORT).show();


                    }
                });

            //ListVeiw on click ends Here



       }


    //on create Ends Here----------------------------------------------------------------------------------------------------------------


    //Button Next Click listner Starts from Here
    public void next(View view) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    editor.putBoolean("FirstTime2", false);
                    editor.commit();

                    Intent i = new Intent(Main2Activity.this, absentStudent.class);

                    startActivity(i);

                    finish();
                }
            }, 0);

    }

    //Button Next click Listner ends Here







}

