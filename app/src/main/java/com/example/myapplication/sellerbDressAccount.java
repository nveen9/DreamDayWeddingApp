package com.example.myapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class sellerbDressAccount extends AppCompatActivity {

    ImageView Propic,package1Pic,package2Pic,package3Pic,work1,work2,work3,work4,work5;
    private TextView businessName,setLoc,Addrs,close,current,different,chngpriss,pricestarts;
    ConstraintLayout sellerLayout,LocLayout;
    LinearLayout pricellayout;
    Button logout,notification,prof,pricedb,hm,pf,nf;
    TextInputEditText sprice;
    TextInputLayout spricel;
    Uri imgUr;
    long id=0;

    private FirebaseUser User;
    private DatabaseReference re;
    private FirebaseAuth mAuth;
    private String uID;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerb_dress_account);

        Propic = findViewById(R.id.propic);
        logout = findViewById(R.id.LObutton);
        package1Pic = findViewById(R.id.packge1);
        package2Pic = findViewById(R.id.packge2);
        package3Pic = findViewById(R.id.packge3);
        work1 = findViewById(R.id.work1);
        work2 = findViewById(R.id.work2);
        work3 = findViewById(R.id.work3);
        work4 = findViewById(R.id.work4);
        work5 = findViewById(R.id.work5);
        businessName = findViewById(R.id.txtBusinessName);
        current = findViewById(R.id.CuLoc);
        different = findViewById(R.id.DiLoc);
        setLoc = findViewById(R.id.Locality);
        Addrs = findViewById(R.id.Address);
        notification = findViewById(R.id.notification);
        prof = findViewById(R.id.prof);
        sellerLayout = findViewById(R.id.sellerLayout);
        LocLayout = findViewById(R.id.LocLayout);
        close = findViewById(R.id.closetxt);
        chngpriss = findViewById(R.id.chngprissbtn);
        pricedb = findViewById(R.id.pricedb);
        sprice = findViewById(R.id.sprice);
        spricel = findViewById(R.id.spricel);
        pricestarts = findViewById(R.id.pricestarts);
        pricellayout = findViewById(R.id.pricellayout);
        hm = findViewById(R.id.hm);
        pf = findViewById(R.id.pf);
        nf = findViewById(R.id.nf);

        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImagesRefPP = storageRef.child("bDress/" + userID+"/" + "profile_Pic");
        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Propic);
            }
        });

        StorageReference ImagesRefP1 = storageRef.child("bDress/" + userID+"/" + "package1");
        ImagesRefP1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(package1Pic);
            }
        });

        StorageReference ImagesRefP2 = storageRef.child("bDress/" + userID+"/" + "package2");
        ImagesRefP2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(package2Pic);
            }
        });

        StorageReference ImagesRefP3 = storageRef.child("bDress/" + userID+"/" + "package3");
        ImagesRefP3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(package3Pic);
            }
        });

        StorageReference ImagesRefW1 = storageRef.child("bDress/" + userID+"/" + "work1");
        ImagesRefW1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(work1);
            }
        });

        StorageReference ImagesRefW2 = storageRef.child("bDress/" + userID+"/" + "work2");
        ImagesRefW2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(work2);
            }
        });

        StorageReference ImagesRefW3 = storageRef.child("bDress/" + userID+"/" + "work3");
        ImagesRefW3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(work3);
            }
        });

        StorageReference ImagesRefW4 = storageRef.child("bDress/" + userID+"/" + "work4");
        ImagesRefW4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(work4);
            }
        });

        StorageReference ImagesRefW5 = storageRef.child("bDress/" + userID+"/" + "work5");
        ImagesRefW5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(work5);
            }
        });

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("bDress");
        uID = User.getUid();

        re.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                seller detail = snapshot.getValue(seller.class);

                if (detail!=null){
                    String firstName = detail.Business_Name;
                    String adrs = detail.Address;
                    double spric = detail.Starting_Price;

                    businessName.setText(firstName);
                    Addrs.setText(adrs);
                    sprice.setText(String.format("%.2f",spric));
                    pricestarts.setText(String.format("%.2f",spric));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Request");
        uID = User.getUid();

        re.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    id = (int)snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Request");
        uID = User.getUid();

        re.child(uID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                String name = snapshot.child("user_Name").getValue(String.class);
                String date = snapshot.child("date").getValue(String.class);
                NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                Intent intent = new Intent(sellerbDressAccount.this, notification.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(sellerbDressAccount.this, 0, intent, 0);

                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                String message = name + " wants to reserve a date on " + date;

                NotificationCompat.Builder builder = new NotificationCompat.Builder(sellerbDressAccount.this, "Dream Day");
                builder.setContentTitle("Reserve");
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                builder.setContentText(message);
                builder.setSmallIcon(R.drawable.ic_notify);
                builder.setSound(uri);
                builder.setContentIntent(pendingIntent).setAutoCancel(true);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);

//                    manager.notify(1, builder.build());

                SharedPreferences preferences = getSharedPreferences(sellerAccount.class.getSimpleName(), Context.MODE_PRIVATE);
                int notific = preferences.getInt("number", 0);
                manager.notify(notific, builder.build());
                SharedPreferences.Editor editor = preferences.edit();
                notific++;
                editor.putInt("number", notific);
                editor.commit();

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

//        re.child(uID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    String name = dataSnapshot.child("user_Name").getValue(String.class);
//                    String date = dataSnapshot.child("date").getValue(String.class);
//                    String mobile = dataSnapshot.child("user_Mobile").getValue(String.class);
//                    NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//                    Intent intent = new Intent(sellerAccount.this, notification.class);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(sellerAccount.this, 0, intent, 0);
//
//                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    String message = name + " wants to reserve a date on " +date;
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(sellerAccount.this,"Dream Day");
//                    builder.setContentTitle("Reserve");
//                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
//                    builder.setContentText(message);
//                    builder.setSmallIcon(R.drawable.ic_notify);
//                    builder.setSound(uri);
//                    builder.setContentIntent(pendingIntent).setAutoCancel(true);
//                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//
//                    manager.notify(1,builder.build());
//
//                    SharedPreferences preferences = getSharedPreferences(sellerAccount.class.getSimpleName(),Context.MODE_PRIVATE);
//                    int notific = preferences.getInt("number",0);
//                    manager.notify(notific,builder.build());
//                    SharedPreferences.Editor editor = preferences.edit();
//                    notific++;
//                    editor.putInt("number",notific);
//                    editor.commit();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerbDressAccount.this, sellerbDressProfileEdit.class);
                startActivity(intent);
            }
        });

        nf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerbDressAccount.this, notification.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerbDressAccount.this, notification.class);
                startActivity(intent);
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerbDressAccount.this, sellerbDressProfileEdit.class);
                startActivity(intent);
            }
        });

        package1Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,2);
            }
        });

        package2Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,3);
            }
        });

        package3Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,4);
            }
        });

        work1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,5);
            }
        });

        work2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,6);
            }
        });

        work3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,7);
            }
        });

        work4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,8);
            }
        });

        work5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,9);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocLayout.setVisibility(View.GONE);
            }
        });

        setLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocLayout.setVisibility(View.VISIBLE);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(sellerbDressAccount.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    RealtimeLocation();
                } else {
                    ActivityCompat.requestPermissions(sellerbDressAccount.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        different.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerbDressAccount.this, bDressMap.class);
                startActivity(intent);
                LocLayout.setVisibility(View.GONE);
            }
        });

        chngpriss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceChange();
            }
        });

        pricedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceUpdate();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAuth.signOut();
