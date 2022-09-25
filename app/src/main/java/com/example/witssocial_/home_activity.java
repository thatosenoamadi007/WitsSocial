package com.example.witssocial_;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class home_activity extends AppCompatActivity {

   //find all views in activity
    private RecyclerView recyclerView;
    public BottomNavigationView bottomNavigationView;
    private home_adapter mainAdapter;

 
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initlise the recycler view, layout manager and firebase recycler options
        recyclerView = (RecyclerView) findViewById(R.id.homerecview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("All Posts"),Post.class).build();
        bottomNavigationbar();

        RecyclerView recyclerView = findViewById(R.id.homerecview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
       
       //set recyclerview to the main adapter
        recyclerView.getRecycledViewPool().clear();
        mainAdapter= new home_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    //function to navigate the bottom navigation menu
    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.posts_timeline);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            item.getItemId();
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

    //adapter start listening
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    //adapter stop listening
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }


}
