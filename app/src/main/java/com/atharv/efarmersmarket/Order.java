package com.atharv.efarmersmarket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order extends AppCompatActivity {
    ImageView imageView;
    int resId,db_price,db_quantity,updated_quantity,Total_Price;
    Boolean flag;
    EditText address,editText_quantity;
    String VegName,collection,get_email,db_name,db_mobile;
    TextView price,pr_name,quantity;
    FirebaseFirestore db;
    SwipeRefreshLayout swipeRefreshLayout;
    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#f08b26"));
        actionBar.setTitle("Order");
        actionBar.setBackgroundDrawable(colorDrawable);

        if (ContextCompat.checkSelfPermission(Order.this , Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Order.this , new String[]{Manifest.permission.SEND_SMS}, 100);
        }

        imageView=(ImageView)findViewById(R.id.image);
        price=(TextView)findViewById(R.id.rate);
        pr_name=(TextView)findViewById(R.id.pr_name);
        quantity=(TextView)findViewById(R.id.quantity);
        address=(EditText) findViewById(R.id.address);
        editText_quantity=(EditText) findViewById(R.id.et_quantity);

        Intent intent=getIntent();
        VegName=intent.getStringExtra("VegName");
        collection=intent.getStringExtra("Category");
        Bundle bundle=intent.getExtras();
        if (bundle != null) {
            resId= bundle.getInt("Image");
            imageView.setImageResource(resId);
        }

        swipeRefreshLayout = findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });

        pr_name.setText(VegName);

        singleToneClass singleToneClass = com.atharv.efarmersmarket.singleToneClass.getInstance();
        get_email=singleToneClass.getData();

        db=FirebaseFirestore.getInstance();

        DocumentReference documentReference=db.collection(collection).document(VegName);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    db_price=documentSnapshot.getLong("Price").intValue();
                    db_quantity=documentSnapshot.getLong("Quantity").intValue();
                    price.setText("Price:"+db_price+" Rs/Kg");
//                    db_quantity=0;
                    if(db_quantity<=0){
                        quantity.setTextColor(Color.RED);
                        quantity.setText("In Stock:"+db_quantity+"Kg\nNot Available");
                    }
                    else{
                        quantity.setText("In Stock:"+db_quantity+" Kg");
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });

        DocumentReference documentReference1=db.collection("Users").document(get_email);
        documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    db_name=documentSnapshot.getString("Name");
                    db_mobile=documentSnapshot.getString("MobileNo");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });
    }
    public void order(View view) {
        RadioButton cod = (RadioButton) findViewById(R.id.cod);
        RadioButton upi = (RadioButton) findViewById(R.id.upi);
        if (cod.isChecked()) {
//            Toast.makeText(this, "You selected : "+.getText().toString(), Toast.LENGTH_SHORT).show();
              pay_on_delivery();
        }
        if (upi.isChecked()) {
//            Toast.makeText(this, "You selected : " + upi.getText().toString(), Toast.LENGTH_SHORT).show();
            payUsingUpi();
        }
    }
    
    //COD
    public void pay_on_delivery(){
        int quan = Integer.parseInt(editText_quantity.getText().toString());
        String add = address.getText().toString();
        Total_Price=quan*db_price;
        String q=String.valueOf(quan);
        String t=String.valueOf(Total_Price);
        SimpleDateFormat formatter = new SimpleDateFormat("E dd/MM/yyyy 'at' hh:mm:ss a");
        Date date = new Date();
        String dt= formatter.format(date);

        Map<String, Object> ord_data = new HashMap<>();
        ord_data.put("Name", db_name);
        ord_data.put("MobileNo", db_mobile);
        ord_data.put("Email", get_email);
        ord_data.put("Category", collection);
        ord_data.put("Product", VegName);
        ord_data.put("Quantity", q);
        ord_data.put("Address", add);
        ord_data.put("TotalPrice", t);
        ord_data.put("Date", dt);
        ord_data.put("PayementMode","Pay On Delivery");

        updated_quantity=db_quantity-quan;

        Map update_data=new HashMap<>();
        update_data.put("Quantity",updated_quantity);
        update_data.put("Price",db_price);

        if (db_quantity < quan) {
            Toast.makeText(this, "Product Limit Exceeded", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String no="8080728482";
                String msg = "Customer Name:"+db_name+"\nMobile No.:"+db_mobile+"\nEmail: "+get_email+"\nCategory: " + collection + "\nProduct Name: " + VegName + "\nQuantity: " + quantity.getText().toString() + "\nAddress: " + address.getText().toString()+"\nTotal Price: "+t;
                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage(no, null, msg, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(Order.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
            }

            db.collection("Orders")
                    .document(get_email)
                    .set(ord_data).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Order.this, "Ordered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Order.this, "An Error Occurred Failed to place to Order", Toast.LENGTH_SHORT).show();
                        }
                    });

            db.collection(collection)
                    .document(VegName)
                    .update(update_data)
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            address.setText("");
                            editText_quantity.setText("");
                            recreate();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            recreate();
                            Toast.makeText(Order.this, "Unable to Order an error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    
    //UPI Payment
    void payUsingUpi() {
        int quan = Integer.parseInt(editText_quantity.getText().toString());
        Total_Price=quan*db_price;
        String amount=String.valueOf(Total_Price);

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "atharvvkale22@oksbi")
                .appendQueryParameter("pn", "E Farmers Market")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Order.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(Order.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(Order.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
//                pay_on_delivery();
                upi_pay();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Order.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Order.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Order.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void upi_pay() {
        int quan = Integer.parseInt(editText_quantity.getText().toString());
        String add = address.getText().toString();
        Total_Price=quan*db_price;
        String q=String.valueOf(quan);
        String t=String.valueOf(Total_Price);
        SimpleDateFormat formatter = new SimpleDateFormat("E dd/MM/yyyy 'at' hh:mm:ss a");
        Date date = new Date();
        String dt= formatter.format(date);

        Map<String, Object> ord_data = new HashMap<>();
        ord_data.put("Name", db_name);
        ord_data.put("MobileNo", db_mobile);
        ord_data.put("Email", get_email);
        ord_data.put("Category", collection);
        ord_data.put("Product", VegName);
        ord_data.put("Quantity", q);
        ord_data.put("Address", add);
        ord_data.put("TotalPrice", t);
        ord_data.put("Date", dt);
        ord_data.put("PayementMode","UPI");

        updated_quantity=db_quantity-quan;
        Map update_data=new HashMap<>();
        update_data.put("Quantity",updated_quantity);
        update_data.put("Price",db_price);

        if (db_quantity < quan) {
            Toast.makeText(this, "Product Limit Exceeded", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String msg = "Customer Name:"+db_name+"\nMobile No.:"+db_mobile+"Email: +"+get_email+"\nCategory: " + collection + "\nProduct Name: " + VegName + "\nQuantity: " + quantity.getText().toString() + "\nAddress: " + address.getText().toString();
                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage("8080728482", null, msg, null, null);
                Toast.makeText(Order.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(Order.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
            }

            db.collection("Orders")
                    .document(get_email)
                    .set(ord_data).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Order.this, "Ordered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Order.this, "An Error Occurred Failed to place to Order", Toast.LENGTH_SHORT).show();
                        }
                    });

            db.collection(collection)
                    .document(VegName)
                    .update(update_data)
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            address.setText("");
                            editText_quantity.setText("");
                            recreate();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            recreate();
                            Toast.makeText(Order.this, "Unable to Order an error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}