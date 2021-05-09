package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateQuestion extends AppCompatActivity {

    EditText titleQuestion;
    EditText answer_A;
    EditText answer_B;
    EditText answer_C;
    EditText answer_D;
    EditText answer_correct;

    String userID;
    FirebaseUser user;

    Integer randomId = new Random().nextInt();
    String questionID = Integer.toString(randomId);

    Button btnSaveQuestion;
    Button btnCancelQuestion;

    DatabaseReference reference;

    Integer nrQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

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

        ///////////////////////////////////////////////////////////

        //Spinner

        List<String> states = Arrays.asList("A", "B", "C", "D");

        final Spinner spinner = findViewById(R.id.spinner_id);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //Atribuiri



        titleQuestion = findViewById(R.id.titleQuestion);
        answer_A = findViewById(R.id.answer_A_id);
        answer_B = findViewById(R.id.answer_B_id);
        answer_C = findViewById(R.id.answer_C_id);
        answer_D = findViewById(R.id.answer_D_id);

        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);
        btnCancelQuestion = findViewById(R.id.btnCancelQuestion);

        btnCancelQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateQuestion.this, CreazaTest.class);
                startActivity(intent);
            }
        });

        btnSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();

                reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+userID).child("Questions");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.exists()){
                            nrQuestions = (int) datasnapshot.getChildrenCount();
                        }
                        else
                        {
                            nrQuestions = 0;
                        }

                        reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+userID).child("Questions").child("Question"+questionID);

                        reference.child("titleQuestion").setValue(titleQuestion.getText().toString());
                        reference.child("answer_A").setValue(answer_A.getText().toString());
                        reference.getRef().child("answer_B").setValue(answer_B.getText().toString());
                        reference.getRef().child("answer_C").setValue(answer_C.getText().toString());
                        reference.getRef().child("answer_D").setValue(answer_D.getText().toString());
                        reference.getRef().child("answer_correct").setValue(spinner.getSelectedItem().toString());
                        reference.getRef().child("serial_number").setValue(nrQuestions.toString());
                        reference.getRef().child("question_id").setValue(questionID);

                        Intent intent = new Intent(CreateQuestion.this, CreazaTest.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}