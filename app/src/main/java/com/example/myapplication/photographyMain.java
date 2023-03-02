package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class photographyMain extends AppCompatActivity {

    ImageView Propic;
    private TextView businessName,locality,address,datetime,nopackage,nowork,pricestarts;
    Button book,confirm,ok,cll,fb,ig,yt,wb,hm,pf,lo;
    long id=0;
    Uri uri,uripn;

    private imgsliderAdapter adapter;
    private SliderView sliderView,sliderViewW;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_photography_main);

        mAuth = FirebaseAuth.getInstance();

        Propic = findViewById(R.id.propic);
        businessName = findViewById(R.id.txtBusinessName);
        locality = findViewById(R.id.Locality);
        address = findViewById(R.id.Address);
        book = findViewById(R.id.book);
        confirm = findViewById(R.id.confirm);
        datetime = findViewById(R.id.datetime);
        nopackage = findViewById(R.id.nopackage);
        nowork = findViewById(R.id.nowork);
        ok = findViewById(R.id.ok);
        cll = findViewById(R.id.cllbtn);
        fb = findViewById(R.id.fbbtn);
        ig = findViewById(R.id.igbtn);
        yt = findViewById(R.id.ytbtn);
        wb = findViewById(R.id.wbbtn);
        pricestarts = findViewById(R.id.pricestarts);
        hm = findViewById(R.id.hm);
        pf = findViewById(R.id.pf);
        lo = findViewById(R.id.LObutton);
