package com.example.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.camera.Configuration.uIds;
import static com.example.camera.Configuration.uImages;
import static com.example.camera.Configuration.udescription;
import static com.example.camera.Configuration.z;

public class mysimplearrayadapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public mysimplearrayadapter(Context context, String[] uIds) {
        super(context, R.layout.row, uIds);
        this.context = context;
        this.values = uIds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.textview1);
        TextView textView2 = rowView.findViewById(R.id.textview2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image0007);
        textView1.setText(values[position]);
        textView2.setText(udescription[position]);
        String url = uImages[position];
       Picasso.with(context)
                .load(url)
                .into(imageView);

        return rowView;
    }


}
