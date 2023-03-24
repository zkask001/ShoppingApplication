package com.example.shoppingapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class indoor_map_simple extends AppCompatActivity {
    public void launchSettings(View v){
        //launch the settings page

        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_map_simple);

        // get a reference to the Firebase Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // create a reference to the image file you want to retrieve
        String imageUrl = "gs://shopping-application-8b7c0.appspot.com/floorplans/indoormapimage.jpg";
        StorageReference imageRef = storage.getReferenceFromUrl(imageUrl);

        // download the image and convert it to a Bitmap
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // set the image to an ImageView
                ImageView imageView = findViewById(R.id.indoor_map_view);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                // handle errors that occurred while downloading the image
                Log.e(TAG, "Error downloading image from Firebase Storage", exception);
            }
        });

        // retrieve relevant views from layout file
        ImageView imageView = findViewById(R.id.indoor_map_view);
        EditText productNameEditText = findViewById(R.id.product_name);
        EditText stockNumberEditText = findViewById(R.id.stock_number);
        Button saveProductButton = findViewById(R.id.save_product_button);
        ImageView placeholderIcon = findViewById(R.id.location_icon_image);
        EditText searchProductBar = findViewById(R.id.search_item);
        Button searchProductButton = findViewById(R.id.search_item_button);
        ImageView placeholderPinpoint = findViewById(R.id.location_pinpoint_image);
        TextView productInfo = findViewById(R.id.productInfo);


        // get a reference to the root view of the activity
        View root = findViewById(android.R.id.content);
        // set an on touch listener to detect screen clicks on image while selecting product location
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();

                    // check if the touch event is within the bounds of the image view
                    Rect rect = new Rect();
                    imageView.getGlobalVisibleRect(rect);

                    if (rect.contains((int)x, (int)y)) {
                        // the touch event occurred within the bounds of the image view
                        Toast.makeText(indoor_map_simple.this, "Clicked at x=" + x + ", y=" + y, Toast.LENGTH_SHORT).show();

                        // place a placeholder icon at the touch event coordinates
                        placeholderIcon.setX(x - 60);
                        placeholderIcon.setY(y - 60);
                        placeholderIcon.setVisibility(View.VISIBLE);

                        // clear the placeholder pin
                        placeholderPinpoint.setVisibility(View.INVISIBLE);
                    }
                }
                return true;
            }
        });

        // set an on touch listener for the save-product button
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check initially if placeholder icon is visible, indicating a location has been selected
                if (placeholderIcon.getVisibility() == View.VISIBLE) {

                    // get the X and Y coordinates of the last touch event on the image
                    float x = placeholderIcon.getX();
                    float y = placeholderIcon.getY();

                    // save the coordinates and product information to Firebase
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
                    String productId = databaseRef.push().getKey();
                    String productName = productNameEditText.getText().toString();
                    String stockNumberString = stockNumberEditText.getText().toString();
                    int stockNumber = Integer.parseInt(stockNumberString);
                    Product product = new Product(productId, productName, stockNumber, x, y);
                    databaseRef.child(productId).setValue(product);

                    // clear the product name and stock number fields
                    productNameEditText.setText("");
                    stockNumberEditText.setText("");

                    // clear the placeholder icon
                    placeholderIcon.setVisibility(View.INVISIBLE);

                    // inform user the product has been added successfully
                    Toast.makeText(indoor_map_simple.this, productName + " has been added", Toast.LENGTH_SHORT).show();
                } else {

                    // inform user they need to select a location on the indoor map
                    Toast.makeText(indoor_map_simple.this, "Please select a location for the product", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // set on click listener for the search-product button
        searchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // retrieve the coordinates and product information from Firebase
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
                Query query = databaseRef.orderByChild("productName").equalTo(searchProductBar.getText().toString());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            DataSnapshot productSnapshot = snapshot.getChildren().iterator().next();
                            Product product = productSnapshot.getValue(Product.class);

                            // set the X and Y coordinates of the placeholder pinpoint to the location stored in the database
                            placeholderPinpoint.setX(product.getX());
                            placeholderPinpoint.setY(product.getY() - 70);

                            // show the placeholder pinpoint
                            placeholderPinpoint.setVisibility(View.VISIBLE);

                            // clear the placeholder icon
                            placeholderIcon.setVisibility(View.INVISIBLE);

                            // get the product name and stock number from the snapshot
                            String productName = product.getProductName();
                            int stockNumber = product.getStockNumber();

                            // set the attributes of the products
                            productInfo.setText("Product name: " + productName + ", Number in stock: " + stockNumber);

                            //make the constraint layout with product details visible
                            productInfo.setVisibility(View.VISIBLE);

                            // inform user the product has been found
                            Toast.makeText(indoor_map_simple.this, product.getProductName() + " has been found", Toast.LENGTH_SHORT).show();
                        } else {
                            // inform user the product could not be found
                            Toast.makeText(indoor_map_simple.this, "Product not found", Toast.LENGTH_SHORT).show();

                            // make the pinpoint invisible
                            placeholderPinpoint.setVisibility(View.INVISIBLE);
                            // clear the placeholder icon
                            placeholderIcon.setVisibility(View.INVISIBLE);
                            // clear product information
                            productInfo.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // handle database errors
                        if (error != null) {
                            Log.e("TAG", "Database error: " + error.getMessage());
                        } else {
                            Log.e("TAG", "Unknown database error occurred");
                        }
                    }
                });

                // clear the search bar
                searchProductBar.setText("");
            }
        });
    }

}