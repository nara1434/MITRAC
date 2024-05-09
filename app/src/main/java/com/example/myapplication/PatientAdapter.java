package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private List<Patient> patients;

    public PatientAdapter(List<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.bind(patient);
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {

        private TextView patientIdTextView, nameTextView, ageTextView, sexTextView;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            patientIdTextView = itemView.findViewById(R.id.id);
            nameTextView = itemView.findViewById(R.id.name);
            ageTextView = itemView.findViewById(R.id.age);
            sexTextView = itemView.findViewById(R.id.sex);
        }

        public void bind(Patient patient) {
            patientIdTextView.setText("ID: " + String.valueOf(patient.getPatientId()));
            nameTextView.setText("Name: " + patient.getName());
            ageTextView.setText("Age: " + String.valueOf(patient.getAge()));
            sexTextView.setText("Sex: " + patient.getSex());
        }
    }
}
