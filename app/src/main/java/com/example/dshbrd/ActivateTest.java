package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivateTest extends AppCompatActivity {

    TextView login, password;

    Button btnOk;

    ImageButton btnBack;

    String classTest;
    String titleTest;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_test);

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

        ////////////////////////////////////////////////////////////////////

//        login = findViewById(R.id.loginTest_id);
        password = findViewById(R.id.passwordTest_id);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

//        login.setText(userID);
        password.setText(getIntent().getStringExtra("test_id"));
        classTest = getIntent().getStringExtra("clasaTest");
        titleTest = getIntent().getStringExtra("titleTest");

        btnOk = findViewById(R.id.ok_id);
//        btnBack = findViewById(R.id.btnBack_id);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(ActivateTest.this, TestOptions.class);
//                intent.putExtra("test_id", password.toString());
//                intent.putExtra("clasaTest", classTest);
//                intent.putExtra("titleTest", titleTest);
//                startActivity(intent);
//
//            }
//        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivateTest.this, Quizz_Profesor.class);
                Toast.makeText(getApplicationContext(), "Testul a fost activat cu succes!", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }
}