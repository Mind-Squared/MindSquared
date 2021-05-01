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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class CreazaTest extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////
    //DECLARATII

    Button addQuestion;
    Button validateTest;

    Integer randomId = new Random().nextInt();
    String testID = Integer.toString(randomId);

    DatabaseReference reference;

    RecyclerView questionRecycler;
    ArrayList<Questions> list;
    QuestionAdapter questionAdapter;

    String userID;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creaza_test);

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


        addQuestion = findViewById(R.id.addQuestion);
        validateTest = findViewById(R.id.validateTest_id);
        questionRecycler = findViewById(R.id.questionRecycler_id);

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreazaTest.this, CreateQuestion.class);
                startActivity(intent);
            }
        });

        //working with data

        questionRecycler.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Questions>();

        //get data from database

        reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test").child("Questions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot dataSnapshot1: datasnapshot.getChildren()){
                    Questions p = dataSnapshot1.getValue(Questions.class);
                    list.add(p);
                }

                questionAdapter = new QuestionAdapter(CreazaTest.this, list);
                questionRecycler.setAdapter(questionAdapter);
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        validateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreazaTest.this, TestName.class);
                startActivity(intent);

//                questionsNodes.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if(task.isSuccessful()){
//
//                            Intent intent = new Intent(CreazaTest.this, TestName.class);
////                            Toast.makeText(getApplicationContext(), "Testul Dvs a fost înregistrat cu succes!", Toast.LENGTH_LONG).show();
//                            startActivity(intent);
//
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

            }
        });





    }
}