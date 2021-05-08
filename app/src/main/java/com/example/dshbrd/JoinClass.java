package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class JoinClass extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private EditText codIntrodusEt;
    private Button buttonJoinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);

        ///////////////////////////////////////////////////////////
        //NAVIGARE PRIN BOTTOM MENU///////////////////////////////

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewHome);

        bottomNavigationView.setSelectedItemId(R.id.menuHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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

        //initial view
        codIntrodusEt = findViewById(R.id.enterKeyEt);
        buttonJoinBtn = findViewById(R.id.butonJoin);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJoinClass();
                return;
            }
        });
    }

    private void startJoinClass() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Clasă în căutare");

        final String codIntrodus = codIntrodusEt.getText().toString().trim();

        if (TextUtils.isEmpty(codIntrodus)) {
            Toast.makeText(this, "Introdu un cod", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        joinClass(codIntrodus);
    }

    private void joinClass(String codIntrodus) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("clase");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("entry").getValue().toString().equals(codIntrodus)) {

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("uid", firebaseAuth.getUid());
                        hashMap.put("role", "participant");

                        ref.child(codIntrodus).child("Participants").child(firebaseAuth.getUid()).setValue(hashMap)

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users");

                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("rating", "0");
                                hashMap1.put("role", "participant");
                                hashMap1.put("nume", ds.child("className").getValue().toString());
                                hashMap1.put("key", codIntrodus);

                                ref1.child(firebaseAuth.getUid()).child("clase").child(codIntrodus).setValue(hashMap1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(JoinClass.this, "Clasă găsită", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(JoinClass.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(JoinClass.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}