package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.shoppingapplication.IndoorMapView;
import com.example.shoppingapplication.R;

public class indoor_map_simple extends AppCompatActivity {

    private static final ProductLocation[] PRODUCT_LOCATIONS = {
            new ProductLocation("Product 1", 100, 200),
            new ProductLocation("Product 2", 150, 250),
            new ProductLocation("Product 3", 200, 300),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_map_simple);

//        IndoorMapView indoorMapView = findViewById(R.id.indoorMapView);
//        indoorMapView.setProductLocations(PRODUCT_LOCATIONS);
//        indoorMapView.setOnProductClickListener(new IndoorMapView.OnProductClickListener() {
//            @Override
//            public void onProductClick(ProductLocation productLocation) {
//                Toast.makeText(getContext(), productLocation.productName, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private static class ProductLocation {
        String productName;
        int x;
        int y;

        ProductLocation(String productName, int x, int y) {
            this.productName = productName;
            this.x = x;
            this.y = y;
        }
    }
}