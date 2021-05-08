package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class EditQuestion extends AppCompatActivity {

    EditText titleQuestion;
    EditText answer_A;
    EditText answer_B;
    EditText answer_C;
    EditText answer_D;

    String serial_number;
    String question_id;
    String answerCorrect;

    Button btnSaveUpdateQuestion;
    Button btnDeleteQuestion;

    ImageButton btnBack;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

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

        ////////////////////////////////////////////////////////////

        //Spinner

        List<String> states = Arrays.asList("A", "B", "C", "D");

        final Spinner spinner = findViewById(R.id.spinner_id);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //Atribuiri

        btnBack = findViewById(R.id.btnBack_id);

        titleQuestion = findViewById(R.id.editTitleQuestion_id);
        answer_A = findViewById(R.id.editAnswer_A_id);
        answer_B = findViewById(R.id.editAnswer_B_id);
        answer_C = findViewById(R.id.editAnswer_C_id);
        answer_D = findViewById(R.id.editAnswer_D_id);
//        answerCorrect = findViewById(R.id.editAnswer_correct_id);

        btnSaveUpdateQuestion = findViewById(R.id.btnSaveUpdateQuestion);
        btnDeleteQuestion = findViewById(R.id.btnDeleteQuestion);

        titleQuestion.setText(getIntent().getStringExtra("titleQuestion"));
        answer_A.setText(getIntent().getStringExtra("answer_A"));
        answer_B.setText(getIntent().getStringExtra("answer_B"));
        answer_C.setText(getIntent().getStringExtra("answer_C"));
        answer_D.setText(getIntent().getStringExtra("answer_D"));
        answerCorrect = (getIntent().getStringExtra("answerCorrect"));

        if(answerCorrect.equals("A"))
            spinner.setSelection(adapter.getPosition("A"));
        else if(answerCorrect.equals("B"))
            spinner.setSelection(adapter.getPosition("B"));
        else if(answerCorrect.equals("C"))
            spinner.setSelection(adapter.getPosition("C"));
        else if(answerCorrect.equals("D"))
            spinner.setSelection(adapter.getPosition("D"));

        serial_number = getIntent().getStringExtra("serial_number");
        question_id = getIntent().getStringExtra("question_id");

        reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test").child("Questions").child("Question"+question_id);

        btnDeleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(EditQuestion.this, CreazaTest.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditQuestion.this, CreazaTest.class);
                startActivity(intent);
            }
        });

        btnSaveUpdateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                        datasnapshot.getRef().child("titleQuestion").setValue(titleQuestion.getText().toString());
                        datasnapshot.getRef().child("answer_A").setValue(answer_A.getText().toString());
                        datasnapshot.getRef().child("answer_B").setValue(answer_B.getText().toString());
                        datasnapshot.getRef().child("answer_C").setValue(answer_C.getText().toString());
                        datasnapshot.getRef().child("answer_D").setValue(answer_D.getText().toString());
                        datasnapshot.getRef().child("answer_correct").setValue(spinner.getSelectedItem().toString());

                        Intent intent = new Intent(EditQuestion.this, CreazaTest.class);
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