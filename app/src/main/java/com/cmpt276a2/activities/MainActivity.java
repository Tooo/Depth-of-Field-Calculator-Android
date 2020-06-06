package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cmpt276a2.R;
import com.cmpt276a2.model.Lens;
import com.cmpt276a2.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private LensManager manager = new LensManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLenList();
        setupAddButton();
    }

    private void populateLenList() {
        manager.add(new Lens("Canon", 1.8, 50, R.drawable.len_blue));
        manager.add(new Lens("Tamron", 2.8, 90, R.drawable.len_green));
        manager.add(new Lens("Sigma", 2.8, 200, R.drawable.len_orange));
        manager.add(new Lens("Nikon", 4, 200, R.drawable.len_yellow));
    }

    private void setupAddButton () {
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLens.class);
                startActivity(intent);
            }
        });
    }
}