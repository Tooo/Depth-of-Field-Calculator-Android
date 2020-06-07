package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmpt276a2.R;
import com.cmpt276a2.model.DOFCalculator;
import com.cmpt276a2.model.Lens;
import com.cmpt276a2.model.LensManager;

import java.text.DecimalFormat;

public class CalculateDOF extends AppCompatActivity {
    private LensManager myLens;
    private int indexLen;

    private double coc;
    private double distance;
    private double aperture;

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
        setupCoC();
        setupCalculateButton();
    }

    private int getLen() {
        Intent intent = getIntent();
        int indexLen = intent.getIntExtra("indexLen", 0);

        TextView textView = (TextView) findViewById(R.id.txtLen);
        String lenString = myLens.get(indexLen).toString();
        textView.setText(lenString);

        return indexLen;
    }

    private void setupCoC() {
        EditText coCInput = (EditText)findViewById(R.id.inputCoC);
        coCInput.setText("0.029");
    }

    private void setupCalculateButton() {
        Button btn = findViewById(R.id.btnCalculate);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText cocInput = (EditText)findViewById(R.id.inputCoC);
                String cocString = cocInput.getText().toString();
                coc = Double.parseDouble(cocString);

                EditText distanceInput = (EditText)findViewById(R.id.inputDist);
                String distanceString = distanceInput.getText().toString();
                distance = Double.parseDouble(distanceString);

                EditText apertureInput = (EditText)findViewById(R.id.inputAperture);
                String apertureString = apertureInput.getText().toString();
                aperture = Double.parseDouble(apertureString);

                calculate();

                TextView nearText = findViewById(R.id.dof_txtNearFocal);
                nearText.setText(formatM(nearFocal/1000) + "m");

                TextView farText = findViewById(R.id.dof_txtFarFocal);
                farText.setText(formatM(farFocal/1000) + "m");

                TextView hyperText = findViewById(R.id.dof_txtHyper);
                hyperText.setText(formatM(hyperFocal/1000) + "m");

                TextView dofText = findViewById(R.id.dof_txtDoF);
                dofText.setText(formatM(depthOfField/1000) + "m");

            }
        });
    }

    private void calculate() {
        Lens len = myLens.get(indexLen);
        double distanceMM = distance*1000;

        hyperFocal = DOFCalculator.hyperFocalDist(len, aperture, coc);
        nearFocal = DOFCalculator.nearFocalPoint(len, distanceMM, aperture, coc);
        farFocal = DOFCalculator.farFocalPoint(len, distanceMM, aperture, coc);
        depthOfField = DOFCalculator.depthOfField(len, distanceMM, aperture, coc);
    }

    // Convert to 2 decimal
    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}