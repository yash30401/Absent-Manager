package com.yashveer.attendencemanager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    String[] studentCount;

    SQLiteDatabase sqLiteDatabase;
    SQLiteDatabase sqLiteDatabase2;


    Cursor c;


    MyDataHelper2 myDataHelper2;

    ListView absentStudentListView;

    ArrayAdapter<String> absentStudentArrayAdapter;


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Button nextButton;

    Toolbar mTopToolbar;

    EditText student_name;
    EditText student_name2;
    AlertDialog alertDialog;

    MyDataHelper myDataHelper;

    Animation absentanim;


    //onCreate Starts From her------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        absentanim= AnimationUtils.loadAnimation(this,R.anim.dialoganim);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);


        myDataHelper = new MyDataHelper(this);
        sqLiteDatabase = myDataHelper.getWritableDatabase();

        sharedpreferences = getSharedPreferences("com.yashveer.attendencemanager", MODE_PRIVATE);
        editor = sharedpreferences.edit();

        myDataHelper2 = new MyDataHelper2(this);

        sqLiteDatabase2 = myDataHelper2.getWritableDatabase();

        sqLiteDatabase = this.openOrCreateDatabase("com.yashveer.attendencemanager", MODE_PRIVATE, null);

        c = sqLiteDatabase.rawQuery("SELECT * FROM studentsName", null);

        studentCount = new String[c.getCount()];

        absentStudentListView = findViewById(R.id.absentStudentListView);


        absentStudentArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentCount);

        absentStudentListView.setAdapter(absentStudentArrayAdapter);


        nextButton = findViewById(R.id.nextButton);

        c.moveToFirst();


        for (int i = 0; i < studentCount.length; i++) {


            studentCount[i] = c.getString(1);

            c.moveToNext();
        }

        absentStudentListView.startAnimation(absentanim);

        //ListView Onclick Starts from here

        absentStudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myDataHelper2.insertData(parent.getItemAtPosition(position).toString(), sqLiteDatabase2);

                nextButton.setVisibility(View.VISIBLE);
                Toast.makeText(Main2Activity.this, "Student " + parent.getItemAtPosition(position).toString() + " Added to absent List", Toast.LENGTH_SHORT).show();


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addordelete, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_student) {

            LayoutInflater inflater = LayoutInflater.from(Main2Activity.this);
            View myView = inflater.inflate(R.layout.dialog_students_name, null);


            student_name = myView.findViewById(R.id.studentName);

            alertDialog = new AlertDialog.Builder(Main2Activity.this).setView(myView).create();

            alertDialog.show();

            return true;
        } else if (id == R.id.delete_student) {

            LayoutInflater inflater = LayoutInflater.from(Main2Activity.this);
            View myView = inflater.inflate(R.layout.dialog2, null);


            student_name2 = myView.findViewById(R.id.studentName2);

            alertDialog = new AlertDialog.Builder(Main2Activity.this).setView(myView).create();

            alertDialog.show();
        }else if(id==R.id.aboutapp2){
            Intent i=new Intent(Main2Activity.this,aboutapp.class);
            startActivity(i);
        }else if(id==R.id.aboutdeveloper){
            Intent i=new Intent(Main2Activity.this,aboutdeveloper.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public void addStudentClick(View view) {


        if (!student_name.getText().toString().matches("^[A-Za-z\\s]+$")) {
            Toast.makeText(Main2Activity.this, "Please Put Valid Name inside The Field", Toast.LENGTH_LONG).show();
        } else {


            myDataHelper.insertData(student_name.getText().toString(), sqLiteDatabase);

            Toast.makeText(Main2Activity.this,"You have to start the app again if you wish to see the changes",Toast.LENGTH_LONG).show();



            alertDialog.dismiss();


        }

    }


    public void deleteStudent (View view){


        if (!student_name2.getText().toString().matches("^[A-Za-z\\s]+$")) {
            Toast.makeText(Main2Activity.this, "Please Put Valid Name inside The Field", Toast.LENGTH_LONG).show();
        } else {



            sqLiteDatabase.delete("studentsName","name=?",new String[]{student_name2.getText().toString()});

            sqLiteDatabase2.delete("absentstudentsName","name=?",new String[]{student_name2.getText().toString()});

            Toast.makeText(Main2Activity.this,"You have to start the app again if you wish to see the changes",Toast.LENGTH_LONG).show();

            alertDialog.dismiss();


        }

    }

    public void cancelStudentClick(View view){
        alertDialog.dismiss();

    }

    public void cancelStudentClick2(View view){
        alertDialog.dismiss();

    }
}


