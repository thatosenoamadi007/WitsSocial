package com.example.witssocial_;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    AppCompatTextView top_bar_my_name,my_email,my_username,number_of_followers,number_of_following,my_profile_description,see_list_of_followers,see_list_of_following;
    AppCompatButton edit_my_profile,my_account_settings;
    RecyclerView my_account_profile_recyclerview;
    de.hdodenhof.circleimageview.CircleImageView userprofile;
    home_adapter mainAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialize variables and bottom navigation
        initializeVariables();
        bottomNavigationbar();


        //set names and descriptions
        setStaticValues();

        //edit my profile
        editProfile();

        //see list of followers
        number_of_followers.setOnClickListener(view -> {startActivity(new Intent(Profile.this,Followers.class).putExtra("came_from","Profile"));});
        see_list_of_followers.setOnClickListener(view -> {startActivity(new Intent(Profile.this,Followers.class).putExtra("came_from","Profile"));});

        //see list of following
        number_of_following.setOnClickListener(view -> {startActivity(new Intent(Profile.this,Following.class).putExtra("came_from","Profile"));});
        see_list_of_following.setOnClickListener(view -> {startActivity(new Intent(Profile.this,Following.class).putExtra("came_from","Profile"));});

        //go to settings
        my_account_settings.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Account_Settings.class)));


        //load my posts content
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        my_account_profile_recyclerview.setLayoutManager(linearLayoutManager);
        String email="karabol@gmail.com";
        try{
            email=Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        }catch (Exception e){
            email="karabol@gmail.com";
        }
        assert email != null;
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Posts").child(email.replace("@","").replace(".","")),Post.class).build();
        mainAdapter= new home_adapter(options,getApplicationContext(),"my_profile","null","null","null");
        my_account_profile_recyclerview.setAdapter(mainAdapter);


    }

    private void editProfile() {
        String my_Email=my_email.getText().toString();
        final String[] my_full_name = {my_username.getText().toString()};
        final String[] my_description = {my_profile_description.getText().toString()};
        String id="";
        try{
            id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){
            id="1sHMCTUdp0UwvnfEUdLe6Q6mJif2";
        }
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Users").child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        my_description[0] =user_class.getDescription();
                        my_full_name[0]=user_class.getUsername();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Error occurred trying to retrieve description", Toast.LENGTH_SHORT).show();
                    }
                });
        //Toast.makeText(this, my_description[0], Toast.LENGTH_SHORT).show();
        edit_my_profile.setOnClickListener(view -> startActivity(new Intent(Profile.this,Edit_Profile.class).putExtra("receiver_id",my_Email).putExtra("receiver_username",my_full_name[0]).putExtra("receiver_description", my_description[0])));
    }

    @SuppressLint("SetTextI18n")
    private void setStaticValues() {
        String name="";
        String id="";
        try{
            name=Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
            id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){
            name="karabol@gmail.com";
            id="iZW27xhBsNbqVF4omIsRyQev2K72";
        }
        my_email.setText(name);
        top_bar_my_name.setText("Profile");
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Users").child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        //assert user_class != null;
                        my_username.setText(user_class.getUsername());
                        my_profile_description.setText(user_class.getDescription());
                        Glide.with(userprofile.getContext())
                                .load(user_class.getImage())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_baseline_person_24)
                                .into(userprofile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Error occurred trying to update username and description", Toast.LENGTH_SHORT).show();
                    }
                });

        //set number of followers and number of following
        setNumberFollowersFollowing();
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
        see_list_of_followers=findViewById(R.id.see_list_of_followers);
        see_list_of_following=findViewById(R.id.see_list_of_following);
        userprofile=findViewById(R.id.userprofile);
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

    private void setNumberFollowersFollowing() {
        String friend_email="krabol@gmail.com";
        try{friend_email=Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();}
        catch (Exception e){friend_email="karabol@gmail.com";}
        String friend_email2=friend_email;
        //Toast.makeText(Profile.this, friend_email+"-----"+friend_email.replace("@","").replace(".",""), Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("User Followers")
                .child(friend_email.replace("@","").replace(".",""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int count=(int)snapshot.getChildrenCount();
                            number_of_followers.setText(Integer.toString(count));

                        }else{
                            number_of_followers.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("User Following")
                .child(friend_email2.replace("@","").replace(".",""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int count=(int)snapshot.getChildrenCount();
                            number_of_following.setText(Integer.toString(count));
                        }else{
                            number_of_following.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}