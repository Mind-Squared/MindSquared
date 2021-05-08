package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

public class CreateClassActivity extends AppCompatActivity {

    //private ActionBar actionBar;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private EditText clasaNumeEt, clasaNumeLiceuEt;
    private Button butonCreare;

    Integer randomId = new Random().nextInt();
    public String classID = Integer.toString(Math.abs(randomId));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

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

        //initial view
        clasaNumeEt = findViewById(R.id.clasaNumeEt);
        clasaNumeLiceuEt = findViewById(R.id.clasaNumeLiceuEt);
        butonCreare = findViewById(R.id.butonCreare);

        firebaseAuth = FirebaseAuth.getInstance();

        butonCreare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreatingClass();
                return;
            }
        });

    }

    private void startCreatingClass() {
        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage("Clasa se creează");

        final String clasaNume = clasaNumeEt.getText().toString().trim();
        final String clasaNumeLiceu = clasaNumeLiceuEt.getText().toString().trim();

        if (TextUtils.isEmpty(clasaNume)){
            Toast.makeText(this, "Dă clasei un nume", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        createClass( clasaNume, clasaNumeLiceu, classID);
    }

    private void createClass (String className, String classSchooName, final String g_timestamp) {
        final HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("entry", classID);
        hashMap.put("className", className);
        //hashMap.put("classSchoolName", classSchooName);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clase");
        ref.child(g_timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");

                        HashMap<String, String> hashMap2 = new HashMap<>();
                        hashMap2.put("rating", "0");
                        hashMap2.put("role", "creator");
                        hashMap2.put("nume", className);
                        hashMap2.put("key", classID);

                        ref2.child(firebaseAuth.getUid()).child("clase").child(g_timestamp).setValue(hashMap2);

                        HashMap<String, String> hashMap1 = new HashMap<>();
                        hashMap1.put("uid", firebaseAuth.getUid());
                        hashMap1.put("role", "creator");

                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("clase");
                        ref1.child(g_timestamp).child("Participants").child(firebaseAuth.getUid())
                                .setValue(hashMap1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText( CreateClassActivity.this, "Clasă creată...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText( CreateClassActivity.this, ""+e.getMessage(),  Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText( CreateClassActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();

    String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}