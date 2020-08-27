package com.yashveer.attendencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    boolean FirstTime;
    boolean FirstTime2;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animationView);

         sharedPreferences=getSharedPreferences("com.yashveer.attendencemanager",MODE_PRIVATE);

         FirstTime=sharedPreferences.getBoolean("FirstTime",true);
        FirstTime2=sharedPreferences.getBoolean("FirstTime2",true);





    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {


            if (FirstTime==true){
                Intent i=new Intent(MainActivity.this,Attendence_Manager.class);
                startActivity(i);
            }else{

                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

            }

            if (FirstTime2==true){
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }else{

                Intent i=new Intent(MainActivity.this,absentStudent.class);
                startActivity(i);

            }


            //invoke the SecondActivity.

            finish();
            //the current activity will get finished.
        }
    }, 4000);


    }
}
