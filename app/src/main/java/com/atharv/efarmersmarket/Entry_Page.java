package com.atharv.efarmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Entry_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void click(View view) {
        Intent intent = new Intent(Entry_Page.this,seller_login.class);
        startActivity(intent);
        finish();
    }
    public void customer_login(View view){
        Intent intent = new Intent(Entry_Page.this,customer_login.class);
//        Intent intent = new Intent(Entry_Page.this,Upi_Payment.class);
//        Intent intent = new Intent(Entry_Page.this,customer_home.class);
        startActivity(intent);
        finish();
    }
    public void SignUp(View view){
        Intent intent = new Intent(Entry_Page.this,customer_signup.class);
        startActivity(intent);
        finish();
    }
}