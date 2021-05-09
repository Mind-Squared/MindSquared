package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestOptions extends AppCompatActivity implements View.OnClickListener{

    String classTest;
    String titleTest;
    String test_id;

//    private  CardView deleteTest;
    private CardView editNameClassTest;
    private CardView activateTest;
    private CardView editQuestionstest;
    private CardView rezultateTesteCardView;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    ImageButton btnBack;
    Button deleteTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_options);

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

        /////////////////////////////////////////////////////////////////////////////
        //Atribuiri CardView-uri

        editNameClassTest = findViewById(R.id.editNameClassTest);
        deleteTest = findViewById(R.id.deleteTest_id);
        activateTest = findViewById(R.id.activateTest_id);
        editQuestionstest = findViewById(R.id.editQuestionsTest_id);
        rezultateTesteCardView = findViewById(R.id.rezultateTesteCardView_id);

        btnBack = findViewById(R.id.btnBack_id);

        //Adaugare click Listener-uri la cardview-uri

        activateTest.setOnClickListener(this);
        deleteTest.setOnClickListener(this);
        editNameClassTest.setOnClickListener(this);
        editQuestionstest.setOnClickListener(this);
        rezultateTesteCardView.setOnClickListener(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestOptions.this, TesteCreate.class);
                startActivity(intent);

            }
        });

        //Extragere  valori din putextra

        test_id = getIntent().getStringExtra("test_id");
        classTest = getIntent().getStringExtra("clasaTest");
        titleTest = getIntent().getStringExtra("titleTest");

        //database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

    }

    @Override
    public void onClick(View v) {

        //Functionalitate CardView-uri

        Intent intent;

        switch (v.getId()){

            case R.id.editNameClassTest : intent = new Intent(TestOptions.this, EditNameClassTest.class);
            intent.putExtra("titleTest", titleTest);
            intent.putExtra("classTest", classTest);
            intent.putExtra("test_id", test_id);
            startActivity(intent);
            break;
            case  R.id.deleteTest_id :

                reference = FirebaseDatabase.getInstance().getReference().child("Tests").child("Test"+test_id);

                reference.removeValue();

                reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id);

                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Intent a = new Intent(TestOptions.this, TesteCreate.class);
                            Toast.makeText(getApplicationContext(), "Testul a fost șters!", Toast.LENGTH_LONG).show();
                            startActivity(a);

                        }

                    }
                });
                break;
            case R.id.activateTest_id : intent = new Intent(TestOptions.this, ActivateTest.class);
                intent.putExtra("test_id", test_id);
            startActivity(intent);
            break;

            case R.id.editQuestionsTest_id : intent  = new Intent(TestOptions.this, CreatedTestQuestions.class);
                intent.putExtra("test_id", test_id);
            startActivity(intent);
            break;
            case R.id.rezultateTesteCardView_id : intent = new Intent(TestOptions.this, RezultateTeste.class);
                intent.putExtra("test_id", test_id);
                startActivity(intent);
                break;
            default:break;

        }

    }
}