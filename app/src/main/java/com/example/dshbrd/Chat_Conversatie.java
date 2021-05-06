package com.example.dshbrd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Chat_Conversatie extends AppCompatActivity {
    String nume , cod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__conversatie);

        Intent intent = getIntent();
        cod = intent.getStringExtra("CodClasa");
        nume = intent.getStringExtra("NumeClasa");
        Log.d("Conversatie", "esti bine boss");
    }
}