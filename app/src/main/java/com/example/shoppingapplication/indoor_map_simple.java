package com.example.shoppingapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.Toast;

import com.example.shoppingapplication.IndoorMapView;
import com.example.shoppingapplication.R;
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

//    private static final ProductLocation[] PRODUCT_LOCATIONS = {
//            new ProductLocation("Product 1", 100, 200),
//            new ProductLocation("Product 2", 150, 250),
//            new ProductLocation("Product 3", 200, 300),
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_map_simple);

        // Get a reference to the Firebase Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

// Create a reference to the image file you want to retrieve
        String imageUrl = "gs://shopping-application-8b7c0.appspot.com/floorplans/indoormapimage.jpg";
        StorageReference imageRef = storage.getReferenceFromUrl(imageUrl);

// Download the image and convert it to a Bitmap
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // Set the image to an ImageView
                ImageView imageView = findViewById(R.id.indoor_map_view);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors that occur

                // Handle any errors that occurred while downloading the image
                Log.e(TAG, "Error downloading image from Firebase Storage", exception);
            }
        });

//        IndoorMapView indoorMapView = findViewById(R.id.indoorMapView);
//        indoorMapView.setProductLocations(PRODUCT_LOCATIONS);
//        indoorMapView.setOnProductClickListener(new IndoorMapView.OnProductClickListener() {
//            @Override
//            public void onProductClick(ProductLocation productLocation) {
//                Toast.makeText(getContext(), productLocation.productName, Toast.LENGTH_SHORT).show();
//            }
//        });

        ImageView imageView = findViewById(R.id.indoor_map_view);
        EditText productNameEditText = findViewById(R.id.product_name);
        EditText stockNumberEditText = findViewById(R.id.stock_number);
        Button saveProductButton = findViewById(R.id.save_product_button);
        ImageView placeholderIcon = findViewById(R.id.location_icon_image);
        EditText searchProductBar = findViewById(R.id.search_item);
        Button searchProductButton = findViewById(R.id.search_item_button);
        ImageView placeholderPinpoint = findViewById(R.id.location_pinpoint_image);

//        EditText productNameFill = findViewById(R.id.productNameInfFill);

        // Get a reference to the root view of the activity
        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();

                    // Check if the touch event is within the bounds of the image view
                    Rect rect = new Rect();
                    imageView.getGlobalVisibleRect(rect);

                    if (rect.contains((int)x, (int)y)) {
                        // The touch event occurred within the bounds of the image view
                        Toast.makeText(indoor_map_simple.this, "Clicked at x=" + x + ", y=" + y, Toast.LENGTH_SHORT).show();

                        // Place a placeholder icon at the touch event coordinates
                        placeholderIcon.setX(x - 60);
                        placeholderIcon.setY(y - 60);
                        placeholderIcon.setVisibility(View.VISIBLE);
                    }
                }
                return true;
            }
        });

//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    // Get the X and Y coordinates of the touch event
//                    float x = event.getX();
//                    float y = event.getY();
//
//                    // Show the x and y coordinates in a toast message
//                    Toast.makeText(indoor_map_simple.this, "Clicked at x=" + x + ", y=" + y, Toast.LENGTH_SHORT).show();
//
//                    // Save the coordinates and product information to Firebase
//                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
//                    String productId = databaseRef.push().getKey();
//                    String productName = productNameEditText.getText().toString();
//                    String stockNumberString = stockNumberEditText.getText().toString();
//                    int stockNumber = Integer.parseInt(stockNumberString);
//                    Product product = new Product(productId, productName, stockNumber, x, y);
//                    databaseRef.child(productId).setValue(product);
//
//                    // Place a placeholder icon at the touch event coordinates
//                    ImageView placeholderIcon = findViewById(R.id.location_icon_image);
//                    placeholderIcon.setX(event.getX());
//                    placeholderIcon.setY(event.getY());
//                    placeholderIcon.setVisibility(View.VISIBLE);
//
//                    return true;
//                }
//                return false;
//            }
//        });

        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check initially if placeholder icon is visible, indicating a location has been selected
                if (placeholderIcon.getVisibility() == View.VISIBLE) {
                    // The ImageView is visible

                    // Get the X and Y coordinates of the last touch event on the image
                    float x = placeholderIcon.getX();
                    float y = placeholderIcon.getY();

                    // Save the coordinates and product information to Firebase
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
                    String productId = databaseRef.push().getKey();
                    String productName = productNameEditText.getText().toString();
                    String stockNumberString = stockNumberEditText.getText().toString();
                    int stockNumber = Integer.parseInt(stockNumberString);
                    Product product = new Product(productId, productName, stockNumber, x, y);
                    databaseRef.child(productId).setValue(product);

                    // Clear the product name and stock number fields
                    productNameEditText.setText("");
                    stockNumberEditText.setText("");

                    // Clear the placeholder icon
                    placeholderIcon.setVisibility(View.INVISIBLE);

                    // Inform user the product has been added successfully
                    Toast.makeText(indoor_map_simple.this, productName + " has been added", Toast.LENGTH_SHORT).show();
                } else {
                    // The ImageView is not visible

                    // Inform user they need to select a location on the indoor map
                    Toast.makeText(indoor_map_simple.this, "Please select a location for the product", Toast.LENGTH_SHORT).show();
                }

            }
        });

        searchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the coordinates and product information from Firebase
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
                Query query = databaseRef.orderByChild("productName").equalTo(searchProductBar.getText().toString());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            DataSnapshot productSnapshot = snapshot.getChildren().iterator().next();
                            Product product = productSnapshot.getValue(Product.class);

                            // Set the X and Y coordinates of the placeholder pinpoint to the location stored in the database
                            placeholderPinpoint.setX(product.getX());
                            placeholderPinpoint.setY(product.getY() - 70);

                            // Show the placeholder pinpoint
                            placeholderPinpoint.setVisibility(View.VISIBLE);

                            // Inform user the product has been found
                            Toast.makeText(indoor_map_simple.this, product.getProductName() + " has been found", Toast.LENGTH_SHORT).show();
                        } else {
                            // Inform user the product could not be found
                            Toast.makeText(indoor_map_simple.this, "Product not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error here
                        if (error != null) {
                            Log.e("TAG", "Database error: " + error.getMessage());
                        } else {
                            Log.e("TAG", "Unknown database error occurred");
                        }
                    }
                });

                // Clear the search bar
                searchProductBar.setText("");
            }
        });
    }

}