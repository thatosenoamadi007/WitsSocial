package com.example.witssocial_;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    
    //get all views in main activity 
    EditText username, password, confirmPass;
    TextView loginPage;
    Button signUpBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //initilise views in activity
        username = findViewById(R.id.usernameSignup);
        password = findViewById(R.id.passwordSignUp);
        confirmPass = findViewById(R.id.confirmPasswordSignUp);
        loginPage = findViewById(R.id.goToLogin);
        signUpBtn = findViewById(R.id.signUpBtn);
        mAuth = FirebaseAuth.getInstance();

        
        //onClick listener for sign up button
        signUpBtn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(username.getText().toString())){
                Toast.makeText(MainActivity.this, "Username cannot be empty",
                        Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(MainActivity.this, "Username cannot be empty",
                        Toast.LENGTH_SHORT).show();
            }else if(!TextUtils.equals(password.getText().toString(),confirmPass.getText().toString())){
                Toast.makeText(MainActivity.this, "Passwords don't match",
                        Toast.LENGTH_SHORT).show();
            }else{
                //if all is well create user with email and password and save user to database
                mAuth.createUserWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
                        user User = new user(username.getText().toString());
                        Toast.makeText(MainActivity.this, username.getText().toString(), Toast.LENGTH_SHORT).show();
                        db.setValue(User);
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                });



            }
        });
        //go to login page
        loginPage.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, login.class)));
    }
}
