package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {

    TextView titlePage, addTitle, addDesc, addDate;
    EditText titleTask, descTask, dateTask;
    Button btnSaveTask, btnCancel;

    DatabaseReference reference;
    Integer taskId = new Random().nextInt();
    String keydoes = Integer.toString(taskId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlePage = findViewById(R.id.titlepage);
        addTitle = findViewById(R.id.addtitle);
        titleTask = findViewById(R.id.titletask);

        addDesc = findViewById(R.id.adddesc);
        addDate = findViewById(R.id.adddate);

        descTask = findViewById(R.id.desctask);
        dateTask = findViewById(R.id.datetask);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("TaskApp").child("Task"+taskId);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        datasnapshot.getRef().child("titledoes").setValue(titleTask.getText().toString());
                        datasnapshot.getRef().child("descdoes").setValue(descTask.getText().toString());
                        datasnapshot.getRef().child("datedoes").setValue(dateTask.getText().toString());
                        datasnapshot.getRef().child("keydoes").setValue(keydoes);

                        Intent a = new Intent(NewTaskActivity.this, MyDoes.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}