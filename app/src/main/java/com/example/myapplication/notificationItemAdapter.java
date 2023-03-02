package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notificationItemAdapter extends RecyclerView.Adapter<notificationItemAdapter.NotifitemHolder> {

    Context context;
    ArrayList<request> list;

    public notificationItemAdapter(Context context, ArrayList<request> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public NotifitemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
        return new NotifitemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull notificationItemAdapter.NotifitemHolder holder, int position) {

        request rq = list.get(position);
        holder.date.setText("Name - " + rq.getUser_Name() + "\nMobile Number - "+ rq.getUser_Mobile()+ "\nReservation on " +rq.getDate());

        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uripn = Uri.parse("tel:" + rq.getUser_Mobile());
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, uripn);
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context, "Invalid Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Alert");
                builder.setMessage("Are you sure, Want to Accept!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference re = FirebaseDatabase.getInstance().getReference("Request");
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = u.getUid();
                        re.child(userID).orderByChild("user_ID").equalTo(rq.getUser_ID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String stat = dataSnapshot.child("status").getValue(String.class);
                                    if (stat.equals("Pending")){
                                        dataSnapshot.getRef().child("status").setValue("Accept");
                                        holder.btnreject.setVisibility(View.GONE);
                                        holder.btnaccept.setVisibility(View.GONE);
                                        dialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.btnreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Alert");
                builder.setMessage("Are you sure, Want to Reject!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference re = FirebaseDatabase.getInstance().getReference("Request");
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = u.getUid();
                        re.child(userID).orderByChild("user_ID").equalTo(rq.getUser_ID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String stat = dataSnapshot.child("status").getValue(String.class);
                                    if (stat.equals("Pending")){
                                        dataSnapshot.getRef().child("status").setValue("Sorry, Won't available");
                                        holder.btnreject.setVisibility(View.GONE);
                                        holder.btnaccept.setVisibility(View.GONE);
                                        dialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request");
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        String userID = u.getUid();

        ref.child(userID).orderByChild("status").equalTo(rq.getStatus()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String stat = dataSnapshot.child("status").getValue(String.class);
                    if (stat.equals("Pending")){
                        holder.btnreject.setVisibility(View.VISIBLE);
                        holder.btnaccept.setVisibility(View.VISIBLE);
                    }else {
                        holder.btnreject.setVisibility(View.GONE);
                        holder.btnaccept.setVisibility(View.GONE);
                        holder.status.setText(stat);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NotifitemHolder extends RecyclerView.ViewHolder{

        TextView date,status;
        Button btnaccept,btnreject;

        public NotifitemHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.txtDate);
            status = itemView.findViewById(R.id.status);
            btnaccept = itemView.findViewById(R.id.btnaccept);
            btnreject = itemView.findViewById(R.id.btnreject);
        }
    }

}
