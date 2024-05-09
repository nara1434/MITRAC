package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class medicineMonitoring extends AppCompatActivity {

    private TextView medicineNameTextView1, doseTextView1, typeTextView1;
    private TextView medicineNameTextView2, doseTextView2, typeTextView2;
    private TextView medicineNameTextView3, doseTextView3, typeTextView3;
    private Button saveButton;

    String patientID;
    private static final String FETCH_DATA_URL = "http://10.0.2.2/php/get.php";
    private static final String UPDATE_COUNT_URL = "http://10.0.2.2/php/streak.php";

    private int trueCount = 0; // Counter for True selections

    private boolean morningFetched = false;
    private boolean afternoonFetched = false;
    private boolean nightFetched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_monitoring);

        // Initialize UI components
        medicineNameTextView1 = findViewById(R.id.medicineName1);
        doseTextView1 = findViewById(R.id.dose1);
        typeTextView1 = findViewById(R.id.type1);

        medicineNameTextView2 = findViewById(R.id.medicineName2);
        doseTextView2 = findViewById(R.id.dose2);
        typeTextView2 = findViewById(R.id.type2);

        medicineNameTextView3 = findViewById(R.id.medicineName3);
        doseTextView3 = findViewById(R.id.dose3);
        typeTextView3 = findViewById(R.id.type3);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(false); // Disable the button initially
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patient_id")) {
            patientID = intent.getStringExtra("patient_id");
            Toast.makeText(this, "p1 " + patientID, Toast.LENGTH_SHORT).show();

            // Fetch data based on the presence of "Before Food" or "After Food" types
            fetchDataBasedOnType(patientID);

        } else {
            // Handle the case where patient ID is not provided
            Toast.makeText(this, "Patient ID not provided", Toast.LENGTH_SHORT).show();
            // Optionally, you can finish the activity or redirect the user
            // to another page if the patient ID is required to proceed.
            finish(); // Finish the activity
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the patient ID is not null before proceeding
                if (patientID != null && !patientID.isEmpty()) {
                    if (morningFetched && afternoonFetched && nightFetched) {
                        // Add your logic here if needed
                        Toast.makeText(medicineMonitoring.this, "true count: " + trueCount, Toast.LENGTH_SHORT).show();
                        Log.d("FinalCount", "Final True Count: " + trueCount);

                        int maxScore = (trueCount == 3) ? 1 : 0;
                        Toast.makeText(medicineMonitoring.this, "Max Score: " + maxScore, Toast.LENGTH_SHORT).show();
                        // Pass the total count and max score to the PHP script
                        updateTotalCountInTable(patientID, trueCount);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please wait for data to be fetched", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where patient ID is null or empty
                    Toast.makeText(medicineMonitoring.this, "Invalid Patient ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to fetch data based on the presence of "Before Food" or "After Food" types
    private void fetchDataBasedOnType(String patientId) {
        StringRequest request = new StringRequest(Request.Method.POST, FETCH_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG111", "Response: " + response); // Log the response for debugging
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Check if the array is not empty
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.d("TAG", "Medicine_name: " + jsonObject.optString("Medicine_name"));
                                    Log.d("TAG", "Dose: " + jsonObject.optString("Dose"));
                                    Log.d("TAG", "Type: " + jsonObject.optString("Type"));

                                    // Update UI with fetched data for each medicine only if the type matches
                                    String type = jsonObject.optString("Type");
                                    if ("Before Food".equals(type) || "After Food".equals(type)) {
                                        updateUIForMedicine(type, i + 1, jsonObject);
                                    }
                                }
                            } else {
                                // Handle the case where the array is empty
                                Toast.makeText(getApplicationContext(), "No data found for the specified types", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG", "Error parsing JSON: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        } finally {
                            // Set the flag based on the fetched data
                            morningFetched = true; // Assuming morning, afternoon, and night are fetched together
                            afternoonFetched = true;
                            nightFetched = true;
                            // Enable the save button if all data is fetched
                            saveButton.setEnabled(true);
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
                // Assuming you don't need to specify the type in this request
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void updateUIForMedicine(String timeOfDay, int medicineNumber, JSONObject jsonObject) {
        try {
            switch (medicineNumber) {
                case 1:
                    if ("Before Food".equals(timeOfDay) || "After Food".equals(timeOfDay)) {
                        medicineNameTextView1.setText(jsonObject.optString("Medicine_name"));
                        doseTextView1.setText(jsonObject.optString("Dose"));
                        typeTextView1.setText(jsonObject.optString("Type"));
                        handleRadioButtons(timeOfDay, 1);
                    }
                    break;
                case 2:
                    if ("Before Food".equals(timeOfDay) || "After Food".equals(timeOfDay)) {
                        medicineNameTextView2.setText(jsonObject.optString("Medicine_name"));
                        doseTextView2.setText(jsonObject.optString("Dose"));
                        typeTextView2.setText(jsonObject.optString("Type"));
                        handleRadioButtons(timeOfDay, 2);
                    }
                    break;
                case 3:
                    if ("Before Food".equals(timeOfDay) || "After Food".equals(timeOfDay)) {
                        medicineNameTextView3.setText(jsonObject.optString("Medicine_name"));
                        doseTextView3.setText(jsonObject.optString("Dose"));
                        typeTextView3.setText(jsonObject.optString("Type"));
                        handleRadioButtons(timeOfDay, 3);
                    }
                    break;
                // Add more cases if needed
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "Error updating UI for medicine: " + e.getMessage());
            // Handle the exception or throw it if needed
        }
    }

    private void handleRadioButtons(String timeOfDay, int medicineNumber) {
        RadioGroup radioGroup;
        switch (medicineNumber) {
            case 1:
                radioGroup = findViewById(R.id.radioGroup1);
                break;
            case 2:
                radioGroup = findViewById(R.id.radioGroup2);
                break;
            case 3:
                radioGroup = findViewById(R.id.radioGroup3);
                break;
            default:
                return;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton givenRadioButton = findViewById(checkedId);
                if (givenRadioButton != null) {
                    boolean isGiven = givenRadioButton.getText().toString().equals("Given");
                    // Perform logic based on the selected option (Given/Not Given) for the specific timeOfDay and medicineNumber
                    Log.d("TAG", "Time of Day: " + timeOfDay + ", Medicine Number: " + medicineNumber + ", Selected: " + isGiven);

                    if (isGiven) {
                        trueCount++;
                        Log.d("TAG", "True Count after Group " + medicineNumber + ": " + trueCount);
                        // Carry the count to the next group if available
                        carryCountToNextGroup(medicineNumber);
                    }
                }
            }
        });
    }

    private void carryCountToNextGroup(int currentMedicineNumber) {
        // Increment the count in the next available group
        int nextMedicineNumber = currentMedicineNumber + 1;
        if (nextMedicineNumber <= 3) {
            switch (nextMedicineNumber) {
                case 2:
                    RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
                    int selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                    Log.d("TAG", "Selected RadioButton ID 2: " + selectedRadioButtonId2);
                    if (selectedRadioButtonId2 != -1) {
                        RadioButton givenRadioButton2 = findViewById(selectedRadioButtonId2);
                        Log.d("TAG", "Selected RadioButton Text 2: " + givenRadioButton2.getText().toString());
                        if (givenRadioButton2.getText().toString().equals("Given")) {
                            trueCount++;
                            Log.d("TAG", "True Count after Group 2: " + trueCount);
                        }
                    }
                    break;
                case 3:
                    RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);
                    int selectedRadioButtonId3 = radioGroup3.getCheckedRadioButtonId();
                    Log.d("TAG", "Selected RadioButton ID 3: " + selectedRadioButtonId3);
                    if (selectedRadioButtonId3 != -1) {
                        RadioButton givenRadioButton3 = findViewById(selectedRadioButtonId3);
                        Log.d("TAG", "Selected RadioButton Text 3: " + givenRadioButton3.getText().toString());
                        if (givenRadioButton3.getText().toString().equals("Given")) {
                            trueCount++;
                            Log.d("TAG", "True Count after Group 3: " + trueCount);
                        }
                    }
                    break;
            }
        }
    }

    private void clearCheckedRadioGroups() {
        // Clear all checked radio buttons in RadioGroup1, RadioGroup2, and RadioGroup3
        clearRadioGroup(R.id.radioGroup1);
        clearRadioGroup(R.id.radioGroup2);
        clearRadioGroup(R.id.radioGroup3);
    }

    private void clearRadioGroup(int radioGroupId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        radioGroup.clearCheck();
    }

    private void updateTotalCountInTable(final String patientId, final int totalCount) {
        // Set max score based on the total count
        int maxScore = (totalCount == 3) ? 1 : 0;

        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_COUNT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server if needed
                        Log.d("UpdateCount", "Response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("UpdateCount", "Record inserted successfully");

                                // Reset the count after saving
                                trueCount = 0;
                                clearCheckedRadioGroups();  // Reset all checked RadioGroups
                            } else {
                                Log.e("UpdateCount", "Error inserting record");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("UpdateCount", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("UpdateCount", "Error updating total count: " + error.toString());
                        error.printStackTrace();
                        // Handle the error if needed
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", patientId);
                // params.put("totalCount", String.valueOf(totalCount));
                params.put("maxScore", String.valueOf(maxScore)); // Add max score parameter
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
