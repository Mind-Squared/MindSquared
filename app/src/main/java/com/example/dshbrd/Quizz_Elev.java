package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Quizz_Elev extends AppCompatActivity implements View.OnClickListener{

    private CardView openTest;
    private CardView rezultateTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz__elev);

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

        //////////////////////////////////////////////////////////////

        openTest = findViewById(R.id.enterTest_id);
        rezultateTeste = findViewById(R.id.rezultateTeste_id);

        openTest.setOnClickListener(this);
        rezultateTeste.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.enterTest_id : intent = new Intent(Quizz_Elev.this, OpenTest.class);
            startActivity(intent);
            break;

            case R.id.rezultateTeste_id : intent = new Intent(Quizz_Elev.this, RezultateTeste_Elev.class);
            startActivity(intent);
            break;
            default: break;

        }

    }
}