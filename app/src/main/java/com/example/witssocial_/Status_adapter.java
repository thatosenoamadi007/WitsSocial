package com.example.witssocial_;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Status_adapter extends FirebaseRecyclerAdapter<Post,Status_adapter.myViewHolder> {
    Context context;
    public Status_adapter(@NonNull FirebaseRecyclerOptions<Post> options,Context context){
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Post post) {

        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1")
                .child("Users")
                .child(post.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        Glide.with(holder.status_pic.getContext())
                                .load(user_class.getImage())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_baseline_person_24)
                                .into(holder.status_pic);

                        holder.status_pic.setOnClickListener(v -> {

                                Intent intent=new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setType("*/*");
                                intent.setData(Uri.parse(user_class.getImage()));
                                context.startActivity(intent);

                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_model,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        de.hdodenhof.circleimageview.CircleImageView status_pic;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            status_pic = itemView.findViewById(R.id.status_pic);
        }
    }

}
