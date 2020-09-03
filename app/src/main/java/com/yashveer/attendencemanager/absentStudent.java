package com.yashveer.attendencemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.AlertDialog;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;



import android.os.Environment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;


import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Locale;


public class absentStudent extends AppCompatActivity {


    String []studentCount;
    SQLiteDatabase sqLiteDatabase2;

    Cursor c;

    ListView absentListview;
    ArrayAdapter<String> absentListAdapter;
    ArrayList<String> absentsharingList;

    Toolbar mTopToolbar;

    private static final int WRITE_EXTERNAL_STORAGE_CODE=1;
    private static final int PERMISSION_REQUEST_CODE = 101;

    String  file_path=null;

    String filename;
    String filename2;
    String timeStamp;

    AlertDialog alertDialog;

    Animation dialoganim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_student);

        dialoganim= AnimationUtils.loadAnimation(this,R.anim.dialoganim);


        sqLiteDatabase2=this.openOrCreateDatabase("com.yashveer.attendencemanager2",MODE_PRIVATE,null);
        c=sqLiteDatabase2.rawQuery("SELECT * FROM absentstudentsName",null);


        absentListview=findViewById(R.id.absentListView);
        studentCount=new String[c.getCount()];


        absentsharingList=new ArrayList<>();
        absentListAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,studentCount);
        absentListview.setAdapter(absentListAdapter);
        absentListview.startAnimation(dialoganim);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        c.moveToFirst();


        for(int i=0;i<studentCount.length;i++){
            studentCount[i]=c.getString(1);
           absentsharingList.add(studentCount[i]);
            c.moveToNext();
        }

        Toolbar toolbar=new Toolbar(this);

        if(studentCount.length==0){
            Intent i=new Intent(absentStudent.this,Main2Activity.class);
            startActivity(i);
        }



    }

    public void deleteData(View view){

        LayoutInflater inflater = LayoutInflater.from(absentStudent.this);
        View myView = inflater.inflate(R.layout.warningdialog, null);


        alertDialog = new AlertDialog.Builder(absentStudent.this).setView(myView).create();


        alertDialog.show();

    }

    public void sure(View view){

        sqLiteDatabase2=this.openOrCreateDatabase("com.yashveer.attendencemanager2",0,null);
        String sql="DELETE FROM absentstudentsName";

        sqLiteDatabase2.execSQL(sql);

        Intent i=new Intent(absentStudent.this,Main2Activity.class);
        startActivity(i);
    }

    public void cancelsure(View view){
        alertDialog.dismiss();
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
        }else if(id==R.id.aboutaaplication){
            Intent i=new Intent(absentStudent.this,aboutapp.class);
            startActivity(i);
        }else if(id==R.id.developer){
            Intent i=new Intent(absentStudent.this,aboutdeveloper.class);
            startActivity(i);
        }else if(id==R.id.privacypolicy){
            Intent i=new Intent(absentStudent.this,privacypolicy.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareAnswer() {

        create_file();


        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Absent Student/" + filename);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
        startActivity(Intent.createChooser(sharingIntent, "Share file with"));



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




    private void saveToTxtFile(String absent_student) {

         timeStamp=new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(System.currentTimeMillis());

        try{

            File path=Environment.getExternalStorageDirectory();

            File dir=new File(path+"/Absent Student/");
            dir.mkdir();

             filename= "AbsentStudent_"+timeStamp+".txt";
            filename2= "AbsentStudent_"+timeStamp;

            File file=new File(dir,filename);

            FileWriter fw=new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);


            for(int i=0;i<studentCount.length;i++) {
                bw.write(i+1+". "+absentsharingList.get(i)+"\n");
            }
            bw.close();
            Toast.makeText(this,filename+" is saved to\n"+dir,Toast.LENGTH_LONG).show();

            file_path=path.getPath();

        }
        catch (Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

}
