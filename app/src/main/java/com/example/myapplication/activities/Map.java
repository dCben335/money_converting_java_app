package com.example.myapplication.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.mapbox.mapboxsdk.WellKnownTileServer;
import androidx.annotation.NonNull;
import com.example.myapplication.R;
import com.example.myapplication.globals.layout.Main;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;


public class Map extends Main {
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String styleUrl = initiateMapSettings();
        loadContent(this, R.layout.map);
        injectMap(savedInstanceState, styleUrl);
    }

    protected String initiateMapSettings() {
        String apiKey = "uBctdXB4pPaJOqBcg0O8";
        String mapId = "a58748e5-0742-4a46-912f-02da61abd2f1";
        String styleUrl = "https://api.maptiler.com/maps/" + mapId + "/style.json?key=" + apiKey;
        Mapbox.getInstance(this,  apiKey, WellKnownTileServer.MapLibre);

        return styleUrl;
    }

    protected void injectMap(Bundle savedInstanceState, String styleUrl) {

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(styleUrl, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Icon customMarkerIcon = getMarkerIcon(R.drawable.marker);
                        addMarker(
                                mapboxMap,
                                new LatLng(0, 0),
                                "Votre position",
                                "",
                                customMarkerIcon
                        );

                        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                                .target(new LatLng(47, 5))
                                .zoom(25.5)
                                .build()
                        );
                    }
                });
            }
        });


    }
    private void addMarker(MapboxMap mapboxMap, LatLng latLng, String title, String description, Icon icon) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(description)
                .icon(icon);

        Marker marker = mapboxMap.addMarker(markerOptions);


        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            public boolean onMarkerClick(@NonNull Marker marker) {
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.0));
                return true;
            }
        });
    }

    private Icon getMarkerIcon(int src) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), src);

        int width = 50;
        int height = 50;
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false);

        Icon customMarkerIcon = IconFactory.getInstance(Map.this).fromBitmap(resizedBitmap);
        return customMarkerIcon;
    }







    // Add lifecycle methods for MapView
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
