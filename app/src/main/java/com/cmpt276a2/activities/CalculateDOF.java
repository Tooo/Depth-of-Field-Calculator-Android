package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
        setupCoC();
        setupCalculateButton();
    }

    private int getLen() {
        Intent intent = getIntent();
        int indexLen = intent.getIntExtra("indexLen", 0);

        TextView textView = (TextView) findViewById(R.id.dof_txtLen);
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

    private void setupCoC() {
        EditText coCInput = (EditText)findViewById(R.id.dof_inputCoC);
        coCInput.setText("0.029");

    }

    private void setupCalculateButton() {
        Button btn = findViewById(R.id.dof_btnCalculate);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText cocInput = (EditText)findViewById(R.id.dof_inputCoC);
                String cocString = cocInput.getText().toString();

                EditText distanceInput = (EditText)findViewById(R.id.dof_inputDist);
                String distanceString = distanceInput.getText().toString();

                EditText apertureInput = (EditText)findViewById(R.id.dof_inputAperture);
                String apertureString = apertureInput.getText().toString();

                validateValues(cocString, distanceString, apertureString);



                double coc = Double.parseDouble(cocString);
                double distance = Double.parseDouble(distanceString);
                double aperture = Double.parseDouble(apertureString);

                calculate(coc, distance, aperture);

                int dofTextID[] = {R.id.dof_txtNearFocalNum, R.id.dof_txtFarFocalNum, R.id.dof_txtHyperNum, R.id.dof_txtDoFNum};
                double calculation[] = {nearFocal, farFocal, hyperFocal, depthOfField};

                for (int i = 0; i < 4; i++) {
                    TextView textView = findViewById(dofTextID[i]);
                    textView.setText(formatM(calculation[i]/1000) + "m");
                }

            }
        });
    }

    // Refer to Stack Overthrow
    // https://stackoverflow.com/questions/5702771/how-to-use-single-textwatcher-for-multiple-edittexts
    private class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private String validateValues(String cocString, String distanceString, String apertureString) {

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