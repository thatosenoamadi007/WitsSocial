package com.example.witssocial_;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText username, password;
    Button logIn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.logInPass);
        logIn = findViewById(R.id.logInBtn);
        mAuth = FirebaseAuth.getInstance();


        logIn.setOnClickListener(view -> mAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(login.this, SearchUsers.class));
            }
        }).addOnFailureListener(e -> Toast.makeText(login.this, "Failed", Toast.LENGTH_SHORT).show()));
    }
}