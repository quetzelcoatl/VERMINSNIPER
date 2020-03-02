package com.example.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import org.apache.commons.lang3.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.camera.Configuration.kar;
import static com.example.camera.Configuration.rbitmap;
import static com.example.camera.Configuration.userImage;
import static com.example.camera.Configuration.garbagecheck;

public class CameraActivity extends Activity {
    //Camera.PictureCallback jpegCallback;
    int babu = 1;
    private Camera mCamera;
    Button gallery,AILIST,cameranext;
    File photoFile = null;
    File image;
    int lol2;
    int a;
    String[] listarray;
    ListView list;
    int karh;
    int EXTERNAL_STORAGE_CODE = 1000;
    private float threshold = (float) 0.4;
    int i = 0;
    int karw;
    Button nope;
    int PICK_IMAGE_REQUEST = 7;
    ByteArrayInputStream bs;
    TextView text;
    String loloolol;
    ImageView scope;
    Uri photoURI = null;
    String pathToFile;
    View view9;
    Button bob,IBM,next,back;
    private final String API_KEY = "bJtm8vL1MclwQ1WW5l4pVTUPiHQMnDXLu21Fq27rkMGv";
    Bitmap bitmap;
    int chedder = 1;
    ImageView IMAGEVIEW999, upperblack, lowerblack;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        back = findViewById(R.id.back);
       // view9 = this.getWindow().getDecorView();
      //  view9.setVisibility(View.INVISIBLE);

        if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_CODE);
        }


        AILIST = findViewById(R.id.AILIST);

        scope = findViewById(R.id.scope);
        // Create an instance of Camera
        gallery = findViewById(R.id.galleryhome);
        mCamera = getCameraInstance();
        IMAGEVIEW999 = findViewById(R.id.imageView999);

        //next = findViewById(R.id.next);
        cameranext = findViewById(R.id.cameraopen);


        IMAGEVIEW999.setAlpha(0);
        //next.setVisibility(View.INVISIBLE);
        bob = findViewById(R.id.button_capture);
        bob.setVisibility(View.VISIBLE);
