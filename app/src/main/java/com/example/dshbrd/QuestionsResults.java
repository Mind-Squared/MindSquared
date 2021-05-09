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
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionsResults extends AppCompatActivity {

    ImageButton btnBack;

    String test_id;

    private FirebaseUser user;
    private DatabaseReference reference;
    DatabaseReference auxReference;
    private String userID;

    RecyclerView resultQuestionsRecycler;
    ArrayList<ResultQuestion_elev> list;
    ResultQuestion_elev_Adapter resultQuestion_elev_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_results);

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

        btnBack = findViewById(R.id.btnBack_id);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(QuestionsResults.this, RezultateTeste_Elev.class);
                startActivity(intent);

            }
        });

        test_id = getIntent().getStringExtra("test_id");

        resultQuestionsRecycler = findViewById(R.id.resultQuestionsRecycler_id);
        resultQuestionsRecycler.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<ResultQuestion_elev>();

        //data

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Log.d("testa", userID+test_id);

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("Tests").child("Test"+test_id).child("answers");
        reference.child("Questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){

                    ResultQuestion_elev p = dataSnapshot1.getValue(ResultQuestion_elev.class);
                    list.add(p);

                }

                Log.d("testa", Integer.toString(list.size()));

                resultQuestion_elev_adapter = new ResultQuestion_elev_Adapter(QuestionsResults.this, list);
                resultQuestionsRecycler.setAdapter(resultQuestion_elev_adapter);
                resultQuestion_elev_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}