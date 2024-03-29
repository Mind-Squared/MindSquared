package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class
EditTask extends AppCompatActivity {

    EditText titleTask, descTask, dateTask;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titleTask = findViewById(R.id.titletask);
        descTask = findViewById(R.id.desctask);
        dateTask = findViewById(R.id.datetask);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        titleTask.setText(getIntent().getStringExtra("titledoes"));
        descTask.setText(getIntent().getStringExtra("descdoes"));
        dateTask.setText(getIntent().getStringExtra("datedoes"));

        String keykeyDoes = getIntent().getStringExtra("keydoes");

        reference = FirebaseDatabase.getInstance().getReference().child("TaskApp").child("Task" + keykeyDoes);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTask.this, MyDoes.class);
                            startActivity(a);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        datasnapshot.getRef().child("titledoes").setValue(titleTask.getText().toString());
                        datasnapshot.getRef().child("descdoes").setValue(descTask.getText().toString());
                        datasnapshot.getRef().child("datedoes").setValue(dateTask.getText().toString());
                        datasnapshot.getRef().child("keydoes").setValue(keykeyDoes);

                        Intent a = new Intent(EditTask.this, MyDoes.class);
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