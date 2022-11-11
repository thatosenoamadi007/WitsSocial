package com.example.witssocial_;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class home_activity extends AppCompatActivity {

   //find all views in activity
    //TextView all_post,media_post,text_post;
    private RecyclerView recyclerView,show_statuses;
    public BottomNavigationView bottomNavigationView;
    private home_adapter mainAdapter;
    private Status_adapter status_adapter;
 
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       /* all_post=findViewById(R.id.All_Posts);
        media_post=findViewById(R.id.Media_Posts);
        text_post=findViewById(R.id.Text_Posts);*/
         //initlise the recycler view, layout manager and firebase recycler options
        recyclerView =findViewById(R.id.homerecview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("All Posts"),Post.class).build();
        mainAdapter= new home_adapter(options,getApplicationContext(),"home_activity","null","null","null");
        recyclerView.setAdapter(mainAdapter);
        bottomNavigationbar();

        /*all_post.setOnClickListener(view -> {
            all_post.setBackgroundColor(Color.WHITE);
            media_post.setBackgroundColor(Color.parseColor("#F6F4F4"));
            text_post.setBackgroundColor(Color.parseColor("#F6F4F4"));
        });

        media_post.setOnClickListener(view -> {
            media_post.setBackgroundColor(Color.WHITE);
            all_post.setBackgroundColor(Color.parseColor("#F6F4F4"));
            text_post.setBackgroundColor(Color.parseColor("#F6F4F4"));
        });

        text_post.setOnClickListener(view -> {
            text_post.setBackgroundColor(Color.WHITE);
            all_post.setBackgroundColor(Color.parseColor("#F6F4F4"));
            media_post.setBackgroundColor(Color.parseColor("#F6F4F4"));
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
        //status_adapter.startListening();
    }

     //fuction to navigate the bottom navigation menu
    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.posts_timeline);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            item.getItemId();
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (home_activity.this, Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                Intent intent = new Intent (home_activity.this, add_post.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.chat) {
               Intent intent = new Intent (home_activity.this, SearchUsers.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.messages) {
                Intent intent = new Intent (home_activity.this, Messages.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getStringExtra("sign_out_or_not")!=null){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Are you sure you want to log out?");
            alertDialogBuilder
                    //.setMessage("Click yes to sign out!")
                    .setCancelable(true)
                    .setPositiveButton("Yes",
                            (dialog, id) -> {
                                startActivity(new Intent(home_activity.this,login.class));
                            })
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            super.onBackPressed();
        }

    }
}
