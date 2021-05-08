package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChoiceAnswer extends AppCompatActivity {

    TextView solveTitleQuestion;

    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton_A;
    RadioButton radioButton_B;
    RadioButton radioButton_C;
    RadioButton radioButton_D;

    Button btnApply;

    String answer_A;
    String answer_B;
    String answer_C;
    String answer_D;
    String answer_correct;
    String test_id;
    String titleQuestion;
    String testCreator;
    String questionId;
    String titleTest;
    String nrQuestions;

    String firstname;
    String lastname;

    private Integer ok = 0;

    private FirebaseUser user;
    private DatabaseReference reference;
    DatabaseReference auxReference;
    private String userID;

    String correctLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_answer);

        answer_A = getIntent().getStringExtra("answer_A");
        answer_B = getIntent().getStringExtra("answer_B");
        answer_C = getIntent().getStringExtra("answer_C");
        answer_D = getIntent().getStringExtra("answer_D");
        titleQuestion = getIntent().getStringExtra("titleQuestion");
        testCreator = getIntent().getStringExtra("testCreator");
        questionId = getIntent().getStringExtra("question_id");
        test_id = getIntent().getStringExtra("test_id");
        answer_correct = getIntent().getStringExtra("answer_correct");

        solveTitleQuestion = findViewById(R.id.solveTitleQuestion_id);

        radioButton_A = findViewById(R.id.radio1_id);
        radioButton_B = findViewById(R.id.radio2_id);
        radioButton_C = findViewById(R.id.radio3_id);
        radioButton_D = findViewById(R.id.radio4_id);
        radioGroup = findViewById(R.id.radioGroup_id);
        btnApply = findViewById(R.id.apply_id);

        radioButton_A.setText(answer_A);
        radioButton_B.setText(answer_B);
        radioButton_C.setText(answer_C);
        radioButton_D.setText(answer_D);

        solveTitleQuestion.setText(titleQuestion);

        //data

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+test_id);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                nrQuestions = datasnapshot.child("nrQuestions").getValue().toString();
                titleTest = datasnapshot.child("titleTest").getValue().toString();
                Log.d("testa", nrQuestions);

                auxReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id);
                auxReference.child("titleTest").setValue(titleTest);
                auxReference.child("nrQuestions").setValue(nrQuestions);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Log.d("testa", testCreator);


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                if(radioId == R.id.radio1_id)
                    correctLetter = "A";
                else if(radioId == R.id.radio2_id)
                    correctLetter = "B";
                else if(radioId == R.id.radio3_id)
                    correctLetter = "C";
                else if(radioId == R.id.radio4_id)
                    correctLetter = "D";

                radioButton.setChecked(true);

                if(correctLetter.equals(answer_correct))
                    ok = 1;
                else ok = 0;


                reference = FirebaseDatabase.getInstance().getReference().child("users").child(testCreator).child("Tests").child("Test"+test_id).child("answers").child(userID);
                reference.child("Questions").child("Question"+questionId).child("user_answer").setValue(correctLetter);
                reference.child("Questions").child("Question"+questionId).child("ok").setValue(ok.toString());
                reference.child("Questions").child("Question"+questionId).child("answer_correct").setValue(answer_correct);
                reference.child("Questions").child("Question"+questionId).child("titleQuestion").setValue(titleQuestion);

//                reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+test_id).child("answers").child(userID).child("Questions");
//                reference.child("Question"+questionId).setValue(correctLetter);

                reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id).child("answers");
                reference.child("Questions").child("Question"+questionId).child("user_answer").setValue(correctLetter);
                reference.child("Questions").child("Question"+questionId).child("ok").setValue(ok.toString());
                reference.child("Questions").child("Question"+questionId).child("answer_correct").setValue(answer_correct);
                reference.child("Questions").child("Question"+questionId).child("titleQuestion").setValue(titleQuestion);

                reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+test_id).child("answers").child(userID).child("Questions");
                reference.child("Question"+questionId).child("user_answer").setValue(correctLetter);
                reference.child("Question"+questionId).child("ok").setValue(ok.toString());

                Intent intent = new Intent(ChoiceAnswer.this, SolveTest.class);
                intent.putExtra("test_id", test_id);

                startActivity(intent);

            }
        });

    }

    public void checkButton(View v){

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        Toast.makeText(this, "Ai selectat:"+radioButton.getText(), Toast.LENGTH_SHORT).show();

    }

}