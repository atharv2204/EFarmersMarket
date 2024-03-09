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

public class Seller_Confirrmed_Orders extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firestore;
    Context context;
    CardView cardview;
    LinearLayout.LayoutParams cvlayoutparams, tvlayoutparams, btnlayoutparams;
    TextView textview;
    LinearLayout linearLayout;
    String collection, doc, Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode,con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_confirrmed_orders);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Confirm Orders");
        actionBar.setBackgroundDrawable(colorDrawable);

        firestore=FirebaseFirestore.getInstance();
        linearLayout = findViewById(R.id.linearLayout);
        context=getApplicationContext();

        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });

        if (ContextCompat.checkSelfPermission(Seller_Confirrmed_Orders.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Seller_Confirrmed_Orders.this, new String[]{Manifest.permission.SEND_SMS}, 100);
        }

        try {
            firestore.collection("ConfirmedOrders").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            con = documentChange.getDocument().getData().get("OrderConfirm").toString();
                            addDataToView(Name, Email, Mobile_no, Category, Address, Product, Total_Price, Quantity, date, paymode);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Seller_Confirrmed_Orders.this, "No Orders Currently!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception exception) {
            Toast.makeText(Seller_Confirrmed_Orders.this, "No Orders  Currently", Toast.LENGTH_SHORT).show();
        }
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
        cardview.setCardBackgroundColor(Color.parseColor("#498df2"));
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

        Button delivered = new Button(getApplicationContext());
        delivered.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        delivered.setLayoutParams(layoutparams);
        delivered.setText("Delivered");
        delivered.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("ConfirmedOrders")
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
                                    ord_data.put("Delivered", "Yes");
                                    ord_data.put("DeliverdOn", dt);

                                    firestore.collection("DeliveredOrders")
                                            .document(UEmail)
                                            .set(ord_data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "Order Delivered", Toast.LENGTH_SHORT).show();
                                                    firestore.collection("ConfirmedOrders")
                                                            .document(documentID)
                                                            .delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
//                                                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                                    try {
                                                                        String msg = "Dear " + UName + " your order for: \nCategory: " + UCategory + "\nProduct Name: " + UProduct + "\nQuantity: " + UQuantity + "Kg\nTotal Price: " + UTotal_Price + " is Delivered.\nOn " + dt;
                                                                        SmsManager smgr = SmsManager.getDefault();
                                                                        smgr.sendTextMessage(UMobile_no, null, msg, null, null);
                                                                        Toast.makeText(Seller_Confirrmed_Orders.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                                                                    } catch (Exception e) {
                                                                        Toast.makeText(Seller_Confirrmed_Orders.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    recreate();
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

        Button Cancel = new Button(getApplicationContext());
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("ConfirmedOrders")
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


                                    firestore.collection("ConfirmedOrders")
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
                                                        Toast.makeText(Seller_Confirrmed_Orders.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                                                    } catch (Exception e) {
                                                        Toast.makeText(Seller_Confirrmed_Orders.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                                                    }
                                                    recreate();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Failed to Update Server Error", Toast.LENGTH_SHORT).show();
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

        linearLayoutInner.addView(delivered);
        linearLayoutInner.addView(Cancel);
        linearLayoutInner.setLayoutParams(layoutparams);
        linearLayoutInner.setOrientation(LinearLayout.VERTICAL);
        cardview.addView(linearLayoutInner);
        linearLayout.addView(cardview);
    }
}