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
    }

    private void populateListView() {
        ArrayAdapter<Lens> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById((R.id.main_listLens));
        list.setAdapter(adapter);
    }

    // Refer to Brain Fraser video: ListView with Images and Text: Android Programming
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
        ListView list = (ListView) findViewById(R.id.main_listLens);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CalculateDOF.class);
                intent.putExtra("indexLen", position);
                startActivity(intent);
            }
        });
    }

    private void setupAddButton() {
        FloatingActionButton fab = findViewById(R.id.main_fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLens.class);

                // New Lens = -1
                intent.putExtra("indexLen", -1);
                startActivity(intent);
            }
        });
    }
}