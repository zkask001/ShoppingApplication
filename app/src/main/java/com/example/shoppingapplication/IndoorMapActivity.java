package com.example.shoppingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


//  package com.example.mapsetup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// Implement OnMapReadyCallback.
public class IndoorMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_indoor_map);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}

//public class IndoorMapActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_indoor_map);
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                LatLng location = new LatLng(37.7749, -122.4194);
//                googleMap.addMarker(new MarkerOptions().position(location).title("Marker in San Francisco"));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//            }
//        });
//    }
//}

//public class IndoorMapActivity extends Activity {
//
//    // Google Map
//    private GoogleMap map;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_indoor_map);
//
//        // Initialize the map
////        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.35, -122.0), 18));
//
//        // Add markers for food items to collect
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(37.3525, -122.0667))
//                .title("Bread")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(37.3530, -122.0670))
//                .title("Milk")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(37.3535, -122.0673))
//                .title("Eggs")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
////         Calculate and display the efficient route
//        List<LatLng> points = TSP.getShortestRoute(map.getProjection(), map.getMyLocation(), map.getMarkers());
//        map.addPolyline(new PolylineOptions()
//                .addAll(points)
//                .width(10)
//                .color(Color.RED));
//    }
//
//}

