package com.example.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static com.example.camera.Configuration.check;
import static com.example.camera.Configuration.uIds;
import static com.example.camera.Configuration.udescription;
import static com.example.camera.Configuration.uImages;
import static com.example.camera.Configuration.doublecheck;
import static com.example.camera.Configuration.uwarnings;
import static com.example.camera.Configuration.uImages;
import static com.example.camera.Configuration.z;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    int num = 0;
    private Context context;
    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.map_marker_info_window, null);
        ImageView img = v.findViewById(R.id.imageofmarker);
            TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
            TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        String url = uImages[z];

        if(url!=null) {
            Picasso.with(context)
                    .load(url)
                    .into(img);

            Toast.makeText(context.getApplicationContext(),"Tap To Show Image",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context.getApplicationContext(),"cannot preview image!",Toast.LENGTH_SHORT).show();
        tvLat.append("Name : "+uIds[z]);
        tvLng.append("Description : "+udescription[z]);


            return v;
        }
    }
/**    URL url = null;
 try {
 url = new URL(uImages[z]);
 } catch (MalformedURLException e) {
 e.printStackTrace();
 }
 Bitmap bmp = null;
 assert url != null;
 try {
 bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
 } catch (IOException e) {
 e.printStackTrace();
 }

 img.setImageBitmap(bmp);

if(uImages[z]!=null){
        Picasso.with(context).load(uImages[z]).into(img);
        }*/