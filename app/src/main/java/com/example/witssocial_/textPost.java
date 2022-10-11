package com.example.witssocial_;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class textPost extends AppCompatActivity {

    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_post);
        EditText editText = findViewById(R.id.textPostEdit);
        Button btnTxt = findViewById(R.id.btnTextPost);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef= storage.getReference();


        btnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date currentTime = Calendar.getInstance().getTime();
                String time = currentTime.toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Post post = new Post(editText.getText().toString(),"",user.getEmail());
                        /*FirebaseDatabase.getInstance().getReference("Posts")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(time)
                                .setValue(post);
                        FirebaseDatabase.getInstance().getReference("All Posts")
                                .child(time)
                                .setValue(post);*/
                FirebaseDatabase.getInstance().getReference("Wits Social Database")
                        .child("textPosts")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@","").replace(".",""))
                        .child(time)
                        .setValue(post);
                FirebaseDatabase.getInstance().getReference("Wits Social Database")
                        .child("All Text Posts")
                        .child(time)
                        .setValue(post);
                Toast.makeText(textPost.this,"Text uploaded", Toast.LENGTH_SHORT).show();

            }
        });

    }
}