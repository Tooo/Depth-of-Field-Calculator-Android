package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cmpt276a2.R;
import com.cmpt276a2.model.Lens;
import com.cmpt276a2.model.LensManager;

public class AddLens extends AppCompatActivity {
    private LensManager myLens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);

        myLens = LensManager.getInstance();

        setupBackButton();
        setupSaveButton();
    }

    private void setupBackButton() {
        ImageButton btn = findViewById(R.id.add_imgbtnBack);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLens.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupSaveButton() {
        ImageButton btn = findViewById(R.id.add_imgbtnSave);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String message;

                // Make
                EditText makeInput = (EditText)findViewById(R.id.add_inputMake);
                String make = makeInput.getText().toString();

                if (make.length() == 0 ) {
                    message = getResources().getString(R.string.error_make);
                    Toast.makeText(AddLens.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Focal
                EditText makeFocal = (EditText)findViewById(R.id.add_inputFocal);
                String focalString = makeFocal.getText().toString();

                if (focalString.length() == 0 ) {
                    message = getResources().getString(R.string.error_focal_string);
                    Toast.makeText(AddLens.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                int focal = Integer.parseInt(focalString);
                if (focal <= 0) {
                    message = getResources().getString(R.string.error_focal);
                    Toast.makeText(AddLens.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aperture
                EditText makeAperture = (EditText)findViewById(R.id.add_inputAperture);
                String apertureString = makeAperture.getText().toString();

                if (apertureString.length() == 0 ) {
                    message = getResources().getString(R.string.error_focal_string);
                    Toast.makeText(AddLens.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }
                double aperture = Double.parseDouble(apertureString);
                if (aperture < 1.4 ) {
                    message = getResources().getString(R.string.error_aperture);
                    Toast.makeText(AddLens.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                myLens.add(new Lens(make, aperture, focal, R.drawable.len_red));

                Intent intent = new Intent(AddLens.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}