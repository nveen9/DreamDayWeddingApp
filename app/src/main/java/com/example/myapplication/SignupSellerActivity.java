package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SignupSellerActivity extends AppCompatActivity {

    private TextInputEditText businessname,email,pass,confpass,mobile,businessAddress,businessLink,startprice;
    private Button sign,log;
    TextView already,register,usersignup;
    ProgressBar probar;
    Animation userpassbtna,welcoma;
    Spinner dropdownMenu;

    ArrayList<String> choose;

    String selectedDD = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_signup_seller);

        //Authentication
        mAuth = FirebaseAuth.getInstance();

        //Set buttons,text boxes,labels IDs
        businessname = findViewById(R.id.BusinessName);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        confpass = findViewById(R.id.ConfirmPassword);
        mobile = findViewById(R.id.mobileNum);
        businessAddress = findViewById(R.id.bAddress);
        businessLink = findViewById(R.id.bLink);
        startprice = findViewById(R.id.sPrice);
        sign = findViewById(R.id.Sbutton);
        log = findViewById(R.id.Lbutton);
        usersignup = findViewById(R.id.userSbtn);
        already = findViewById(R.id.txtalready);
        register = findViewById(R.id.txtRegister);
        probar = findViewById(R.id.loading);
        dropdownMenu = findViewById(R.id.dropdownMenu);

        userpassbtna = AnimationUtils.loadAnimation(this,R.anim.logintextandbtn);
        welcoma = AnimationUtils.loadAnimation(this,R.anim.loginwelcome);

        //Set Animation
        businessname.setAnimation(userpassbtna);
        email.setAnimation(userpassbtna);
        pass.setAnimation(userpassbtna);
        confpass.setAnimation(userpassbtna);
        mobile.setAnimation(userpassbtna);
        businessAddress.setAnimation(userpassbtna);
        businessLink.setAnimation(userpassbtna);
        sign.setAnimation(userpassbtna);
        log.setAnimation(userpassbtna);
        usersignup.setAnimation(userpassbtna);
        already.setAnimation(userpassbtna);
        register.setAnimation(welcoma);

        usersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupSellerActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupSellerActivity.this, Login.class);
                startActivity(intent);
            }
        });

        choose = new ArrayList<>();
        choose.add("Choose your Service");
        choose.add("Photography");
        choose.add("Hotel");
        choose.add("Bride Dress");
        choose.add("Groom Dress");
        choose.add("Entertainment");
        choose.add("Wedding Decorator");
        choose.add("Wedding Car");

        dropdownMenu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,choose));
        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                selectedDD = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDD.equals("Photography")){
                    photography();
                }
                else if (selectedDD.equals("Hotel")){
                    hotel();
                }
                else if (selectedDD.equals("Bride Dress")){
                    bDress();
                }
                else if (selectedDD.equals("Groom Dress")){
                    gDress();
                }
                else if (selectedDD.equals("Entertainment")){
                    enterT();
                }
                else if (selectedDD.equals("Wedding Decorator")){
                    wDec();
                }
                else if (selectedDD.equals("Wedding Car")){
                    wCar();
                }
                else if (selectedDD.equals("Choose your Service")){

                    Toast.makeText(SignupSellerActivity.this, "Choose your Service", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void wCar() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("WeddingCar").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("WeddingCar").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("WeddingCar").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, SellerSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

    private void wDec() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("WeddingDecorator").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("WeddingDecorator").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("WeddingDecorator").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, SellerSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

    private void enterT() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("Entertainment").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("Entertainment").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("Entertainment").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, SellerSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

    private void gDress() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("GroomDress").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("GroomDress").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("GroomDress").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, SellerSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

    private void bDress() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("bDress").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("bDress").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("bDress").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, sellerbDressSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

    private void hotel() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("Hotel").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("Hotel").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("Hotel").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, sellerHotelSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

    private void photography() {
        String bname = businessname.getText().toString();
        String mail = email.getText().toString();
        String pw = pass.getText().toString();
        String cpw = confpass.getText().toString();
        String num = mobile.getText().toString();
        String baddress = businessAddress.getText().toString();
        String blink = businessLink.getText().toString();
        String sprice = startprice.getText().toString();
        double lati = 0;
        double longi = 0;
        String local = "No Location";
        String addr = "";

        if (bname.isEmpty()){
            businessname.setError("Required");
            businessname.requestFocus();
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
        else if (baddress.isEmpty()){
            businessAddress.setError("Required");
            mobile.requestFocus();
        }
        else if (sprice.isEmpty()){
            startprice.setError("Required");
            startprice.requestFocus();
        }
        else {
            try {
                double startPrc = Double.parseDouble(sprice);
                seller sl = new seller(bname,mail,pw,num,baddress,blink,null,null,lati,longi,local,addr,null,null,null,null,null,null,null,null,startPrc);
                //loading icon visible
                probar.setVisibility(View.VISIBLE);
                //Save data on Authentication and Realtime Database
                FirebaseDatabase.getInstance().getReference("Photography").orderByChild("mobile").equalTo(num).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                FirebaseDatabase.getInstance().getReference("Photography").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                                            uid.sendEmailVerification();
                                                            String userID = uid.getUid();
                                                            FirebaseDatabase.getInstance().getReference("Photography").child(userID).child("user_Id").setValue(userID);
//                                                            mailSend();
                                                            probar.setVisibility(View.GONE);

                                                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                                            String message = "Check Your Inbox for Email Verification and \nUpload the both side of Your NIC";

                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(SignupSellerActivity.this,"Dream Day");
                                                            builder.setContentTitle("Register");
                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                                                            builder.setContentText(message);
                                                            builder.setSmallIcon(R.drawable.ic_notify);
                                                            builder.setSound(uri);
                                                            builder.setAutoCancel(true);
                                                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                                                            manager.notify(1,builder.build());

                                                            Intent intent = new Intent(SignupSellerActivity.this, SellerSignupNIC.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(SignupSellerActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
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
            }catch (Exception e){
                startprice.setError("Invalid Number or Contain Letters");
            }

        }
    }

//    private void mailSend() {
//        String UserID = mAuth.getCurrentUser().getUid();
//        String mail = email.getText().toString();
//        String email = gmail.email;
//        String subject = "Service Provider has been Registered";
//        String messagetxt = "Service Provider recently Registered,\n\nUID - "+UserID+"\nEmail - "+mail;
//        registerMail rm = new registerMail(this,email,subject,messagetxt);
//        rm.execute();
//    }
}