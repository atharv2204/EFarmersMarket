package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class view_order_grains extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_grains);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("View Grains");
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void Order_Maize(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.maize);
        intent.putExtra("VegName","Maize");
        startActivity(intent);
    }
    public void Order_Oats(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.oats);
        intent.putExtra("VegName","Oat");
        startActivity(intent);
    }
    public void Order_Rice(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.rice);
        intent.putExtra("VegName","Rice");
        startActivity(intent);
    }
    public void Order_Sorghum(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.sorghum);
        intent.putExtra("VegName","Sorghum");
        startActivity(intent);
    }
    public void Order_Teff(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.teff);
        intent.putExtra("VegName","Teff");
        startActivity(intent);
    }
    public void Order_Wheat(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.wheat);
        intent.putExtra("VegName","Wheat");
        startActivity(intent);
    }
    public void Order_Barley(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.barley);
        intent.putExtra("VegName","Barley");
        startActivity(intent);
    }
    public void Order_Rye(View view){
        intent=new Intent(view_order_grains.this,Order.class);
        intent.putExtra("Category","Grains");
        intent.putExtra("Image", R.drawable.rye);
        intent.putExtra("VegName","Rye");
        startActivity(intent);
    }
}