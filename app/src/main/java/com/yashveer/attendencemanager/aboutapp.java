package com.yashveer.attendencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class aboutapp extends AppCompatActivity {


    TextView aboutapp;
    Animation title,infoanim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);

        aboutapp=findViewById(R.id.abotuapp);
        title= AnimationUtils.loadAnimation(this,R.anim.aboutappanim);
        infoanim= AnimationUtils.loadAnimation(this,R.anim.infoanim);

        aboutapp.startAnimation(title);




    }
}