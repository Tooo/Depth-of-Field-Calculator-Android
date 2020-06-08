package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmpt276a2.R;
import com.cmpt276a2.model.DOFCalculator;
import com.cmpt276a2.model.Lens;
import com.cmpt276a2.model.LensManager;

import java.text.DecimalFormat;

public class CalculateDOF extends AppCompatActivity {
    private LensManager myLens;
    private int indexLen;

    private double hyperFocal;
    private double nearFocal;
    private double farFocal;
    private double depthOfField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_dof);

        myLens = LensManager.getInstance();
        indexLen = getLen();

        setupBackButton();
        setupEditButton();
        setupInputs();
    }

    private int getLen() {
        Intent intent = getIntent();
        int indexLen = intent.getIntExtra("indexLen", 0);

        TextView textView = findViewById(R.id.dof_txtLen);
        String lenString = myLens.get(indexLen).toString();
        textView.setText(lenString);
        return indexLen;
    }

    private void setupBackButton() {
        ImageButton btn = findViewById(R.id.dof_imgbtnBackDof);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculateDOF.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupEditButton() {
        ImageButton btn = findViewById(R.id.dof_imgbtnEdit);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculateDOF.this, AddLens.class);
                intent.putExtra("indexLen", indexLen);
                startActivity(intent);
            }
        });
    }

    private void setupInputs() {
        int[] inputID = {R.id.dof_inputCoC, R.id.dof_inputDist, R.id.dof_inputAperture};

        // Input Text Watcher in all three inputs
        for (int value : inputID) {
            EditText input = findViewById(value);
            input.addTextChangedListener(new InputTextWatcher());
        }

        // Pre-fill CoC
        EditText coCInput = findViewById(R.id.dof_inputCoC);
        coCInput.setText(getResources().getString(R.string.coc_input));
    }

    // Refer to Stack Overthrow
    // https://stackoverflow.com/questions/5702771/how-to-use-single-textwatcher-for-multiple-edittexts
    private class InputTextWatcher implements TextWatcher {

        private InputTextWatcher() {
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable editable) {
            int[] inputID = {R.id.dof_inputCoC, R.id.dof_inputDist, R.id.dof_inputAperture};
            int[] dofTextID = {R.id.dof_txtNearFocalNum, R.id.dof_txtFarFocalNum, R.id.dof_txtHyperNum, R.id.dof_txtDoFNum};
            String[] values = new String[inputID.length];

            for (int i = 0; i < inputID.length; i++) {
                EditText input = findViewById(inputID[i]);
                values[i] = input.getText().toString();
            }

            String invalidMessage = validateValues(values);
            if (invalidMessage.length() != 0) {
                for (int i = 0; i < 4; i++) {
                    TextView textView = findViewById(dofTextID[i]);
                    textView.setText(invalidMessage);
                }
                return;
            }

            double coc = Double.parseDouble(values[0]);
            double distance = Double.parseDouble(values[1]);
            double aperture = Double.parseDouble(values[2]);

            calculate(coc, distance, aperture);
            double[] calculation = {nearFocal, farFocal, hyperFocal, depthOfField};

            for (int i = 0; i < 4; i++) {
                TextView textView = findViewById(dofTextID[i]);
                textView.setText(formatM(calculation[i]/1000) + "m");
            }
        }
    }

    private String validateValues(String[] values) {
        int[] invalidMessages = {R.string.invalid_coc, R.string.invalid_distance, R.string.invalid_aperture};
        double number;

        // Check if empty values
        for (String value : values) {
            if (value.length() == 0) {
                return getResources().getString(R.string.invalid_values);
            }
        }

        for (int i = 0; i<values.length; i++) {
            try {
                number = Double.parseDouble(values[i]);
                Boolean[] isValidNumber = {number <= 0, number <= 0, number < 1.4};
                if (isValidNumber[i]) {
                    return getResources().getString(invalidMessages[i]);
                }
            }
            catch (NumberFormatException e){
                return getResources().getString(invalidMessages[i]);
            }
        }

        return "";
    }

    private void calculate(double coc, double distance, double aperture) {
        Lens len = myLens.get(indexLen);
        double distanceMM = distance*1000;

        hyperFocal = DOFCalculator.hyperFocalDist(len, aperture, coc);
        nearFocal = DOFCalculator.nearFocalPoint(len, distanceMM, aperture, coc);
        farFocal = DOFCalculator.farFocalPoint(len, distanceMM, aperture, coc);
        depthOfField = DOFCalculator.depthOfField(len, distanceMM, aperture, coc);
    }

    // Convert to 2 decimal
    // Code: Assignment 1 - CameraTextUI.java
    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}