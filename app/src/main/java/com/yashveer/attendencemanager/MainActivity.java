package com.yashveer.attendencemanager;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity extends AppCompatActivity {

    boolean FirstTime;
    SharedPreferences sharedPreferences;
    boolean FirstTime2;

    TextView textView,textView2,creator;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animation= AnimationUtils.loadAnimation(this,R.anim.animtextview);

        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        creator=findViewById(R.id.creator);

        textView.startAnimation(animation);

        textView.animate().translationY(100).setDuration(2000).start();
        textView2.animate().translationY(-100).setDuration(2000).start();
        creator.animate().translationY(-100).setDuration(2000).start();



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

                if (FirstTime2==true){
                    Intent i2=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i2);
                }else{

                    Intent i2=new Intent(MainActivity.this,absentStudent.class);
                    startActivity(i2);

                }

            }






            //invoke the SecondActivity.

            finish();
            //the current activity will get finished.
        }
    }, 4000);


    }
}
