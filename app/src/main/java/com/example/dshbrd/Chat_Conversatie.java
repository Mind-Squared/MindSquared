package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.ArrayList;

public class Chat_Conversatie extends AppCompatActivity {
    RecyclerView recyclerView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef , mesaj_nou_ref;
    String nume , cod , userID , firstname , lastname;
    ImageButton button;
    String mesaj_scris , g_timestamp;
    EditText editTextmesaj;
    Mesaj_conversatie mesaj_conversatie;

    ArrayList<Mesaj_conversatie> mesaje = new ArrayList<>();

    ConversatieAdapter adapter = new ConversatieAdapter(this, mesaje);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__conversatie);

        recyclerView = findViewById(R.id.recyclerView_conversatie);

        Intent intent = getIntent();
        cod = intent.getStringExtra("CodClasa");
        nume = intent.getStringExtra("NumeClasa");
        userID = intent.getStringExtra("userID");
        firstname = intent.getStringExtra("userFirstname");
        lastname = intent.getStringExtra("userLastname");

        // final String g_timestamp = ""+System.currentTimeMillis();
        editTextmesaj = findViewById(R.id.editText_scrieText);
        button = findViewById(R.id.button_send);
        Log.d("inainte de firebase", "aici");
        myRef = database.getReference().child("mesaje").child(cod);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Get_MesajeTrimise(snapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Chat_Conversatie", "database error");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                mesaj_scris = editTextmesaj.getText().toString();
                if (mesaj_scris.length() > 0) {
                    g_timestamp = Instant.now().toString();
                    mesaj_nou_ref = myRef.push();
                    mesaj_conversatie = new Mesaj_conversatie(mesaj_scris, g_timestamp, userID, firstname, lastname);
                    mesaj_nou_ref.child("destinatar").setValue("destinatar");
                    mesaj_nou_ref.child("destinatar").child("cod").setValue(userID);
                    mesaj_nou_ref.child("destinatar").child("firstname").setValue(firstname);
                    mesaj_nou_ref.child("destinatar").child("lastname").setValue(lastname);
                    mesaj_nou_ref.child("time").setValue(mesaj_conversatie.time);
                    mesaj_nou_ref.child("text").setValue(mesaj_conversatie.text);
                    editTextmesaj.setText("");

                    mesaje.add(mesaj_conversatie);

                    recyclerView.setAdapter(adapter);
                    recyclerView.getLayoutManager().scrollToPosition(mesaje.size()-1);
                }
            }

        });

    }
    public void Get_MesajeTrimise(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Mesaj_conversatie _mesaj = new Mesaj_conversatie("", "", "", "", "");
            Destinatar_Conversatie d = new Destinatar_Conversatie();
            d.cod = ds.child("destinatar").child("cod").getValue().toString();
            d.firstname = ds.child("destinatar").child("firstname").getValue().toString();
            d.lastname = ds.child("destinatar").child("lastname").getValue().toString();

            _mesaj.destinatar = d;
            _mesaj.text = ds.child("text").getValue().toString();
            _mesaj.time = ds.child("time").getValue().toString();

            mesaje.add(_mesaj);
        }

        Log.d("mesaje_size", Integer.toString(mesaje.size()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getLayoutManager().scrollToPosition(mesaje.size()-1);
    }
}