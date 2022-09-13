package com.example.shoppingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity2 extends AppCompatActivity {

    //variables for each view initialised
    EditText email, password;
    Button loginBtn, goToRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        goToRegister = findViewById(R.id.goToRegister);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(email);
                checkField(password);

                //if fields are valid, log in user
                if(valid){
                    fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity2.this,"Logged in Successfully", Toast.LENGTH_SHORT).show();
                            //redirect user based on the user type (customer/supermarket):
                            //check user type from user selection data at registration
                            checkUserType(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }

    private void checkUserType(String uid){
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract data from document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
                //identify user type
                if(documentSnapshot.getString("isUser") != null){
                    //user is customer

                    startActivity(new Intent(getApplicationContext(),UserActivity.class));
                    finish();
                }

                if(documentSnapshot.getString("isSupermarket") != null){
                    //user is supermarket

                    startActivity(new Intent(getApplicationContext(),SupermarketActivity.class));
                    finish();
                }
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

    //check that no other user is logged in + check data from the logged in user
    public void onStart() {

        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("isSupermarket") != null){
                        startActivity(new Intent(getApplicationContext(), SupermarketActivity.class));
                        finish();//user can't go back using back button
                    }
                    if(documentSnapshot.getString("isUser") != null){
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        finish();//user can't go back using back button
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
                    finish();
                }
            });
        }
    }
}