package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmpt276a2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddLens extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);

        setupSaveButton();
        setupCancelButton();
    }

    private void setupSaveButton() {


    }

    private void setupCancelButton() {
        Button btn = findViewById(R.id.btnCancel);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLens.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}