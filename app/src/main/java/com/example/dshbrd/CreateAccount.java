package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateAccount extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private DatabaseReference userRef;
    private EditText email_;
    private EditText password1_;
    private EditText password2_;
    private Button button_create;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        email_ = findViewById(R.id.create_account_email);
        password1_ = findViewById(R.id.password1);
        password2_ = findViewById(R.id.password2);
        button_create = findViewById(R.id.button_create);


        spinner = findViewById(R.id.spinner_type);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final String[] type = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                type[0] = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mAuth = FirebaseAuth.getInstance();

        userRef = myRef.child("Users").push();
        userRef.setValue("Hello");

        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_.getText().toString() , p1 = password1_.getText().toString()
                        , p2 = password2_.getText().toString()  ;
                User new_user = new User(email , email , type[0]);
                if(p1.equals(p2)){
                    create_user(email,p1);
                    add_to_realtime_database(new_user);
                    startActivity(new Intent(CreateAccount.this,MainActivity.class));


                }
                else{
                    password1_.setText("");
                    password2_.setText("");
                    Toast.makeText(CreateAccount.this, "Nu ati introdus aceeasi parola!" , Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void add_to_realtime_database(User user){
        userRef = myRef.child("users").push();
        userRef.setValue(user);

    }

    public void create_user(String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else{
                            updateUI(null);
                            Log.d("CreateAccount", "Mai incearca");
                        }
                    }

                });
    }
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"You Didn't signed in",Toast.LENGTH_LONG).show();
        }

    }
}