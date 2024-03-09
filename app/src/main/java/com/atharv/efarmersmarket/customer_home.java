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

public class customer_home extends AppCompatActivity {
    String document_name;
    String get_DBName;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        ActionBar actionBar=getSupportActionBar();
////        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
//        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
//        actionBar.setTitle("Welcome");
//        actionBar.setBackgroundDrawable(colorDrawable);
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
    public void order_vegetables(View view){
        Intent intent=new Intent(customer_home.this,View_order_vegetables.class);
        intent.putExtra("Name","Vegetables");
        startActivity(intent);
    }
    public void order_fruits(View view){
        Intent intent=new Intent(customer_home.this,View_order_fruits.class);
        intent.putExtra("Name","Fruits");
        startActivity(intent);
    }
    public void order_pulses(View view){
        Intent intent=new Intent(customer_home.this,view_order_pulses.class);
        intent.putExtra("Name","Pulses");
        startActivity(intent);
    }
    public void order_grains(View view){
        Intent intent=new Intent(customer_home.this,view_order_grains.class);
        intent.putExtra("Name","Grains");
        startActivity(intent);
    }
}