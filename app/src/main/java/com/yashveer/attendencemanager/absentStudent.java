package com.yashveer.attendencemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class absentStudent extends AppCompatActivity {


    String []studentCount;

    SQLiteDatabase sqLiteDatabase2;


    Cursor c;



    ListView absentListview;

    ArrayAdapter<String> absentListAdapter;

    Toolbar mTopToolbar;

    ArrayList<String> absentsharingList;

    private static final int WRITE_EXTERNAL_STORAGE_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_student);

        sqLiteDatabase2=this.openOrCreateDatabase("com.yashveer.attendencemanager2",MODE_PRIVATE,null);


        c=sqLiteDatabase2.rawQuery("SELECT * FROM absentstudentsName",null);

        absentListview=findViewById(R.id.absentListView);

        studentCount=new String[c.getCount()];

        absentsharingList=new ArrayList<>();

        absentListAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,studentCount);

        absentListview.setAdapter(absentListAdapter);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        c.moveToFirst();




         for(int i=0;i<studentCount.length;i++){
            studentCount[i]=c.getString(1);
           absentsharingList.add(studentCount[i]);
            c.moveToNext();
        }



        Toolbar toolbar=new Toolbar(this);




    }

    public void deleteData(View view){
        sqLiteDatabase2=this.openOrCreateDatabase("com.yashveer.attendencemanager2",0,null);
        String sql="DELETE FROM absentstudentsName";

        sqLiteDatabase2.execSQL(sql);

        Intent i=new Intent(absentStudent.this,Main2Activity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharingmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.share){
            shareAnswer();
        }else if(id==R.id.save){
            create_file();
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareAnswer() {


    }

    public void  create_file(){


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                String permission[]={Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission,WRITE_EXTERNAL_STORAGE_CODE);
            }else{
                saveToTxtFile("Absent Student");
            }
        }else{
            saveToTxtFile("Absent Student");
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    saveToTxtFile("Absent Student");
                }else {
                    Toast.makeText(this,"Storage Permission is required to store data.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void saveToTxtFile(String absent_student) {

        String timeStamp=new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(System.currentTimeMillis());

        try{

            File path=Environment.getExternalStorageDirectory();

            File dir=new File(path+"/My Files/");
            dir.mkdir();

            String filename= "MyFile_"+timeStamp+".txt";

            File file=new File(dir,filename);

            FileWriter fw=new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);

            for(int i=0;i<studentCount.length;i++) {
                bw.write(i+1+". "+absentsharingList.get(i)+"\n");
            }
            bw.close();
            Toast.makeText(this,filename+" is saved to\n"+dir,Toast.LENGTH_LONG).show();


        }
        catch (Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}