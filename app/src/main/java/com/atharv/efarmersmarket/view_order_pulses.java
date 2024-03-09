package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class view_order_pulses extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_pulses);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("View Pulses");
        actionBar.setBackgroundDrawable(colorDrawable);
    }
    
    public void Order_Turdal(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.turdal);
        intent.putExtra("VegName","Turdal");
        startActivity(intent);
    }
    public void Order_Soyabean(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.soyabean);
        intent.putExtra("VegName","Soyabeans");
        startActivity(intent);
    }
    public void Order_mungdal(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.mung_dal);
        intent.putExtra("VegName","Mungdaal");
        startActivity(intent);
    }
    public void Order_Masoordal(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.masoor_dal);
        intent.putExtra("VegName","Masoordal");
        startActivity(intent);
    }
    public void Order_greenpeas(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.green_peas);
        intent.putExtra("VegName","GreenPeas");
        startActivity(intent);
    }
    public void Order_Chickpeas(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.chickpeas);
        intent.putExtra("VegName","Chickpeas");
        startActivity(intent);
    }
    public void Order_ChannaDal(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.chana_dal);
        intent.putExtra("VegName","ChanaDal");
        startActivity(intent);
    }
    public void Order_Blackeyedpeas(View view){
        intent=new Intent(view_order_pulses.this,Order.class);
        intent.putExtra("Category","Pulses");
        intent.putExtra("Image", R.drawable.black_eyed_peas);
        intent.putExtra("VegName","BlackedEyedPeas");
        startActivity(intent);
    }
}