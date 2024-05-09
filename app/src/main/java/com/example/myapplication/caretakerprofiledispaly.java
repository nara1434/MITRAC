package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class caretakerprofiledispaly extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7, t8;
    public String c_id ="1434";

    // Replace this URL with your server URL
    private static final String fetchDataUrl = "http://10.0.2.2/PHP/ct_profiledisplay.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretakerprofiledispaly);

        t1 = findViewById(R.id.id);
        t2 = findViewById(R.id.name);
        t3 = findViewById(R.id.age);
        t4 = findViewById(R.id.sex);
        t5 = findViewById(R.id.mn);
        t6 = findViewById(R.id.qua);
        t7 = findViewById(R.id.addr);
        t8 = findViewById(R.id.ms);

        fetchData(c_id);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the caretakerprofileupdate activity and pass the caretaker's id
                Intent intent = new Intent(caretakerprofiledispaly.this, caretakerprofileupdate.class);
                intent.putExtra("c_id", c_id);
                startActivity(intent);
            }
        });
    }

    private void fetchData(final String c_id) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        t1.setText(jsonObject.optString("c_id"));
                        t2.setText(jsonObject.optString("name"));
                        t3.setText(jsonObject.optString("age"));
                        t4.setText(jsonObject.optString("sex"));
                        t5.setText(jsonObject.optString("mobile_number"));
                        t6.setText(jsonObject.optString("qualification"));
                        t7.setText(jsonObject.optString("address"));
                        t8.setText(jsonObject.optString("marital_status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("c_id", c_id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
