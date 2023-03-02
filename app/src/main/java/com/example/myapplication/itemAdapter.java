package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemHolder> {

    Context context;
    ArrayList<seller> list;


    public itemAdapter(Context context, ArrayList<seller> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new itemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull itemAdapter.itemHolder holder, int position) {

        seller sl = list.get(position);
        holder.FirstName.setText(sl.getBusiness_Name());
        Glide.with(context).load(list.get(position).getProfile_Pic()).into(holder.disply);

        holder.listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference re = FirebaseDatabase.getInstance().getReference("Photography");
                String userID = sl.getUser_Id();
                re.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Intent intent = new Intent(context,photographyMain.class);
                            intent.putExtra("UserID",userID);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Hotel");
                ref.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Intent intent = new Intent(context,hotelMain.class);
                            intent.putExtra("UserID",userID);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                DatabaseReference refe = FirebaseDatabase.getInstance().getReference("bDress");
                refe.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Intent intent = new Intent(context,bDressMain.class);
                            intent.putExtra("UserID",userID);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class itemHolder extends RecyclerView.ViewHolder{

        TextView FirstName;
        ImageView disply;
        ConstraintLayout listBtn;

        public itemHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            FirstName = itemView.findViewById(R.id.txtFirstname);
            disply = itemView.findViewById(R.id.displaypic);
            listBtn = itemView.findViewById(R.id.listBtn);
        }
    }

    public void fltrList(ArrayList<seller> filterList){
        list = filterList;
        notifyDataSetChanged();
    }
}
