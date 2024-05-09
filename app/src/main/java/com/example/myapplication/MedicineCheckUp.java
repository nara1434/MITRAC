package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicineCheckUp extends AppCompatActivity {

    private TextView medicineNameTextView;
    private TextView dosageTextView;
    private TextView sessionTextView;
    private RadioGroup radioGroupYesNo;
    private RadioButton yesRadioButton;
    private RadioButton noRadioButton;
    private Button saveButton;

    private int yesCount = 0;
    private String patientID = "1434";
    private static final String fetchDataUrl = "http://10.0.2.2/php/get.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_check_up);

        // Initialize UI components
        medicineNameTextView = findViewById(R.id.m);
        dosageTextView = findViewById(R.id.d);
        sessionTextView = findViewById(R.id.s);
        radioGroupYesNo = findViewById(R.id.radioGroupYesNo);
        yesRadioButton = findViewById(R.id.tr);
        noRadioButton = findViewById(R.id.fl);
        saveButton = findViewById(R.id.save);
        fetchData(patientID);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupYesNo.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    // If True radio button is selected, increment the count
                    if (selectedId == yesRadioButton.getId()) {
                        yesCount++;
                        // Display the count in a toast (you can update UI as needed)
                        Toast.makeText(getApplicationContext(), "Yes Count: " + yesCount, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MedicineCheckUp.this, "Choose one option !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchData(final String patientId) {
        StringRequest request = new StringRequest(Request.Method.POST, fetchDataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG111", "Response: " + response); // Log the response for debugging
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Check if the array is not empty
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                Log.d("TAG", "Medicine_name: " + jsonObject.optString("Medicine_name"));
                                Log.d("TAG", "Dose: " + jsonObject.optString("Dose"));
                                Log.d("TAG", "Type: " + jsonObject.optString("Type"));

                                // Update UI with fetched data
                                medicineNameTextView.setText(jsonObject.optString("Medicine_name"));
                                dosageTextView.setText(jsonObject.optString("Dose"));
                                sessionTextView.setText(jsonObject.optString("Type"));
                            } else {
                                // Handle the case where the array is empty
                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG", "Error parsing JSON: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "Error fetching data: " + error.toString());
                        error.printStackTrace(); // Log the full stack trace
                        Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", patientId);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
