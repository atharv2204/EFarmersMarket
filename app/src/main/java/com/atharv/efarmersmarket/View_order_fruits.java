package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class View_order_fruits extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_fruits);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
//        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        actionBar.setTitle("View Fruits");
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void Order_Apple(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.apple);
        intent.putExtra("VegName","Apple");
        startActivity(intent);
    }

    public void Order_Banana(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.banana);
        intent.putExtra("VegName","Banana");
        startActivity(intent);
    }

    public void Order_Kiwi(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.kiwi);
        intent.putExtra("VegName","Kiwi");
        startActivity(intent);
    }

    public void Order_Mango(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.mango);
        intent.putExtra("VegName","Mango");
        startActivity(intent);
    }

    public void Order_Mulberry(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.blackberry);
        intent.putExtra("VegName","Mulberry");
        startActivity(intent);
    }

    public void Order_Orange(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.orange);
        intent.putExtra("VegName","Orange");
        startActivity(intent);
    }

    public void Order_Papaya(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.papaya);
        intent.putExtra("VegName","Papaya");
        startActivity(intent);
    }
    public void Order_Peer(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.pear);
        intent.putExtra("VegName","Peer");
        startActivity(intent);
    }
    public void Order_Pineapple(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.panapple);
        intent.putExtra("VegName","Pineapple");
        startActivity(intent);
    }
    public void Order_Watermelon(View view) {
        intent=new Intent(View_order_fruits.this , Order.class);
        intent.putExtra("Category","Fruits");
        intent.putExtra("Image",R.drawable.watermelon);
        intent.putExtra("VegName","Watermelon");
        startActivity(intent);
    }
}
