package com.example.camera;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

public class Description extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 6;
    EditText editTextName,editTextDescription,editTextWarning;
    Button buttonAddItem,buttonaddimage;
    Button buttoncameraimage;
    Bitmap bitmap = null;

    ImageView imageView;
    File photoFile = null;
    Uri photoURI = null;
    String pathToFile;
    File image;
    Button getlocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        setContentView(R.layout.add_item);
        editTextName = (EditText)findViewById(R.id.et_item_name);
        editTextDescription = (EditText)findViewById(R.id.et_brand);
        editTextWarning = findViewById(R.id.et_text);
      //  buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        final Button buttonAddItem = findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);
        Button check = findViewById(R.id.check);
        getlocation = findViewById(R.id.buttongetlocation);
        buttonaddimage = findViewById(R.id.btn_select_image2);
        buttoncameraimage = findViewById(R.id.cameraImage);
        imageView = findViewById(R.id.image99);

        if(balloon==1){
            editTextName.setText(permname);
            editTextDescription.setText(permdesc);
            editTextWarning.setText(perwarning);
            imageView.setImageBitmap(rbitmap);
            balloon=0;
        }



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permname = editTextName.getText().toString().trim();
                permdesc = editTextDescription.getText().toString().trim();
                perwarning = editTextWarning.getText().toString().trim();
                balloon = 1;

                if(bitmap != null) {
                    Intent intent2 = new Intent(getApplicationContext(), IBM_AI.class);
                    startActivity(intent2);
                }
                else
                    Toast.makeText(Description.this,"No Image Detected", Toast.LENGTH_LONG).show();

            }
        });
        buttonaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==buttonaddimage) {
                    showFileChooser();
                }
            }
        });
        buttoncameraimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==buttoncameraimage){
                    try {
                        dispatchPictureTakeAction();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==getlocation)
                {
                    permname = editTextName.getText().toString().trim();
                    permdesc = editTextDescription.getText().toString().trim();
                    perwarning = editTextWarning.getText().toString().trim();
                    balloon = 1;
                    Intent intent = new Intent(getApplicationContext(),GPS.class);
                    startActivity(intent);
                }
            }
        });
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==buttonAddItem&&imagecheck==1)
                {

                   // addItemToSheet();
                }
                else
                    Toast.makeText(Description.this,"GARBAGE NOT DETECTED, PLEASE ADD ANOTHER PICTURE",Toast.LENGTH_LONG).show();
            }
        });
  }
    public  void   addItemToSheet(Context mContext) {
        //final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
       // final ProgressDialog loading = ProgressDialog.show(mContext,"Adding Item","Please wait");
        /* permname = editTextName.getText().toString().trim();
         permdesc = editTextDescription.getText().toString().trim();
         perwarning = editTextWarning.getText().toString().trim();
         balloon = 1;*/
         perwarning = " ";

       /* if(latitude==0&&longitude==0)
        {
            Intent intent = new Intent(getApplicationContext(),GPS.class);
            startActivity(intent);
        }*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbw_GjjEz9iA4RNHzPSJ60u-aV4y-1AyuxjzIbvlJY2g_JdjBsI/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // loading.dismiss();
                        //Toast.makeText(Description.this,response,Toast.LENGTH_LONG).show();
                       // Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                       // Intent intent = new Intent(mContext,MainActivity.class);
                     //   startActivity(intent);

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
       // loading.dismiss();
        int socketTimeOut = 1000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

       // RequestQueue queue = Volley.newRequestQueue(this);
        RequestQueue queue = Volley.newRequestQueue(mContext);

        queue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    static final int REQUEST_TAKE_CAPTURE = 1;
    private void dispatchPictureTakeAction() throws IOException {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            photoFile = createPhotoFile();
            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                photoURI = FileProvider.getUriForFile(Description.this, "com.example.CAMERA.FileProvider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePic, REQUEST_TAKE_CAPTURE);
            }
        }
    }
    private File createPhotoFile() {
        @SuppressLint("SimpleDateFormat") String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = null;
        storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("mylog", "Excep : " + e.toString());
        }
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                rbitmap = getResizedBitmap(bitmap,250);Toast.makeText(this, String.valueOf(rbitmap), Toast.LENGTH_LONG).show();
                userImage = getStringImage(rbitmap);
                imageView.setImageBitmap(rbitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_CAPTURE) {
                if (pathToFile != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoURI);
                        rbitmap = getResizedBitmap(bitmap, 250);
                        Toast.makeText(this, String.valueOf(rbitmap), Toast.LENGTH_LONG).show();
                        userImage = getStringImage(rbitmap);
                       // imageView.setImageBitmap(BitmapFactory.decodeFile(pathToFile));
                        imageView.setImageBitmap(rbitmap);
                    //    permimage = imageView;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }
    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){

           // addItemToSheet();
        }
    }
}
