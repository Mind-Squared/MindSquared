package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Calendar extends AppCompatActivity {

    private ArrayList<CalendarElement> calendarElements;
    RecyclerView recyclerView;
    DatabaseReference reff;

    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ////////////////////////////////////////////////////////////////
        //Implementare Calendar -- basic deocamdata -- /////////////////

        calendarElements = new ArrayList<>();
        recyclerView = findViewById(R.id.calendarview);

        setCalendarInfo();
        setAdapter();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewCalendar);
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
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menuDone:
                        startActivity(new Intent(getApplicationContext(), DoneActivities.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //Database whatever
        reff = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("TaskApp");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                calendarElements.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    CalendarElement element = snapshot1.getValue(CalendarElement.class);
                    calendarElements.add(element);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        CalendarAdapter adapter = new CalendarAdapter(calendarElements);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setCalendarInfo() {
        calendarElements.add(new CalendarElement("Trebuie sa facem chestia asta 123", "15/06/2022", "12:00 - 15:00"));
    }
}