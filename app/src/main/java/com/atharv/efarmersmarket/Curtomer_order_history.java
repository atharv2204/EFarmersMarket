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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Curtomer_order_history extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    String document_name;
    FirebaseFirestore db ;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams cvlayoutparams ,tvlayoutparams ;
    TextView textview;
    LinearLayout linearLayout;
    String collection, doc, Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode, deliver,devlivered_on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curtomer_order_history);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("History");
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

//        try {
            DocumentReference documentReference = db.collection("DeliveredOrders").document(document_name);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Name = documentSnapshot.getString("Name");
                        Email = documentSnapshot.getString("Email");
                        Mobile_no = documentSnapshot.getString("Email");
                        Category = documentSnapshot.getString("Category");
                        Product = documentSnapshot.getString("Product");
                        Quantity = documentSnapshot.getString("Quantity");
                        Address = documentSnapshot.getString("Address");
                        Total_Price = documentSnapshot.getString("TotalPrice");
                        paymode=documentSnapshot.getString("PayementMode");
                        date = documentSnapshot.getString("Date");
                        devlivered_on=documentSnapshot.getString("DeliverdOn");
                        addDataToView(Name,Email,Mobile_no,Category,Address,Product,Total_Price,Quantity,date,paymode,devlivered_on);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            });
//        }
//        catch (Exception e){
//            Toast.makeText(context, "No Orders Placed", Toast.LENGTH_SHORT).show();
//        }
    }

    private void addDataToView(String UName, String UEmail, String UMobile_no, String UCategory, String UAddress, String UProduct, String UTotal_Price, String UQuantity, String Udate, String Upaymode,String Udelivered_on) {
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
        cardview.setCardBackgroundColor(Color.parseColor("#de8518"));
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);
        textview = new TextView(getApplicationContext());
        textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textview.setLayoutParams(layoutparams);
        String text = "Name: " + UName + "\nEmail: " + UEmail + "\nMobile No:" + UMobile_no + "\nCategory: " + UCategory + "\nProduct: " + UProduct + "\nQuantity: " + UQuantity + "\nAddress: " + UAddress + "\nOrdered on:" + Udate + "\nPayment Mode:" + Upaymode+"\nDelivered On: "+Udelivered_on+"\n=======================\nTotal Price: " + UTotal_Price ;
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