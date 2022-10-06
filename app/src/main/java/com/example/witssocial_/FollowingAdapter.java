package com.example.witssocial_;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.remote.EspressoRemoteMessage;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FollowingAdapter extends FirebaseRecyclerAdapter<user_class,FollowingAdapter.myViewHolder> {

    Context context;
    String remove_delete_button;
    public FollowingAdapter(@NonNull FirebaseRecyclerOptions<user_class> options, Context context,String remove_delete_button){
        super(options);
        this.context=context;
        this.remove_delete_button=remove_delete_button;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull user_class post) {

        holder.email.setText(post.getEmail());
        holder.username.setText(post.getUsername());
        if(remove_delete_button.equals("yes")){
            holder.unfollow_user_in_list_of_following.setBackgroundColor(Color.WHITE);
        }else{
            holder.unfollow_user_in_list_of_following.setOnClickListener(view -> {
                String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
                String branch2=post.getEmail().replace("@","").replace(".","");
                //remove from list of people im following
                FirebaseDatabase.getInstance().getReference()
                        .child("Wits Social Database")
                        .child("User Following")
                        .child(branch1)
                        .child(branch2)
                        .removeValue();
                //.addOnSuccessListener(unused -> Toast.makeText(a_FriendProfile.this, "Successfully unfollowed user", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(a_FriendProfile.this, "Error while trying to unfollow user.", Toast.LENGTH_SHORT).show());
                //remove from list of list of people who are following me
                FirebaseDatabase.getInstance().getReference()
                        .child("Wits Social Database")
                        .child("User Followers")
                        .child(branch2)
                        .child(branch1)
                        .removeValue();
            });
        }

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_following,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView email, username;
        AppCompatImageView unfollow_user_in_list_of_following;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            username = itemView.findViewById(R.id.username);
            unfollow_user_in_list_of_following=itemView.findViewById(R.id.unfollow_user_in_list_of_following);
        }
    }

}
