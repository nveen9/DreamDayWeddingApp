package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class home extends AppCompatActivity {

    private TextInputEditText fst,lst,pass,mobile,cfpass;
    private TextInputLayout cfpassl;
    private TextView fname,hii,email,passchange;
    Button logout,fstdb,lstdb,passdb,mobiledb,hm;
    ImageView Propic;
    Uri imgUr;
    Animation btnandname,hi;

    private FirebaseUser User;
    private DatabaseReference re;
    private FirebaseAuth mAuth;
    private String uID;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        hii = findViewById(R.id.textHi);
        fname = findViewById(R.id.textViewFirstName);
        fst = findViewById(R.id.first);
        lst = findViewById(R.id.last);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        cfpass = findViewById(R.id.cnfpassword);
        cfpassl = findViewById(R.id.cnfpasswordl);
        mobile = findViewById(R.id.mobileNum);
        logout = findViewById(R.id.LObutton);
        Propic = findViewById(R.id.propic);
        passchange = findViewById(R.id.chngpassbtn);
        fstdb = findViewById(R.id.fstdb);
        lstdb = findViewById(R.id.lstdb);
        passdb = findViewById(R.id.passdb);
        mobiledb = findViewById(R.id.mobiledb);
        hm = findViewById(R.id.hm);

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImagesRef = storageRef.child("user/" + userID+"/" + "Pro Pic");
        ImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Propic);
            }
        });

        btnandname = AnimationUtils.loadAnimation(this,R.anim.logintextandbtn);
        hi = AnimationUtils.loadAnimation(this,R.anim.loginwelcome);

        fname.setAnimation(btnandname);
        hii.setAnimation(hi);

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("User");
        uID = User.getUid();

        re.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                user detail = snapshot.getValue(user.class);

                if (detail!=null){
                    String firstName = detail.First_Name;
                    String lastName = detail.Last_Name;
                    String mail = detail.Email;
                    String password = detail.Password;
                    String Number = detail.Mobile;

                    fname.setText(firstName);
                    fst.setText(firstName);
                    lst.setText(lastName);
                    email.setText(mail);
                    pass.setText(password);
                    mobile.setText(Number);

                    fst.setSelection(fst.getText().length());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, firstHome.class);
                startActivity(intent);
            }
        });

        fst.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    fstdb.setVisibility(View.VISIBLE);
                }
                else {
                    fstdb.setVisibility(View.GONE);
                }
            }
        });

        fstdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstUpdate();
            }
        });

        lst.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    lstdb.setVisibility(View.VISIBLE);
                }
                else {
                    lstdb.setVisibility(View.GONE);
                }
            }
        });

        lstdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastUpdate();
            }
        });

        passchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassChange();
            }
        });

        passdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassUpdate();
            }
        });

        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mobiledb.setVisibility(View.VISIBLE);
                }
                else {
                    mobiledb.setVisibility(View.GONE);
                }
            }
        });

        mobiledb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileUpdate();
            }
        });

        Propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,1);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(home.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void FirstUpdate() {

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("User");
        uID = User.getUid();

        String firstName = fst.getText().toString();
        if (firstName.isEmpty()){
            fst.setError("Cannot be Empty");
            fst.requestFocus();
        }
        else {
            re.child(uID).child("first_Name").setValue(firstName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(home.this, "First Name Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(home.this, "Error when Update First Name", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void LastUpdate() {

        mAuth = FirebaseAuth.getInstance();
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("User");
        uID = User.getUid();

        String lastName = lst.getText().toString();

        re.child(uID).child("last_Name").setValue(lastName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(home.this, "Last Name Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(home.this, "Error when Update Last Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void PassChange() {

        pass.requestFocus();
        pass.setSelection(pass.length());
        cfpass.setVisibility(View.VISIBLE);
        cfpassl.setVisibility(View.VISIBLE);
        passchange.setVisibility(View.GONE);
        passdb.setVisibility(View.VISIBLE);
    }

    private void PassUpdate() {

        String password = pass.getText().toString();
        String cpassword = cfpass.getText().toString();

        if (password.isEmpty()){
            pass.setError("Cannot be Empty");
            pass.requestFocus();
        }
        else if (password.length() < 6){
            pass.setError("Minimum 6 Characters");
            pass.requestFocus();
        }
        else if (!password.equals(cpassword)){
            cfpass.setError("Password not Match");
            cfpass.requestFocus();
        }
        else {
            mAuth = FirebaseAuth.getInstance();
            User = mAuth.getCurrentUser();
            re = FirebaseDatabase.getInstance().getReference("User");
            uID = User.getUid();

            User.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        re.child(uID).child("password").setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mAuth.signOut();
                                    Toast.makeText(home.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(home.this,Login.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(home.this, "Error when Update Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(home.this, "Error when Update Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void mobileUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("User");
        uID = User.getUid();

        String mob = mobile.getText().toString();
        if (mob.isEmpty()){
            mobile.setError("Cannot be Empty");
            mobile.requestFocus();
        }
        else {
            FirebaseDatabase.getInstance().getReference("User").orderByChild("mobile").equalTo(mob).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        mobile.setError("Mobile already exist");
                        mobile.requestFocus();
                    }
                    else {
                        re.child(uID).child("mobile").setValue(mob).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(home.this, "Mobile Updated", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(home.this, "Error when Mobiles Name", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK && data !=null){
            imgUr = data.getData();
            Propic.setImageURI(imgUr);
            saveimg();
        }
    }

    private void saveimg() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        String userID = mAuth.getCurrentUser().getUid();
        //String exten = UUID.randomUUID().toString();
        //StorageReference ImagesRef = storageRef.child(exten+"/" + "1");
        StorageReference ImagesRef = storageRef.child("user/" + userID+"/" + "Pro Pic");

        ImagesRef.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(home.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Intent sendimage = new Intent(home.this, firstHome.class);
                //store and send to another activity without firebase
                //sendimage.putExtra("image", imgUr);
                startActivity(sendimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(home.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
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