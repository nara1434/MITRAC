package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorHomePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RequestQueue requestQueue;
    private List<PatientInfo> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_page);
        requestQueue = Volley.newRequestQueue(this);

        // Set up RecyclerView for the main content
        recyclerView = findViewById(R.id.recyclerViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new CustomAdapter(dataList);
        recyclerView.setAdapter(adapter);

        // Find the ImageButtons by their IDs
        ImageButton addPatient = findViewById(R.id.image);
        ImageButton doctorProfile = findViewById(R.id.image1);
        ImageButton viewReasons = findViewById(R.id.image2);
        ImageButton videos = findViewById(R.id.image3);
        ImageButton medicineTimings = findViewById(R.id.image4);
        ImageButton appointments = findViewById(R.id.image5);
        ImageButton notify = findViewById(R.id.bell);
        Button btn = findViewById(R.id.see);
        ImageButton viewAnswers = findViewById(R.id.answers);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomePage.this, search.class);
                startActivity(intent);
            }
        });
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DoctorHomePage.this, notifications.class);
                startActivity(intent);
            }
        });

        // Set OnClickListeners for the ImageButtons
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity when the ImageButton is clicked
                Intent intent = new Intent(DoctorHomePage.this, addPatient.class);
                startActivity(intent);
            }
        });

        doctorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity when the ImageButton is clicked
                Intent intent = new Intent(DoctorHomePage.this, doctorprofiledisplay.class);
                startActivity(intent);
            }
        });

        viewReasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity when the ImageButton is clicked
                Intent intent = new Intent(DoctorHomePage.this, SearchViewReasons.class);
                startActivity(intent);
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity when the ImageButton is clicked
                Intent intent = new Intent(DoctorHomePage.this, addvideos.class);
                startActivity(intent);
            }
        });
        viewAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomePage.this,answersview.class);
                startActivity(intent);
            }
        });

        medicineTimings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomePage.this, searchaddmedicine.class);
                startActivity(intent);
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity when the ImageButton is clicked
                Intent intent = new Intent(DoctorHomePage.this, status.class);
                startActivity(intent);
            }
        });

        // Fetch data from PHP on activity creation
        fetchfromPHP();
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<PatientInfo> dataList;

        public CustomAdapter(List<PatientInfo> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewpatientinfo, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PatientInfo patient = dataList.get(position);

            if (holder.idTextView != null) {
                holder.idTextView.setText("ID          : " + (patient.getId() != null ? patient.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name   : " + (patient.getName() != null ? patient.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Gender: " + (patient.getGender() != null ? patient.getGender() : ""));
            }
            if (holder.phnoTextView != null) {
                holder.phnoTextView.setText("Phno    : " + (patient.getPhno() != null ? patient.getPhno() : ""));
            }

            if (holder.profileImageView != null && patient.getProfilePhoto() != null
                    && !patient.getProfilePhoto().isEmpty()) {
                String completeImageUrl = "http://10.0.2.2/php/" + patient.getProfilePhoto();
                Picasso.get().load(completeImageUrl).into(holder.profileImageView);
            } else {
                holder.profileImageView.setImageResource(R.drawable.person);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedItem = patient.getId() != null ? patient.getId() : "";

                    Intent intent = new Intent(DoctorHomePage.this, PatientProfile.class);
                    intent.putExtra("patientId", selectedItem);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView, nameTextView, genderTextView, phnoTextView;
            ImageView profileImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.id);
                nameTextView = itemView.findViewById(R.id.name);
                genderTextView = itemView.findViewById(R.id.gender);
                phnoTextView = itemView.findViewById(R.id.phno);
                profileImageView = itemView.findViewById(R.id.profile);
            }
        }
    }

    public void fetchfromPHP() {
        String url = "http://10.0.2.2/Php/patients_recently_added.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            if (jsonResponse.has("status") && jsonResponse.getString("status").equals("success")) {
                                JSONArray jsonArray = jsonResponse.getJSONArray("patients");
                                dataList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.optString("patient_id");
                                    String name = jsonObject.optString("name");
                                    String gender = jsonObject.optString("sex");
                                    String phno = jsonObject.optString("mobile_number");
                                    String profilePhoto = jsonObject.optString("img");

                                    dataList.add(new PatientInfo(id, name, gender, phno, profilePhoto));
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(DoctorHomePage.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DoctorHomePage.this, "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error);
            }
        });

        queue.add(stringRequest);
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }
}
