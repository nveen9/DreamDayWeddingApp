package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class passwordResetOtp extends AppCompatActivity {

    Button ok;
    TextInputEditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_otp);

        ok = findViewById(R.id.rOkbutton);
        code = findViewById(R.id.otp);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");
        String ref = intent.getStringExtra("Ref");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cd = code.getText().toString();

                if (cd.isEmpty()){
                    code.setError("Required");
                    code.requestFocus();
                }else {
                    FirebaseDatabase.getInstance().getReference(ref).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            int code1 = snapshot.child("otp").getValue(Integer.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String password = snapshot.child("password").getValue(String.class);
                            int code2 = Integer.parseInt(cd);
                            if (code2 == code1){
                                Intent intent1 = new Intent(passwordResetOtp.this,resetNewPassword.class);
                                intent1.putExtra("UID",uid);
                                intent1.putExtra("Ref",ref);
                                intent1.putExtra("email",email);
                                intent1.putExtra("password",password);
                                startActivity(intent1);
                            }else {
                                Toast.makeText(passwordResetOtp.this, "Incorrect Code, Check the Email", Toast.LENGTH_SHORT).show();
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