package com.example.witssocial_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class a_FriendProfile extends AppCompatActivity {
    AppCompatImageView go_back_to_search;
    de.hdodenhof.circleimageview.CircleImageView friend_profile;
    AppCompatTextView top_bar_friend_email,friend_description,friend_email,friend_name,number_of_followers,number_of_following,see_list_of_followers,see_list_of_following;
    AppCompatButton message_friend,follow_friend;
    RecyclerView friend_recyclerview;
    home_adapter mainAdapter;
    String profile_pic_url;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afriend_profile);

        //initialize variables
        initializeVariables();

        //set variables
        String friend_Email=getIntent().getStringExtra("receiver_id");
        String friend_Name=getIntent().getStringExtra("receiver_username");
        String friend_Description=getIntent().getStringExtra("receiver_description");
        profile_pic_url=getIntent().getStringExtra("receiver_profile_pic");
        top_bar_friend_email.setText(friend_Email);
        friend_email.setText(friend_Email);
        friend_name.setText(friend_Name);
        friend_description.setText(friend_Description);
        friend_profile=findViewById(R.id.friend_profile);
        Glide.with(friend_profile.getContext())
                .load(profile_pic_url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_baseline_person_24)
                .into(friend_profile);

        //set number of followers and number of following
        setNumberFollowersFollowing(friend_Email);

        //check if you already follow user
        ifFollowsUser();

        //add to recently searched
        user_class user=new user_class(getIntent().getStringExtra("receiver_id"),getIntent().getStringExtra("receiver_username"),getIntent().getStringExtra("receiver_description"),profile_pic_url);
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=getIntent().getStringExtra("receiver_id").replace("@","").replace(".","");
        recentlySearched(user,branch1,branch2);

        //go back to previous activity
        go_back_to_search.setOnClickListener(view -> startActivity(new Intent(a_FriendProfile.this,SearchUsers.class)));

        //message friend
        message_friend.setOnClickListener(view -> startActivity(new Intent(a_FriendProfile.this,InsideMessage.class).putExtra("receiver_id",friend_Email).putExtra("receiver_username",friend_Name).putExtra("receiver_description",friend_Description).putExtra("receiver_profile_pic",profile_pic_url)));

        //load friend posts content
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        friend_recyclerview.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Posts").child(user.getEmail().replaceAll("@","").replace(".","")),Post.class).build();
        mainAdapter= new home_adapter(options,getApplicationContext(),"friend_profile",friend_Email,friend_Name,friend_Description);
        friend_recyclerview.setAdapter(mainAdapter);

        //follow friend
        follow_friend.setOnClickListener(view -> follow_Unfollow_friend());

        //see list of followers
        String rec=getIntent().getStringExtra("receiver_id");
        String rec_us=getIntent().getStringExtra("receiver_username");
        String rec_desc=getIntent().getStringExtra("receiver_username");
        number_of_followers.setOnClickListener(view -> {startActivity(new Intent(a_FriendProfile.this,Followers.class).putExtra("came_from","a_FriendProfile").putExtra("receiver_id",rec).putExtra("receiver_username",rec_us).putExtra("receiver_username",rec_desc).putExtra("receiver_profile_pic",profile_pic_url));});
        see_list_of_followers.setOnClickListener(view -> {startActivity(new Intent(a_FriendProfile.this,Followers.class).putExtra("came_from","a_FriendProfile").putExtra("receiver_id",rec).putExtra("receiver_username",rec_us).putExtra("receiver_username",rec_desc).putExtra("receiver_profile_pic",profile_pic_url));});

        //see list of following
        number_of_following.setOnClickListener(view -> {startActivity(new Intent(a_FriendProfile.this,Following.class).putExtra("came_from","a_FriendProfile").putExtra("receiver_id",rec).putExtra("receiver_username",rec_us).putExtra("receiver_username",rec_desc).putExtra("receiver_profile_pic",profile_pic_url));});
        see_list_of_following.setOnClickListener(view -> {startActivity(new Intent(a_FriendProfile.this,Following.class).putExtra("came_from","a_FriendProfile").putExtra("receiver_id",rec).putExtra("receiver_username",rec_us).putExtra("receiver_username",rec_desc).putExtra("receiver_profile_pic",profile_pic_url));});

    }

    private void setNumberFollowersFollowing(String friend_email) {
        String email=friend_email;
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("User Followers")
                .child(email.replace("@","").replace(".",""))
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
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("User Following")
                .child(friend_email.replace("@","").replace(".",""))
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

    private void recentlySearched(user_class user,String branch1,String branch2) {
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("Search History")
                .child(branch1)
                .child(branch2)
                .setValue(user);//.addOnSuccessListener(unused -> Toast.makeText(a_FriendProfile.this, "Added to recently searched.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(a_FriendProfile.this, "Couldn't add to recently searched.", Toast.LENGTH_SHORT).show());
    }

    private void initializeVariables() {
        number_of_followers=findViewById(R.id.number_of_followers);
        number_of_following=findViewById(R.id.number_of_following);
        follow_friend=findViewById(R.id.follow_friend);
        message_friend=findViewById(R.id.message_friend);
        friend_name=findViewById(R.id.friend_name);
        friend_email=findViewById(R.id.friend_email);
        top_bar_friend_email=findViewById(R.id.top_bar_friend_name);
        go_back_to_search=findViewById(R.id.go_back_to_search);
        friend_recyclerview=findViewById(R.id.friend_profile_recyclerview);
        friend_description=findViewById(R.id.friend_description);
        see_list_of_followers=findViewById(R.id.see_list_of_followers);
        see_list_of_following=findViewById(R.id.see_list_of_following);
    }

    private void ifFollowsUser() {
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=friend_email.getText().toString().replace("@","").replace(".","");
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            follow_friend.setText("Following");
                            follow_friend.setBackgroundColor(Color.parseColor("#F6F4F4"));
                        }else{
                            follow_friend.setText("Follow");
                            follow_friend.setBackgroundColor(Color.WHITE);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(a_FriendProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void follow_Unfollow_friend() {
        String mode=follow_friend.getText().toString();
        user_class user=new user_class(friend_email.getText().toString(),friend_name.getText().toString(),friend_description.getText().toString(),profile_pic_url);
        String branch1= Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@","").replace(".","");
        String branch2=friend_email.getText().toString().replace("@","").replace(".","");
        if(mode.equals("Follow")){
            followFriend(user,branch1,branch2);
            follow_friend.setText("Following");
            follow_friend.setBackgroundColor(Color.parseColor("#F6F4F4"));
        }else{
            unfollowFriend(branch1,branch2);
            follow_friend.setText("Follow");
            follow_friend.setBackgroundColor(Color.WHITE);
        }
    }

    private void unfollowFriend(String branch1, String branch2) {
        //remove from list of people im following
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .removeValue();
                //.addOnSuccessListener(unused -> Toast.makeText(a_FriendProfile.this, "Successfully unfollowed user", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(a_FriendProfile.this, "Error while trying to unfollow user.", Toast.LENGTH_SHORT).show());
        //remove from list of list of people who are following me
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("User Followers")
                .child(branch2)
                .child(branch1)
                .removeValue();
    }

    private void followFriend(user_class user, String branch1, String branch2){
        //add to list to people im following
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database")
                .child("User Following")
                .child(branch1)
                .child(branch2)
                .setValue(user);
        //add to list of list of people who are following me
        getUserDetails(branch1,branch2);
        //user_class user_class=new user_class(details[0],details[1],details[2] );

    }

    private void getUserDetails(String branch1, String branch2){
        FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                        assert user_class != null;
                        FirebaseDatabase.getInstance().getReference()
                                .child("Wits Social Database")
                                .child("User Followers")
                                .child(branch2)
                                .child(branch1)
                                .setValue(user_class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(a_FriendProfile.this, "Error occurred trying to retrive user details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

}