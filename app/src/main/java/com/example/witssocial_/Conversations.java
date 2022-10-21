package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Conversations extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        bottomNavigationbar();
    }

    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.chat_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.posts_timeline) {
                Intent intent = new Intent (Conversations.this, home_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (Conversations.this, Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                Intent intent = new Intent (Conversations.this, add_post.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }

            if (item.getItemId() == R.id.searchbtn) {
                Intent intent = new Intent (Conversations.this, SearchUsers.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            return false;
        });
    }
}