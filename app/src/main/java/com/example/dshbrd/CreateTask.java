package com.example.dshbrd;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.IntArrayEvaluator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateTask extends AppCompatActivity {

    public String numeClasa , codClasa;
    private FirebaseAuth firebaseAuth;
    private EditText taskTitleEt, taskDescEt, desiredNumberEt;
    private Button createTaskBtn;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Intent intent = getIntent();
        codClasa = intent.getStringExtra("CodClasa");
        numeClasa = intent.getStringExtra("NumeClasa");

        taskTitleEt = findViewById(R.id.tasktitleEt);
        taskDescEt = findViewById(R.id.taskDescriptionEt);
        desiredNumberEt = findViewById(R.id.numarEleviEt);
        createTaskBtn = findViewById(R.id.createTaskButton);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreatingTask();
            }
        });
    }

    private void startCreatingTask () {
        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage("Taskul se creează");

        final String taskTitle = taskTitleEt.getText().toString().trim();
        final String taskDesc = taskDescEt.getText().toString().trim();
        final String desiredNumber = desiredNumberEt.getText().toString().trim();

        if (TextUtils.isEmpty(taskTitle)){
            Toast.makeText(this, "Dă taskului un nume", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        String g_timestamp = ""+System.currentTimeMillis();

        createTask(taskTitle, taskDesc, desiredNumber, g_timestamp);
    }

    private void createTask( String taskTitle, String taskDesc, String desiredNumber, String g_timestamp){

        final HashMap<String, String> hashMap = new HashMap<>();
        final Integer number = Integer.parseInt(desiredNumber);

        hashMap.put("titlu", taskTitle);
        hashMap.put("descriere", taskDesc);
        hashMap.put("numar_participanti", desiredNumber);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("clase").child(codClasa).child("tasks");

        ref.child(g_timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("clase").child(codClasa).child("tasks");


            }
        })
    }
}