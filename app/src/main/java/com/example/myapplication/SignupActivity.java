package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText fName,lName,email,pass,mobile,confpass;
    private Button sign,log;
    Animation userpassbtna,welcoma;
    TextView already,register,sellerReg;
    ProgressBar probar;

    //Authentication
    private FirebaseAuth mAuth;
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference re = database.getReference("Couple");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_signup);

        //Authentication
        mAuth = FirebaseAuth.getInstance();

        //Set buttons,text boxes,labels IDs
        log = findViewById(R.id.Lbutton);
        sellerReg = findViewById(R.id.sellerSbtn);
        fName = findViewById(R.id.FirstName);
        lName = findViewById(R.id.LastName);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        confpass = findViewById(R.id.ConfirmPassword);
        mobile = findViewById(R.id.mobileNum);
        sign = findViewById(R.id.Sbutton);
        already = findViewById(R.id.txtalready);
        register = findViewById(R.id.txtRegister);
        probar = findViewById(R.id.loading);

        userpassbtna = AnimationUtils.loadAnimation(this,R.anim.logintextandbtn);
        welcoma = AnimationUtils.loadAnimation(this,R.anim.loginwelcome);

        //Set Animation
        fName.setAnimation(userpassbtna);
        lName.setAnimation(userpassbtna);
        email.setAnimation(userpassbtna);
        pass.setAnimation(userpassbtna);
        confpass.setAnimation(userpassbtna);
        mobile.setAnimation(userpassbtna);
        sign.setAnimation(userpassbtna);
        log.setAnimation(userpassbtna);
        sellerReg.setAnimation(userpassbtna);
        already.setAnimation(userpassbtna);
        register.setAnimation(welcoma);

//        if (mAuth.getCurrentUser() != null){
//            Intent signInActivity = new Intent(SignupActivity.this,firstHome.class);
//            signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(signInActivity);
//        }

        //Login Button click event
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, Login.class);
                startActivity(intent);
            }
        });
        //Seller Signup Button click event
        sellerReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SignupSellerActivity.class);
                startActivity(intent);
            }
        });

        //Sign Button click event
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fName.getText().toString();
                String lname = lName.getText().toString();
                String mail = email.getText().toString();
                String pw = pass.getText().toString();
                String cpw = confpass.getText().toString();
                String num = mobile.getText().toString();
                
                if (fname.isEmpty()){
                    fName.setError("Required");
                    fName.requestFocus();
                }
                else if (mail.isEmpty()){
                    email.setError("Required");
                    email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Provide valid Email");
                    email.requestFocus();
                }
                else if (pw.isEmpty()){
                    pass.setError("Required");
                    pass.requestFocus();
                }
                else if (pw.length() < 6){
                    pass.setError("Minimum 6 Characters");
                    pass.requestFocus();
                }
                else if (!pw.equals(cpw)){
                    confpass.setError("Password not Match");
                    confpass.requestFocus();
                }
                else if (num.isEmpty()){
                    mobile.setError("Required");
                    mobile.requestFocus();
                }
                else if (num.length() < 10){
                    mobile.setError("Recheck the Number");
                    mobile.requestFocus();
                }
                else {
                    //Save data on Realtime Database only...
                    /*re.child(num).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                mobile.setError("Mobile already Exist");
                                mobile.requestFocus();
                            } else {

                                re.child(num).setValue(us);
                                Toast.makeText(SignupActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignupActivity.this, Login.class);
                                startActivity(i);

                            }
                        @Override
                            public void onCancelled (@NonNull @NotNull DatabaseError error){

                            }
                        }
                    });*/

                    //loading icon visible
                    probar.setVisibility(View.VISIBLE);
                    //Save data on Authentication and Realtime Database
                    FirebaseDatabase.getInstance().getReference("User").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                mobile.setError("Mobile already exist");
                                mobile.requestFocus();
                                probar.setVisibility(View.GONE);
                            }
                            else {
                                mAuth.createUserWithEmailAndPassword(mail,pw)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                                if (task.isSuccessful()){
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    String uid = user.getUid();
                                                    user us = new user(fname,lname,mail,pw,num,uid);
                                                    FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                //Commented 3 line for send email verification when log
                                                                //FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
                                                                //uid.sendEmailVerification();
                                                                probar.setVisibility(View.GONE);
                                                                Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                //Toast.makeText(SignupActivity.this, "Verify your Account using Email to Sign In", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SignupActivity.this, Login.class);
                                                                startActivity(intent);
                                                            }
                                                            else {
                                                                Toast.makeText(SignupActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
                                                                probar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                                }
                                                else {
                                                    email.setError("Email already Exist");
                                                    email.requestFocus();
                                                    probar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
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