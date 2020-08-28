package com.yashveer.attendencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;



public class absentStudent extends AppCompatActivity {


    String []studentCount;

    SQLiteDatabase sqLiteDatabase2;


    Cursor c;



    ListView absentListview;

    ArrayAdapter<String> absentListAdapter;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    Calendar calendar;


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



        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

         calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);


        alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);





    }

public void DelteDataInAlarmReciever(){
        String sql="DELETE FROM absentstudentsName";
        sqLiteDatabase2.execSQL(sql);
}


}