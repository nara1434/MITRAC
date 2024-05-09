package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class medicine_insertion extends Dialog {

    private Context context;

    public medicine_insertion(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_medicine_input);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setTextAppearance(context, R.style.DialogTitleStyle);
        final EditText medicineNameEditText = findViewById(R.id.medicineNameEditText);
        final EditText dose1 = findViewById(R.id.dose);
        final Spinner sessionSpinner = findViewById(R.id.sessionSpinner);

        // Define the array of options
        String[] sessionOptions = {"Before Food", "After Food"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, sessionOptions);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        sessionSpinner.setAdapter(adapter);

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicineName = medicineNameEditText.getText().toString();
                String dose = dose1.getText().toString();
                String session = sessionSpinner.getSelectedItem().toString();

                ((MedicineData) context).displayMedicineInformation(medicineName, dose, session);

                dismiss();
            }
        });
    }

}
