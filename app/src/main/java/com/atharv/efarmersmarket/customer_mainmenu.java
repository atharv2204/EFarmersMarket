package com.atharv.efarmersmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class customer_mainmenu extends AppCompatActivity {
    String document_name;
    String get_DBName;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_mainmenu);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        singleToneClass singleToneClass = com.atharv.efarmersmarket.singleToneClass.getInstance();
        document_name=singleToneClass.getData();

        db= FirebaseFirestore.getInstance();

        DocumentReference documentReference=db.collection("Users").document(document_name);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    get_DBName=documentSnapshot.getString("Name");
                    Toast.makeText(getApplicationContext(), "Welcome "+get_DBName, Toast.LENGTH_SHORT).show();
                    ActionBar actionBar=getSupportActionBar();
                    ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
                    assert actionBar != null;
                    actionBar.setTitle("Hello,"+get_DBName);
                    actionBar.setBackgroundDrawable(colorDrawable);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });
    }
    public void buy(View view){
        Intent intent=new Intent(customer_mainmenu.this,customer_home.class);
        startActivity(intent);
    }
    public void view_orders(View view){
        Intent intent=new Intent(customer_mainmenu.this,customer_orders.class);
        startActivity(intent);
    }
    public void customer_his(View view){
        Intent intent=new Intent(customer_mainmenu.this,Curtomer_order_history.class);
        startActivity(intent);
    }
    public void logout(View view){
        Intent intent=new Intent(customer_mainmenu.this,Entry_Page.class);
        startActivity(intent);
        finish();
    }
}