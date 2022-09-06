package com.example.witssocial_;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public int add(int num1, int num2){
        return num1 + num2;
    }
}