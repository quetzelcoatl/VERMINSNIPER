package com.example.camera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import static com.example.camera.Configuration.latLng;
import static com.example.camera.Configuration.latLngPosition;
import static com.example.camera.Configuration.latitude;
import static com.example.camera.Configuration.longitude;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.camera.Configuration.LIST_COMPLAIN_URL;
import static com.example.camera.Configuration.KEY_UID;
import static com.example.camera.Configuration.KEY_DESC;
import static com.example.camera.Configuration.KEY_WARN;
import static com.example.camera.Configuration.KEY_IMAGE;
import static com.example.camera.Configuration.KEY_LATITUDE;
import static com.example.camera.Configuration.KEY_LONGITUDE;
import static com.example.camera.Configuration.check;
import static com.example.camera.Configuration.permdesc;
import static com.example.camera.Configuration.permname;
import static com.example.camera.Configuration.permimage;
import static com.example.camera.Configuration.perwarning;
import static com.example.camera.Configuration.balloon;
import static com.example.camera.Configuration.KEY_LOCATION;
import static com.example.camera.Configuration.latitude;
import static com.example.camera.Configuration.longitude;
import static com.example.camera.Configuration.permimage;
import static com.example.camera.Configuration.templatitude;
import static com.example.camera.Configuration.templongitude;
import static com.example.camera.Configuration.ulatitude;
import static com.example.camera.Configuration.locationfinal;
import static com.example.camera.Configuration.ulongitude;
import static com.example.camera.Configuration.latLngPosition;
import static com.example.camera.Configuration.latLng;
import static com.example.camera.Configuration.userImage;
import static com.example.camera.Configuration.CameraImageURI;
import static com.example.camera.Configuration.Nametext;
import static com.example.camera.Configuration.Descriptiontext;
import static com.example.camera.Configuration.Warningtext;

import static com.example.camera.Configuration.rbitmap;
import static com.example.camera.Configuration.IBMFILE;
import static com.example.camera.Configuration.imagecheck;

public class ComplainPage extends FragmentActivity implements OnMapReadyCallback {


    private static final int LOCATION_CODE = 999;
    Location currentLocation;
    Button next;
    EditText name,description;
    ImageView comlpainimage;
    Context context999;
    Button OK;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complainpage9);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        if (ContextCompat.checkSelfPermission(ComplainPage.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ComplainPage.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        };

        comlpainimage = findViewById(R.id.imageView008);
        name = findViewById(R.id.NAME);
        description = findViewById(R.id.DESCRIPTION);
        next = findViewById(R.id.submit);

        latitude=0;
        longitude=0;
        permname = " ";
        permdesc = " ";


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permname = name.getText().toString().trim();
                permdesc = description.getText().toString().trim();
                userImage = getStringImage(rbitmap);

                Description cls = new Description();
                cls.addItemToSheet(ComplainPage.this);

                Toast.makeText(ComplainPage.this,"Success!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                //addItemToSheet();
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }


    private void fetchLastLocation() {

        comlpainimage.setImageBitmap(rbitmap);

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
                    supportMapFragment.getMapAsync(ComplainPage.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_launchersmall);
        latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        final MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Hold To Adjust Position")
                .draggable(true)
                .icon(bitmapDescriptor);

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener(){
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {



                Geocoder gc = new Geocoder(ComplainPage.this);
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
                String subloc= "",premis="",subarea="",loca="",count="";
                subloc = add.getSubLocality();
                premis = add.getPremises();
                subarea = add.getSubAdminArea();
                loca = add.getLocality();
                count = add.getCountryCode();



               // marker.setTitle(add.getAdminArea()+" "+add.getLocality()+" "+add.getPostalCode());
                marker.setTitle(subloc+" "+subarea);
                marker.setIcon(bitmapDescriptor);
                marker.setSnippet(loca+" "+count);
                marker.showInfoWindow();
            }
        });

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        googleMap.addMarker(markerOptions);
        latLng = markerOptions.getPosition();
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;

        }
    }*/

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    private void   addItemToSheet() {

        permname = name.getText().toString().trim();
        permdesc = name.getText().toString().trim();
        userImage = getStringImage(rbitmap);

      //  final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");

        balloon = 1;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbw_GjjEz9iA4RNHzPSJ60u-aV4y-1AyuxjzIbvlJY2g_JdjBsI/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //loading.dismiss();
                        Toast.makeText(ComplainPage.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> parmas = new HashMap<>();
                parmas.put("action","addItem");

                parmas.put(KEY_UID,permname);
                parmas.put(KEY_DESC,permdesc);
                parmas.put(KEY_WARN,perwarning);
                parmas.put(KEY_IMAGE,userImage);
                parmas.put(KEY_LATITUDE,String.valueOf(latitude));
                parmas.put(KEY_LONGITUDE,String.valueOf(longitude));
                return parmas;

            }
        };
        int socketTimeOut = 5000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Toast.makeText(ComplainPage.this,
                        "Tower Location Approximation Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(ComplainPage.this,
                        "Cell tower Approximation not Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


}
