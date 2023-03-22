package com.example.shoppingapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.shoppingapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get the SupportMapFragment and notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // set up the marker for the store
        LatLng storeLocation = new LatLng(51.4067, 0.0145);
        Marker storeMarker = mMap.addMarker(new MarkerOptions().position(storeLocation).title("Food Store"));
        storeMarker.setVisible(false); // Hide the marker by default

        // set up the search bar
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // check if the query matches "Sainsbury's"
                if (query.equals("Sainsbury's")) {
                    // show the marker and move the camera to its location
                    storeMarker.setVisible(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // set up the marker click listener
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(storeMarker)) {
                    // launch new activity for the food store
                    Intent intent = new Intent(MapsActivity.this, indoor_map_simple.class);
                    intent.putExtra("storeName", "Food Store");
                    intent.putExtra("storeLocation", storeLocation);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}