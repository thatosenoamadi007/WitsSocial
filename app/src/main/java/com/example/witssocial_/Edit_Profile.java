package com.example.witssocial_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile extends AppCompatActivity {
    AppCompatImageView go_back_to_my_profile;
    AppCompatButton cancel_any_changes,save_profile_changes;
    EditText email,full_name,description,password;
    CircleImageView my_profile_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //initialize contents
        initializeValues();

        //go back to my profile or cancel changes
        goBack();

        //save changes
        saveChanges();

    }

    private void initializeValues() {
        String my_email=getIntent().getStringExtra("receiver_id");
        String my_full_name=getIntent().getStringExtra("receiver_username");
        String my_profile_description=getIntent().getStringExtra("receiver_description");
        email=findViewById(R.id.Email);
        full_name=findViewById(R.id.FullName);
        description=findViewById(R.id.Description);
        my_profile_pic=findViewById(R.id.my_profile_pic);
        password=findViewById(R.id.Password);
        email.setText(my_email);
        full_name.setText(my_full_name);
        //Toast.makeText(this, my_profile_description, Toast.LENGTH_SHORT).show();
        description.setText(my_profile_description);
        //password.setText(FirebaseAuth.getInstance().getCurrentUser().updatePassword());
    }

    private void saveChanges() {
        save_profile_changes=findViewById(R.id.save_profile_changes);
        save_profile_changes.setOnClickListener(view -> update());
    }

    private void update() {
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).updateEmail(email.getText().toString());
        if(!password.getText().toString().isEmpty()){
            FirebaseAuth.getInstance().getCurrentUser().updatePassword(password.getText().toString());
        }

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Wits Social Database").child("Users").child((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()));
        user_class User = new user_class(email.getText().toString(),full_name.getText().toString(),description.getText().toString());
        db.setValue(User)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Saved changes", Toast.LENGTH_SHORT).show());

    }

    private void goBack() {
        go_back_to_my_profile=findViewById(R.id.go_back_to_my_profile);
        cancel_any_changes=findViewById(R.id.cancel_any_changes);
        cancel_any_changes.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Profile.class)));
        go_back_to_my_profile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Profile.class)));
    }


}