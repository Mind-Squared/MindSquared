package com.example.dshbrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {

    private static final String TAG = "NewTaskActivity";
    TextView titlePage, addTitle, addDesc, addDate, buttonData, buttonTime;
    EditText titleTask, descTask, dateTask;
    Button btnSaveTask, btnCancel;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private FirebaseUser user;
    private String userID;

    String time, date;
    DatabaseReference reference;
    private java.util.Calendar calendar = java.util.Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
    String dateTime = simpleDateFormat.format(calendar.getTime());
    String taskId = dateTime;
    String keydoes = taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        ///////////////////////////////////////////////////////////
        //NAVIGARE PRIN BOTTOM MENU///////////////////////////////

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewMyDoes);

        bottomNavigationView.setSelectedItemId(R.id.menuDoing);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuDoing:
                        return true;
                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menuDone:
                        startActivity(new Intent(getApplicationContext(), DoneActivities.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        titlePage = findViewById(R.id.titlepage);
        addTitle = findViewById(R.id.addtitle);
        titleTask = findViewById(R.id.titletask);

        addDesc = findViewById(R.id.adddesc);
        addDate = findViewById(R.id.adddate);

        descTask = findViewById(R.id.desctask);
        dateTask = findViewById(R.id.datetask);

        buttonData = (TextView) findViewById(R.id.selectDate);
        buttonTime = findViewById(R.id.selectTime);


        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                int year = cal.get(java.util.Calendar.YEAR);
                int month = cal.get(java.util.Calendar.MONTH);
                int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: " + day + "/" + month + "/" + year );
                date = day + "/" + month + "/" + year;
                buttonData.setText(date);
            }
        };

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.util.Calendar c = java.util.Calendar.getInstance();
                int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = c.get(java.util.Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NewTaskActivity.this,
                        hour, mTimeSetListener,
                        minute,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateFormat.is24HourFormat(NewTaskActivity.this));
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
                Log.d(TAG, "onTimeSet: " + hour + ":" + minute );
                buttonTime.setText(time);
            }
        };

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data to database
                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();

                reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("TaskApp").child("Task"+taskId);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        datasnapshot.getRef().child("atitledoes").setValue(titleTask.getText().toString());
                        datasnapshot.getRef().child("bdatedoes").setValue(date);
                        datasnapshot.getRef().child("ctimedoes").setValue(time);
                        datasnapshot.getRef().child("descdoes").setValue(descTask.getText().toString());
                        datasnapshot.getRef().child("keydoes").setValue(keydoes);

                        Intent a = new Intent(NewTaskActivity.this, MyDoes.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}