package com.example.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import java.lang.*;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import static com.example.camera.Configuration.latLng;
import static com.example.camera.Configuration.latLngPosition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import static com.example.camera.Configuration.uImages;
import static com.example.camera.Configuration.check;
import static com.example.camera.Configuration.ulatitude;
import static com.example.camera.Configuration.z;
import static com.example.camera.Configuration.ulongitude;
import static com.example.camera.Configuration.doublecheck;
import static com.example.camera.Configuration.ulocation;
import static java.lang.Thread.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class MAP extends FragmentActivity implements OnMapReadyCallback {
    Location currentLocation;
    Switch heatmap;
    Button OK;
    Marker marker;
    TileOverlay mOverlay;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=100;
    private HeatmapTileProvider mProvider;
    int i,q;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapviewjson);
        OK = findViewById(R.id.buttonOK2);
        heatmap = findViewById(R.id.heatmap);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OK==v){
                    i=0;
                    check=0;
                    Intent intent009 = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent009);
                }
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()
                            +""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MAP.this);
                }
            }
        });
    }
    int[] colors = {
            Color.rgb(102, 225, 0), // green
            Color.rgb(255, 0, 0)    // red
    };

    float[] startPoints = {
            0.2f, 1f
    };

    Gradient gradient = new Gradient(colors, startPoints);
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final GoogleMap mMap = googleMap;
        for(i=0;i<check;i++) {
            doublecheck[i] = i;
            latLng = new LatLng(ulatitude[i], ulongitude[i]);
            /** final MarkerOptions markerOptions = new MarkerOptions().position(latLng)
             .title(String.valueOf(i));*/

            //BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_launchersmall);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(i)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
               @Override
                public boolean onMarkerClick(Marker marker) {

                    z = Integer.valueOf(marker.getTitle());
                    MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
                    googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
                    return false;
                }
            });

            heatmap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bchecked) {
                    if(bchecked){
                        for(q=0;q<check;q++) {
                        latLng = new LatLng(ulatitude[q], ulongitude[q]);
                        mProvider = new HeatmapTileProvider.Builder()
                                .data(Collections.singleton(latLng))
                                .gradient(gradient)
                                .radius(15)
                                .build();
                            mOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                        }
                    }
                    else{
                        for(q=0;q<check;q++) {
                            mOverlay.remove();
                        }

                    }
                }
            });
        //    MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
           // googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);
     //       googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
         //   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        //    googleMap.addMarker(markerOptions);
        //    latLng = markerOptions.getPosition();

          //  MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());
         //   googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;

        }
    }
}
