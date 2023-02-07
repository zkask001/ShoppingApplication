package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Shopping Application - Home"); //change name displayed at top of main activity
    }

    public void toggle(View v){

    }

//    public void disable(View v){ //disables button + changes text of button
//        v.setEnabled(false);
//        Log.d("success","button disabled"); //for debugging, can see in Logcat
//        Button b = (Button) v;
//        b.setText("Disabled button");
//    }

//    public void disable2(View v){ //disables button3 when button pressed + changes text
//        View myView = findViewById(R.id.button3);
//        myView.setEnabled(false);
//        Button button = (Button) myView;
//        button.setText("New Disabled");
//    }

    public void handleText(View v){ //get text from a text field + change another text with the input
        TextView t = findViewById(R.id.source);
        String input = t.getText().toString();
        ((TextView)findViewById(R.id.output)).setText(input);

        Toast.makeText(this, "Alert", Toast.LENGTH_LONG).show(); //shows alert
                                    //or input
        Log.d("info", input);
    }

    public void launchSettings(View v){
        //launch a new activity

        Intent i = new Intent(this, SettingsActivity.class);
        //this refers to current activity (main activity in this case)

        //pass data between activities:
        String message = ((EditText)findViewById(R.id.editTextTextPersonName2)).getText().toString();
        i.putExtra("COOL", message);

        startActivity(i);
    }

    public void launchLogin(View v){
        //launch the login page

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void launchRegister(View v){
        //launch the register page

        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void launchLoginNew(View v){
        Intent i = new Intent(this, LoginActivity2.class);
        startActivity(i);
    }

    public void launchIndoorMap(View v){
        //launch the login page

        Intent i = new Intent(this, IndoorMapActivity.class);
        startActivity(i);
    }

}