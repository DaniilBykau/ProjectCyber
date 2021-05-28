package com.example.projectcyber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemRecycleActivity extends AppCompatActivity {


    private TextView nameItem, passwordItem;
    private Button changeStatusButton;
    private String parentBD = "users";

    private String name = "";
    private String role = "";
    private String phone = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recycle);

        nameItem = findViewById(R.id.item_name_changer);
        passwordItem = findViewById(R.id.item_password_changer);



        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            role = extras.getString("role");
            name = extras.getString("name");
            phone = extras.getString("phone");
            password = extras.getString("password");
        }

        nameItem.setText(name);
        passwordItem.setText(password);
        changeStatusButton = findViewById(R.id.changeStatusButton);

        String finalName = name;
        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference rootRef;
                rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        FirebaseDatabase.getInstance().getReference()
                                .child("users")
                                .child(phone)
                                .child("role")
                                .setValue("admin");

                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
            }
        });
    }
}