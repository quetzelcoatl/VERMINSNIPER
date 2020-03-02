package com.example.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.camera.Configuration.latLng;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.camera.Configuration.latitude;
import static com.example.camera.Configuration.longitude;
import static com.example.camera.Configuration.templatitude;
import static com.example.camera.Configuration.templongitude;
import static com.example.camera.Configuration.locationfinal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPS extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    TextView latitude99;
    TextView longitude99;
    Button goback;
    Button showlocation;
    Location currentLocation;
    private GoogleApiClient googleApiClient;
    public FusedLocationProviderClient fusedLocationProviderClient;
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps2);
        latitude99 = findViewById(R.id.textViewLATITUDE99);
        longitude99 = findViewById(R.id.textViewLONGITUDE99);
        goback = findViewById(R.id.buttongoback);
        showlocation = findViewById(R.id.buttonshowlocation);
        showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==showlocation){
                    Intent intent007 = new Intent(getApplicationContext(),AdjustLocation.class);
                    startActivity(intent007);
                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==goback)
                {
                    if(latitude==0&&longitude==0){
                        latitude=templatitude;
                        longitude=templongitude;
                    }
                    Intent intent = new Intent(getApplicationContext(),Description.class);
                    startActivity(intent);
                }
            }
        });
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
   //     fetch lastlocation;

    }
    @Override
    protected void onStart(){
        super.onStart();
        googleApiClient.connect();
    }
    @Override
    protected void onStop(){
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                templatitude = location.getLatitude();
                                templongitude = location.getLongitude();
                                latitude99.setText(String.valueOf(location.getLatitude()));
                                longitude99.setText(String.valueOf(location.getLongitude()));
                            }
                        }
                    });
        }
    }
    private void requestPermission (){
        ActivityCompat.requestPermissions(GPS.this,new
                String[]{ACCESS_FINE_LOCATION},RequestPermissionCode);
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.e("GPS","CONNECTION SUSPENDED");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.e("GPS","CONNECTION FAILED: "+connectionResult.getErrorCode());
    }
  /**  public class GPS2 extends FragmentActivity implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    }*/
}
