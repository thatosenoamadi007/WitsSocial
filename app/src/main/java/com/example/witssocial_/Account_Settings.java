package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Account_Settings extends AppCompatActivity {
    AppCompatButton sign_out;
    AppCompatImageView go_back_to_my_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //go back to my profile
        goBack();

        //sign out of my account
        sign_out=findViewById(R.id.SignOut);
        sign_out.setOnClickListener(view -> {signOut();});



    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Account_Settings.this,login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    //back to my profile

    private void goBack() {
        go_back_to_my_profile=findViewById(R.id.go_back_to_my_profile);
        go_back_to_my_profile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Profile.class)));
    }
}