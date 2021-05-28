package com.example.projectcyber;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText loginPasswordEditText, loginPhoneEditText;
    private String parentBD = "users";
    private TextView signUpLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        loginPasswordEditText = findViewById(R.id.login_password);
        loginPhoneEditText = findViewById(R.id.login_phone);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = loginPhoneEditText.getText().toString();
                String password  = loginPasswordEditText.getText().toString();
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "password can not be blank", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(LoginActivity.this, "phone number can not be blank", Toast.LENGTH_SHORT).show();
                }else{
                    validateDetails(phoneNumber, password);
                }
            }
        });
    }

    private void validateDetails(String phoneNumber, String password){
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.child(parentBD).child(phoneNumber).exists()){
                    Users userdata = snapshot.child(parentBD).child(phoneNumber).getValue(Users.class);

                    if(userdata.getPassword().equals(password)){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        if (userdata.getRole().equals("admin")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "this phone number does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

}