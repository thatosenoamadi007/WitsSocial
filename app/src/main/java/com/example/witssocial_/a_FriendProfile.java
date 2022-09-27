package com.example.witssocial_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class a_FriendProfile extends AppCompatActivity {
    AppCompatImageView go_back_to_search;
    AppCompatTextView top_bar_friend_email,friend_email,friend_name,number_of_followers,number_of_following;
    AppCompatButton message_friend,follow_friend;
    RecyclerView friend_recyclerview;
    home_adapter mainAdapter;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afriend_profile);

        //initialize variables
        initializeVariables();

        //set variables
        String friendEmail=getIntent().getStringExtra("receiver_id");
        top_bar_friend_email.setText(friendEmail);
        friend_email.setText(friendEmail);
        friend_name.setText(friendEmail);

        //check if you already follow user
        ifFollowsUser();

        //add to recently searched
        user user=new user(getIntent().getStringExtra("receiver_id"));
        String branch1= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@","").replace(".","");
        String branch2=getIntent().getStringExtra("receiver_id").replace("@","").replace(".","");
        recentlySearched(user,branch1,branch2);

        //go back to previous activity
        go_back_to_search.setOnClickListener(view -> startActivity(new Intent(a_FriendProfile.this,SearchUsers.class)));

        //message friend
        message_friend.setOnClickListener(view -> startActivity(new Intent(a_FriendProfile.this,InsideMessage.class).putExtra("receiver_id",friendEmail)));

        //load friend posts content
        friend_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("All Posts"),Post.class).build();
        mainAdapter= new home_adapter(options);
        friend_recyclerview.setAdapter(mainAdapter);

        //follow friend
        follow_friend.setOnClickListener(view -> follow_Unfollow_friend());

    }

    private void recentlySearched(user user,String branch1,String branch2) {
        FirebaseDatabase.getInstance().getReference()
                .child("Search History")
                .child(branch1)
                .child(branch2)
                .setValue(user).addOnSuccessListener(unused -> {
                    Toast.makeText(a_FriendProfile.this, "Added to recently searched.", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(a_FriendProfile.this, "Couldn't add to recently searched.", Toast.LENGTH_SHORT).show());
    }

    private void initializeVariables() {
        number_of_followers=findViewById(R.id.number_of_followers);
        number_of_following=findViewById(R.id.number_of_following);
        follow_friend=findViewById(R.id.follow_friend);
        message_friend=findViewById(R.id.message_friend);
        friend_name=findViewById(R.id.friend_name);
        friend_email=findViewById(R.id.friend_email);
        top_bar_friend_email=findViewById(R.id.top_bar_friend_name);
        go_back_to_search=findViewById(R.id.go_back_to_search);
        friend_recyclerview=findViewById(R.id.friend_profile_recyclerview);
    }

    private void ifFollowsUser() {
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=friend_email.getText().toString().replace("@","").replace(".","");
        FirebaseDatabase.getInstance().getReference()
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            follow_friend.setText("Following");
                            follow_friend.setBackgroundColor(Color.parseColor("#F6F4F4"));
                        }else{
                            follow_friend.setText("Follow");
                            follow_friend.setBackgroundColor(Color.WHITE);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(a_FriendProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void follow_Unfollow_friend() {
        String mode=follow_friend.getText().toString();
        user user=new user(friend_email.getText().toString());
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=friend_email.getText().toString().replace("@","").replace(".","");
        if(mode.equals("Follow")){
            followFriend(user,branch1,branch2);
            follow_friend.setText("Following");
            follow_friend.setBackgroundColor(Color.parseColor("#F6F4F4"));
        }else{
            unfollowFriend(branch1,branch2);
            follow_friend.setText("Follow");
            follow_friend.setBackgroundColor(Color.WHITE);
        }
    }

    private void unfollowFriend(String branch1, String branch2) {
        FirebaseDatabase.getInstance().getReference()
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .removeValue()
                .addOnSuccessListener(unused -> Toast.makeText(a_FriendProfile.this, "Successfully unfollowed user", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(a_FriendProfile.this, "Error while trying to unfollow user.", Toast.LENGTH_SHORT).show());
    }

    private void followFriend(user user, String branch1, String branch2){
        FirebaseDatabase.getInstance().getReference()
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .setValue(user).addOnSuccessListener(unused -> Toast.makeText(a_FriendProfile.this, "Successfully followed user", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(a_FriendProfile.this, "Error while trying to follow user.", Toast.LENGTH_SHORT).show());
    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

}