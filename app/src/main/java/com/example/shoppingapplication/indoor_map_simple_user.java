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

public class indoor_map_simple_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_map_simple_user);

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
        EditText searchProductBar = findViewById(R.id.search_item);
        Button searchProductButton = findViewById(R.id.search_item_button);
        ImageView placeholderPinpoint = findViewById(R.id.location_pinpoint_image);
        TextView productInfo = findViewById(R.id.productInfo);

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

                            // get the product name and stock number from the snapshot
                            String productName = product.getProductName();
                            int stockNumber = product.getStockNumber();

                            // set the attributes of the products
                            productInfo.setText("Product name: " + productName + ", Number in stock: " + stockNumber);

                            //make the constraint layout with product details visible
                            productInfo.setVisibility(View.VISIBLE);

                            // inform user the product has been found
                            Toast.makeText(indoor_map_simple_user.this, product.getProductName() + " has been found", Toast.LENGTH_SHORT).show();
                        } else {
                            // inform user the product could not be found
                            Toast.makeText(indoor_map_simple_user.this, "Product not found", Toast.LENGTH_SHORT).show();

                            // make the pinpoint invisible
                            placeholderPinpoint.setVisibility(View.INVISIBLE);
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