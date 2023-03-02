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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class sellerLogin extends AppCompatActivity {

    TextInputEditText username,password;
    Button login,signup,userlogin;
    TextView welcome,reset;
    Animation userpassbtna,welcoma;
    ProgressBar probar;

    //Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Button button = findViewById(R.id.Sbutton);
        username = findViewById(R.id.TextEmailAddress);
        password = findViewById(R.id.TextPassword);
        login = findViewById(R.id.Lbutton);
        signup = findViewById(R.id.Sbutton);
        userlogin = findViewById(R.id.userSbtn);
        welcome = findViewById(R.id.txtWelcome);
        probar = findViewById(R.id.loading);
        reset = findViewById(R.id.txtreset);

        mAuth = FirebaseAuth.getInstance();

        userpassbtna = AnimationUtils.loadAnimation(this,R.anim.logintextandbtn);
        welcoma = AnimationUtils.loadAnimation(this,R.anim.loginwelcome);

        username.setAnimation(userpassbtna);
        password.setAnimation(userpassbtna);
        welcome.setAnimation(welcoma);
        login.setAnimation(userpassbtna);
        signup.setAnimation(userpassbtna);
        userlogin.setAnimation(userpassbtna);
        reset.setAnimation(userpassbtna);

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerLogin.this,Login.class);
                startActivity(intent);
            }
        });
    }
    public void sigbtn(View view) {
        Intent intent = new Intent(sellerLogin.this, SignupSellerActivity.class);
        startActivity(intent);
    }

    public void resetbtn(View view) {
        Intent intent = new Intent(sellerLogin.this, resetPassword.class);
        startActivity(intent);
    }
    //Login Button click event
    public void loginbtn(View view) {
        String textuser = username.getText().toString();
        String userpass = password.getText().toString();

        if (textuser.isEmpty()){
            username.setError("Enter the Email");
            username.requestFocus();
        }
        else if (userpass.isEmpty()){
            password.setError("Enter the Password");
            password.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(textuser).matches()){
            username.setError("Provide valid Email");
            username.requestFocus();
        }
        else {
            userAvailable();
        }
    }

    private void userAvailable() {

        final String usernameTxt = username.getText().toString();
        final String passwordTxt = password.getText().toString();

        probar.setVisibility(View.VISIBLE);
        //Authentication code for login by check user exist
        mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Commented lines in here for check if user verify email
                            //FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
                            //if (uid.isEmailVerified()){
                            probar.setVisibility(View.GONE);
                            Toast.makeText(sellerLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(sellerLogin.this, sellerAccount.class);
                            startActivity(intent);
                            //}
                            //else {
                            //Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
                            //probar.setVisibility(View.GONE);
                            //}

                        }
                        else {
                            Toast.makeText(sellerLogin.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
                            probar.setVisibility(View.GONE);
                        }

                    }
                });
    }
}