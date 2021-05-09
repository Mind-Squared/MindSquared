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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreatedTestQuestions extends AppCompatActivity {

    Button btnSaveEditedTestQuestions;

    ImageButton btnBack;

    DatabaseReference reference;

    RecyclerView createdTestQuestionsRecycler;
    ArrayList<CreatedQuestions> list;
    CreatedQuestionAdapter createdQuestionAdapter;

    String userID;
    String test_id;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_test_questions);

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

        //////////////////////////////////////////////////////////////////
        //Atribuire

        test_id = getIntent().getStringExtra("test_id");

//        btnSaveEditedTestQuestions = findViewById(R.id.saveEditedTestQuestions_id);
        createdTestQuestionsRecycler = findViewById(R.id.createdTestQuestionsRecycler_id);
        btnBack = findViewById(R.id.btnBack_id);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreatedTestQuestions.this, TestOptions.class);
                intent.putExtra("test_id", test_id);
                startActivity(intent);

            }
        });

//        btnSaveEditedTestQuestions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Testul a fost editat cu succes!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(CreatedTestQuestions.this, TestOptions.class);
//                startActivity(intent);
//            }
//        });

        //data

        createdTestQuestionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CreatedQuestions>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

//        Log.d("testa", userID+"     "+test_id);
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id).child("Questions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot dataSnapshot1: datasnapshot.getChildren()){

                    CreatedQuestions p = dataSnapshot1.getValue(CreatedQuestions.class);
                    list.add(p);

                }

                createdQuestionAdapter = new CreatedQuestionAdapter(CreatedTestQuestions.this, list);
                createdTestQuestionsRecycler.setAdapter(createdQuestionAdapter);
                createdQuestionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}