package com.example.witssocial_;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class InsideMessage extends AppCompatActivity {
    //variables used when activity loads
    AppCompatImageView go_back;
    AppCompatTextView show_friend_name;

    //variables used when displaying messages
    RecyclerView recyclerView;
    insidemessageAdapter mainAdapter;

    //variables used when sending message
    AppCompatEditText typed_message;
    AppCompatImageView send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_message);

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

    void delegatedToSendMessage(){
        String message_content= Objects.requireNonNull(typed_message.getText()).toString();
        String name=getIntent().getStringExtra("receiver_id");
        String currentDateTime=get_CurrentDateTime();

        //add to the database that contains all the chat history
        messageObject messageObject=new messageObject(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(),name.toLowerCase(Locale.ROOT),message_content,currentDateTime);
        String branch1= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()).replace("@","").replace(".","");
        String branch2=name.replace("@","").replace(".","");
        saveToChatHistory(currentDateTime,messageObject,branch1,branch2);
        saveToChatHistory(currentDateTime,messageObject,branch2,branch1);

        //add to the database that shows list of friends im chatting with and recent message sent to them
        //user user=new user(name);
        //sendToListOfUsers(user,branch1,branch2);
        //user=new user(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //sendToListOfUsers(user,branch2,branch1);

    }

    void saveToChatHistory(String currentDateTime, messageObject messageObject, String branch1, String branch2){
            FirebaseDatabase.getInstance().getReference()
                .child("Chat history")
                .child(branch1)
                .child(branch2)
                .child(currentDateTime.replaceAll("/", "|"))
                .setValue(messageObject).addOnSuccessListener(unused -> typed_message.setText("")).addOnFailureListener(e -> Toast.makeText(InsideMessage.this, "Message not sent, try again.", Toast.LENGTH_SHORT).show());
    }

   /* void sendToListOfUsers(user user, String branch1, String branch2){
        FirebaseDatabase.getInstance().getReference()
                .child("List of friends")
                .child(branch1)
                .child(branch2)
                .setValue(user).addOnSuccessListener(unused -> {

                }).addOnFailureListener(e -> Toast.makeText(InsideMessage.this, "Couldn't update latest messages on the database", Toast.LENGTH_SHORT).show());
    }*/

    //convert current time to correcto format
    String get_CurrentDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    void initializeTopBarElement(){
        //go back to previous activity
        go_back();


        //get friend name and display it
        String name=getIntent().getStringExtra("receiver_id");
        //String name="dummy@gmail.com";
        show_friend_name= findViewById(R.id.show_friend_name_insidemessage);
        show_friend_name.setText(name);
    }

    //button to go back to profile
    void go_back(){
        go_back= findViewById(R.id.go_back_insidemessage);
        go_back.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SearchUsers.class)));

    }

    //display all the messages
    private void display() {
        String name=getIntent().getStringExtra("receiver_id");
        //String name="mammogram";
        recyclerView = findViewById(R.id.messages_insidemessage);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter = new insidemessageAdapter(new FirebaseRecyclerOptions.Builder<messageObject>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Chat history").child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).replaceAll("@","").replace(".","")).child(name.replace("@","").replace(".","")), messageObject.class)
                .build(),getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

}
