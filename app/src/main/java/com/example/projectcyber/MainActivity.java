package com.example.projectcyber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    List<Users> usersData;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.infoRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersData = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        helperAdapter= new HelperAdapter(this, usersData);
        recyclerView.setAdapter(helperAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    usersData.add(user) ;
                }
                helperAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        helperAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        helperAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onPause() {
        super.onPause();
        helperAdapter.notifyDataSetChanged();
        usersData.clear();
    }
}