package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    TextInputEditText username,password;
    Button login,signup;
    TextView welcome,reset,txtsignup;
    Animation userpassbtna,welcoma;
    ProgressBar probar;

    //Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        Button button = findViewById(R.id.Sbutton);
        username = findViewById(R.id.TextEmailAddress);
        password = findViewById(R.id.TextPassword);
        login = findViewById(R.id.Lbutton);
        signup = findViewById(R.id.Sbutton);
        welcome = findViewById(R.id.txtWelcome);
        probar = findViewById(R.id.loading);
        reset = findViewById(R.id.txtreset);
        txtsignup = findViewById(R.id.txtsignup);

        mAuth = FirebaseAuth.getInstance();

        userpassbtna = AnimationUtils.loadAnimation(this,R.anim.logintextandbtn);
        welcoma = AnimationUtils.loadAnimation(this,R.anim.loginwelcome);

        username.setAnimation(userpassbtna);
        password.setAnimation(userpassbtna);
        welcome.setAnimation(welcoma);
        login.setAnimation(userpassbtna);
        signup.setAnimation(userpassbtna);
        reset.setAnimation(userpassbtna);
        txtsignup.setAnimation(userpassbtna);

        //SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        //String checkbox = preferences.getString("remember","");



        /*rember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }
                else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });**/
    }

    public void sigbtn(View view) {
        Intent intent = new Intent(Login.this, SignupActivity.class);
        startActivity(intent);
    }

    public void resetbtn(View view) {
        Intent intent = new Intent(Login.this, resetPassword.class);
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
        FirebaseDatabase.getInstance().getReference("User").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
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
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, firstHome.class);
                                        startActivity(intent);
                                        //}
                                        //else {
                                        //Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
                                        //probar.setVisibility(View.GONE);
                                        //}

                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Photography").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Hotel").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerHotelAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("bDress").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerbDressAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("GroomDress").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Entertainment").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("WeddingDecorator").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        probar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("WeddingCar").orderByChild("email").equalTo(usernameTxt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //Authentication code for login by check user exist
                    mAuth.signInWithEmailAndPassword(usernameTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        String userID = uid.getUid();
//                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                                        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "NIC Front");
//                                        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
                                        //Commented lines in here for check if user verify email
//                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
//                                        if (uid.isEmailVerified()){
                                        probar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, sellerAccount.class);
                                        startActivity(intent);
//                                        }
//                                        else {
//                                        Toast.makeText(Login.this, "Check your Email for Verify the Account", Toast.LENGTH_SHORT).show();
//                                        probar.setVisibility(View.GONE);
//                                        }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                Toast.makeText(Login.this, "Upload NIC", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(Login.this, SellerSignupNIC.class);
//                                                startActivity(intent);
//                                            }
//                                        });
                                    }
                                    else {
                                        Toast.makeText(Login.this, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
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

        //These for realtime database only.
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Couple");

        Query checkUsername = reference.orderByChild("mobile").equalTo(usernameTxt);

        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    username.setError(null);

                    final String dbPass = snapshot.child(usernameTxt).child("pass").getValue(String.class);

                    if (dbPass.equals(passwordTxt)) {
                        username.setError(null);

                        String dbfirstName = snapshot.child(usernameTxt).child("fname").getValue(String.class);
                        String dbemail = snapshot.child(usernameTxt).child("email").getValue(String.class);
                        String dbmobile = snapshot.child(usernameTxt).child("mobile").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), home.class);

                        intent.putExtra("fname",dbfirstName);
                        intent.putExtra("email",dbemail);
                        intent.putExtra("mobile",dbmobile);
                        intent.putExtra("pass",dbPass);

                        startActivity(intent);
                    }
                    else {
                        password.setError("Password Incorrect");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("User doesn't Exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });**/
    }
}