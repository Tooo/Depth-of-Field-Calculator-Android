package com.cmpt276a2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cmpt276a2.R;
import com.cmpt276a2.model.LensManager;

public class CalculateDOF extends AppCompatActivity {
    private LensManager myLens;
    int indexLen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_d_o_f);

        myLens = LensManager.getInstance();
        indexLen = getLen();

    }

    private int getLen() {
        Intent intent = getIntent();
        int indexLen = intent.getIntExtra("indexLen", 0);

        TextView textView = (TextView) findViewById(R.id.txtLen);
        String lenString = myLens.get(indexLen).toString();
        textView.setText(lenString);

        return indexLen;
    }


}