package com.example.witssocial_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class add_post extends AppCompatActivity {
    private ImageView addPost;
    private TextView caption;
    private Button postBtn;
    private Uri imageUri;
    private StorageReference storageRef;
    BottomNavigationView bottomNavigationView;
    private String determine_type_of_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        addPost = findViewById(R.id.choosePost);
        caption = findViewById(R.id.makecaption);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        postBtn = findViewById(R.id.postBtn);
        storageRef= storage.getReference();

        bottomNavigationbar();
        postBtn.setOnClickListener(view -> uploadPost());
        addPost.setOnClickListener(view -> selectPost());
    }

    private void selectPost() {
        Intent intent = new Intent();
        intent.setType("*/*");
        final Intent intent1 = intent.setAction((Intent.ACTION_GET_CONTENT));
        startActivityForResult(Intent.createChooser(intent1,"SELECT POST"),10);
    }

    private void bottomNavigationbar() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_post);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
           if (item.getItemId() == R.id.posts_timeline) {
                Intent intent = new Intent (add_post.this, home_activity.class);
                startActivity(intent);
               overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                return true;
            }

            if (item.getItemId() == R.id.chat_list) {
                Intent intent = new Intent (add_post.this, Conversations.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.account) {
                Intent intent = new Intent (add_post.this, Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.add_post) {
                return true;
            }
            if (item.getItemId() == R.id.searchbtn) {
               Intent intent = new Intent (add_post.this, SearchUsers.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                return true;
            }
            if (item.getItemId() == R.id.messages) {
                Intent intent = new Intent (add_post.this, Messages.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
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
            //postBtn.setOnClickListener(view -> uploadPost());
        }else{
            Toast.makeText(add_post.this, "resultcode"+requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPost() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File is loading......");
        if(!caption.getText().toString().isEmpty()) {
            pd.show();
        }

        String time;
        String currentDateTime=get_CurrentDateTime();
        time=currentDateTime.replaceAll("/", "|");
        final String userkey= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        if(imageUri!=null) {

            StorageReference galleryPictures = storageRef.child("Post/" + userkey).child(time);
            galleryPictures.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();

                        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        String key = database.getReference("All Posts").push().getKey();
                        //Post post = new Post(uri.toString(),caption.getText().toString(),user.getEmail());
                        Post post = new Post(uri.toString(), caption.getText().toString(), user.getUid(), key, "image_caption");*/
                        /*FirebaseDatabase.getInstance().getReference("Wits Social Database")
                                .child("Posts")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@", "").replace(".", ""))
                                .child(time)
                                .setValue(post);
                        FirebaseDatabase.getInstance().getReference("Wits Social Database")
                                .child("All Posts")
                                .child(time)
                                .setValue(post);*/
                        uploadWholePost(uri.toString(),time,"media_post");
                        Toast.makeText(this, "Post Uploaded.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        caption.setText("");
                        imageUri=null;

                        String tmpuri = "@drawable/icupload";
                        int imageResource = getResources().getIdentifier(tmpuri, null, getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        addPost.setImageDrawable(res);
                    });
        }else {
            if(!caption.getText().toString().isEmpty()){
                uploadWholePost("null",time,"text_post");
                Toast.makeText(this, "Post Uploaded.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                caption.setText("");
            }else{
                Toast.makeText(this, "Cannot upload empty post.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadWholePost(String uri,String time,String type) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("All Posts").push().getKey();
        assert user != null;
        Post post = new Post(uri, caption.getText().toString(), user.getUid(), key, type);
        FirebaseDatabase.getInstance().getReference("Wits Social Database1")
                .child("Posts")
                .child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replace("@", "").replace(".", ""))
                .child(time)
                .setValue(post);
        FirebaseDatabase.getInstance().getReference("Wits Social Database1")
                .child("All Posts")
                .child(time)
                .setValue(post);
    }

    String get_CurrentDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }


}
