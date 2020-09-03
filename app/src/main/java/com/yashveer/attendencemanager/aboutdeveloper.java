package com.yashveer.attendencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class aboutdeveloper extends AppCompatActivity {

    TextView aboutdeveloper,info;
    Animation titledeveloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutdeveloper);

        aboutdeveloper=findViewById(R.id.aboutdeveloper);

        titledeveloper= AnimationUtils.loadAnimation(this,R.anim.aboutappanim);

       aboutdeveloper.startAnimation(titledeveloper);

    }
}