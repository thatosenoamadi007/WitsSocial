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
import com.google.firebase.database.FirebaseDatabase;

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
                    String friend_Email=getIntent().getStringExtra("receiver_id");
                    String friend_Name=getIntent().getStringExtra("receiver_username");
                    String friend_Description=getIntent().getStringExtra("receiver_description");
                    startActivity(new Intent(Comment_Section.this,a_FriendProfile.class).putExtra("receiver_id",friend_Email).putExtra("receiver_username",friend_Name).putExtra("receiver_description",friend_Description));
                }
            }
        });

        //show all comments first
        show_all_comments=findViewById(R.id.show_all_comments);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        show_all_comments.setLayoutManager(linearLayoutManager);
        String id=getIntent().getStringExtra("post_id");
        if(id==null){
            id="-NDif6a4eeDvqNB4jHJs";
        }
        FirebaseRecyclerOptions<comment> options = new FirebaseRecyclerOptions.Builder<comment>().setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Comments").child(id),comment.class).build();
        commentsAdapter= new CommentsAdapter(options);
        show_all_comments.setAdapter(commentsAdapter);

        //add a comment
        add_a_comment=findViewById(R.id.add_a_comment);
        upload_comment=findViewById(R.id.upload_comment);
        String finalId = id;
        upload_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!add_a_comment.getText().toString().isEmpty()){

                    String key=FirebaseDatabase.getInstance().getReference("Wits Social Database").push().getKey();
                    comment comment=new comment(add_a_comment.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                    FirebaseDatabase.getInstance().getReference("Wits Social Database")
                            .child("Comments")
                            .child(finalId)
                            .child(key)
                            .setValue(comment)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Toast.makeText(Comment_Section.this, "Comment added.", Toast.LENGTH_SHORT).show();
                                    add_a_comment.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Comment_Section.this, "Error trying to upload comment.", Toast.LENGTH_SHORT).show();
                                }
                            });
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