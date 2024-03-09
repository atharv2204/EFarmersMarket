package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Seller_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Home");
        actionBar.setBackgroundDrawable(colorDrawable);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    public void add_product(View view){
        Intent intent=new Intent(Seller_Home.this,add_product.class);
        startActivity(intent);
    }
    public void View_products(View view){
        Intent intent=new Intent(Seller_Home.this,view_products.class);
        startActivity(intent);
    }
    public void Order(View view){
        Intent intent=new Intent(Seller_Home.this,View_Orders_Seller.class);
        startActivity(intent);
    }
    public void confirm_ord(View view){
        Intent intent=new Intent(Seller_Home.this,Seller_Confirrmed_Orders.class);
        startActivity(intent);
    }
    public void history(View view){
        Intent intent=new Intent(Seller_Home.this,Seller_Order_History.class);
        startActivity(intent);
    }
    public void Logout(View view){
        Intent intent=new Intent(Seller_Home.this,Entry_Page.class);
        startActivity(intent);
        finish();
    }
}