package com.example.witssocial_;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    //find all views in login page
    EditText email, password;
    TextView signupBtn;
    Button logIn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        //initialize all views in activity
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        logIn = findViewById(R.id.SignIn);
        signupBtn = findViewById(R.id.SignUp);
        mAuth = FirebaseAuth.getInstance();

        //onClick listener for login button to sign in user with email and password
        logIn.setOnClickListener(view -> mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(login.this, home_activity.class).putExtra("sign_out_or_not","yes"));
            }
        }).addOnFailureListener(e -> Toast.makeText(login.this, e.toString(), Toast.LENGTH_SHORT).show()));

        signupBtn.setOnClickListener(view -> {startActivity(new Intent(login.this,MainActivity.class));});
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
