package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity implements ChatAdapter.OnChatListener {

    RecyclerView recyclerView;


    private TextView textview ;
    public String userID;
    private DatabaseReference reference;
    private DataSnapshot clase;
    private Button button_new_page;
    User user;
    String[] email = new String[10];
    String[] cod , nume = new String[100];

    ArrayList<JoinedClass_Chat> Lista = new ArrayList<JoinedClass_Chat>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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

//        button_new_page = findViewById(R.id.new_chat);
//
//        button_new_page.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Chat.this , CreateNewChat.class);
//                intent.putExtra("userID", userID);
//                startActivity(intent);
//            }
//        });

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        reference = FirebaseDatabase.getInstance().getReference("users");
        email[0] = "nothing";
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ChatAdapter myadapter = new ChatAdapter(this, userID , Lista );
//        Log.d("showDataagain", Integer.toString(Lista.size()));
//        recyclerView = findViewById(R.id.recyclerchat);
//        recyclerView.setAdapter(myadapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void showData(DataSnapshot snapshot) {
        Log.d("showData" , "am ajuns aici");
        user = snapshot.child(userID).getValue(User.class);
        clase = snapshot.child(userID).child("clase");
        int i = 0 ;
        Log.d("showData" , "am ajuns aici2");
        for (DataSnapshot ds : clase.getChildren()){
            JoinedClass_Chat clasaa  = new JoinedClass_Chat("","", "", "");
            clasaa.uid = ds.getKey().toString();
             clasaa.role = ds.child("role").getValue().toString();
            clasaa.rating = ds.child("rating").getValue().toString();
            clasaa.nume = ds.child("nume").getValue().toString();
            Lista.add(clasaa);
        }
//        Log.d("showData1", user.email);
//        Log.d("showData2", Integer.toString(Lista.size()));
//        Log.d("showData2", Lista.get(0).nume);

        ChatAdapter myadapter = new ChatAdapter(this, userID , Lista , cod  , this , user.firstname , user.lastname  );
        Log.d("showDataagain", Integer.toString(Lista.size()));
        recyclerView = findViewById(R.id.recyclerchat);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getClasses(DataSnapshot snapshot){

    }

    @Override
    public void OnChatClick(int position) {

        Log.d("Apasat", Integer.toString(position));

        Intent intent = new Intent(this , Chat_Conversatie.class);

        intent.putExtra("CodClasa", Lista.get(position).uid);
        intent.putExtra("NumeClasa", Lista.get(position).nume);
        intent.putExtra("userID", userID);
        intent.putExtra("userFirstname" , user.firstname);
        intent.putExtra("userLastname", user.lastname);
        startActivity(intent);
    }


}