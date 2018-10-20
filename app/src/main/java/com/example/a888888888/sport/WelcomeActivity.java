package com.example.a888888888.sport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{
    private CircleImageView welcome_image;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome_image=findViewById(R.id.welcome_image);
        //imageView 設定動畫元件(透明度調整)
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        welcome_image.setAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent();
        //將原本Activity的換成MainActivity
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        Log.i("回去主頁面",""+1);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
