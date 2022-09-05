package com.example.witssocial_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsideMessage extends AppCompatActivity {
    //variables used when activity loads
    LinearLayout go_back;
    de.hdodenhof.circleimageview.CircleImageView go_profile;
    TextView show_friend_name;

    //variables used when displaying messages
    RecyclerView recyclerView;
    insidemessageAdapter mainAdapter;

    //variables used when sending message
    EditText typed_message;
    ImageView send_message;

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
        send_message=(ImageView) findViewById(R.id.send_message_InsideMessage);
        typed_message=(EditText) findViewById(R.id.message_to_send_InsideMessage);

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegatedToSendMessage();

            }
        });
    }

    void delegatedToSendMessage(){
        String message_content=typed_message.getText().toString();
        String name=getIntent().getStringExtra("receiver_id");
        String currentDateTime=get_CurrentDateTime();

        //add to the database that conatins all the chat history
        messageObject messageObject=new messageObject(FirebaseAuth.getInstance().getCurrentUser().getEmail(),name.toLowerCase(Locale.ROOT),message_content,currentDateTime);
        String branch1=FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace("@","").replace(".","");
        String branch2=name.replace("@","").replace(".","");
        saveToChatHistory(name,currentDateTime,messageObject,branch1,branch2);
        saveToChatHistory(name,currentDateTime,messageObject,branch2,branch1);

        //add to the database that shows list of friends im chatting with and recent message sent to them
        String branch3=FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace("@","").replace(".","");
        String branch4=name.replace("@","").replace(".","");
        user user=new user(name);
        sendToListOfUsers(name,user,branch1,branch2);
        user=new user(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        sendToListOfUsers(name,user,branch2,branch1);

    }

    void saveToChatHistory(String name,String currentDateTime,messageObject messageObject,String branch1,String branch2){
        Task<Void> firebaseDatabase=FirebaseDatabase.getInstance().getReference()
                .child("Chat history")
                .child(branch1)
                .child(branch2)
                .child(currentDateTime.replaceAll("/","|"))
                .setValue(messageObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        typed_message.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsideMessage.this, "Message not sent, try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void sendToListOfUsers(String name,user user,String branch1,String branch2){
        Task<Void> firebaseDatabase2=FirebaseDatabase.getInstance().getReference()
                .child("List of friends")
                .child(branch1)
                .child(branch2)
                .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsideMessage.this, "Couldn't update latest messages on the database", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    String get_CurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    void initializeTopBarElement(){
        //go back to previous activity
        go_back();

        //go to friend profile
        //go_profile();

        //get friend name and display it
        String name=getIntent().getStringExtra("receiver_id");
        //String name="dummy@gmail.com";
        show_friend_name=(TextView) findViewById(R.id.show_friend_name_insidemessage);
        show_friend_name.setText(name);
    }

    void go_back(){
        go_back=(LinearLayout) findViewById(R.id.go_back_insidemessage);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SearchUsers.class));
            }
        });

    }

    private void display() {
        String name=getIntent().getStringExtra("receiver_id");
        //String name="dummygmailcom";
        recyclerView = (RecyclerView)findViewById(R.id.messages_insidemessage);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<messageObject> options =
                new FirebaseRecyclerOptions.Builder<messageObject>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Chat history").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("@","").replace(".","")).child(name.replace("@","").replace(".","")), messageObject.class)
                        .build();
        mainAdapter = new insidemessageAdapter(options,getApplicationContext());
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
        //mainAdapter.stopListening();
    }

}
