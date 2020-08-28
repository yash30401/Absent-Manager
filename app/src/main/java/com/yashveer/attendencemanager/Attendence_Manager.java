package com.yashveer.attendencemanager;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;




import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import android.os.Handler;


import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;



public class Attendence_Manager extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ArrayAdapter<String> arrayAdapter;

    Button DoneButton;


    boolean FirstTime = true;



    BottomNavigationView bottomNavigationView;
    EditText student_name;
    Button add_student;
    Button cancel_student;

   AlertDialog alertDialog;

    int buttonTap=0;


    SQLiteDatabase studentNameData;
    MyDataHelper myDataHelper;





    //OnCreate start from Here!>--------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_manager);


        listView = findViewById(R.id.ListView);


        arrayList = new ArrayList<>();


        DoneButton = findViewById(R.id.DoneButton);

        sharedpreferences = getSharedPreferences("com.yashveer.attendencemanager", MODE_PRIVATE);
        editor = sharedpreferences.edit();


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1
                , arrayList);


        listView.setAdapter(arrayAdapter);


        listView.setDivider(null);


        bottomNavigationView = findViewById(R.id.bottom_bar);

        add_student=findViewById(R.id.addStudent);
        cancel_student=findViewById(R.id.cancel_student);



      myDataHelper=new MyDataHelper(this);
        studentNameData=myDataHelper.getWritableDatabase();



        // ListView Item Selected Start From Here!>--------------------------------------------------------------------------------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Attendence_Manager.this);
                builder.setCancelable(true);




                builder.setMessage("Do you want to remove this Name from your List?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                               arrayAdapter.remove(arrayList.get(position));



                                studentNameData.delete("studentsName","_id = ?",new String[]{String.valueOf(position+1)});

                            }
                        });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });//ListView Item Selected Delete from here!>--------------------------------------------------------------------------------------------


        //On Bottom_Navigation Item Selected Start From here!>--------------------------------------------------------------------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {




                    LayoutInflater inflater=LayoutInflater.from(Attendence_Manager.this);
                    View myView =inflater.inflate(R.layout.dialog_students_name,null);


                student_name=myView.findViewById(R.id.studentName);

                 alertDialog =new  AlertDialog.Builder(Attendence_Manager.this).setView(myView).create();

                alertDialog.show();




                return false;
            }
        });//Bottom Navigation end Here!>--------------------------------------------------------------------------------------------





    }
    //OnCreate End Here!>--------------------------------------------------------------------------------------------

    //DialogButton Onclicks Start Here!------------------------------------------------------------------------------------------------------
    public void addStudentClick(View view){



            buttonTap++;


        if (!student_name.getText().toString().matches("^[A-Za-z\\s]+$")) {
            Toast.makeText(Attendence_Manager.this, "Please Put Valid Name inside The Field", Toast.LENGTH_LONG).show();
        } else {

            arrayList.add(student_name.getText().toString());

            DoneButton.setVisibility(View.VISIBLE);



            myDataHelper.insertData(student_name.getText().toString(),studentNameData);


            alertDialog.dismiss();


        }

    }

    public void cancelStudentClick(View view){
        alertDialog.dismiss();

    }
//Dialogs Onclicks End Here!>--------------------------------------------------------------------------------------------


        //DoneButton OnClick StartFrom HEre!>--------------------------------------------------------------------------------------------
        public void Done_Click (View view){


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    editor.putBoolean("FirstTime", false);
                    editor.commit();

                    Intent i = new Intent(Attendence_Manager.this, Main2Activity.class);

                    startActivity(i);

                    finish();
                }
            }, 0);

        }
//Done onClick End Here!>--------------------------------------------------------------------------------------------



}





