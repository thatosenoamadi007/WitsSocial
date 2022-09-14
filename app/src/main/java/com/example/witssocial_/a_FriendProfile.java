package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class a_FriendProfile extends AppCompatActivity {
    AppCompatImageView go_back_to_search;
    AppCompatTextView top_bar_friend_email,friend_email,friend_name;
    AppCompatButton message_friend,follow_friend;
    RecyclerView friend_recyclerview;
    home_adapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afriend_profile);

        //initialize variables
        follow_friend=findViewById(R.id.follow_friend);
        message_friend=findViewById(R.id.message_friend);
        friend_name=findViewById(R.id.friend_name);
        friend_email=findViewById(R.id.friend_email);
        top_bar_friend_email=findViewById(R.id.top_bar_friend_name);
        go_back_to_search=findViewById(R.id.go_back_to_search);
        friend_recyclerview=findViewById(R.id.friend_profile_recyclerview);

        //set variables
        String friendEmail=getIntent().getStringExtra("receiver_id");
        top_bar_friend_email.setText(friendEmail);
        friend_email.setText(friendEmail);
        friend_name.setText(friendEmail);

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
        follow_friend.setOnClickListener(view -> {
            follow_Unfollow_friend();
        });

    }

    private void follow_Unfollow_friend() {
        String mode=follow_friend.getText().toString();
        if(mode.equals("Follow")){

        }else{

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
}