//                Intent signInActivity = new Intent(sellerAccount.this, Login.class);
//                signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(signInActivity);
                mAuth.signOut();
                Intent intent = new Intent(sellerbDressAccount.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void PriceChange() {

        sprice.requestFocus();
        sprice.setSelection(sprice.length());
        sprice.setVisibility(View.VISIBLE);
        spricel.setVisibility(View.VISIBLE);
        chngpriss.setVisibility(View.GONE);
        pricedb.setVisibility(View.VISIBLE);
        pricellayout.setVisibility(View.GONE);
    }

    private void PriceUpdate() {
        String price = sprice.getText().toString();

        if (price.isEmpty()){
            sprice.setError("Cannot be Empty");
            sprice.requestFocus();
        }else {
            try {
                double startPrc = Double.parseDouble(price);

                User = mAuth.getCurrentUser();
                re = FirebaseDatabase.getInstance().getReference("bDress");
                uID = User.getUid();

                re.child(uID).child("starting_Price").setValue(startPrc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pricedb.setVisibility(View.GONE);
                            chngpriss.setVisibility(View.VISIBLE);
                            sprice.setVisibility(View.GONE);
                            spricel.setVisibility(View.GONE);
                            pricellayout.setVisibility(View.VISIBLE);
                            Toast.makeText(sellerbDressAccount.this, "Price Updated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(sellerbDressAccount.this, "Error when Update Price", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void RealtimeLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(sellerbDressAccount.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        double latitude = addresses.get(0).getLatitude();
                        double longitude = addresses.get(0).getLongitude();
                        String locality = addresses.get(0).getLocality();
                        String address = addresses.get(0).getAddressLine(0);
                        Addrs.setText(address);
                        LocLayout.setVisibility(View.GONE);
                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = uid.getUid();
                        FirebaseDatabase.getInstance().getReference("bDress").child(userID).child("latitude").setValue(latitude);
                        FirebaseDatabase.getInstance().getReference("bDress").child(userID).child("longitude").setValue(longitude);
                        if (locality==null){
                            FirebaseDatabase.getInstance().getReference("bDress").child(userID).child("locality").setValue("");
                        }else {
                            FirebaseDatabase.getInstance().getReference("bDress").child(userID).child("locality").setValue(locality);
                        }
                        FirebaseDatabase.getInstance().getReference("bDress").child(userID).child("address").setValue(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==2 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            package1Pic.setImageURI(imgUr);
            saveimgPckg1();
        }
        if (requestCode==3 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            package2Pic.setImageURI(imgUr);
            saveimgPckg2();
        }
        if (requestCode==4 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            package3Pic.setImageURI(imgUr);
            saveimgPckg3();
        }
        if (requestCode==5 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            work1.setImageURI(imgUr);
            saveimgWrk1();
        }
        if (requestCode==6 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            work2.setImageURI(imgUr);
            saveimgWrk2();
        }
        if (requestCode==7 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            work3.setImageURI(imgUr);
            saveimgWrk3();
        }
        if (requestCode==8 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            work4.setImageURI(imgUr);
            saveimgWrk4();
        }
        if (requestCode==9 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            work5.setImageURI(imgUr);
            saveimgWrk5();
        }
    }

    private void saveimgPckg1() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefP1 = storageRef.child("bDress/" + userID+"/" + "package1");

        ImagesRefP1.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefP1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("package1").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgPckg2() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefP2 = storageRef.child("bDress/" + userID+"/" + "package2");

        ImagesRefP2.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefP2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("package2").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgPckg3() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefP3 = storageRef.child("bDress/" + userID+"/" + "package3");

        ImagesRefP3.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefP3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("package3").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgWrk1() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefW1 = storageRef.child("bDress/" + userID+"/" + "work1");

        ImagesRefW1.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefW1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("work1").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgWrk2() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefW2 = storageRef.child("bDress/" + userID+"/" + "work2");

        ImagesRefW2.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefW2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("work2").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgWrk3() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefW3 = storageRef.child("bDress/" + userID+"/" + "work3");

        ImagesRefW3.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefW3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("work3").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgWrk4() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefW4 = storageRef.child("bDress/" + userID+"/" + "work4");

        ImagesRefW4.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefW4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("work4").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgWrk5() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        re = FirebaseDatabase.getInstance().getReference("bDress");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRefW5 = storageRef.child("bDress/" + userID+"/" + "work5");

        ImagesRefW5.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRefW5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("work5").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerbDressAccount.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerbDressAccount.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }
}