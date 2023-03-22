package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class IndoorMapActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout file as the content view.
        setContentView(R.layout.activity_indoor_map2);

        //get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    //get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));

        LatLng newarkLatLng = new LatLng(51.336916,-0.117010);

        //ground overlay to add an indoor map image to google maps
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.indoor_map))
                .position(newarkLatLng, 70f, 60f);
        googleMap.addGroundOverlay(newarkMap);

        //move camera on start to the desired location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newarkLatLng, 17));

        //add zoom controls to map
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //allow user to zoom in and out using pinch gestures
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}