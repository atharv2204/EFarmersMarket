package com.atharv.efarmersmarket;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class View_Orders_Seller extends AppCompatActivity {
    FirebaseFirestore firestore;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams cvlayoutparams, tvlayoutparams, btnlayoutparams;
    TextView textview;
    Button delivered;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    String collection, doc, Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode;
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders_seller);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Order Details");
        actionBar.setBackgroundDrawable(colorDrawable);
        String e;
        context = getApplicationContext();
        linearLayout = findViewById(R.id.linearLayout);
        firestore = FirebaseFirestore.getInstance();

        if (ContextCompat.checkSelfPermission(View_Orders_Seller.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(View_Orders_Seller.this, new String[]{Manifest.permission.SEND_SMS}, 100);
        }

        try {
            firestore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    try {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            doc = documentChange.getDocument().getId();
                            data = data + " " + doc;
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
//                    Log.d("hello",""+Quantity+Total_Price);
//                    Toast.makeText(context, ""+doc, Toast.LENGTH_SHORT).show();
//                            CreateCardViewProgrammatically();
                            addDataToView(Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode);
                        }
                    } catch (Exception e) {
                        Toast.makeText(View_Orders_Seller.this, "An error Occured!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception exception) {
            Toast.makeText(context, "No Orders Received Currently", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });
    }

    public void CreateCardViewProgrammatically() {

        cardview = new CardView(context);

        cvlayoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tvlayoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

//        btnlayoutparams=new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        btnlayoutparams.gravity=Gravity.CENTER;
//        btnlayoutparams.setMargins(0,0,0,20);
//
//        LinearLayout layout=new LinearLayout(context);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//        ));
//        layout.setOrientation(LinearLayout.VERTICAL);

        cvlayoutparams.setMargins(30, 30, 30, 30);

        cardview.setLayoutParams(cvlayoutparams);

        cardview.setRadius(15);

        cardview.setPadding(25, 25, 25, 25);
        int cv = Color.parseColor("#36BFB1");
        cardview.setCardBackgroundColor(cv);

//        cardview.setTooltipText(doc);

        cardview.setMaxCardElevation(30);

        cardview.setMaxCardElevation(6);

        textview = new TextView(context);

        textview.setLayoutParams(tvlayoutparams);

        String text = "Name: " + Name + "\nEmail: " + Email + "\nMobile No:" + Mobile_no + "\nCategory: " + Category + "\nProduct: " + Product + "\nQuantity: " + Quantity + "\nAddress: " + Address + "\nTotal Price: " + Total_Price + "\nOrdered on:" + date + "\nPayment Mode:" + paymode;
        textview.setText(text);

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

        textview.setTextColor(Color.BLACK);

        textview.setPadding(50, 100, 50, 100);

//        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);

//        cardview.addView(layout);
//        layout.addView(textview);

//        delivered=new Button(context);
//
//        delivered.setLayoutParams(btnlayoutparams);
//
//        delivered.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
//
//        delivered.setTextColor(Color.WHITE);
//
//        delivered.setText("Delivered");
//
//        delivered.setBackgroundResource(R.drawable.add_button);

//        delivered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        delivered.setBackground(new ColorDrawable(Color.parseColor("#D51515")));
//        delivered.setBackgroundColor(Color.RED);

//        cardview.addView(delivered);
//        layout.addView(delivered);

        linearLayout.addView(cardview);

//        cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Hello"+cardview.getTooltipText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void addDataToView(String UName, String UEmail, String UMobile_no, String UCategory, String UAddress, String UProduct, String UTotal_Price, String UQuantity, String Udate, String Upaymode) {
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
        cardview.setCardBackgroundColor(Color.parseColor("#36BFB1"));
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);
        textview = new TextView(getApplicationContext());
        textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textview.setLayoutParams(layoutparams);
        String text = "Name: " + UName + "\nEmail: " + UEmail + "\nMobile No:" + UMobile_no + "\nCategory: " + UCategory + "\nProduct: " + UProduct + "\nQuantity: " + UQuantity + "\nAddress: " + UAddress + "\nTotal Price: " + UTotal_Price + "\nOrdered on:" + Udate + "\nPayment Mode:" + Upaymode;
        textview.setText(text);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        textview.setTextColor(Color.WHITE);
        textview.setPadding(25, 25, 25, 25);
        textview.setGravity(Gravity.CENTER);
        linearLayoutInner.addView(textview);

        Button delete = new Button(getApplicationContext());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("Orders")
                        .whereEqualTo("Name", UName)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentID = documentSnapshot.getId();

                                    Map<String, Object> ord_data = new HashMap<>();
                                    ord_data.put("Name", UName);
                                    ord_data.put("MobileNo", UMobile_no);
                                    ord_data.put("Email", UEmail);
                                    ord_data.put("Category", UCategory);
                                    ord_data.put("Product", UProduct);
                                    ord_data.put("Quantity", UQuantity);
                                    ord_data.put("Address", UAddress);
                                    ord_data.put("TotalPrice", UTotal_Price);
                                    ord_data.put("Date", Udate);
                                    ord_data.put("PayementMode", Upaymode);
                                    ord_data.put("OrderConfirm", "Confirmed");

                                    firestore.collection("ConfirmedOrders")
                                            .document(UEmail)
                                            .set(ord_data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Order Confirm", Toast.LENGTH_SHORT).show();
                                                    firestore.collection("Orders")
                                                            .document(documentID)
                                                            .delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
//                                                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                                    try {
                                                                        String msg = "Dear " + UName + " your order for: \nCategory: " + UCategory + "\nProduct Name: " + UProduct + "\nQuantity: " + UQuantity + "Kg\nTotal Price: " + UTotal_Price + " is Confirm.";
                                                                        SmsManager smgr = SmsManager.getDefault();
                                                                        smgr.sendTextMessage(UMobile_no, null, msg, null, null);
                                                                        Toast.makeText(View_Orders_Seller.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                                                                    } catch (Exception e) {
                                                                        Toast.makeText(View_Orders_Seller.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    recreate();
//                                                    startActivity(new Intent(ViewAllStock.this , MainActivity.class));
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(context, "Failed to Confirm Server Error", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });



                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
            }
        });
        delete.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        delete.setLayoutParams(layoutparams);
        delete.setText("Confirm");
        delete.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        Button Cancel = new Button(getApplicationContext());
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("Orders")
                        .whereEqualTo("Name", UName)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentID = documentSnapshot.getId();

                                    SimpleDateFormat formatter = new SimpleDateFormat("E dd/MM/yyyy 'at' hh:mm:ss a");
                                    Date date = new Date();
                                    String dt = formatter.format(date);

                                    firestore.collection("Orders")
                                            .document(documentID)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show();
                                                    try {
                                                        String msg = "Dear " + UName + " your order for: \nCategory: " + UCategory + "\nProduct Name: " + UProduct + "\nQuantity: " + UQuantity + "Kg\nTotal Price: " + UTotal_Price + " is Cancelled due to some reasons.\nOn " + dt;
                                                        SmsManager smgr = SmsManager.getDefault();
                                                        smgr.sendTextMessage(UMobile_no, null, msg, null, null);
                                                        Toast.makeText(View_Orders_Seller.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                                                    } catch (Exception e) {
                                                        Toast.makeText(View_Orders_Seller.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                                                    }
                                                    recreate();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Failed to Confirm Server Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        Cancel.setLayoutParams(layoutparams);
        Cancel.setText("Cancel");
        Cancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        linearLayoutInner.addView(Cancel);
        linearLayoutInner.addView(delete);
        linearLayoutInner.setLayoutParams(layoutparams);
        linearLayoutInner.setOrientation(LinearLayout.VERTICAL);
        cardview.addView(linearLayoutInner);
        linearLayout.addView(cardview);
    }
}