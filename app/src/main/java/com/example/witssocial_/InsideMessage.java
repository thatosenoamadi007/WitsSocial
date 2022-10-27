package com.example.witssocial_;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class InsideMessage extends AppCompatActivity {
    //variables used when activity loads
    AppCompatImageView go_back;
    AppCompatTextView show_friend_name;
    de.hdodenhof.circleimageview.CircleImageView show_friend_profile_image_insidemessage;

    //variables used when displaying messages
    RecyclerView recyclerView;
    insidemessageAdapter mainAdapter;

    //variables used when sending message
    AppCompatEditText typed_message;
    AppCompatImageView send_message,attachement_to_send_insidemessage;

    private Uri imageUri;
    private StorageReference storageRef;

    Dialog attachment_pop_up;
    String format="text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_message);
        attachment_pop_up=new Dialog(this);
        //initialize elements on the top bar or action bar
        initializeTopBarElement();

        //fetches messages from the firebase database and displays them
        display();

        //send message to the firebase database
        send_message();

    }

    private void send_message() {
        send_message= findViewById(R.id.send_message_InsideMessage);
        typed_message= findViewById(R.id.message_to_send_InsideMessage);

        send_message.setOnClickListener(view -> delegatedToSendMessage());
    }

    //delegating to send message

    void delegatedToSendMessage(){
        String message_content= Objects.requireNonNull(typed_message.getText()).toString();
        String name=getIntent().getStringExtra("receiver_id");
        String currentDateTime=get_CurrentDateTime();

        if(imageUri==null) {
            sendMessage(name, currentDateTime, message_content, "null");
        }else{
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("File is loading......");
            if(!typed_message.getText().toString().isEmpty()) {
                pd.show();
            }

            String time;
            time=currentDateTime.replaceAll("/", "|");
            final String userkey= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            StorageReference galleryPictures = FirebaseStorage.getInstance().getReference().child("Message_Images/" + userkey).child(time);
            galleryPictures.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();

                        sendMessage(name, currentDateTime, message_content, uri.toString());
                        Toast.makeText(this, "Message sent.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        imageUri=null;
                        format="text";
                    });

        }
    }

    // send messages to other users

    void sendMessage(String name,String currentDateTime,String message_content,String image_attachment){
        //add to the database that contains all the chat history
        messageObject messageObject=new messageObject(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(),name.toLowerCase(Locale.ROOT),message_content,currentDateTime,image_attachment,format);
        String branch1= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@","").replace(".","");
        String branch2=name.replace("@","").replace(".","");
        saveToChatHistory(currentDateTime,messageObject,branch1,branch2);
        saveToChatHistory(currentDateTime,messageObject,branch2,branch1);

        //add to the database that shows list of friends im chatting with and recent message sent to them
        likers user=new likers(name);
        sendToListOfUsers(user,branch1,branch2);

    }

    void saveToChatHistory(String currentDateTime, messageObject messageObject, String branch1, String branch2){
            FirebaseDatabase.getInstance().getReference()
                    .child("Wits Social Database1")
                .child("Chat history")
                .child(branch1)
                .child(branch2)
                .child(currentDateTime.replaceAll("/", "|"))
                .setValue(messageObject).addOnSuccessListener(unused -> typed_message.setText("")).addOnFailureListener(e -> Toast.makeText(InsideMessage.this, "Message not sent, try again.", Toast.LENGTH_SHORT).show());
    }

    void sendToListOfUsers(likers user, String branch1, String branch2){
        FirebaseDatabase.getInstance().getReference()
                .child("Wits Social Database1")
                .child("Archived Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child(branch2)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Wits Social Database1")
                                    .child("List of friends")
                                    .child(branch1)
                                    .child(branch2)
                                    .setValue(user).addOnSuccessListener(unused -> {

                                    }).addOnFailureListener(e -> Toast.makeText(InsideMessage.this, "Couldn't update latest messages on the database", Toast.LENGTH_SHORT).show());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    //get current date time

    String get_CurrentDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    void initializeTopBarElement(){
        attachement_to_send_insidemessage=findViewById(R.id.attachement_to_send_insidemessage);
        //attachement_to_send_insidemessage.setOnClickListener(view -> selectPost());
        attachement_to_send_insidemessage.setOnClickListener(view -> popUpWindow());
        //go back to previous activity
        go_back();


        //get friend name and display it
        String name=getIntent().getStringExtra("receiver_id");
        String friend_profile1=getIntent().getStringExtra("receiver_profile_pic");
        show_friend_name= findViewById(R.id.show_friend_name_insidemessage);
        show_friend_profile_image_insidemessage=findViewById(R.id.show_friend_profile_image_insidemessage);
        Glide.with(show_friend_profile_image_insidemessage.getContext())
                .load(friend_profile1)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_baseline_person_24)
                .into(show_friend_profile_image_insidemessage);
        show_friend_name.setText(name);
    }

    private void popUpWindow() {
        de.hdodenhof.circleimageview.CircleImageView image_attachment,document_attachment,audio_attachment,video_attachment;
        attachment_pop_up.setContentView(R.layout.attachment_type_popup);
        image_attachment=attachment_pop_up.findViewById(R.id.image_attachment);
        document_attachment=attachment_pop_up.findViewById(R.id.document_attachment);
        audio_attachment=attachment_pop_up.findViewById(R.id.audio_attachment);
        video_attachment=attachment_pop_up.findViewById(R.id.video_attachment);
        image_attachment.setOnClickListener(view -> {
            format="image";
            attachment_pop_up.dismiss();
            selectPost();
        });
        document_attachment.setOnClickListener(view -> {
            format="documents";
            attachment_pop_up.dismiss();
            selectPost();
        });
        audio_attachment.setOnClickListener(view -> {
            format="audio";
            attachment_pop_up.dismiss();
            selectPost();
        });
        video_attachment.setOnClickListener(view -> {
            format="video";
            attachment_pop_up.dismiss();
            selectPost();
        });
        attachment_pop_up.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        attachment_pop_up.show();
    }

    void go_back(){
        String friend_Email=getIntent().getStringExtra("receiver_id");
        String friend_Name=getIntent().getStringExtra("receiver_username");
        String friend_Description=getIntent().getStringExtra("receiver_description");
        String friend_profile=getIntent().getStringExtra("receiver_profile_pic");
        String came_from=getIntent().getStringExtra("came_from");
        go_back= findViewById(R.id.go_back_insidemessage);
        if(came_from.equals("Messages")){
            go_back.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Messages.class)));
        }
        else if(came_from.equals("Archive_Users")){
            go_back.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Archive_Users.class)));
        }
        else{
            go_back.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),a_FriendProfile.class).putExtra("receiver_id",friend_Email).putExtra("receiver_username",friend_Name).putExtra("receiver_description",friend_Description).putExtra("receiver_profile_pic",friend_profile)));
        }

    }

    private void display() {
        String name=getIntent().getStringExtra("receiver_id");
        recyclerView = findViewById(R.id.messages_insidemessage);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter = new insidemessageAdapter(new FirebaseRecyclerOptions.Builder<messageObject>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Wits Social Database1").child("Chat history").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replaceAll("@","").replace(".","")).child(name.replace("@","").replace(".","")), messageObject.class)
                .build(),getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    private void selectPost() {
        Intent intent = new Intent();
        //intent.setType("*/*");
        if(format.equals("image")){
            intent.setType("image/*");
        }
        else if(format.equals("documents")){
            intent.setType("application/pdf");
        }
        else if(format.equals("audio")){
            intent.setType("audio/*");
        }
        //else(format.equals("video")){
        else{
            intent.setType("video/*");
        }
        final Intent intent1 = intent.setAction((Intent.ACTION_GET_CONTENT));
        startActivityForResult(Intent.createChooser(intent1,"Add Attachment"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
        }else{
            format="text";
            Toast.makeText(InsideMessage.this, "Error trying to pick attachment.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }

}
