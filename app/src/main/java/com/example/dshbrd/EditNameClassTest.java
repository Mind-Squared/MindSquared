package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditNameClassTest extends AppCompatActivity {

    Button btnSaveModification;

    EditText editTestName;
    EditText editClassName;

    String test_id;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_class_test);

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

        ////////////////////////////////////////////////////////

        editTestName = findViewById(R.id.editTestName_id);
        editClassName = findViewById(R.id.editClassName_id);

        editClassName.setText(getIntent().getStringExtra("classTest"));
        editTestName.setText(getIntent().getStringExtra("titleTest"));
        test_id = getIntent().getStringExtra("test_id");

        btnSaveModification = findViewById(R.id.saveModification_id);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id);

        btnSaveModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        reference.getRef().child("titleTest").setValue(editTestName.getText().toString());
                        reference.getRef().child("clasaTest").setValue(editClassName.getText().toString());

                        Intent intent = new Intent(EditNameClassTest.this, TesteCreate.class);
                        startActivity(intent);

            }
        });



    }
}