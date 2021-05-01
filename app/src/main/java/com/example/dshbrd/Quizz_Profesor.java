package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Quizz_Profesor extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////
    //DECLARATII

    //Declaratii CardView-uri
    private CardView creazaTestCardView;
    private CardView testeCreateCardView;
    private CardView rezultateTesteCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz__profesor);

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

        ///////////////////////////////////////////////
        //Atribuire CardView-uri

        creazaTestCardView = findViewById(R.id.creazaTestCardView_id);
        testeCreateCardView = findViewById(R.id.testeCreateCardView_id);
        rezultateTesteCardView = findViewById(R.id.rezultateTesteCardView_id);

        //Adaugare click Listener la CardView-uri

        creazaTestCardView.setOnClickListener(this);
        testeCreateCardView.setOnClickListener(this);
        rezultateTesteCardView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //////////////////////////////////////////////////////////
        //NAVIGARE PRIN CARDVIEW-URI

        Intent intent;

        switch (v.getId()){

            case R.id.creazaTestCardView_id : intent = new Intent(Quizz_Profesor.this, CreazaTest.class);
                 startActivity(intent);
                 break;
            case R.id.testeCreateCardView_id : intent = new Intent(Quizz_Profesor.this, TesteCreate.class);
                 startActivity(intent);
                 break;
            case R.id.rezultateTesteCardView_id : intent = new Intent(Quizz_Profesor.this, RezultateTeste.class);
                 startActivity(intent);
                 break;
            default: break;
        }

    }
}