package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class resetPassword extends AppCompatActivity {

    TextInputEditText username;
    Button reset;
    ProgressBar probar;

    Random otp;
    int number;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        username = findViewById(R.id.TextEmailAddress);
        reset = findViewById(R.id.rbutton);
        probar = findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();
    }
    public void rstbtn(View view) {

        fgtPass();
    }

    private void fgtPass() {
        String email = username.getText().toString();

        if (email.isEmpty()){
            username.setError("Email required");
            username.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Provide valid Email");
            username.requestFocus();
        }
        else {
            probar.setVisibility(View.VISIBLE);
//            mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(resetPassword.this, "Check the Email to reset Password", Toast.LENGTH_SHORT).show();
//                        probar.setVisibility(View.GONE);
//                        Intent intent = new Intent(resetPassword.this, Login.class);
//                        startActivity(intent);
//                    }
//                    else {
//                        Toast.makeText(resetPassword.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                        probar.setVisibility(View.GONE);
//                    }
//                }
//            });
            mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<SignInMethodQueryResult> task) {
                    if (task.getResult().getSignInMethods().size() == 0){
                        probar.setVisibility(View.GONE);
                        Toast.makeText(resetPassword.this, "User doesn't Exist", Toast.LENGTH_SHORT).show();
                    }else {
                        FirebaseDatabase.getInstance().getReference("User").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    otp = new Random();
                                    number = otp.nextInt(999999);
                                    dataSnapshot.getRef().child("otp").setValue(number);
                                    String uID = dataSnapshot.child("user_Id").getValue(String.class);
                                    mailSend();
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(resetPassword.this, "Check Email for the Code", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(resetPassword.this,passwordResetOtp.class);
                                    intent.putExtra("UID",uID);
                                    intent.putExtra("Ref","User");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference("Photography").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    otp = new Random();
                                    number = otp.nextInt(999999);
                                    dataSnapshot.getRef().child("otp").setValue(number);
                                    String uID = dataSnapshot.child("user_Id").getValue(String.class);
                                    mailSend();
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(resetPassword.this, "Check Email for the Code", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(resetPassword.this,passwordResetOtp.class);
                                    intent.putExtra("UID",uID);
                                    intent.putExtra("Ref","Photography");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference("Hotel").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    otp = new Random();
                                    number = otp.nextInt(999999);
                                    dataSnapshot.getRef().child("otp").setValue(number);
                                    String uID = dataSnapshot.child("user_Id").getValue(String.class);
                                    mailSend();
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(resetPassword.this, "Check Email for the Code", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(resetPassword.this,passwordResetOtp.class);
                                    intent.putExtra("UID",uID);
                                    intent.putExtra("Ref","Hotel");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference("bDress").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    otp = new Random();
                                    number = otp.nextInt(999999);
                                    dataSnapshot.getRef().child("otp").setValue(number);
                                    String uID = dataSnapshot.child("user_Id").getValue(String.class);
                                    mailSend();
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(resetPassword.this, "Check Email for the Code", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(resetPassword.this,passwordResetOtp.class);
                                    intent.putExtra("UID",uID);
                                    intent.putExtra("Ref","bDress");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference("gDress").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    otp = new Random();
                                    number = otp.nextInt(999999);
                                    dataSnapshot.getRef().child("otp").setValue(number);
                                    String uID = dataSnapshot.child("user_Id").getValue(String.class);
                                    mailSend();
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(resetPassword.this, "Check Email for the Code", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(resetPassword.this,passwordResetOtp.class);
                                    intent.putExtra("UID",uID);
                                    intent.putExtra("Ref","gDress");
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                }
            });
        }
    }

    private void mailSend() {
        String email = username.getText().toString();;
        String subject = "DreamDay - The Code for Reset Password";
        String messagetxt = "Your Code for Reset the Password is - "+number;
        registerMail rm = new registerMail(this,email,subject,messagetxt);
        rm.execute();
    }
}