package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //get data from another activity:
        Intent i = getIntent();
        String message = i.getStringExtra("COOL");
        ((TextView)findViewById(R.id.displayData)).setText(message);

    }
}