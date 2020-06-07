package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmpt276a2.R;
import com.cmpt276a2.model.Lens;
import com.cmpt276a2.model.LensManager;

public class AddLens extends AppCompatActivity {
    private LensManager myLens;
    private int indexLen;

    private String[] colours = {"Red", "Orange", "Yellow", "Green", "Blue"};
    private int[] iconList = {R.drawable.len_red, R.drawable.len_orange, R.drawable.len_yellow, R.drawable.len_green, R.drawable.len_blue};
    private int iconID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);

        myLens = LensManager.getInstance();

        Intent intent = getIntent();
        indexLen = intent.getIntExtra("indexLen", 0);

        setupBackButton();
        setupIconSelector();
        setupSaveButton();

        if (indexLen != -1) {
            loadLens();
        }
    }

    private void loadLens() {
        Lens len = myLens.get(indexLen);

        // Make
        EditText makeInput = (EditText)findViewById(R.id.add_inputMake);
        makeInput.setText(len.getMake());

        // Focal
        EditText focalInput = (EditText)findViewById(R.id.add_inputFocal);
        focalInput.setText("" +len.getFocalLength());

        // Aperture
        EditText apertureInput = (EditText)findViewById(R.id.add_inputAperture);
        apertureInput.setText("" + len.getMaxAperture());

        Spinner iconSpinner = findViewById(R.id.add_spinIcon);
        iconID = len.getIdIcon();
        int index = 0;
        for (int i = 0; i<iconList.length; i++) {
            if (iconList.equals(iconID)) {
                index = i;
                break;
            }
        }
        iconSpinner.setSelection(index, false);

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

    private void setupIconSelector() {
        Spinner spinner = findViewById(R.id.add_spinIcon);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddLens.this, android.R.layout.simple_spinner_item, colours);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = findViewById(R.id.add_imgLen);
                iconID = iconList[position];
                imageView.setImageResource(iconID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

                if (indexLen == -1) {
                    myLens.add(new Lens(make, aperture, focal, iconID));
                } else {
                    saveOddLens(make, focal, aperture);
                }

                Intent intent = new Intent(AddLens.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateValues(String make, String focalString, String apertureString) {
        return true;
    }

    private void saveOddLens(String make, int focal, double aperture) {
        Lens len = myLens.get(indexLen);

        len.setMake(make);
        len.setFocalLength(focal);
        len.setMaxAperture(aperture);
        len.setIdIcon(iconID);
    }
}