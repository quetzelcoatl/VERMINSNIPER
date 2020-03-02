package com.example.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

import static com.example.camera.Configuration.LIST_COMPLAIN_URL;
import static com.example.camera.Configuration.kar;
import static com.example.camera.Configuration.rbitmap;
import static com.example.camera.Configuration.garbagecheck;

public class picvalidatepage2 extends AppCompatActivity {

    Button next,nope,AILIST,analyse;
    TextView text;
    ImageView image0009;
    int a =0;
    TextView text_of_AI;
    LottieAnimationView loadingloading;
    ImageView IBMimage;
    String imageString;
    ByteArrayInputStream bs;
    Button btnFetchResults;
    Bitmap babu;
    Button goback;
    int lol =1;
    Single<ClassifiedImages> observable;
    private float threshold = (float) 0.4;
    private final String API_KEY = "bJtm8vL1MclwQ1WW5l4pVTUPiHQMnDXLu21Fq27rkMGv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picvalidate);

        loadingloading = findViewById(R.id.lottieAnimationView123);
        loadingloading.setVisibility(View.INVISIBLE);
        kar=0;
        analyse = findViewById(R.id.analyse);
        next = findViewById(R.id.next);
        nope = findViewById(R.id.nope);
        AILIST = findViewById(R.id.AILIST);
        text = findViewById(R.id.text);

        analyse.setVisibility(View.INVISIBLE);

        text.setVisibility(View.INVISIBLE);
        image0009 = findViewById(R.id.imageView999);
        image0009.setImageBitmap(rbitmap);
        if(garbagecheck!=1)
            next.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                Intent intent123 = new Intent(getApplicationContext(), ComplainPage.class);
                startActivity(intent123);

            }
        });

        nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent9990 = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent9990);

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

        if(rbitmap == null)
        {
            Intent backlol = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(backlol);

        }
        else {
            analyse.setVisibility(View.VISIBLE);
            image0009.setImageBitmap(rbitmap);
            Bitmaptofile();


            analyse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loadingloading.setVisibility(View.VISIBLE);
                    analyse.setVisibility(View.INVISIBLE);



                    ibmlol();
                }
            });
        }
    }

    public void ibmlol()
    {
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
            if (result.getClassName().equals("garbage") || result.getClassName().equals("garbage heap") || result.getClassName().equals("Garbage") || result.getClassName().equals("landfill") || result.getClassName().equals("recycling plant")) {
                kar = 1;
                text.setTypeface(null, Typeface.BOLD_ITALIC);
                text.append(result.getClassName() + "  " + result.getScore() * 100 + "%\n");
                next.setVisibility(View.VISIBLE);
                garbagecheck = 1;
                loadingloading.setVisibility(View.INVISIBLE);
            } else {
                loadingloading.setVisibility(View.INVISIBLE);
                text.setTypeface(null, Typeface.BOLD);
                text.append(result.getClassName() + "  " + result.getScore() * 100 + "%\n");
            }
        }
        karadi();
        AILIST.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent001 = new Intent(getApplicationContext(),picvalidatepage2.class);
            startActivity(intent001);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void Bitmaptofile()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rbitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        bs = new ByteArrayInputStream(bitmapdata);
    }
    public void karadi()
    {
        if(kar == 1)
            Toast.makeText(this, "GARBAGE FOUND", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "NO GARBAGE FOUND", Toast.LENGTH_LONG).show();


        // ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listarray);
        // list.setAdapter(arrayAdapter);
    }

}