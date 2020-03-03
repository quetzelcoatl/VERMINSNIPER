package com.example.camera;
import java.io.ByteArrayOutputStream;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import java.io.File;
import static android.os.Environment.getExternalStoragePublicDirectory;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.camera.Configuration.check;
import static com.example.camera.Configuration.userImage;
import static com.example.camera.Configuration.CameraImageURI;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
        private static final int PICK_IMAGE_REQUEST = 8;

        Button buttonAddItem;
        Button btnTakePic;
        Button buttonaddimage,refresh,blackview;
        ImageView imageView;
        BottomNavigationView main_nav;
        String pathToFile;
        Bitmap bitmap = null;
        Bitmap rbitmap;
        File photoFile = null;
        File image = null;
        int CAMERA_REQUEST_CODE, LOCATION_CODE;
        Uri photoURI = null;
        TextView text;
        Button complainmap;
        LottieAnimationView lottieAnimationView;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                check = 0;
                complainmap = findViewById(R.id.buttoncomplainmap);
                lottieAnimationView = findViewById(R.id.nointernet);
                buttonAddItem = (Button) findViewById(R.id.btn_add_item);
                refresh = findViewById(R.id.refresh);
                lottieAnimationView.setVisibility(View.INVISIBLE);
                refresh.setVisibility(View.INVISIBLE);
                buttonAddItem.setVisibility(View.VISIBLE);
                complainmap.setVisibility(View.VISIBLE);

                refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent refresh001 = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(refresh001);
                        }
                });

                complainmap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                if(isNetworkConnected()!=true){

                                        complainmap.setVisibility(View.INVISIBLE);
                                        buttonAddItem.setVisibility(View.INVISIBLE);
                                        lottieAnimationView.setVisibility(View.VISIBLE);
                                        refresh.setVisibility(View.VISIBLE);

                                }
                                else {
                                        Intent intent1234 = new Intent(getApplicationContext(), newJSON.class);
                                        startActivity(intent1234);
                                }
                        }
                });
              //  main_nav = findViewById(R.id.nav_view);
              //  main_nav.setOnNavigationItemSelectedListener(this);
             //   main_nav.setSelectedItemId(R.id.Home_Fragment);

                if(isNetworkConnected()!=true){

                        Toast.makeText(this,"No Internet Connectivity",Toast.LENGTH_LONG).show();
                        complainmap.setVisibility(View.INVISIBLE);
                        buttonAddItem.setVisibility(View.INVISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        refresh.setVisibility(View.VISIBLE);

                }




                buttonAddItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (v == buttonAddItem) {

                                        if(isNetworkConnected()!=true){

                                                complainmap.setVisibility(View.INVISIBLE);
                                                buttonAddItem.setVisibility(View.INVISIBLE);
                                                lottieAnimationView.setVisibility(View.VISIBLE);
                                                refresh.setVisibility(View.VISIBLE);

                                        }
                                        else {
                                                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                                                        != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                                                }
                                                else if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

                                                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_CODE);
                                                }
                                                else
                                                {

                                                        Intent intent = new Intent(getApplicationContext(), cameraxandroid.class);
                                                        startActivity(intent);
                                                }
                                        }
                                }
                        }
                });




                imageView = (ImageView) findViewById(R.id.image);
                Calendar calendar = Calendar.getInstance();
               // String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                //TextView textViewDate = findViewById(R.id.text_view_date);
               // textViewDate.setText(currentDate);

        }
        private boolean isNetworkConnected() {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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

                if (requestCode == CAMERA_REQUEST_CODE) {

                        // Checking whether user granted the permission or not.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                                // Showing the toast message
                                Toast.makeText(MainActivity.this,
                                        "Camera Permission Granted",
                                        Toast.LENGTH_SHORT)
                                        .show();
                        }
                        else {
                                Toast.makeText(MainActivity.this,
                                        "Camera Permission Denied",
                                        Toast.LENGTH_SHORT)
                                        .show();
                        }
                }
                /*
                if (requestCode == LOCATION_CODE) {

                        // Checking whether user granted the permission or not.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                                // Showing the toast message
                                Toast.makeText(MainActivity.this,
                                        "Tower Location Approximation Granted",
                                        Toast.LENGTH_SHORT)
                                        .show();
                        }
                        else {
                                Toast.makeText(MainActivity.this,
                                        "Cell Location Approximation Granted",
                                        Toast.LENGTH_SHORT)
                                        .show();
                        }
                }*/
        }
}






