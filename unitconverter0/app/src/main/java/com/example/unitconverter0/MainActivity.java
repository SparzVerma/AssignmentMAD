package com.example.unitconverter0;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner fromUnit, toUnit;
    private TextView resultText;
    private final String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    private final HashMap<String, Double> unitToMeters = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        resultText = findViewById(R.id.resultText);

        // Conversion values
        unitToMeters.put("Feet", 0.3048);
        unitToMeters.put("Inches", 0.0254);
        unitToMeters.put("Centimeters", 0.01);
        unitToMeters.put("Meters", 1.0);
        unitToMeters.put("Yards", 0.9144);

        // Set up Spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        // Add listeners
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { convertUnits(); }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        fromUnit.setOnItemSelectedListener(spinnerListener);
        toUnit.setOnItemSelectedListener(spinnerListener);
    }

    private void convertUnits() {
        String input = inputValue.getText().toString().trim();
        if (input.isEmpty()) {
            resultText.setText("Result");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            String from = fromUnit.getSelectedItem().toString();
            String to = toUnit.getSelectedItem().toString();

            double meters = value * unitToMeters.get(from); // Convert to meters
            double result = meters / unitToMeters.get(to); // Convert to target unit

            resultText.setText(String.format("%.2f %s", result, to));
        } catch (NumberFormatException e) {
            resultText.setText("Invalid Input");
        }
    }
}


