package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class calender extends AppCompatActivity {
    CalendarView calendarView;

    TextView data;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarView = findViewById(R.id.calender);
        data = findViewById(R.id.dis);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Intent intent1 = getIntent();
                String value = intent1.getStringExtra("patient_id");
                Log.d("YourTag", "Patient ID: " + value);
                String date = i+"/"+i1+"/"+i2;
                Intent intent = new Intent(calender.this, availableslots1.class);
                intent.putExtra("key", date);
                intent.putExtra("patient_id",value);
                startActivity(intent);

            }
        });





    }
}