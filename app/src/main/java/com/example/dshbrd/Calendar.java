package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Calendar extends AppCompatActivity {

    private ArrayList<CalendarElement> calendarElements;
    RecyclerView recyclerView;

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
    }

    private void setAdapter() {
        CalendarAdapter adapter = new CalendarAdapter(calendarElements);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setCalendarInfo() {
        calendarElements.add(new CalendarElement("Trebuie sa facem chestia asta 123", "12:00 - 13:15"));
        calendarElements.add(new CalendarElement("Trebuie sa facem chestia asta 234", "18:00 - 19:15"));
        calendarElements.add(new CalendarElement("Trebuie sa facem chestia asta 345", "12:00 - 17:30"));
    }
}