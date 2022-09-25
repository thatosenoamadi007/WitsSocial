package com.example.witssocial_;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    AppCompatTextView top_bar_my_name,my_email,my_username,number_of_followers,number_of_following,my_profile_description;
    AppCompatButton edit_my_profile,my_account_settings;
    RecyclerView my_account_profile_recyclerview;
    home_adapter mainAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialize variables
        initializeVariables();

        bottomNavigationbar();


        //set names and descriptions
        setStaticValues();

        //edit my profile
        editProfile();

        //go to settings
        //my_account_settings.setOnClickListener(view -> startActivity(new Intent(Profile.this,Account_Settings.class)));


        //load my posts content
        my_account_profile_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Posts").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","")),Post.class).build();
        mainAdapter= new home_adapter(options);
        my_account_profile_recyclerview.setAdapter(mainAdapter);


    }

    private void editProfile() {
        String my_Email=my_email.getText().toString();
        String my_full_name=my_username.getText().toString();
        final String[] my_description = {my_profile_description.getText().toString()};
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        my_description[0] =user_class.getDescription();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Error occurred trying to retrieve description", Toast.LENGTH_SHORT).show();
                    }
                });
        //Toast.makeText(this, my_description[0], Toast.LENGTH_SHORT).show();
        edit_my_profile.setOnClickListener(view -> startActivity(new Intent(Profile.this,Edit_Profile.class).putExtra("receiver_id",my_Email).putExtra("receiver_username",my_full_name).putExtra("receiver_description", my_description[0])));
    }

    @SuppressLint("SetTextI18n")
    private void setStaticValues() {
        String name= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        my_email.setText(name);
        top_bar_my_name.setText(name);
        //my_username.setText(name);
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        my_username.setText(user_class.getUsername());
                        my_profile_description.setText(user_class.getDescription());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Error occurred trying to update username and description", Toast.LENGTH_SHORT).show();
                    }
                });
        //my_profile_description.setText("My description");
    }

    private void initializeVariables() {
        number_of_followers=findViewById(R.id.number_of_followers);
        number_of_following=findViewById(R.id.number_of_following);
        edit_my_profile=findViewById(R.id.edit_my_profile);
        my_account_settings=findViewById(R.id.my_account_settings);
        my_username=findViewById(R.id.my_username);
        my_email=findViewById(R.id.my_email);
        top_bar_my_name=findViewById(R.id.top_bar_my_name);
        my_account_profile_recyclerview=findViewById(R.id.my_account_profile_recyclerview);
        my_profile_description=findViewById(R.id.my_profile_description);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    //fuction to navigate the bottom navigation menu
    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            item.getItemId();
            if (item.getItemId() == R.id.posts_timeline) {
                Intent intent = new Intent (Profile.this, home_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                Intent intent = new Intent (Profile.this, add_post.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            if (item.getItemId() == R.id.chat) {
                Intent intent = new Intent (Profile.this, SearchUsers.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }
            return false;
        });
    }
}