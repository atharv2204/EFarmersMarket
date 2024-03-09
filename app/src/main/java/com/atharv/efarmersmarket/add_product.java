package com.atharv.efarmersmarket;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class add_product extends AppCompatActivity {
    String[] product_veg = { "Tomato", "Brinjal", "Cabbage", "Cucumber", "Ladyfinger","Fenugreek","Lemon","Onion","Potato","Raddish"};
    String[] product_grains = { "Wheat", "Rice", "Maize", "Sorghum","Barley", "Oat","Rye","Teff"};
    String[] product_pulses = { "Soyabeans", "Masoordal", "GreenPeas", "Mungdaal", "Turdal","ChanaDal","BlackedEyedPeas","Chickpeas"};
    String[] product_Fruits = { "Mango", "Apple", "Banana", "Orange", "Kiwi","Mulberry","Papaya","Peer","Pineapple","Watermelon"};
    String[] type = { "Vegetables", "Grains", "Pulses", "Fruits"};
    Spinner product,spin;
    EditText pr,qn;
    int db_pri,db_qan;
    String Price,Quantity,collection,doc;
    FirebaseFirestore db;

    //    ArrayAdapter<String> veg,grain,pluses,fruits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Update Product Data");
        actionBar.setBackgroundDrawable(colorDrawable);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        spin = (Spinner) findViewById(R.id.type);

        product = (Spinner) findViewById(R.id.name);

        db=FirebaseFirestore.getInstance();
//        product.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> Category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter<String> Category = new ArrayAdapter<String>(this, R.layout.spinner_list, type);
//        Category.setDropDownViewResource(R.layout.spinner_list);
        //Setting the ArrayAdapter data on the Spinner

        spin.setAdapter(Category);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<String> veg = new ArrayAdapter<String>(add_product.this, android.R.layout.simple_spinner_item, product_veg);
                    veg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    product.setAdapter(veg);
                }
                if (position == 1) {
                    ArrayAdapter<String> grain = new ArrayAdapter<String>(add_product.this, android.R.layout.simple_spinner_item, product_grains);
                    grain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    product.setAdapter(grain);
                }
                if (position == 2) {
                    ArrayAdapter<String> pluses = new ArrayAdapter<String>(add_product.this, android.R.layout.simple_spinner_item, product_pulses);
                    pluses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    product.setAdapter(pluses);
                }
                if (position == 3) {
                    ArrayAdapter<String> fruits = new ArrayAdapter<String>(add_product.this, android.R.layout.simple_spinner_item, product_Fruits);
                    fruits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    product.setAdapter(fruits);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    public void add(View view){

        pr=(EditText)findViewById(R.id.price);
        qn=(EditText)findViewById(R.id.quantity);

        collection=spin.getSelectedItem().toString();
        doc=product.getSelectedItem().toString();
        //        Toast.makeText(this, ""+collection+doc, Toast.LENGTH_SHORT).show();
        if(pr.getText().toString().equals("")){
            pr.setError("Please fill the price");
        }
        else if(qn.getText().toString().equals("")){
            qn.setError("Please fill the price");
        }
        else {
            Price = pr.getText().toString();
            Quantity = qn.getText().toString();

            int pri = Integer.parseInt(Price);
            int quan = Integer.parseInt(Quantity);

            DocumentReference documentReference = db.collection(collection).document(doc);
//        documentReference=db.collection(collection).document(doc);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        int quant = documentSnapshot.getLong("Quantity").intValue();
                        int q = quant + quan;
                        Map data = new HashMap<>();
                        data.put("Price", pri);
                        data.put("Quantity", q);
                        db.collection(collection)
                                .document(doc)
                                .update(data)
                                .addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(add_product.this, "Product Added\n" + collection + " : " + doc + "\nPrice: " + pri + "Rs\nQuantity" + quan, Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(add_product.this, "Unable to book an error occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }


    }
}