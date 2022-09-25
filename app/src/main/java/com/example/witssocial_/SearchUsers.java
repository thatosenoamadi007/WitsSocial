package com.example.witssocial_;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

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
            if (item.getItemId() == R.id.posts_timeline) {
                Intent intent = new Intent (SearchUsers.this, home_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (SearchUsers.this, Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                Intent intent = new Intent (SearchUsers.this, add_post.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            return false;
        });
    }

    private void display() {
        recyclerView = findViewById(R.id.all_friends_chat);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<user_class> options =
                new FirebaseRecyclerOptions.Builder<user_class>()//List of friends User Following
                        //.setQuery(FirebaseDatabase.getInstance().getReference().child("Search History").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replaceAll("@","").replace(".","")).orderByChild("timestamp"), user.class)
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Search History").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replaceAll("@","").replace(".","")).orderByChild("timestamp"), user_class.class)
                        .build();
        mainAdapter = new messagesAdapter(options,getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);

    }

    void changeSearchHintColor(){

        SearchView searchView= findViewById(R.id.search_friend_chat);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.parseColor("#535252"));//#D9D9D9
        textView.setHintTextColor(Color.parseColor("#C9C9C9"));

    }

    private void findFriend(String s) {
        if(s.equals("")){
            findAllFriends();
        }else{
            FirebaseRecyclerOptions<user_class> options =
                    new FirebaseRecyclerOptions.Builder<user_class>()
                            //.setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").startAt(s.toLowerCase(Locale.ROOT)).endAt(s.toLowerCase(Locale.ROOT)+"~"), user.class)//.orderByChild("modName").equalTo("APHY8010")
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").orderByChild("username").startAt(s.toLowerCase(Locale.ROOT)).endAt(s.toLowerCase(Locale.ROOT)+"~"), user_class.class)//.orderByChild("modName").equalTo("APHY8010")
                            .build();
            mainAdapter = new messagesAdapter(options,getApplicationContext());
            mainAdapter.startListening();
            recyclerView.setAdapter(mainAdapter);
        }


    }

    private void findAllFriends() {

        mainAdapter = new messagesAdapter(new FirebaseRecyclerOptions.Builder<user_class>()//List of Friends
                //.setQuery(FirebaseDatabase.getInstance().getReference().child("Search History").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replaceAll("@","").replace(".","")).orderByChild("timestamp"), user.class)
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Search History").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replaceAll("@","").replace(".","")).orderByChild("timestamp"), user_class.class)
                .build(),getApplicationContext());
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
    }
}
