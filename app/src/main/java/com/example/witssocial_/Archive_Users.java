package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Archive_Users extends AppCompatActivity {
    AppCompatImageView go_back_to_chat;
    RecyclerView recyclerView;
    chatlist_adapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_users);

        //go back to search
        go_back_to_chat=findViewById(R.id.go_back_to_chat);
        go_back_to_chat.setOnClickListener(view -> startActivity(new Intent(Archive_Users.this,Messages.class)));

        //display list archived users
        display();
    }

    //when activity loads show my list of archived users
    private void display() {
        recyclerView = findViewById(R.id.all_friends_chat_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        
        //check if testing or user mode
        String id="";
        try{id= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();} catch (Exception e){id="CYFstJWuF9NKirsH8GMewwB0t7m2";}
        FirebaseRecyclerOptions<likers> options = new FirebaseRecyclerOptions.Builder<likers>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Archived Users").child(id), likers.class).build();
        mainAdapter = new chatlist_adapter(options,getApplicationContext(),"Archive_Users");
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);

    }
}
