package com.example.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.model.layer.NullLayer;
import com.google.common.primitives.Bytes;
import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.watson.visual_recognition.v4.model.Collection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import static android.widget.Toast.makeText;
import static com.example.camera.Configuration.userImage;
import static com.example.camera.Configuration.imagecheck;
import static com.example.camera.Configuration.rbitmap;

public class IBM_AI extends AppCompatActivity {
    TextView text_of_AI;
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
        setContentView(R.layout.ibm);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        goback = findViewById(R.id.button2);
        IBMimage = findViewById(R.id.imageforIBM);
        btnFetchResults = findViewById(R.id.button);
        text_of_AI = findViewById(R.id.result_of_AI);
        text_of_AI.setText("IMAGE RECOGNITION");
       // IBMimage.setImageBitmap(getBitmapFromURL(etUrl));

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent back = new Intent(getApplicationContext(),Description.class);
                startActivity(back);
            }
        });

        IBMimage.setImageBitmap(rbitmap);
        Bitmaptofile();
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

        btnFetchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClassifiedImages classifiedImages = visualRecognition.classify(classifyOptions).execute();
                List<ClassResult> resultList = classifiedImages.getImages().get(0).getClassifiers().get(0).getClasses();
                for (ClassResult result : resultList) {
                    if(result.getClassName().equals("garbage")||result.getClassName().equals("garbage heap")||result.getClassName().equals("Garbage")||result.getClassName().equals("landfill")||result.getClassName().equals("recycling plant")) {
                        text_of_AI.setText("Garbage detected\n");
                        lol=0;
                        imagecheck = 1;
                    }
                }
                if(lol==1)
                    text_of_AI.setText("GARBAGE NOT DETECTED");
                for (ClassResult result : resultList)
                text_of_AI.append("\n"+result.getClassName()+"       "+result.getScore());
            }
        });

    }
    public void Bitmaptofile()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rbitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        bs = new ByteArrayInputStream(bitmapdata);
    }

}


/*
        text_of_AI = findViewById(R.id.result_of_AI);

      /*  IamOptions options = new IamOptions.Builder()
                .apiKey("bJtm8vL1MclwQ1WW5l4pVTUPiHQMnDXLu21Fq27rkMGv")
                .build();

        VisualRecognition service = new VisualRecognition("2018-03-19", (Authenticator) options);

        InputStream imagesStream = null;

        {
            try {
                //imagesStream = new FileInputStream("./fruitbowl.jpg");
                imagesStream = new FileInputStream(userImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("entered_image.jpg")
                .threshold((float) 0.6)
                //.classifierIds(Arrays.asList("DefaultCustomModel_1209267249"))
                .classifierIds(Collections.singletonList("DefaultCustomModel_1209267249"))
                .build();

        Response<ClassifiedImages> result = service.classify(classifyOptions).execute();
        text_of_AI.setText((CharSequence) result);

            IamAuthenticator authenticator = new IamAuthenticator("wofGMJjhtlS2x4dthKfFEM0gvqXsvLbrQ8M1V9t5BL-M");
            VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
            visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/02b5b90b-1991-494a-ba3f-04b2f47157cf");

        InputStream imagesStream = null;

        {
            try {
                //imagesStream = new FileInputStream("./fruitbowl.jpg");
                imagesStream = new FileInputStream(userImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile(imagesStream)
                    .imagesFilename("entered_image.jpg")
                    .threshold((float) 0.6)
                    .build();
            ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
            /*System.out.println(
                    "\n******** Classify with the General model ********\n" + result
                            + "\n******** END Classify with the General model ********\n");
        text_of_AI.setText((CharSequence) result);
        IamAuthenticator authenticator = new IamAuthenticator("wofGMJjhtlS2x4dthKfFEM0gvqXsvLbrQ8M1V9t5BL-M");
        VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
        visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/02b5b90b-1991-494a-ba3f-04b2f47157cf");

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .url("https://ibm.biz/BdzLPG")
                .classifierIds(Collections.singletonList("DefaultCustomModel_1209267249"))
                .build();
        ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
        text_of_AI.setText((CharSequence) result);*/
/* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ibm);

        observable = Single.create((SingleOnSubscribe<ClassifiedImages>) emitter -> {
            IamOptions options = new IamOptions.Builder()
                    .apiKey("bJtm8vL1MclwQ1WW5l4pVTUPiHQMnDXLu21Fq27rkMGv")
                    .build();

            InputStream imagesStream = null;

            {
                try {
                    imagesStream = new FileInputStream(String.valueOf(rbitmap));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", options);
            com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    //.imagesFile(imagesStream)
                  //  .imagesFilename("input_image.jpg")
                    .url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_MSV0stLHedka9m0DtnUsPV-oCptHr-5233rIhOCZKlYC0Cg-&s.jpg")
                    .classifierIds(Collections.singletonList("default"))
                    .threshold(threshold)
                    .owners(Collections.singletonList("me"))
                    .build();
            ClassifiedImages classifiedImages = visualRecognition.classify(classifyOptions).execute();
            List<ClassResult> resultList = classifiedImages.getImages().get(0).getClassifiers().get(0).getClasses();
            for (ClassResult result : resultList) {
                if (result.getClassName().equals("garbage")||result.getClassName().equals("Garbage")) {
                    Toast.makeText(this, "Garbage Detected, proceeding to upload to cloud database", Toast.LENGTH_LONG).show();

                }
            }

        });

    }
}*/