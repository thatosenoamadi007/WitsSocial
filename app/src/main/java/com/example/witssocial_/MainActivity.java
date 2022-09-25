package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
    EditText full_name, email, password, confirmPassword;
    TextView loginPage;
    Button signUpBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //initilise views in activity
        initialiseVariables();
        //onClick listener for sign up button
        signUpBtn.setOnClickListener(view -> {
            //check for errors.
            if(!Objects.equals(checkErrors(full_name.getText().toString(), email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString()), "true")){
                Toast.makeText(MainActivity.this,checkErrors(full_name.getText().toString(), email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString()) ,
                        Toast.LENGTH_SHORT).show();
            }
            else{
                //if all is well create user with email and password and save user to database
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            //DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
                            //user User = new user(email.getText().toString());
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").child((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
                            user_class User = new user_class(email.getText().toString(),full_name.getText().toString(),"");
                            db.setValue(User);
                            Toast.makeText(MainActivity.this, "Account Registered.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,login.class));
                        }

                     }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
                 }
        });

        //go to login page
        loginPage.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, login.class)));
    }

    private void initialiseVariables() {
        full_name = findViewById(R.id.FullName);
        email=findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.confirmPassword);
        loginPage = findViewById(R.id.SignIn);
        signUpBtn = findViewById(R.id.SignUp);
        mAuth = FirebaseAuth.getInstance();
    }

    public static String checkErrors(String full_name,String email,String password,String confirmPassword){
        if(full_name.isEmpty()) return "Please enter your full name.";
        else if(email.isEmpty()) return "Email address format is invalid.";
        else if(password.isEmpty()) return "Please enter your password.";
        else if(!Objects.equals(password, confirmPassword)) return "Passwords don't match.";
        else if(password.length()<6) return "The given password is invalid. [Password should be at least 6 characters]";
        return "true";
    }

}

