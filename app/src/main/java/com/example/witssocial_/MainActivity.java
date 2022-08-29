package com.example.witssocial_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText username, password, confirmPass;
    TextView loginPage;
    Button signUpBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameSignup);
        password = findViewById(R.id.passwordSignUp);
        confirmPass = findViewById(R.id.confirmPasswordSignUp);
        loginPage = findViewById(R.id.goToLogin);
        signUpBtn = findViewById(R.id.signUpBtn);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    mAuth.createUserWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child((FirebaseAuth.getInstance().getCurrentUser().getUid()));
                                user User = new user(username.getText().toString());
                                Toast.makeText(MainActivity.this, username.getText().toString(), Toast.LENGTH_SHORT).show();
                                db.setValue(User);
                                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }

                        }

                        private void EditProfile(FirebaseUser user) {
                            mAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString());
                            startActivity(new Intent(MainActivity.this, login.class));
                        }
                    });
                    /*Toast.makeText(MainActivity.this, username.getText().toString() + " "+password.getText().toString(), Toast.LENGTH_SHORT).show();

                    mAuth.createUserWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users");
                    user User = new user(username.getText().toString());
                    Toast.makeText(MainActivity.this, username.getText().toString(), Toast.LENGTH_SHORT).show();
                    db.setValue(User);*/


                }
            }
        });

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, login.class));
            }
        });
    }
}