package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class sellerProfileEdit extends AppCompatActivity {

    ImageView Propic;
    Uri imgUr;
    TextView businessName,email,passchange;
    TextInputEditText fst,add,pass,mobile,cfpass,wlink,flink,ilink,ylink;
    TextInputLayout cfpassl;
    Button logout,fstdb,adddb,passdb,mobiledb,wlinkdb,flinkdb,ilinkdb,ylinkdb,hm,pf,nf;

    private FirebaseUser User;
    private DatabaseReference re;
    private FirebaseAuth mAuth;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile_edit);

        Propic = findViewById(R.id.propic);
        businessName = findViewById(R.id.txtBusinessName);
        email = findViewById(R.id.email);
        fst = findViewById(R.id.first);
        fstdb = findViewById(R.id.fstdb);
        add = findViewById(R.id.address);
        adddb = findViewById(R.id.addrdb);
        pass = findViewById(R.id.password);
        cfpass = findViewById(R.id.cnfpassword);
        cfpassl = findViewById(R.id.cnfpasswordl);
        passdb = findViewById(R.id.passdb);
        passchange = findViewById(R.id.chngpassbtn);
        mobile = findViewById(R.id.mobileNum);
        mobiledb = findViewById(R.id.mobiledb);
        logout = findViewById(R.id.LObutton);
        wlink = findViewById(R.id.wlink);
        wlinkdb = findViewById(R.id.wlinkdb);
        flink = findViewById(R.id.flink);
        flinkdb = findViewById(R.id.flinkdb);
        ilink = findViewById(R.id.ilink);
        ilinkdb = findViewById(R.id.ilinkdb);
        ylink = findViewById(R.id.ylink);
        ylinkdb = findViewById(R.id.ylinkdb);
        hm = findViewById(R.id.hm);
        pf = findViewById(R.id.pf);
        nf = findViewById(R.id.nf);

        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImagesRefPP = storageRef.child("photographer/" + userID+"/" + "profile_Pic");
        ImagesRefPP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Propic);
            }
        });

        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        re.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                seller detail = snapshot.getValue(seller.class);
                String wLink = snapshot.child("business_Link").getValue(String.class);
                String fLink = snapshot.child("f_Link").getValue(String.class);
                String iLink = snapshot.child("i_Link").getValue(String.class);
                String yLink = snapshot.child("y_Link").getValue(String.class);

                wlink.setText(wLink);
                flink.setText(fLink);
                ilink.setText(iLink);
                ylink.setText(yLink);

                if (detail!=null){
                    String firstName = detail.Business_Name;
                    String mail = detail.Email;
                    String adrs = detail.Business_Address;
                    String password = detail.Password;
                    String moble = detail.Mobile;

                    businessName.setText(firstName);
                    email.setText(mail);
                    fst.setText(firstName);
                    add.setText(adrs);
                    pass.setText(password);
                    mobile.setText(moble);

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
                Intent intent = new Intent(sellerProfileEdit.this, sellerAccount.class);
                startActivity(intent);
            }
        });

        nf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerProfileEdit.this, notification.class);
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

        add.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    adddb.setVisibility(View.VISIBLE);
                }
                else {
                    adddb.setVisibility(View.GONE);
                }
            }
        });

        adddb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstAdd();
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

        wlink.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    wlinkdb.setVisibility(View.VISIBLE);
                }
                else {
                    wlinkdb.setVisibility(View.GONE);
                }
            }
        });

        wlinkdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wlinkUpdate();
            }
        });

        flink.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    flinkdb.setVisibility(View.VISIBLE);
                }
                else {
                    flinkdb.setVisibility(View.GONE);
                }
            }
        });

        flinkdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flinkUpdate();
            }
        });

        ilink.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    ilinkdb.setVisibility(View.VISIBLE);
                }
                else {
                    ilinkdb.setVisibility(View.GONE);
                }
            }
        });

        ilinkdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilinkUpdate();
            }
        });

        ylink.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    ylinkdb.setVisibility(View.VISIBLE);
                }
                else {
                    ylinkdb.setVisibility(View.GONE);
                }
            }
        });

        ylinkdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ylinkUpdate();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(sellerProfileEdit.this, Login.class);
                startActivity(intent);
            }
        });