/*

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1233345 = new Intent(getApplicationContext(), ComplainPage.class);
                startActivity(intent1233345);
            }
        });

        AILIST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AILIST.setRotation(180+a);
                    a=a+180;
                    if(a%360==0)
                        text.setVisibility(View.INVISIBLE);
                    else
                        text.setVisibility(View.VISIBLE);
            }
        });
*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent001 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent001);
            }
        });
        cameranext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dispatchPictureTakeAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    try {
                        dispatchPictureTakeAction();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    captureimage();
                }
            }
        });
        /*
        nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent123334 = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent123334);
            }
        });*/

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });

            if(kar!=9) {
                mPreview = new CameraPreview(this, mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview);
            }
            else
            {
                Intent intent001 = new Intent(getApplicationContext(),picvalidatepage2.class);
                startActivity(intent001);
            }

    }
    static final int REQUEST_TAKE_CAPTURE = 1;
    private void dispatchPictureTakeAction() throws IOException {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            photoFile = createPhotoFile();
            if (photoFile != null) {
                kar=9;
                pathToFile = photoFile.getAbsolutePath();
                photoURI = FileProvider.getUriForFile(CameraActivity.this, "com.example.CAMERA.FileProvider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePic, REQUEST_TAKE_CAPTURE);

            }
        }
    }
    private File createPhotoFile() {
        @SuppressLint("SimpleDateFormat") String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = null;
        //storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("mylog", "Excep : " + e.toString());
        }
        return image;
    }
    /*
    public void karadi()
    {
        if(kar == 1)
        Toast.makeText(this, "GARBAGE FOUND", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "NO GARBAGE FOUND", Toast.LENGTH_LONG).show();


       // ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listarray);
       // list.setAdapter(arrayAdapter);
    }*/

    private void captureimage() {

       // view9.setVisibility(View.VISIBLE);
        mCamera.takePicture(null, null, jpegCallback);


    }

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
           // IMAGEVIEW999.setAlpha(255);
         //   mPreview.setAlpha(0);

            /*
            nope.setVisibility(View.VISIBLE);
            text.setText("");
            gallery.setVisibility(View.INVISIBLE);
            scope.setVisibility(View.INVISIBLE);*/

            decodeBitmap = RotateBitmap(decodeBitmap, 90);
          //  IMAGEVIEW999.setImageBitmap(decodeBitmap);
            rbitmap = decodeBitmap;
            Bitmaptofile();
            mCamera.startPreview();
           // IBMAISTUFF();

            Intent intent002 = new Intent(getApplicationContext(),picvalidatepage2.class);
            startActivity(intent002);
        }


        /*public void IBMAISTUFF() {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            IamOptions options = new IamOptions.Builder()
                    .apiKey(API_KEY)
                    .build();

            VisualRecognition visualRecognition = new VisualRecognition("2020-01-28", options);
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    //.url(etUrl)
                    .imagesFile(bs)
                    .imagesFilename("userimage")
                    .classifierIds(Collections.singletonList("default"))
                    //   .classifierIds(Collections.singletonList("2ff61b27-f12a-4a6b-bc55-b01c374c2ecf"))
                    .threshold(threshold)
                    .owners(Collections.singletonList("me"))
                    .build();

            ClassifiedImages classifiedImages = visualRecognition.classify(classifyOptions).execute();
            List<ClassResult> resultList = classifiedImages.getImages().get(0).getClassifiers().get(0).getClasses();
            for (ClassResult result : resultList) {
                if(result.getClassName().equals("garbage")||result.getClassName().equals("garbage heap")||result.getClassName().equals("Garbage")||result.getClassName().equals("landfill")||result.getClassName().equals("recycling plant")) {
                    kar = 1;
                    text.setTypeface(null, Typeface.BOLD_ITALIC);
                    text.append(result.getClassName()+ "  " +result.getScore()*100+"%\n");
                    next.setVisibility(View.VISIBLE);
                    garbagecheck = 1;
                }
                else
                {

                    text.setTypeface(null, Typeface.BOLD);
                    text.append(result.getClassName()+ "  " +result.getScore()*100+"%\n");
                }
                i=i+1;
            }
            //karadi();
            AILIST.setVisibility(View.VISIBLE);

        }*/
    };

    public static Bitmap RotateBitmap(Bitmap source, float angle) {

        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {

        }
        return c;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }

    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
               // view9.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                rbitmap = getResizedBitmap(bitmap, 500);
               // mPreview.setAlpha(0);
                Bitmaptofile();
                userImage = getStringImage(rbitmap);

               // IMAGEVIEW999.setImageBitmap(bitmap);
                /*
                IMAGEVIEW999.setAlpha(255);bob.setVisibility(View.INVISIBLE);
                gallery.setVisibility(View.INVISIBLE);
                scope.setVisibility(View.INVISIBLE);
                nope.setVisibility(View.VISIBLE);*/

                chedder = 0;

                Intent intent001 = new Intent(getApplicationContext(),picvalidatepage2.class);
                startActivity(intent001);


            } catch (IOException e) {
                e.printStackTrace();
            }


               /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                IamOptions options = new IamOptions.Builder()
                        .apiKey(API_KEY)
                        .build();

                VisualRecognition visualRecognition = new VisualRecognition("2020-01-28", options);
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        //.url(etUrl)
                        .imagesFile(bs)
                        .imagesFilename("userimage")
                        .classifierIds(Collections.singletonList("default"))
                        //   .classifierIds(Collections.singletonList("2ff61b27-f12a-4a6b-bc55-b01c374c2ecf"))
                        .threshold(threshold)
                        .owners(Collections.singletonList("me"))
                        .build();

                ClassifiedImages classifiedImages = visualRecognition.classify(classifyOptions).execute();
                List<ClassResult> resultList = classifiedImages.getImages().get(0).getClassifiers().get(0).getClasses();
            for (ClassResult result : resultList) {
                if(result.getClassName().equals("garbage")||result.getClassName().equals("garbage heap")||result.getClassName().equals("Garbage")||result.getClassName().equals("landfill")||result.getClassName().equals("recycling plant")) {
                    kar = 1;
                    text.setTypeface(null, Typeface.BOLD_ITALIC);
                    text.append(result.getClassName()+ "  " +result.getScore()*100+"%\n");
                    next.setVisibility(View.VISIBLE);
                    garbagecheck = 1;
                }
                else
                {

                    text.setTypeface(null, Typeface.BOLD);
                    text.append(result.getClassName()+ "  " +result.getScore()*100+"%\n");
                }
                i=i+1;
            }
            karadi();
            AILIST.setVisibility(View.VISIBLE);*/
        }
        else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_CAPTURE) {
                if (pathToFile != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoURI);
                        rbitmap = getResizedBitmap(bitmap, 500);
                        userImage = getStringImage(rbitmap);
                        kar = 9;
                        Intent intent001 = new Intent(getApplicationContext(),picvalidatepage2.class);
                        startActivity(intent001);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 0, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    public void Bitmaptofile()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rbitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        bs = new ByteArrayInputStream(bitmapdata);

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

        if (requestCode == EXTERNAL_STORAGE_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(CameraActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(CameraActivity.this,
                        "Storage Permission Denied",
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
