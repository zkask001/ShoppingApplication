package com.example.shoppingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //register activity components initialised
    EditText fullName, email, password, phone;
    Button createAccountBtn, goToLogin;
    CheckBox isUserBox, isSupermarketBox;
    boolean valid = true;
    //firebase authentication and firestore database variables set
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialise Firebase variables
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //variables set for each view using view IDs
        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        createAccountBtn = findViewById(R.id.createAccountBtn);
        goToLogin = findViewById(R.id.goToLogin);

        isUserBox = findViewById(R.id.isUser);
        isSupermarketBox = findViewById(R.id.isSupermarket);

        //check box logic (user can only check one selection box)
        isUserBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isSupermarketBox.setChecked(false);
                }
            }
        });
        isSupermarketBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isUserBox.setChecked(false);
                }
            }
        });

        //after clicking register button, field checks are made
        createAccountBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //run method to check if each field filled in
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);

                //selection validation check (customer/supermarket)
                if(!(isUserBox.isChecked() || isSupermarketBox.isChecked())){
                    Toast.makeText(RegisterActivity.this, "Select Your User Type", Toast.LENGTH_SHORT).show();
                    return;
                }


        //check if data entered by user is valid
        if(valid){
            //if valid, user registration starts

            //create a user using entered email and password
            fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                //onSuccessListener added to trigger a message if user created successfully and redirect user to login activity
                @Override
                public void onSuccess(AuthResult authResult) {

                    //save current user being created (to retrieve user ID later)
                    FirebaseUser user = fAuth.getCurrentUser();

                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                    //store other information about user in the database:
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("FullName",fullName.getText().toString());
                    userInfo.put("UserEmail",email.getText().toString());
                    userInfo.put("PhoneNumber",phone.getText().toString());
                    //specify whether the user is a client user
                    if(isSupermarketBox.isChecked()){
                        userInfo.put("isSupermarket", "1");
                    }
                    if(isUserBox.isChecked()){
                        userInfo.put("isUser", "1");
                    }

                    //store the data in the database
                    df.set(userInfo);

                    //send user to the right activity for their selected user type
                    if(isUserBox.isChecked()){
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        finish();//user can't go back using back button
                    }
                    if(isSupermarketBox.isChecked()){
                        startActivity(new Intent(getApplicationContext(), SupermarketActivity.class));
                        finish();//user can't go back using back button
                    }

                }
                //OnFailureListener for when user account not created
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            }
            }

        });

//        redirect user to login screen after registration
        goToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
            }

        });
    }

    //method to check if all fields are filled in by user
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            //display error message if all fields are not filled in
            textField.setError("Error");
            valid = false;
        }else{
            valid = true;
        }

        return valid;
    }
}