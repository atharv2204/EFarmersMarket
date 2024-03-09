package com.atharv.efarmersmarket;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Seller_Order_History extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firestore;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams cvlayoutparams, tvlayoutparams, btnlayoutparams;
    TextView textview;
    LinearLayout linearLayout;
    String collection, doc, Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode, deliver,devlivered_on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_history);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("History");
        actionBar.setBackgroundDrawable(colorDrawable);

        firestore=FirebaseFirestore.getInstance();
        linearLayout = findViewById(R.id.linearLayout);

        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });

        try {
            firestore.collection("DeliveredOrders").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    try {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            doc = documentChange.getDocument().getId();
                            Name = documentChange.getDocument().getData().get("Name").toString();
                            Email = documentChange.getDocument().getData().get("Email").toString();
                            Mobile_no = documentChange.getDocument().getData().get("MobileNo").toString();
                            Category = documentChange.getDocument().getData().get("Category").toString();
                            Product = documentChange.getDocument().getData().get("Product").toString();
                            Quantity = documentChange.getDocument().getData().get("Quantity").toString();
                            Address = documentChange.getDocument().getData().get("Address").toString();
                            Total_Price = documentChange.getDocument().getData().get("TotalPrice").toString();
                            date = documentChange.getDocument().getData().get("Date").toString();
                            paymode = documentChange.getDocument().getData().get("PayementMode").toString();
                            deliver = documentChange.getDocument().getData().get("Delivered").toString();
                            devlivered_on = documentChange.getDocument().getData().get("DeliverdOn").toString();

                            addDataToView(Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode,deliver,devlivered_on);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Seller_Order_History.this, "No Orders Currently!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception exception) {
            Toast.makeText(Seller_Order_History.this, "No Orders Currently", Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataToView(String UName, String UEmail, String UMobile_no, String UCategory, String UAddress, String UProduct, String UTotal_Price, String UQuantity, String Udate, String Upaymode,String Udeliver,String Udelivered_on) {
        cardview = new CardView(getApplicationContext());
        LinearLayout linearLayoutInner = new LinearLayout(getApplicationContext());

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutparams.setMargins(10, 15, 10, 15);

        cardview.setLayoutParams(layoutparams);
        cardview.setRadius(15);
        cardview.setPadding(25, 25, 25, 25);
        cardview.setCardBackgroundColor(Color.parseColor("#eb4034"));
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);
        textview = new TextView(getApplicationContext());
        textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textview.setLayoutParams(layoutparams);
        String text = "Name: " + UName + "\nEmail: " + UEmail + "\nMobile No:" + UMobile_no + "\nCategory: " + UCategory + "\nProduct: " + UProduct + "\nQuantity: " + UQuantity + "\nAddress: " + UAddress + "\nTotal Price: " + UTotal_Price + "\nOrdered on:" + Udate + "\nPayment Mode:" + Upaymode+"Delivered: "+Udeliver+"\nDelivered On: "+Udelivered_on;
        textview.setText(text);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        textview.setTextColor(Color.WHITE);
        textview.setPadding(25, 25, 25, 25);
        textview.setGravity(Gravity.CENTER);
        linearLayoutInner.addView(textview);

        linearLayoutInner.setLayoutParams(layoutparams);
        linearLayoutInner.setOrientation(LinearLayout.VERTICAL);
        cardview.addView(linearLayoutInner);
        linearLayout.addView(cardview);
    }

}