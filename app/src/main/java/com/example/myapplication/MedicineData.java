package com.example.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineData extends AppCompatActivity {
    private String patientId ;
    private ArrayList<String> Ids = new ArrayList<>();
    private ArrayList<String> medicineNames = new ArrayList<>();
    private ArrayList<String> dose = new ArrayList<>();
    private ArrayList<String> session = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_data);
        patientId = getIntent().getStringExtra("patient_id");
        //Log.d("tag", "Patient ID: " + patientId);
    }

    public void showMedicineDialog(View view) {
        medicine_insertion dialog = new medicine_insertion(this);
        dialog.show();
    }

    public void displayMedicineInformation(String medicineName, String doses, String sessions) {
       // Log.d("MedicineData", "Patient ID: " + patientId);
        Ids.add(patientId);
        medicineNames.add(medicineName);
        dose.add(doses);
        session.add(sessions);
        displayMedicineDetails();
    }

    private void displayMedicineDetails() {
        LinearLayout linearLayout = findViewById(R.id.medicineLayout);
        linearLayout.removeAllViews();

        for (int i = 0; i < medicineNames.size(); i++) {
            TextView titleTextView = new TextView(this);
            titleTextView.setText(getFormattedTitleText(
                    medicineNames.get(i),
                    dose.get(i),
                    session.get(i)
            ));

            linearLayout.addView(titleTextView);
        }

        enableSaveButton();
    }

    private SpannableString getFormattedTitleText(String medicineName, String dose, String session) {
        String titleText = "Medicine Name  :  " + medicineName + "\n" + "\n"
                + "Dose                     :  " + dose + "\n" + "\n"
                + "Session                :  " + session + "\n" + "\n" + "\n" ;
        SpannableString spannableString = new SpannableString(titleText);

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, titleText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(20, true), 0, titleText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public void enableSaveButton() {
        Button saveMedButton = findViewById(R.id.saveMed);
        saveMedButton.setEnabled(true);
        saveMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedicineToDatabase();
            }
        });
    }

    public void saveMedicineToDatabase() {
        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < medicineNames.size(); i++) {
            JsonObject medicineJson = new JsonObject();
            medicineJson.addProperty("id", Ids.get(i));
            medicineJson.addProperty("medicine_name", medicineNames.get(i));
            medicineJson.addProperty("dose", dose.get(i));
            medicineJson.addProperty("session", session.get(i));

            jsonArray.add(medicineJson);
        }

        sendToPhpFile(jsonArray);
    }

    private void sendToPhpFile(JsonArray jsonArray) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/PHP/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Medicine apiService = retrofit.create(Medicine.class);
        Call<JsonElement> call = apiService.saveMedicine(jsonArray);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    JsonElement jsonResponse = response.body();

                    if (jsonResponse != null && jsonResponse.isJsonObject()) {
                        JsonObject jsonObject = jsonResponse.getAsJsonObject();

                        if (jsonObject.has("status")) {
                            String status = jsonObject.get("status").getAsString();

                            if ("success".equals(status)) {
                                Toast.makeText(MedicineData.this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                            } else {
                                handleErrorMessage(jsonObject);
                            }
                        } else {
                            Toast.makeText(MedicineData.this, "Unexpected response format", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MedicineData.this, "Unexpected response format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MedicineData.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(MedicineData.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("doc_medicine", "Error: " + t.getMessage());
            }
        });
    }

    private void handleErrorMessage(JsonObject jsonObject) {
        if (jsonObject.has("message")) {
            String message = jsonObject.get("message").getAsString();
            Toast.makeText(MedicineData.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MedicineData.this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }
}
