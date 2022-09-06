package com.example.witssocial_;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;

public class add_post extends AppCompatActivity {
    private ImageView addPost;
    private TextView caption;
    private Button postBtn;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private home_adapter mainAdapter;
    private RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        addPost = findViewById(R.id.choosePost);
        caption = findViewById(R.id.makecaption);
        storage = FirebaseStorage.getInstance();
        postBtn = findViewById(R.id.postBtn);
        storageRef=storage.getReference();

        bottomNavigationbar();

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPost();
            }

        });
    }

    private void selectPost() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction((intent.ACTION_GET_CONTENT));
        startActivityForResult(Intent.createChooser(intent,"SELECT POST"),10);
    }

    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_post);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
           if (item.getItemId() == R.id.posts_timeline) {
                Intent intent = new Intent (add_post.this, home_activity.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (add_post.this, Profile.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                //Intent intent = new Intent (add_post.this, add_post.class);
               // startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.chat) {
               Intent intent = new Intent (add_post.this, SearchUsers.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            addPost.setImageURI(imageUri);
            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPost();
                }
            });
        }else{
            Toast.makeText(add_post.this, "resultcode"+requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPost() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File is loading......");
        pd.show();

        Date currentTime = Calendar.getInstance().getTime();
        String time = currentTime.toString();
        final String userkey= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

            StorageReference galleryPictures = storageRef.child("Post/"+userkey).child(time);
            galleryPictures.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isComplete());
                            Uri uri =uriTask.getResult();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Post post = new Post(uri.toString(),caption.getText().toString(),user.getEmail());
                            FirebaseDatabase.getInstance().getReference("Posts")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                    .child(time)
                                    .setValue(post);
                            FirebaseDatabase.getInstance().getReference("All Posts")
                                    .child(time)
                                    .setValue(post);
                            Toast.makeText(add_post.this,"Picture uploaded", Toast.LENGTH_SHORT).show();

                        }
                    });


            

    }
}
