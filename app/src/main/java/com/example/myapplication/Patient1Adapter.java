package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Patient1Adapter extends RecyclerView.Adapter<Patient1Adapter.PatientViewHolder> {

    private List<Patient1> patients;

    public Patient1Adapter(List<Patient1> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient1, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient1 patient = patients.get(position);
        holder.textPatientId.setText("Patient ID: " + patient.getPatientId());
        holder.textName.setText("Name: " + patient.getName());
        holder.textGender.setText("Gender: " + patient.gender());
        holder.textPhno.setText("Phone: " + patient.phno());

        // Set the profile image if needed
        if (patient.getProfileImage() != null) {
            holder.profileImageView.setImageBitmap(patient.getProfileImage());
        } else {
            holder.profileImageView.setImageResource(R.drawable.person);
        }
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setPatients(List<Patient1> patients) {
        this.patients = patients;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView textPatientId;
        TextView textName;
        TextView textGender;
        TextView textPhno;
        ImageView profileImageView;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            textPatientId = itemView.findViewById(R.id.id);
            textName = itemView.findViewById(R.id.name);
            textGender = itemView.findViewById(R.id.gender);
            textPhno = itemView.findViewById(R.id.phno);
            profileImageView = itemView.findViewById(R.id.profile);
        }
    }
}
