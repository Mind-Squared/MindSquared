package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.experimental.UseExperimental;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////////////
    //DECLARATII////////////////////////////////////////////////////

    private String profileType;


    ///////////////////////////////////////
    //Declaratii pentru imaginea de profil
    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private TextView textviewUser;
    //////////////////////////////////////
    //Declaratii CardView
    private CardView chatCardView;
    private CardView quizzCardView;
    private CardView calendarCardView;
    private CardView classCardView;

    //////////////////////////////////////
    //Baza de date
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////////////////
        //Definire card-uri
        chatCardView = findViewById(R.id.chatCardView_id);
        quizzCardView = findViewById((R.id.quizzCardView_id));
        calendarCardView = findViewById(R.id.calendarCardView_id);
        classCardView = findViewById(R.id.classCardView_id);

        //adaugare Click Listener la card uri

        chatCardView.setOnClickListener(this);
        quizzCardView.setOnClickListener(this);
        calendarCardView.setOnClickListener(this);
        classCardView.setOnClickListener(this);


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
                        return true;
                    case R.id.menuDone:
                        startActivity(new Intent(getApplicationContext(), DoneActivities.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        /////////////////////////////////////////////////////////////////
        //SETAREA IMAGINII DE PROFIL////////////////////////////////////
        textviewUser = (TextView) findViewById(R.id.textView2);
        ProfileImage = (CircleImageView) findViewById(R.id.Profile_Image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });

        ////////////////////////////////////////////////////////////////
        //Definirea tipului de profil

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();
        ///textviewUser.setText("Hello "+user.getEmail().toString() + " !");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    profileType = userProfile.type;
                    Log.d("testa", profileType);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    /////////////////////////////////////////////////////////////////
    //SETAREA IMAGINII DE PROFIL////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                ProfileImage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View v) {

        Intent intent, intentElev, intentProfesor;

        /////////////////////////////////////////////////////////////
        //NAVIGARE PRIN CARDVIEW-URI

        switch (v.getId()){
            case R.id.chatCardView_id: intent = new Intent(MainActivity.this, Chat.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            break;
            case R.id.quizzCardView_id: intentElev = new Intent(MainActivity.this, Quizz_Elev.class);
                                      intentProfesor = new Intent(MainActivity.this, Quizz_Profesor.class);
                                      if(profileType.equals("Elev"))
                                          startActivity(intentElev);
                                      else
                                          startActivity(intentProfesor);
            break;
            case R.id.calendarCardView_id: intent = new Intent(MainActivity.this, Calendar.class);
                startActivity(intent);
            break;
            case R.id.classCardView_id: intent = new Intent(MainActivity.this, Class.class);
                startActivity(intent);
                break;
            default:break;
        }

    }
}