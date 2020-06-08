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
        setupDeleteButton();

        if (indexLen >= 0 && indexLen < myLens.size()) {
            loadLens();
        }
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
                int[] inputID = {R.id.add_inputMake, R.id.add_inputFocal, R.id.add_inputAperture};
                String[] values = new String[inputID.length];

                for (int i = 0; i < inputID.length; i++) {
                    EditText input = findViewById(inputID[i]);
                    values[i] = input.getText().toString();
                }

                // Validate values
                String errorMessage = validateValues(values);
                if (errorMessage.length() != 0) {
                    Toast.makeText(AddLens.this, errorMessage, Toast.LENGTH_SHORT).show();
                    return;
                }

                int focal = Integer.parseInt(values[1]);
                double aperture = Double.parseDouble(values[2]);

                // New or old Lens
                if (indexLen == -1) {
                    myLens.add(new Lens(values[0], aperture, focal, iconID));
                } else {
                    saveOddLens(values[0], focal, aperture);
                }

                Intent intent = new Intent(AddLens.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String validateValues(String[] values) {
        int[] errorMessages = {R.string.error_make, R.string.error_focal_empty, R.string.error_aperture_empty};

        // Validate empty String
        for (int i = 0; i<values.length; i++) {
            if (values[i].length() == 0) {
                return getResources().getString(errorMessages[i]);
            }
        }

        // Validate Focal
        try {
            int focal = Integer.parseInt(values[1]);
            if (focal <= 0) {
                return getResources().getString(R.string.error_focal);
            }
        }
        catch (NumberFormatException e) {
            return getResources().getString(R.string.error_focal_string);
        }

        // Validate Aperture
        try {
            double aperture = Double.parseDouble(values[2]);
            if (aperture < 1.4 ) {
                return getResources().getString(R.string.error_aperture);
            }
        }
        catch (NumberFormatException e) {
            return getResources().getString(R.string.error_aperture_string);
        }

        return "";
    }

    private void saveOddLens(String make, int focal, double aperture) {
        Lens len = myLens.get(indexLen);

        len.setMake(make);
        len.setFocalLength(focal);
        len.setMaxAperture(aperture);
        len.setIdIcon(iconID);
    }

    private void setupDeleteButton() {
        ImageButton deleteButton = findViewById(R.id.add_imgbtnDelete);

        // Hide Delete Button
        if (indexLen == -1) {
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete Len
                Lens len = myLens.get(indexLen);
                myLens.remove(len);

                Intent intent = new Intent(AddLens.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadLens() {
        Lens len = myLens.get(indexLen);

        // Load values in input
        int[] inputID = {R.id.add_inputMake, R.id.add_inputFocal, R.id.add_inputAperture};
        String[] values = {len.getMake(), "" +len.getFocalLength(), "" + len.getMaxAperture()};

        for (int i = 0; i < inputID.length; i++) {
            EditText input = findViewById(inputID[i]);
            input.setText(values[i]);
        }

        // Load image and change spinner selection
        Spinner iconSpinner = findViewById(R.id.add_spinIcon);
        iconID = len.getIdIcon();
        int index = 0;
        for (int i = 0; i<iconList.length; i++) {
            if (iconList[i] == iconID) {
                index = i;
                break;
            }
        }
        iconSpinner.setSelection(index, false);
    }
}