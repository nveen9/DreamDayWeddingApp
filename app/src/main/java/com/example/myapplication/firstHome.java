package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class firstHome extends AppCompatActivity {

    private TextView fname,hii;
    Button logout,pf,photography,hotel,bdress,gdress,enterbtn,wdecbtn,wcarbtn;
    ImageView Propic;
    Animation btnandname,hi;

    private FirebaseUser User;
    private DatabaseReference re;
    private FirebaseAuth mAuth;
    private String uID;
    int imageValue;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_first_home);

        mAuth = FirebaseAuth.getInstance();

        hii = findViewById(R.id.textHi);
        fname = findViewById(R.id.textViewFirstName);
        logout = findViewById(R.id.logoutbtn);
        pf = findViewById(R.id.pf);
        Propic = findViewById(R.id.propic);
        photography = findViewById(R.id.photogrbtn);
        hotel = findViewById(R.id.hotelbtn);
        bdress = findViewById(R.id.bdressbtn);
        gdress = findViewById(R.id.gdressbtn);
        enterbtn = findViewById(R.id.enterbtn);
        wdecbtn = findViewById(R.id.wdecbtn);
        wcarbtn = findViewById(R.id.wcarbtn);

        btnandname = AnimationUtils.loadAnimation(this,R.anim.logintextandbtn);
        hi = AnimationUtils.loadAnimation(this,R.anim.loginwelcome);

        fname.setAnimation(btnandname);
        Propic.setAnimation(btnandname);
        photography.setAnimation(btnandname);
        hotel.setAnimation(btnandname);
        bdress.setAnimation(btnandname);
        enterbtn.setAnimation(btnandname);
        wdecbtn.setAnimation(btnandname);
        wcarbtn.setAnimation(btnandname);
        gdress.setAnimation(btnandname);
        hii.setAnimation(hi);

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImagesRef = storageRef.child("user/" + userID+"/" + "Pro Pic");
        ImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Propic);
            }
        });

        /*
        // Get profile pic when update it on home page (This is not with database, only for show once)
        Bundle bundle = getIntent().getExtras();
        // if bundle is not null then get the image value
        if (bundle != null) {
            imageValue = bundle.getInt("image");
        }
        Propic.setImageResource(imageValue);**/

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("User");
        uID = User.getUid();

        re.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                user detail = snapshot.getValue(user.class);

                if (detail!=null){
                    String firstName = detail.First_Name;

                    fname.setText(firstName);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(firstHome.this, Login.class);
                startActivity(intent);
            }
        });

        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstHome.this, home.class);
                startActivity(intent);
            }
        });

        photography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstHome.this, photoghraphyFirst.class);
                startActivity(intent);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstHome.this, hotelFirst.class);
                startActivity(intent);
            }
        });

        bdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstHome.this, bDressFirst.class);
                startActivity(intent);
            }
        });
    }
}