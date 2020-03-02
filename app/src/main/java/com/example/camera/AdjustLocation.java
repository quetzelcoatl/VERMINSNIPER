package com.example.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import static com.example.camera.Configuration.latLng;
import static com.example.camera.Configuration.latLngPosition;
import static com.example.camera.Configuration.latitude;
import static com.example.camera.Configuration.longitude;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class AdjustLocation extends FragmentActivity implements OnMapReadyCallback {


    Location currentLocation;
    Context context999;
    Button OK;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_adjust);
        OK = findViewById(R.id.buttonOK);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OK==v){
                    Intent intent009 = new Intent(getApplicationContext(),GPS.class);
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
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()
                    +""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(AdjustLocation.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        final MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Hold To Adjust Position")
                .draggable(true);
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener(){
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
             @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder gc = new Geocoder(AdjustLocation.this);
                latLngPosition = marker.getPosition();
                double lat = latLngPosition.latitude;
                double lng = latLngPosition.longitude;
                latitude = lat;
                longitude = lng;
                List<Address> list = null;
                try {
                    list = gc.getFromLocation(lat,lng,1);
                } catch (IOException e){
                    e.printStackTrace();
                }
                Address add = list.get(0);
               marker.setTitle(add.getAdminArea()+" "+add.getLocality()+" "+add.getPostalCode());
                marker.setSnippet(add.getCountryName());
                marker.showInfoWindow();
            }
        });

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        googleMap.addMarker(markerOptions);
        latLng = markerOptions.getPosition();
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
