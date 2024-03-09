package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class view_products extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("View Products");
        actionBar.setBackgroundDrawable(colorDrawable);
    }
    public void view_vegetables(View view){
        intent=new Intent(view_products.this,view_product_details.class);
        intent.putExtra("Name","Vegetables");
        startActivity(intent);
    }
    public void view_fruits(View view){
        intent=new Intent(view_products.this,view_product_details.class);
        intent.putExtra("Name","Fruits");
        startActivity(intent);
    }
    public void view_pulses(View view){
        intent=new Intent(view_products.this,view_product_details.class);
        intent.putExtra("Name","Pulses");
        startActivity(intent);
    }
    public void view_grains(View view){
        intent=new Intent(view_products.this,view_product_details.class);
        intent.putExtra("Name","Grains");
        startActivity(intent);
    }
}