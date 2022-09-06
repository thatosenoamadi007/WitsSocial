package com.example.witssocial_;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class home_activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    public BottomNavigationView bottomNavigationView;
    private home_adapter mainAdapter;
    private home_adapter mainAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(home_activity.this, Profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    return true;
                case R.id.post:
                    startActivity(new Intent(home_activity.this, add_post.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });



        recyclerView = (RecyclerView) findViewById(R.id.homerecview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("AllPost"),Post.class).build();
        bottomNavigationbar();

        //----------------------------

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.homerecview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("All Posts"),Post.class).build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter= new home_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.posts_timeline);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
           if (item.getItemId() == R.id.posts_timeline) {
               // Intent intent = new Intent (home_activity.this, home_activity.class);
               // startActivity(intent);
               // return true;
            }
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (home_activity.this, Profile.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                Intent intent = new Intent (home_activity.this, add_post.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.chat) {
               Intent intent = new Intent (home_activity.this, SearchUsers.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }*/
}
