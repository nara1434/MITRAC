package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CareTakerProfile extends AppCompatActivity {
    EditText t1, t2, t3, t4, t5, t6, t7, t8;
    Button sbmt;
    private static final String url = "http://10.0.2.2/PHP/ct_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_taker_profile);
        sbmt = findViewById(R.id.save);
        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();
            }
        });
    }

    private void insertData() {
        t1 = findViewById(R.id.id);
        t2 = findViewById(R.id.name);
        t3 = findViewById(R.id.age);
        t4 = findViewById(R.id.sex);
        t5 = findViewById(R.id.mn);
        t6 = findViewById(R.id.qua);
        t7 = findViewById(R.id.addr);
        t8 = findViewById(R.id.ms);
        final String id = t1.getText().toString().trim();
        final String name = t2.getText().toString().trim();
        final String age = t3.getText().toString().trim();
        final String sex = t4.getText().toString().trim();
        final String mn = t5.getText().toString().trim();
        final String qua = t6.getText().toString().trim();
        final String addr = t7.getText().toString().trim();
        final String ms = t8.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Clear the input fields
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                        t6.setText("");
                        t7.setText("");
                        t8.setText("");

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                        // Check if the response indicates success
                        if (response.trim().equalsIgnoreCase("success")) {
                            // Navigate to the next activity only if the insertion was successful
                            Intent intent = new Intent(CareTakerProfile.this, caretakerprofiledispaly.class);
                            startActivity(intent);
                        } else {
                            // Handle the case where the response indicates failure
                            Toast.makeText(getApplicationContext(), "Insertion failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("c_id", id);
                param.put("name", name);
                param.put("age", age);
                param.put("sex", sex);
                param.put("mobile_number", mn);
                param.put("qualification", qua);
                param.put("address", addr);
                param.put("marital_status", ms);
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