//        packge1 = findViewById(R.id.packge1);
//        packge2 = findViewById(R.id.packge2);
//        packge3 = findViewById(R.id.packge3);
//        txtpackage1 = findViewById(R.id.txtpackage1);
//        txtpackage2 = findViewById(R.id.txtpackage2);
//        txtpackage3 = findViewById(R.id.txtpackage3);
//        bookLayout = findViewById(R.id.bookLayout);
//        package1Name = findViewById(R.id.pck1Name);
//        package2Name = findViewById(R.id.pck2Name);
//        package3Name = findViewById(R.id.pck3Name);

        DatabaseReference re = FirebaseDatabase.getInstance().getReference("Photography");

        Intent intent = getIntent();
        String uid = intent.getStringExtra("UserID");

        re.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                seller detail = snapshot.getValue(seller.class);

                if (detail!=null){
                    String business_Name = detail.Business_Name;
                    String local = detail.Locality;
                    String adrs = detail.Address;
                    double sp = detail.Starting_Price;

                    businessName.setText(business_Name);
                    locality.setText(local);
                    address.setText(adrs);
                    pricestarts.setText(String.format("%.2f",sp));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        re.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String pn = snapshot.child("mobile").getValue(String.class);

//                String p1 = snapshot.child("pck1_Name").getValue(String.class);
//                String p2 = snapshot.child("pck2_Name").getValue(String.class);
//                String p3 = snapshot.child("pck3_Name").getValue(String.class);
//
//                String p1Url = snapshot.child("package1").getValue(String.class);
//                String p2Url = snapshot.child("package2").getValue(String.class);
//                String p3Url = snapshot.child("package3").getValue(String.class);

                uripn = Uri.parse("tel:" + pn);

//                txtpackage1.setText(p1);
//                txtpackage2.setText(p2);
//                txtpackage3.setText(p3);
//
//                if (p1Url==null && p2Url==null  && p3Url==null){
//                    nopackage.setVisibility(View.VISIBLE);
//                    packge1.setVisibility(View.GONE);
//                    packge2.setVisibility(View.GONE);
//                    packge3.setVisibility(View.GONE);
//                }else if (p1Url!=null || p2Url!=null  || p3Url!=null){
//                    nopackage.setVisibility(View.GONE);
//                    packge1.setVisibility(View.VISIBLE);
//                    packge2.setVisibility(View.VISIBLE);
//                    packge3.setVisibility(View.VISIBLE);
//                    Picasso.get().load(p1Url).into(packge1);
//                    Picasso.get().load(p2Url).into(packge2);
//                    Picasso.get().load(p3Url).into(packge3);
//                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(photographyMain.this, firstHome.class);
                startActivity(intent);
            }
        });

        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(photographyMain.this, home.class);
                startActivity(intent);
            }
        });

        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(photographyMain.this, Login.class);
                startActivity(intent);
            }
        });

        cll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, uripn);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(photographyMain.this, "Invalid Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        try {
                            String fbl = snapshot.child("f_Link").getValue(String.class);

                            uri = Uri.parse(fbl);

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(photographyMain.this, "No Link Found!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        try {
                            String igl = snapshot.child("i_Link").getValue(String.class);

                            uri = Uri.parse(igl);

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(photographyMain.this, "No Link Found!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        try {
                            String yl = snapshot.child("y_Link").getValue(String.class);

                            uri = Uri.parse(yl);

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(photographyMain.this, "No Link Found!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        try {
                            String wbl = snapshot.child("business_Link").getValue(String.class);

                            uri = Uri.parse(wbl);

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(photographyMain.this, "No Link Found!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        sliderViewW = findViewById(R.id.sliderWork);

        re.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                seller sl = snapshot.getValue(seller.class);

                String w1 = sl.Work1;
                String w2 = sl.Work2;
                String w3 = sl.Work3;
                String w4 = sl.Work4;
                String w5 = sl.Work5;

                if (w1 == null && w2 == null && w3 == null && w4 == null && w5 == null){
                    String[] sliderList = {};

                    adapter = new imgsliderAdapter(sliderList);
                    sliderViewW.setSliderAdapter(adapter);
                    nowork.setVisibility(View.VISIBLE);
                }else {
                    nowork.setVisibility(View.GONE);
                    String[] sliderList = {w1,w2,w3,w4,w5};

                    adapter = new imgsliderAdapter(sliderList);
                    sliderViewW.setSliderAdapter(adapter);
                    sliderViewW.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    sliderViewW.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                    sliderViewW.startAutoCycle();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        sliderView = findViewById(R.id.slider);

        re.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                seller sl = snapshot.getValue(seller.class);

                String p1 = sl.Package1;
                String p2 = sl.Package2;
                String p3 = sl.Package3;

                if (p1 == null && p2 == null && p3 == null){
                    String[] sliderList = {};

                    adapter = new imgsliderAdapter(sliderList);
                    sliderView.setSliderAdapter(adapter);
                    nopackage.setVisibility(View.VISIBLE);
                }else {
                    nopackage.setVisibility(View.GONE);
                    String[] sliderList = {p1,p2,p3};

                    adapter = new imgsliderAdapter(sliderList);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                    sliderView.startAutoCycle();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

//        db = FirebaseFirestore.getInstance();
//        db.collection("Photography").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                sellerImages images = documentSnapshot.toObject(sellerImages.class);
//
//                String p1 = images.getPackage1();
//                String p2 = images.getPackage2();
//                String p3 = images.getPackage3();
//
//                String[] sliderList = {p1,p2,p3};
//
//                adapter = new imgsliderAdapter(sliderList);
//                sliderView.setSliderAdapter(adapter);
//                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
//                sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
//                sliderView.startAutoCycle();
//            }
//        });

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImagesRefPP = storageRef.child("photographer/" + uid+"/" + "profile_Pic");
        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Propic);
            }
        });

        locality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseReference re = FirebaseDatabase.getInstance().getReference("Photography");
                    Intent intent = getIntent();
                    String uid = intent.getStringExtra("UserID");

                    re.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            seller detail = snapshot.getValue(seller.class);

                            if (detail!=null){
                                double lati = detail.Latitude;
                                double longi = detail.Longitude;

                                if (lati==0 && longi==0){
                                    Toast.makeText(photographyMain.this, "Cannot find any location", Toast.LENGTH_SHORT).show();
                                }else {
                                    Uri uri = Uri.parse("http://maps.google.com/maps?q=loc:" + lati + "," +longi);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.setPackage("com.google.android.apps.maps");
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }catch (ActivityNotFoundException ex){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request");
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        String userID = u.getUid();

        ref.child(uid).orderByChild("user_ID").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String status = dataSnapshot.child("status").getValue(String.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    datetime.setText("Status:  " + status + "  " + date);
                    if (status.equals("Pending")){
                        confirm.setVisibility(View.GONE);
                        book.setVisibility(View.GONE);
                        ok.setVisibility(View.VISIBLE);
                    }else {
                        book.setVisibility(View.VISIBLE);
                        confirm.setVisibility(View.GONE);
                        ok.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(photographyMain.this);

                builder.setTitle("Alert");
                builder.setMessage("Are you sure, Want to Cancel this Date!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request");
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = u.getUid();

                        ref.child(uid).orderByChild("user_ID").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String status = dataSnapshot.child("status").getValue(String.class);
                                    if (status.equals("Pending")){
                                        dataSnapshot.getRef().child("status").setValue("Canceled");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(photographyMain.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        datetime.setText(date);
                        confirm.setVisibility(View.VISIBLE);
                        book.setVisibility(View.GONE);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Request");
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    id = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(photographyMain.this);

                String date = datetime.getText().toString();

                builder.setTitle("Alert");
                builder.setMessage("Are you sure, want to book the Date " + date);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Seller ID
                        DatabaseReference re = FirebaseDatabase.getInstance().getReference("Request");
                        Intent intent = getIntent();
                        String uid = intent.getStringExtra("UserID");

                        //Date
                        String date = datetime.getText().toString();

                        //User ID
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = u.getUid();

                        //User Name and Mobile
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
                        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                user detail = snapshot.getValue(user.class);

                                if (detail!=null){
                                    String firstName = detail.First_Name;
                                    String Number = detail.Mobile;

                                    String status = "Pending";

                                    //Send data to database and send request
                                    request req = new request(date,userID,firstName,Number,status);
                                    if (date.isEmpty()){
                                        Toast.makeText(photographyMain.this, "Pick a Date", Toast.LENGTH_SHORT).show();
                                    }else {
                                        re.child(uid).child(String.valueOf(id+1)).setValue(req).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(photographyMain.this, "Request sent Successfully", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(photographyMain.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        confirm.setVisibility(View.GONE);
                        book.setVisibility(View.VISIBLE);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}