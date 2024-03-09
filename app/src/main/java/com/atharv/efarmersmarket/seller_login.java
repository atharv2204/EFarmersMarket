package com.atharv.efarmersmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class seller_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void clk(View view)
    {
        EditText uname=(EditText) findViewById(R.id.uname);
        EditText pass=(EditText) findViewById(R.id.pass);
        String name=uname.getText().toString();
        String psw=pass.getText().toString();
        if(name.equals("Admin")&& psw.equals("Admin@123"))
        {
            Toast.makeText(getApplicationContext(),"Login in Successful",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(seller_login.this,Seller_Home.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Login in Failed",Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View view){
        Intent intent=new Intent(this,Entry_Page.class);
        startActivity(intent);
        finish();
    }
}