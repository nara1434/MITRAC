package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SearchViewReasons extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Patient> dataList;
    private List<Patient> filteredList;
    String url = "http://10.0.2.2/PHP/searchreasons.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_reasons);

        dataList = new ArrayList<>();
        filteredList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        fetchFromPHP();
    }

    private void filter(String searchText) {
        filteredList.clear();

        if (searchText.isEmpty()) {
            filteredList.addAll(dataList);
        } else {
            searchText = searchText.toLowerCase().trim();
            for (Patient item : dataList) {
                String patientId = item.getPatientId().toLowerCase();
                // Use contains instead of startsWith for more flexible searching
                if (patientId.contains(searchText)) {
                    filteredList.add(item);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private List<Patient> dataList;

        public CustomAdapter(List<Patient> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchcardview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Patient patient = dataList.get(position);

            // Display only patient ID
            holder.idTextView.setText("patient Id: " + patient.getPatientId());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle item click
                    String selectedItem = patient.getPatientId();
                    Intent intent = new Intent(v.getContext(), ViewReasons.class);
                    intent.putExtra("patient_id", selectedItem);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.idTextView);
            }
        }
    }

    public void fetchFromPHP() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.  POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            // Parse the response as a JSONArray of strings
                            JSONArray jsonArray = new JSONArray(response);

                            // Clear the dataList before adding new values
                            dataList.clear();

                            // Iterate through the array of strings
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String patientId = jsonArray.getString(i);

                                // Create a new Patient with the received patientId
                                Patient patient = new Patient(patientId, "", 0, "");
                                dataList.add(patient);
                            }

                            // Update the adapter with the new dataList
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error);
            }
        });

        // Add the request to the RequestQueue
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
