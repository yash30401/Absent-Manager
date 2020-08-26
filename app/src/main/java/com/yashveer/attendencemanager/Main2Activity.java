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


import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;



public class Main2Activity extends AppCompatActivity {


    String studentCount[];

    SQLiteDatabase sqLiteDatabase;
    SQLiteDatabase sqLiteDatabase2;

    Cursor c;




    LinearLayout linearLayout;

    CheckBox checkBox;



    MyDataHelper myDataHelper;


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
            linearLayout=findViewById(R.id.Linearlay);
        linearLayout.setPadding(30,20,30,10);




        c.moveToFirst();



            for(int i=0;i<studentCount.length;i++){


                studentCount[i]=c.getString(1);

                c.moveToNext();
            }

            for(int i=0;i<studentCount.length;i++) {
                checkBox=new CheckBox(this);
               checkBox.setText(studentCount[i]);

               checkBox.setPadding(20,20,30,20);
                checkBox.setTextColor(getColor(R.color.white));
               checkBox.setElevation(10);
               checkBox.setTranslationZ(10);
               checkBox.setTextSize(20);
               checkBox.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
               checkBox.setId(i);



               checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                linearLayout.addView(checkBox);



            }//For Loop Ends here




             checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     myDataHelper.absentinsertData(buttonView.getText().toString(),sqLiteDatabase2);
                 }
             });








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

