package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class sellerHotelSignupNIC extends AppCompatActivity {

    ImageView nicuploadfront,reverse;
    Uri imgUr,imgUr1;
    Button save;

    private FirebaseAuth mAuth;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_hotel_signup_nic);

        mAuth = FirebaseAuth.getInstance();

        nicuploadfront = findViewById(R.id.NICpic);
        reverse = findViewById(R.id.NICpicReverse);
        save = findViewById(R.id.Savebutton);

        nicuploadfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,1);
            }
        });

        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,2);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgUr == null){
                    Toast.makeText(sellerHotelSignupNIC.this, "NIC Front Side Required!", Toast.LENGTH_SHORT).show();
                }
                else if (imgUr1 == null){
                    Toast.makeText(sellerHotelSignupNIC.this, "NIC Reverse Side Required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(sellerHotelSignupNIC.this, "NIC Successfully Uploaded", Toast.LENGTH_SHORT).show();

                    NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    String message = "You have Successfully registered, \nIt takes less than 3 days to verify the Account. \nWe'll get to you soon.";

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(sellerHotelSignupNIC.this,"Dream Day");
                    builder.setContentTitle("Register");
                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                    builder.setContentText(message);
                    builder.setSmallIcon(R.drawable.ic_notify);
                    builder.setSound(uri);
                    builder.setAutoCancel(true);
                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                    manager.notify(1,builder.build());

                    Intent intent = new Intent(sellerHotelSignupNIC.this,Login.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            nicuploadfront.setImageURI(imgUr);
            saveimgFront();
        }
        if (requestCode==2 && resultCode==RESULT_OK && data !=null){
            imgUr1 = data.getData();
            reverse.setImageURI(imgUr1);
            saveimgReverse();
        }
    }
    private void saveimgFront() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRef = storageRef.child("hotel/" + userID+"/" + "NIC Front");

        ImagesRef.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(sellerHotelSignupNIC.this, "NIC Front Side Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerHotelSignupNIC.this, "NIC Front Side Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressDialogPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading " + (int)progressDialogPercent + "%");
            }
        });
    }

    private void saveimgReverse() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference ImagesRef = storageRef.child("hotel/" + userID+"/" + "NIC Reverse");

        ImagesRef.putFile(imgUr1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(sellerHotelSignupNIC.this, "NIC Reverse Side Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerHotelSignupNIC.this, "NIC Reverse Side Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
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