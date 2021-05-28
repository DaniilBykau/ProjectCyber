package com.example.projectcyber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonSignUp;
    private EditText phoneEditText, passwordEditText, nameEditText, confirmPasswordEditText, roleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp = findViewById(R.id.button_signUp);
        phoneEditText = findViewById(R.id.registerPhoneEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.registerConfirmPasswordEditText);
        nameEditText = findViewById(R.id.registerNameEditText);
        roleEditText = findViewById(R.id.registerRoleEditText);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });
    }

    private void createNewUser(){
        String name = nameEditText.getText().toString();
        String phoneNumber = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirm_password = confirmPasswordEditText.getText().toString();
        String role = roleEditText.getText().toString();

        if(TextUtils.isEmpty(role)){
            Toast.makeText(this, "Role cannot be blank", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "Phone number cannot be blank", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(confirm_password)){
            Toast.makeText(this, "Confirm password cannot be blank", Toast.LENGTH_SHORT).show();
        }

        else if(!password.equals(confirm_password)){
            Toast.makeText(this, "passwords mismatch", Toast.LENGTH_SHORT).show();
        }
        else{
            validateDetails(name, password, phoneNumber, role);
        }
    }

    private void validateDetails(final String name, final String password, final String phoneNumber, final String role){

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(!snapshot.child("users").child(phoneNumber).exists()){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phoneNumber);
                    userDataMap.put("name", name);
                    userDataMap.put("password", password);
                    userDataMap.put("role", role);

                    rootRef.child("users").child(phoneNumber).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUpActivity.this, "account created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
                else{
                    Toast.makeText(SignUpActivity.this,"dep already exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }
}