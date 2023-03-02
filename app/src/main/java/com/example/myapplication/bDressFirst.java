package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class bDressFirst extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    itemAdapter adapter;
    ArrayList<seller> list;
    TextView resulttTxt,clearprice,txtviewpricerange;
    Button fltrBtn,prcfltrBtn,closeprc,hm,pf,lo;
    TextInputEditText startat,endat;
    ConstraintLayout priceLayout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdress_first);

        mAuth = FirebaseAuth.getInstance();

        resulttTxt = findViewById(R.id.resultTxt);
        fltrBtn = findViewById(R.id.fltrBtn);
        prcfltrBtn = findViewById(R.id.prcfltrBtn);
        startat = findViewById(R.id.TxtStartat);
        endat = findViewById(R.id.TxtEndat);
        closeprc = findViewById(R.id.closeprc);
        clearprice = findViewById(R.id.clearprice);
        txtviewpricerange = findViewById(R.id.txtviewpricerange);
        priceLayout = findViewById(R.id.priceLayout);
        recyclerView = findViewById(R.id.list);
        hm = findViewById(R.id.hm);
        pf = findViewById(R.id.pf);
        lo = findViewById(R.id.LObutton);
        databaseReference = FirebaseDatabase.getInstance().getReference("bDress");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new itemAdapter(this, list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    seller sl = dataSnapshot.getValue(seller.class);
                    list.add(sl);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bDressFirst.this, firstHome.class);
                startActivity(intent);
            }
        });

        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bDressFirst.this, home.class);
                startActivity(intent);
            }
        });

        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(bDressFirst.this, Login.class);
                startActivity(intent);
            }
        });

        prcfltrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceLayout.setVisibility(View.VISIBLE);
            }
        });

        txtviewpricerange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceLayout.setVisibility(View.VISIBLE);
            }
        });

        fltrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = startat.getText().toString();
                String end = endat.getText().toString();

                if (start.isEmpty()){
                    startat.setError("Required");
                    startat.requestFocus();
                }
                if (end.isEmpty()){
                    endat.setError("Required");
                    endat.requestFocus();
                }else {
                    try {
                        double starta = Double.parseDouble(start);
                        double enda = Double.parseDouble(end);
                        databaseReference.orderByChild("starting_Price").startAt(starta).endAt(enda).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    clearprice.setVisibility(View.VISIBLE);
                                    priceLayout.setVisibility(View.GONE);
                                    list.clear();
                                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                                        seller sl = dataSnapshot.getValue(seller.class);
                                        list.add(sl);

                                    }
                                }else {
                                    Toast.makeText(bDressFirst.this, "Sorry, No results found that Price Range", Toast.LENGTH_SHORT).show();
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(bDressFirst.this, "Invalid Number or Contain Letters", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        closeprc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceLayout.setVisibility(View.GONE);
            }
        });

        clearprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearprice.setVisibility(View.GONE);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            seller sl = dataSnapshot.getValue(seller.class);
                            list.add(sl);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

//        EditText editText = findViewById(R.id.searchbr);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
        SearchView searchView = findViewById(R.id.searchbr);
//        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        editText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<seller> filterList = new ArrayList<>();

        for (seller itemS: list){
            if (itemS.getBusiness_Name().toLowerCase().contains(text.toLowerCase()) || itemS.getLocality().toLowerCase().contains(text.toLowerCase())){
                filterList.add(itemS);
            }
        }
        if (filterList.isEmpty()){
            String txt = "Sorry, No results found!";
            resulttTxt.setText(txt);
        }else {
            resulttTxt.setText(null);
        }
        adapter.fltrList(filterList);
    }
}