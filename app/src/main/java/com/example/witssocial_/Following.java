package com.example.witssocial_;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Following extends AppCompatActivity {
    AppCompatImageView go_back_to_my_profile;
    RecyclerView show_list_of_following;
    FollowingAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        //go back to profile
        go_back_to_my_profile=findViewById(R.id.go_back_to_my_profile);
        String rec=getIntent().getStringExtra("receiver_id");
        String rec_us=getIntent().getStringExtra("receiver_username");
        String rec_desc=getIntent().getStringExtra("receiver_description");
        String friend_profile=getIntent().getStringExtra("receiver_profile_pic");
        if(getIntent().getStringExtra("came_from")!=null && getIntent().getStringExtra("came_from").equals("a_FriendProfile")){
            go_back_to_my_profile.setOnClickListener(view -> startActivity(new Intent(Following.this,a_FriendProfile.class).putExtra("receiver_id",rec).putExtra("receiver_username",rec_us).putExtra("receiver_description",rec_desc).putExtra("receiver_profile_pic",friend_profile)));
            showFollowingList(rec,"yes");
        }
        else{
            go_back_to_my_profile.setOnClickListener(view -> startActivity(new Intent(Following.this,Profile.class)));
            String email="";
            try{email=Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());}catch (Exception e){email="karabol@gmail.com";}
            showFollowingList(email,"no");
        }




    }

    private void showFollowingList(String rec,String no_yes) {
        //show list of following
        show_list_of_following=findViewById(R.id.show_list_of_following);
        show_list_of_following.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<user_class> options = new FirebaseRecyclerOptions.Builder<user_class>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("User Following").child(rec.replace("@","").replace(".","")),user_class.class).build();
        mainAdapter= new FollowingAdapter(options,getApplicationContext(), no_yes);
        show_list_of_following.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

}
