package com.example.shoppingapplication;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class TSP {

    public static List<LatLng> getShortestRoute(List<LatLng> points) {
        // Initialize the shortest route to a very large value
        double shortestRoute = Double.MAX_VALUE;
        List<LatLng> shortestRoutePoints = null;

        // Generate all permutations of the points
        List<List<LatLng>> permutations = permute(points);
        for (List<LatLng> permutation : permutations) {
            // Calculate the distance of this permutation
            double distance = 0;
            for (int i = 0; i < permutation.size() - 1; i++) {
                distance += distance(permutation.get(i), permutation.get(i + 1));
            }
            // If this permutation is shorter than the current shortest route, update the shortest route
            if (distance < shortestRoute) {
                shortestRoute = distance;
                shortestRoutePoints = permutation;
            }
        }

        return shortestRoutePoints;
    }

    private static double distance(LatLng p1, LatLng p2) {
        // Implement a function to calculate the distance between two points using their latitude and longitude
        double earthRadius = 6371; // Radius of the earth in kilometers
        double lat1 = Math.toRadians(p1.latitude);
        double lat2 = Math.toRadians(p2.latitude);
        double lng1 = Math.toRadians(p1.longitude);
        double lng2 = Math.toRadians(p2.longitude);
        double diffLat = lat2 - lat1;
        double diffLng = lng2 - lng1;
        double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(diffLng / 2) * Math.sin(diffLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c; // Distance in kilometers
    }

    private static List<List<LatLng>> permute(List<LatLng> points) {
        List<List<LatLng>> permutations = new ArrayList<>();
        permute(points, new ArrayList<LatLng>(), permutations);
        return permutations;
    }

    private static void permute(List<LatLng> points, List<LatLng> currentPermutation, List<List<LatLng>> permutations) {
        if (points.isEmpty()) {
            permutations.add(currentPermutation);
        } else {
            for (int i = 0; i < points.size(); i++) {
                List<LatLng> newPoints = new ArrayList<>(points);
                newPoints.remove(i);
                List<LatLng> newPermutation = new ArrayList<>(currentPermutation);
                newPermutation.add(points.get(i));
                permute(newPoints, newPermutation, permutations);
            }
        }
    }


}
