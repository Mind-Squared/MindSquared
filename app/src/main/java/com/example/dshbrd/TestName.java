package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.Random;

public class TestName extends AppCompatActivity {

    Integer nrQuestions = 0;

    Integer randomId = new Random().nextInt();
    String testID = Integer.toString(randomId);

    EditText testName;
    EditText className;

    String nume_test;
    String nume_clasa;

    Button btnValidateTestName;
    ImageButton btnBack;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_name);

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

        ///////////////////////////////////////////////////////
        //Atribuiri

        className = findViewById(R.id.className_id);
        testName = findViewById(R.id.testName_id);

        btnValidateTestName = findViewById(R.id.validateTestName_id);
        btnBack = findViewById(R.id.btnBack_id);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestName.this, CreazaTest.class);
                startActivity(intent);

            }
        });

        btnValidateTestName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();

                reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+testID);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                        datasnapshot.getRef().child("titleTest").setValue(testName.getText().toString());
                        datasnapshot.getRef().child("clasaTest").setValue(className.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference questionsNodes = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test").child("Questions");
                final DatabaseReference toUsersQuestions = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+testID).child("Questions");
                DatabaseReference copyQuestionsNodes = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+testID).child("Questions");
                reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+testID);
                DatabaseReference copyReference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+testID);

                questionsNodes.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            nrQuestions = (int) dataSnapshot.getChildrenCount();
                        }
                        else{
                            nrQuestions = 0;
                        }

                        for(DataSnapshot questionCode : dataSnapshot.getChildren()){

                            String questionCodeKey = questionCode.getKey();
                            String answer_A = questionCode.child("answer_A").getValue(String.class);
                            String answer_B = questionCode.child("answer_B").getValue(String.class);
                            String answer_C = questionCode.child("answer_C").getValue(String.class);
                            String answer_D = questionCode.child("answer_D").getValue(String.class);
                            String answer_correct = questionCode.child("answer_correct").getValue(String.class);
                            String titleQuestion = questionCode.child("titleQuestion").getValue(String.class);
                            String question_id = questionCode.child("question_id").getValue(String.class);

                            toUsersQuestions.child(questionCodeKey).child("answer_A").setValue(answer_A);
                            toUsersQuestions.child(questionCodeKey).child("answer_B").setValue(answer_B);
                            toUsersQuestions.child(questionCodeKey).child("answer_C").setValue(answer_C);
                            toUsersQuestions.child(questionCodeKey).child("answer_D").setValue(answer_D);
                            toUsersQuestions.child(questionCodeKey).child("answer_correct").setValue(answer_correct);
                            toUsersQuestions.child(questionCodeKey).child("titleQuestion").setValue(titleQuestion);
                            toUsersQuestions.child(questionCodeKey).child("question_id").setValue(question_id);
                            toUsersQuestions.child(questionCodeKey).child("question_creator").setValue(userID);
                            toUsersQuestions.child(questionCodeKey).child("test_id").setValue(testID);
                            reference.child("test_id").setValue(testID);
                            reference.child("nrQuestions").setValue(nrQuestions.toString());
                            reference.child("testCreator").setValue(userID);

                            copyQuestionsNodes.child(questionCodeKey).child("answer_A").setValue(answer_A);
                            copyQuestionsNodes.child(questionCodeKey).child("answer_B").setValue(answer_B);
                            copyQuestionsNodes.child(questionCodeKey).child("answer_C").setValue(answer_C);
                            copyQuestionsNodes.child(questionCodeKey).child("answer_D").setValue(answer_D);
                            copyQuestionsNodes.child(questionCodeKey).child("answer_correct").setValue(answer_correct);
                            copyQuestionsNodes.child(questionCodeKey).child("titleQuestion").setValue(titleQuestion);
                            copyQuestionsNodes.child(questionCodeKey).child("question_id").setValue(question_id);
                            copyQuestionsNodes.child(questionCodeKey).child("question_creator").setValue(userID);
                            copyQuestionsNodes.child(questionCodeKey).child("test_id").setValue(testID);
                            copyReference.child("test_id").setValue(testID);
                            copyReference.child("nrQuestions").setValue(nrQuestions.toString());
                            copyReference.child("titleTest").setValue(testName.getText().toString());
                            copyReference.child("clasaTest").setValue(className.getText().toString());
                            copyReference.child("testCreator").setValue(userID);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                questionsNodes.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Intent intent = new Intent(TestName.this, Quizz_Profesor.class);
                            Toast.makeText(getApplicationContext(), "Testul Dvs a fost înregistrat cu succes!", Toast.LENGTH_LONG).show();
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}