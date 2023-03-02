package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Animation logoa,welcomea,buttona;
    ImageView logoI;
    TextView logoname,welcome,Guest;
    Button btn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        logoa = AnimationUtils.loadAnimation(this,R.anim.logoanim);
        welcomea = AnimationUtils.loadAnimation(this,R.anim.welcomeanim);
        buttona = AnimationUtils.loadAnimation(this,R.anim.buttonanim);

        logoI = findViewById(R.id.logo);
        logoname = findViewById(R.id.txtLogo);
        welcome = findViewById(R.id.txtWelcome);
        btn = findViewById(R.id.Lbutton);
        Guest = findViewById(R.id.guest);

        logoI.setAnimation(logoa);
        logoname.setAnimation(logoa);
        welcome.setAnimation(welcomea);
        btn.setAnimation(buttona);
        Guest.setAnimation(logoa);

        mAuth = FirebaseAuth.getInstance();

//        if (mAuth.getCurrentUser() != null){
//            Intent signInActivity = new Intent(MainActivity.this,firstHome.class);
//            signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(signInActivity);
//        }else {
//            Intent signInActivity = new Intent(MainActivity.this,Login.class);
//            signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(signInActivity);
//        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,photoghraphyFirst.class);
                startActivity(intent);
            }
        });

    }
}