//        insta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    String bLink = blink.getText().toString();
//                    Uri uri = Uri.parse(bLink);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                }catch (Exception e){
//                    Toast.makeText(sellerProfileEdit.this, "No Link Found!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        Propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic = new Intent();
                pic.setAction(Intent.ACTION_GET_CONTENT);
                pic.setType("image/*");
                startActivityForResult(pic,1);
            }
        });
    }

    private void FirstAdd() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String addr = add.getText().toString();
        if (addr.isEmpty()){
            add.setError("Cannot be Empty");
            add.requestFocus();
        }
        else {
            re.child(uID).child("business_Address").setValue(addr).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(sellerProfileEdit.this, "Business Address Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(sellerProfileEdit.this, "Error when Update Business Address", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void FirstUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String firstName = fst.getText().toString();
        if (firstName.isEmpty()){
            fst.setError("Cannot be Empty");
            fst.requestFocus();
        }
        else {
            re.child(uID).child("business_Name").setValue(firstName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(sellerProfileEdit.this, "Business Name Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(sellerProfileEdit.this, "Error when Update Business Name", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
            re = FirebaseDatabase.getInstance().getReference("Photography");
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
                                    Toast.makeText(sellerProfileEdit.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(sellerProfileEdit.this,Login.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(sellerProfileEdit.this, "Error when Update Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(sellerProfileEdit.this, "Error when Update Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void mobileUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String mob = mobile.getText().toString();
        if (mob.isEmpty()){
            mobile.setError("Cannot be Empty");
            mobile.requestFocus();
        }
        else {
            FirebaseDatabase.getInstance().getReference("Seller").orderByChild("mobile").equalTo(mob).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    Toast.makeText(sellerProfileEdit.this, "Mobile Updated", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(sellerProfileEdit.this, "Error when Mobiles Name", Toast.LENGTH_SHORT).show();
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

    private void wlinkUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String wLink = wlink.getText().toString();
            re.child(uID).child("business_Link").setValue(wLink).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(sellerProfileEdit.this, "Web Link Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(sellerProfileEdit.this, "Error when Update Web Link", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void flinkUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String fLink = flink.getText().toString();
        re.child(uID).child("f_Link").setValue(fLink).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(sellerProfileEdit.this, "Facebook Link Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(sellerProfileEdit.this, "Error when Update Facebook Link", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ilinkUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String iLink = ilink.getText().toString();
        re.child(uID).child("i_Link").setValue(iLink).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(sellerProfileEdit.this, "Instagram Link Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(sellerProfileEdit.this, "Error when Update Instagram Link", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ylinkUpdate() {
        User = mAuth.getCurrentUser();
        re = FirebaseDatabase.getInstance().getReference("Photography");
        uID = User.getUid();

        String yLink = ylink.getText().toString();
        re.child(uID).child("y_Link").setValue(yLink).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(sellerProfileEdit.this, "Youtube Link Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(sellerProfileEdit.this, "Error when Update Youtube Link", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        re = FirebaseDatabase.getInstance().getReference("Photography");

        String userID = mAuth.getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImagesRef = storageRef.child("photographer/" + userID+"/" + "profile_Pic");

        ImagesRef.putFile(imgUr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        re.child(userID).child("profile_Pic").setValue(uri.toString());
                        progressDialog.dismiss();
                        Toast.makeText(sellerProfileEdit.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(sellerProfileEdit.this,sellerAccount.class);
                        startActivity(intent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(sellerProfileEdit.this, "Image Uploaded Unsuccessful", Toast.LENGTH_SHORT).show();
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