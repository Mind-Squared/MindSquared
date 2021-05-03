package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.HashMap;

public class CreateClassActivity extends AppCompatActivity {

    //private ActionBar actionBar;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private EditText clasaNumeEt, clasaNumeLiceuEt;
    private Button butonCreare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

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

        Fragment fragment;

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

        final String g_timestamp = ""+System.currentTimeMillis();

        createClass( clasaNume, clasaNumeLiceu, g_timestamp);
    }

    private void createClass (String className, String classSchooName, final String g_timestamp) {
        final HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("entry", randomString(8));
        hashMap.put("className", className);
        hashMap.put("classSchoolName", classSchooName);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clase");
        ref.child(g_timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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