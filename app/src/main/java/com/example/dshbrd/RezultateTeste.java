package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RezultateTeste extends AppCompatActivity {

    String test_id;
    String classTest;
    String titleTest;

    ImageButton btnBack;

    private FirebaseUser user;
    private DatabaseReference reference;
    DatabaseReference auxReference;
    private String userID;

    RecyclerView solvedTestsRecycler;
    ArrayList<RezultatUser> list;
    RezultatUser_Adapter rezultatUser_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultate_teste);

        ///////////////////////////////////////////////////////////
        //NAVIGARE PRIN BOTTOM MENU///////////////////////////////

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewHome);

        bottomNavigationView.setSelectedItemId(R.id.menuHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuDoing:
                        startActivity(new Intent(getApplicationContext(), MyDoes.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.menuDone:
                        startActivity(new Intent(getApplicationContext(), DoneActivities.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        ////////////////////////////////////////////

        test_id = getIntent().getStringExtra("test_id");
        classTest = getIntent().getStringExtra("classTest");
        titleTest = getIntent().getStringExtra("titleTest");

        btnBack = findViewById(R.id.btnBack_id);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RezultateTeste.this, TestOptions.class);
                intent.putExtra("titleTest", titleTest);
                intent.putExtra("classTest", classTest);
                intent.putExtra("test_id", test_id);
                startActivity(intent);
            }
        });

        solvedTestsRecycler = findViewById(R.id.solvedTestsRecycler_id);
        solvedTestsRecycler.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<RezultatUser>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Log.d("testc", userID+" "+test_id);
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id).child("answers");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){

                    RezultatUser p = dataSnapshot1.getValue(RezultatUser.class);
                    list.add(p);

                }

                rezultatUser_adapter = new RezultatUser_Adapter(RezultateTeste.this, list);
                solvedTestsRecycler.setAdapter(rezultatUser_adapter);
                rezultatUser_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}