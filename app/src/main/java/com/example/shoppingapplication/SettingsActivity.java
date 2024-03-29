package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void launchMaps(View v){
        //launch the map page

        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void launchIndoorMap2(View v){
        //launch the login page

        Intent i = new Intent(this, indoor_map_simple.class);
        startActivity(i);
    }

    public void launchIndoorMap3(View v){
        //launch the login page

        Intent i = new Intent(this, indoor_map_simple_user.class);
        startActivity(i);
    }
//    public void launchShoppingList(View v){
//        //launch the shopping list page
//
//        Intent i = new Intent(this, ShoppingListActivity.class);
//        startActivity(i);
//    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity2.class));
        finish();
    }
}