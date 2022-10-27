package com.example.witssocial_;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Comment_Section extends AppCompatActivity {
    AppCompatImageView go_back_to_home_activity,upload_comment;
    AppCompatEditText add_a_comment;
    RecyclerView show_all_comments;
    CommentsAdapter commentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);
        String friend_Email=getIntent().getStringExtra("receiver_id");
        String friend_Name=getIntent().getStringExtra("receiver_username");
        String friend_Description=getIntent().getStringExtra("receiver_description");
        String friend_Picture=getIntent().getStringExtra("receiver_profile_pic");
        //go back to home activity
        go_back_to_home_activity=findViewById(R.id.go_back_to_home_activity);
        go_back_to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String came_from=getIntent().getStringExtra("came_from");
                if(came_from.equals("home_activity")){
                    startActivity(new Intent(Comment_Section.this,home_activity.class));
                }
                else if(came_from.equals("my_profile")){
                    startActivity(new Intent(Comment_Section.this,Profile.class));
                }
                else{

                    final String[] profile_pic = new String[1];
                    FirebaseDatabase.getInstance().getReference().child("Wits Social Database1")
                            .child("Users")
                            .child(friend_Picture)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        user_class user_class=snapshot.getValue(com.example.witssocial_.user_class.class);
                                        assert user_class != null;
                                        profile_pic[0] =user_class.getImage();
                                        startActivity(new Intent(Comment_Section.this,a_FriendProfile.class).putExtra("receiver_id",friend_Email).putExtra("receiver_username",friend_Name).putExtra("receiver_description",friend_Description).putExtra("receiver_profile_pic",profile_pic[0]));
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }
            }
        });

        //show all comments first
        show_all_comments=findViewById(R.id.show_all_comments);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        show_all_comments.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<comment> options = new FirebaseRecyclerOptions.Builder<comment>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Comments").child(getIntent().getStringExtra("post_id")),comment.class).build();
        commentsAdapter= new CommentsAdapter(options);
        show_all_comments.setAdapter(commentsAdapter);

        //add a comment
        add_a_comment=findViewById(R.id.add_a_comment);
        upload_comment=findViewById(R.id.upload_comment);
        upload_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.requireNonNull(add_a_comment.getText()).toString().isEmpty()){

                   String key=FirebaseDatabase.getInstance().getReference("Wits Social Database1").push().getKey();
                    comment comment=new comment(add_a_comment.getText().toString(), Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                    assert key != null;
                    FirebaseDatabase.getInstance().getReference("Wits Social Database1")
                            .child("Comments")
                            .child(getIntent().getStringExtra("post_id"))
                            .child(key)
                            .setValue(comment)
                            .addOnSuccessListener(unused -> add_a_comment.setText("")).addOnFailureListener(e -> Toast.makeText(Comment_Section.this, "Error trying to upload comment.", Toast.LENGTH_SHORT).show());
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        commentsAdapter.startListening();
    }
}
