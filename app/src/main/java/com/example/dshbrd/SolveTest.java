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
import android.widget.AbsListView;
import android.widget.Button;
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

public class SolveTest extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    DatabaseReference auxReference;
    private String userID;

    RecyclerView solveTestRecycler;
    ArrayList<Solve_Question> list;
    Solve_QuestionAdapter solve_questionAdapter;

    String testID;
    String okUser;

    String firstname;
    String lastname;
    String testCreator;

    Button btnSendTest;

    Integer ok = 0;
    Integer nrQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_test);

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

        //////////////////////////////////////////////////////////////

        testID = getIntent().getStringExtra("test_id");
        btnSendTest = findViewById(R.id.sendTest_id);

        solveTestRecycler = findViewById(R.id.solveTestRecycler_id);

        solveTestRecycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Solve_Question>();

        //data

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        auxReference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+testID);

        auxReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                testCreator = snapshot.child("testCreator").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        auxReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        auxReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                firstname = snapshot.child("firstname").getValue().toString();
                lastname = snapshot.child("lastname").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+testID).child("Questions");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot dataSnapshot1: datasnapshot.getChildren()){

                    Solve_Question p = dataSnapshot1.getValue(Solve_Question.class);
                    list.add(p);

                }

                solve_questionAdapter = new Solve_QuestionAdapter(SolveTest.this, list);
                solveTestRecycler.setAdapter(solve_questionAdapter);
                solve_questionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSendTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+testID).child("answers").child("Questions");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                    for(DataSnapshot dataSnapshot1: datasnapshot.getChildren()){

                        okUser = dataSnapshot1.child("ok").getValue().toString();

                        if(okUser.equals("1"))
                        {
                            ok++;
                            nrQuestions++;
                        }
                        else nrQuestions++;

                    }

                    auxReference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+testID).child("answers").child(userID);
                    auxReference.child("correct_questions_answered").setValue(ok.toString());

                    auxReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+testID);
                    auxReference.child("correct_questions_answered").setValue(ok.toString());

//                    auxReference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+testID);
//
//                    auxReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            testCreator = snapshot.child("testCreator").getValue().toString();
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

                    Log.d("testb", firstname+" "+lastname+testCreator);
//
                    auxReference = FirebaseDatabase.getInstance().getReference().child("users").child(testCreator).child("Tests").child("Test"+testID).child("answers").child(userID);
                    auxReference.child("correct_questions_answered").setValue(ok.toString());
                    auxReference.child("firstname").setValue(firstname);
                    auxReference.child("lastname").setValue(lastname);
                    auxReference.child("nrQuestions").setValue(nrQuestions);

                    Toast.makeText(getApplicationContext(), "Testul a fost predat cu succes!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SolveTest.this, Quizz_Elev.class);
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