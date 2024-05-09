package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class resultsq extends AppCompatActivity {
    int total; // Change data type to int

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultsq);
        Button btn = findViewById(R.id.btn);

        // Receive the integer value from the intent using the correct key
        Intent intent = getIntent();
        total = intent.getIntExtra("Marks", 0); // Use the correct key "Marks"

        // Find the TextView by its ID
        TextView scoreTextView = findViewById(R.id.score);

        // Set the total score in the TextView
        scoreTextView.setText(String.valueOf(total));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(resultsq.this,reasons.class);
                startActivity(intent1);
            }
        });
    }
}
