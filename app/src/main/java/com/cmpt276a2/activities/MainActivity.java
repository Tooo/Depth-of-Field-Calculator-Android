package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276a2.R;
import com.cmpt276a2.model.Lens;
import com.cmpt276a2.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private LensManager myLens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLens = LensManager.getInstance();

        populateLenList();
        populateListView();
        setupLensClick();
        setupAddButton();
    }

    private void populateLenList() {
        myLens.add(new Lens("Canon", 1.8, 50, R.drawable.len_blue));
        myLens.add(new Lens("Tamron", 2.8, 90, R.drawable.len_green));
        myLens.add(new Lens("Sigma", 2.8, 200, R.drawable.len_orange));
        myLens.add(new Lens("Nikon", 4, 200, R.drawable.len_yellow));
    }

    private void populateListView() {
        ArrayAdapter<Lens> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById((R.id.listLens));
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Lens> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, myLens.getLens());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If ConvertView is null
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            // Find Lens
            Lens currentLens = myLens.get(position);

            // Image
            ImageView imageView = itemView.findViewById(R.id.item_imgLen);
            imageView.setImageResource(currentLens.getIdIcon());

            // Make
            TextView makeText = itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentLens.getMake());

            // Focal Length
            TextView focalText = itemView.findViewById(R.id.item_txtFocal);
            focalText.setText(currentLens.getFocalLength() + "mm");

            // Aperture
            TextView apertureText = itemView.findViewById(R.id.item_txtAperture);
            apertureText.setText("F"+currentLens.getMaxAperture());

            return itemView;
        }
    }

    private void setupLensClick() {
        ListView list = (ListView) findViewById(R.id.listLens);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Lens clickedLens = myLens.get(position);
                String message = "You clicked position " + position + "\nWhich is lens make " + clickedLens.getMake();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAddButton () {
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLens.class);
                startActivity(intent);
            }
        });
    }
}