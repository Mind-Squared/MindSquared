package com.example.dshbrd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetNotifiaction extends AppCompatActivity {

    TextView notificationTime;
    Button selectNotificationTime, cancelNotificastionTime;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notifiaction);

        notificationTime = findViewById(R.id.notificationtime);

        selectNotificationTime = findViewById(R.id.selectnotificationtime);
        selectNotificationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.util.Calendar c = java.util.Calendar.getInstance();
                int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = c.get(java.util.Calendar.MINUTE);
                int year = c.get(java.util.Calendar.YEAR);
                int month = c.get(java.util.Calendar.MONTH);
                int day = c.get(java.util.Calendar.DAY_OF_MONTH);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SetNotifiaction.this,
                        hour, mTimeSetListener,
                        minute,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateFormat.is24HourFormat(SetNotifiaction.this));

                DatePickerDialog dialog = new DatePickerDialog(
                        SetNotifiaction.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                //timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                timePickerDialog.show();
                notificationTime.setText(date + " " + time);
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
            }
        };

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = day + "/" + month + "/" + year;
            }
        };
    }
}