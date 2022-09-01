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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        addPost = findViewById(R.id.choosePost);
        caption = findViewById(R.id.caption);
        storage = FirebaseStorage.getInstance();
        postBtn = findViewById(R.id.postBtn);
        storageRef=storage.getReference();

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
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()),Post.class)
                .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new home_adapter(options);
        recyclerView.setAdapter(mainAdapter);
            

    }
}