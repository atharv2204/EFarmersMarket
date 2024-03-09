package com.atharv.efarmersmarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class view_product_details extends AppCompatActivity {
    FirebaseFirestore firestore ;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams cvlayoutparams ,tvlayoutparams ;
    TextView textview;
    LinearLayout linearLayout;
    String collection,doc;
    SwipeRefreshLayout swipeRefreshLayout;
    public static int Price,Quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Products Details");
        actionBar.setBackgroundDrawable(colorDrawable);

        Price=0 ;
        Quantity=0 ;
        context = getApplicationContext();
        linearLayout = findViewById(R.id.linearLayout);
        firestore = FirebaseFirestore.getInstance() ;

        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });

        Intent intent=getIntent();
        collection=intent.getStringExtra("Name");

        firestore.collection(collection).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    doc=documentChange.getDocument().getId();
                    Price = documentChange.getDocument().getLong("Price").intValue();
                    Quantity = documentChange.getDocument().getLong("Quantity").intValue();
//                    Log.d("h",""+Quantity);
//                    Toast.makeText(context, ""+Price+"   "+Quantity, Toast.LENGTH_SHORT).show();
                    CreateCardViewProgrammatically();
                }
            }
        });

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

        textview.setText(doc+"\nPrice: "+Price+" Rs/Kg\nQuantity: "+Quantity+" Kg");

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

        textview.setTextColor(Color.BLACK);

        textview.setPadding(50,100,50,100);

        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);

        linearLayout.addView(cardview);

    }
}