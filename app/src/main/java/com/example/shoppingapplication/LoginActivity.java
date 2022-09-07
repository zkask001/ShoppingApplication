package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //set variables for each view
    EditText _txtUser, _txtPass;
    Button _btnLogin;
    Spinner _spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set each variable to its corresponding view item on the activity
        _txtPass = (EditText) findViewById(R.id.txtPass);
        _txtUser = (EditText) findViewById(R.id.txtUser);
        _btnLogin = (Button) findViewById(R.id.btnLogin);
        _spinner = (Spinner) findViewById(R.id.spinner);

        //User types set from strings.xml
        //arrayAdapter created to plug in user types from strings.xml and set format
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        _spinner.setAdapter(adapter);

        //set login action dependant on user type
        _btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //save user selected item (user or supermarket)
                String item = _spinner.getSelectedItem().toString();
                //if user selected, open up user activity using an intent
                if(_txtUser.getText().toString().equals("User") && _txtPass.getText().toString().equals("User") && item.equals("User")){
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(intent);
                }
                //if supermarket selected, open up supermarket activity using an intent
                else if(_txtUser.getText().toString().equals("Supermarket") && _txtPass.getText().toString().equals("Supermarket") && item.equals("Supermarket"))
                {
                    Intent intent = new Intent(LoginActivity.this, SupermarketActivity.class);
                    startActivity(intent);
                }
                //if an invalid option/no option selected, error popup will show
                else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}