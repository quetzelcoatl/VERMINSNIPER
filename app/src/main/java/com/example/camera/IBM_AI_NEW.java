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
import static com.example.camera.Configuration.kar;

public class IBM_AI_NEW extends AppCompatActivity {

    ByteArrayInputStream bs;

    int lol =1;
    Single<ClassifiedImages> observable;
    private float threshold = (float) 0.4;
    private final String API_KEY = "bJtm8vL1MclwQ1WW5l4pVTUPiHQMnDXLu21Fq27rkMGv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


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

                ClassifiedImages classifiedImages = visualRecognition.classify(classifyOptions).execute();
                List<ClassResult> resultList = classifiedImages.getImages().get(0).getClassifiers().get(0).getClasses();
                for (ClassResult result : resultList) {
                    if(result.getClassName().equals("garbage")||result.getClassName().equals("garbage heap")||result.getClassName().equals("Garbage")||result.getClassName().equals("landfill")||result.getClassName().equals("recycling plant")) {
                        kar = 1;
                    }
                }

    }
    public void Bitmaptofile()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rbitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        bs = new ByteArrayInputStream(bitmapdata);
    }

}
