package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class resetNewPassword extends AppCompatActivity {

    TextInputEditText pass,confpass;
    Button save;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_new_password);

        pass = findViewById(R.id.password);
        confpass = findViewById(R.id.ConfirmPassword);
        save = findViewById(R.id.psavebutton);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");
        String ref = intent.getStringExtra("Ref");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = pass.getText().toString();
                String cpw = confpass.getText().toString();
                if (pw.isEmpty()){
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
                }else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user = mAuth.getCurrentUser();
                                user.updatePassword(pw).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            FirebaseDatabase.getInstance().getReference(ref).child(uid).child("password").setValue(pw).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        mAuth.signOut();
                                                        Toast.makeText(resetNewPassword.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(resetNewPassword.this,Login.class);
                                                        startActivity(intent);
                                                    }
                                                    else {
                                                        Toast.makeText(resetNewPassword.this, "Error when Reset Password", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(resetNewPassword.this, "Error when Reset Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(resetNewPassword.this, "Error when Reset Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}