package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witssocial_.messagesAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SearchUsers extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    RecyclerView recyclerView;
    messagesAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
        //enable searchView
        initializeSearchView();

        //enable navigation bar
        bottomNavigationbar();

        //Enters fullscreen mode when the app is launched
        //EnterFullSreenMode();

        //changes the hint color of the search view
        changeSearchHintColor();

        //fetches data from the firebase database and displays them
        display();

    }

    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.posts_timeline:
                    startActivity(new Intent(getApplicationContext(),home_activity.class));
                    return true;
                case R.id.account:
                    startActivity(new Intent(getApplicationContext(),Profile.class));
                    return true;
                case R.id.add_post:
                    startActivity(new Intent(getApplicationContext(),add_post.class));
                    return true;
                case R.id.chat:
                    //startActivity(new Intent(getApplicationContext(),SearchUsers.class));
                    return true;
            }
            return false;
        });
    }

    private void display() {
        recyclerView = (RecyclerView)findViewById(R.id.all_friends_chat);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<user> options =
                new FirebaseRecyclerOptions.Builder<user>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("List of friends").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("@","").replace(".","")).orderByChild("timestamp"), user.class)
                        .build();
        mainAdapter = new messagesAdapter(options,getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);

    }

    void EnterFullSreenMode(){
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
    }

    void changeSearchHintColor(){

        SearchView searchView= (SearchView) findViewById(R.id.search_friend_chat);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.parseColor("#535252"));//#D9D9D9
        textView.setHintTextColor(Color.parseColor("#C9C9C9"));

    }

    private void findFriend(String s) {
        if(s.equals("")){
            findAllFriends();
        }else{
            FirebaseRecyclerOptions<user> options =
                    new FirebaseRecyclerOptions.Builder<user>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").startAt(s.toLowerCase()).endAt(s.toLowerCase()+"~"), user.class)//.orderByChild("modName").equalTo("APHY8010")
                            .build();
            mainAdapter = new messagesAdapter(options,getApplicationContext());
            mainAdapter.startListening();
            recyclerView.setAdapter(mainAdapter);
        }


    }

    private void findAllFriends() {
        FirebaseRecyclerOptions<user> options =
                new FirebaseRecyclerOptions.Builder<user>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("List of friends").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("@","").replace(".","")).orderByChild("timestamp"), user.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        mainAdapter = new messagesAdapter(options,getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    void initializeSearchView(){
        searchView=findViewById(R.id.search_friend_chat);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                findFriend(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                findFriend(s);
                return true;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //mainAdapter.stopListening();
    }
}