package com.atharv.efarmersmarket;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class customer_orders extends AppCompatActivity {
    FirebaseFirestore db ;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams cvlayoutparams ,tvlayoutparams ;
    TextView textview;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    String collection,document_name,Category,Quantity,Product,Address,Total_Price,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Your Orders");
        actionBar.setBackgroundDrawable(colorDrawable);

        singleToneClass singleToneClass = com.atharv.efarmersmarket.singleToneClass.getInstance();
        document_name=singleToneClass.getData();

        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });

        db= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        linearLayout = findViewById(R.id.linearLayout);
        db=FirebaseFirestore.getInstance();
        try {
            DocumentReference documentReference = db.collection("Orders").document(document_name);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Category = documentSnapshot.getString("Category");
                        Product = documentSnapshot.getString("Product");
                        Quantity = documentSnapshot.getString("Quantity");
                        Address = documentSnapshot.getString("Address");
                        Total_Price = documentSnapshot.getString("TotalPrice");
                        date = documentSnapshot.getString("Date");
//                    Log.d("hello",""+Quantity+Total_Price);
//                    Toast.makeText(context, ""+doc, Toast.LENGTH_SHORT).show();
                        CreateCardViewProgrammatically();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(context, "No Orders Placed", Toast.LENGTH_SHORT).show();
        }
    }
    public void CreateCardViewProgrammatically(){

        cardview = new CardView(context);

        cvlayoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tvlayoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cvlayoutparams.setMargins(30, 30, 30, 30);

        cardview.setLayoutParams(cvlayoutparams);

        cardview.setRadius(15);

        cardview.setPadding(25, 25, 25, 25);
        int cv = Color.parseColor("#36BFB1");
        cardview.setCardBackgroundColor(cv);

        cardview.setMaxCardElevation(30);

        cardview.setMaxCardElevation(6);

        textview = new TextView(context);

        textview.setLayoutParams(tvlayoutparams);

        String text="Category: "+Category+"\nProduct: "+Product+"\nQuantity: "+Quantity+"\nAddress: "+Address+"\nTotal Price: "+Total_Price+"\nOrdered on:"+date;
        textview.setText(text);

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

        textview.setTextColor(Color.BLACK);

        textview.setPadding(50,100,50,100);

        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);

        linearLayout.addView(cardview);

    }
}