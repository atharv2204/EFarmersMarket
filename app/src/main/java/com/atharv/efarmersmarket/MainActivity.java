package com.atharv.efarmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Animation top,bottom;
    ImageView img;
    TextView main,second,name;
    ProgressBar pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pg=(ProgressBar)findViewById(R.id.pg);
        pg.postDelayed(new Runnable() {
            public void run() {
                pg.setVisibility(View.VISIBLE);
            }
        }, 1500);
        
        top= AnimationUtils.loadAnimation(this,R.anim.topanimation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        img=findViewById(R.id.icon);
        main=findViewById(R.id.head);
        second=findViewById(R.id.sub_head);
        name=findViewById(R.id.creator);

        img.setAnimation(top);
        main.setAnimation(bottom);
        second.setAnimation(bottom);
        name.setAnimation(bottom);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Entry_Page.class);
//                Intent intent=new Intent(MainActivity.this,Seller_Home.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}