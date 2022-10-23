package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Messages extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    RecyclerView recyclerView;
    chatlist_adapter mainAdapter;
    CardView show_list_of_archived_messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //enable navigation bar
        bottomNavigationbar();

        //fetches data from the firebase database and displays them
        display();

        //show_list_of_archived_messages
        show_list_of_archived_messages=findViewById(R.id.show_list_of_archived_messages);
        show_list_of_archived_messages.setOnClickListener(view -> startActivity(new Intent(Messages.this,Archive_Users.class)));
    }

    private void display() {
        recyclerView = findViewById(R.id.all_friends_chat_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        String email="";
        try{email= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());}
        catch (Exception e){email="karabo@gmail.com";}
        FirebaseRecyclerOptions<likers> options =
                new FirebaseRecyclerOptions.Builder<likers>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("List of friends").child(email.replace("@","").replace(".","")), likers.class)
                        .build();
        mainAdapter = new chatlist_adapter(options,getApplicationContext(),"Messages");
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);

    }

    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.messages);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.posts_timeline) {
                Intent intent = new Intent (Messages.this, home_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (Messages.this, Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                Intent intent = new Intent (Messages.this, add_post.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            if (item.getItemId() == R.id.chat) {
                Intent intent = new Intent (Messages.this, SearchUsers.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
}