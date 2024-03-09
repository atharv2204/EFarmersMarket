package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class View_order_vegetables extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_vegetables);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("View Vegetables");
        actionBar.setBackgroundDrawable(colorDrawable);
    }
    public void Order_Tomato(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("Image",R.drawable.tomato);
        intent.putExtra("VegName","Tomato");
        startActivity(intent);
    }
    public void Order_Potato(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("Image", R.drawable.potato);
        intent.putExtra("VegName","Potato");
        startActivity(intent);
    }
    public void Order_Cucumber(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Cucumber");
        intent.putExtra("Image", R.drawable.cucumber);
        startActivity(intent);
    }
    public void Order_fenugreek(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Fenugreek");
        intent.putExtra("Image", R.drawable.fenugreek);
        startActivity(intent);
    }
    public void Order_Raddish(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Raddish");
        intent.putExtra("Image", R.drawable.raddish);
        startActivity(intent);
    }
    public void Order_Lemon(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Lemon");
        intent.putExtra("Image", R.drawable.lemons);
        startActivity(intent);
    }
    public void Order_Brinjal(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Brinjal");
        intent.putExtra("Image", R.drawable.brinjal);
        startActivity(intent);
    }
    public void Order_Cabbage(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Cabbage");
        intent.putExtra("Image", R.drawable.cabbage);
        startActivity(intent);
    }
    public void Order_Onion(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Onion");
        intent.putExtra("Image", R.drawable.onion);
        startActivity(intent);
    }
    public void Order_Ladyfinger(View view){
        intent=new Intent(View_order_vegetables.this,Order.class);
        intent.putExtra("Category","Vegetables");
        intent.putExtra("VegName","Ladyfinger");
        intent.putExtra("Image", R.drawable.ladyfinger);
        startActivity(intent);
    }
}