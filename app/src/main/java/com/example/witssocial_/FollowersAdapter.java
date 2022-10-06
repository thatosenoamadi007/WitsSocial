package com.example.witssocial_;


import android.annotation.SuppressLint;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.remote.EspressoRemoteMessage;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FollowersAdapter extends FirebaseRecyclerAdapter<user_class,FollowersAdapter.myViewHolder> {

    Context context;
    public FollowersAdapter(@NonNull FirebaseRecyclerOptions<user_class> options, Context context){
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull user_class post) {
        holder.email.setText(post.getEmail());
        holder.username.setText(post.getUsername());
        ifFollowsUser(post.getEmail(),holder);
        holder.follow_friend_in_list_of_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.follow_friend_in_list_of_followers.getText().toString().equals("Following")){
                    unfollowUser(post.getEmail());
                    holder.follow_friend_in_list_of_followers.setText("Follow");
                    holder.follow_friend_in_list_of_followers.setBackgroundColor(Color.WHITE);
                }
                else{
                    followUser(post.getEmail(),post.getUsername(),post.getDescription());
                    holder.follow_friend_in_list_of_followers.setText("Following");
                    holder.follow_friend_in_list_of_followers.setBackgroundColor(Color.parseColor("#F6F4F4"));
                }
            }
        });
    }

    private void followUser(String email, String username, String description) {
        user_class user=new user_class(email,username,description);
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=email.replace("@","").replace(".","");
        //add to list to people im following
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .setValue(user);
        //add to list of list of people who are following me
        getUserDetails(branch1,branch2);
    }

    private void getUserDetails(String branch1, String branch2) {
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        FirebaseDatabase.getInstance().getReference()
                                .child("Wits Social Database")
                                .child("User Followers")
                                .child(branch2)
                                .child(branch1)
                                .setValue(user_class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error getDetails", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void unfollowUser(String email) {
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=email.replace("@","").replace(".","");
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
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_list,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView email, username;
        AppCompatButton follow_friend_in_list_of_followers;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            username = itemView.findViewById(R.id.username);
            follow_friend_in_list_of_followers=itemView.findViewById(R.id.follow_friend_in_list_of_followers);
        }
    }

    private void ifFollowsUser(String email,@NonNull myViewHolder holder) {
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=email.replace("@","").replace(".","");
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.follow_friend_in_list_of_followers.setText("Following");
                            holder.follow_friend_in_list_of_followers.setBackgroundColor(Color.parseColor("#F6F4F4"));
                        }else{
                            holder.follow_friend_in_list_of_followers.setText("Follow");
                            holder.follow_friend_in_list_of_followers.setBackgroundColor(Color.WHITE);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error trying to check if im following a user.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
