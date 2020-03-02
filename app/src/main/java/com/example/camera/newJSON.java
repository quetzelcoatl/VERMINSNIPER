package com.example.camera;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.camera.Configuration.LIST_COMPLAIN_URL;
import static com.example.camera.Configuration.check;
import static com.example.camera.Configuration.ulatitude;
import static com.example.camera.Configuration.ulongitude;
import static com.example.camera.Configuration.uIds;
import static com.example.camera.Configuration.udescription;
import static com.example.camera.Configuration.uwarnings;
import static com.example.camera.Configuration.uImages;

public class newJSON extends AppCompatActivity {
TextView text12345;
ListView listView;
Button buttonmap;
LottieAnimationView lottieAnimationView;

String name0007[];
String description0007[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        //text12345 = findViewById(R.id.textViewlocation);
        buttonmap = findViewById(R.id.buttonshowmap);
        buttonmap.setVisibility(View.INVISIBLE);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        listView=findViewById(R.id.listview);
        check = 0;
        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent009 = new Intent(getApplicationContext(),MAP.class);
                startActivity(intent009);
            }
        });
        sendRequest();
        // String jsonStr =
    }
    //private AlertDialog progressDialog;
    //progressDialog = new SpotsDialog(mContext, R.style.Custom);
        private void sendRequest () {

            //final ProgressDialog loading = ProgressDialog.show(this, "Downloading", "Please wait...", false, false);
            //ProgressDialog progressDialog = new ProgressDialog(this, R.raw.worldlocations);
           // progressDialog.setMessage("Downloading co-ordinates. Please wait.");
           // progressDialog.show();
            lottieAnimationView.setVisibility(View.VISIBLE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,LIST_COMPLAIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseJSON(response);
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            buttonmap.setVisibility(View.VISIBLE);
                           //progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(newJSON.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            int socketTimeout = 3000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            stringRequest.setRetryPolicy(policy);


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }



 public void parseJSON(String response){



        try {
           // ArrayList<HashMap<String, String>> userList = new ArrayList<>();
           //   ListView lv = (ListView) findViewById(R.id.lv_items);
            JSONObject jObj = new JSONObject(response);
            JSONArray jsonArry = jObj.getJSONArray("items");
            String [] namelol = new String[jsonArry.length()];
           // check=0;
            for (int j = 0; j < jsonArry.length(); j++) {
                HashMap<String, String> user = new HashMap<>();
                JSONObject obj = jsonArry.getJSONObject(j);
                String latitude1990 = obj.getString("latitude");
                String longitude1990 = obj.getString("longitude");
                String name1990 = obj.getString("Name");
                String desc1990 = obj.getString("Description");
                String warn1990 = obj.getString("warnings");
                String image1990 = obj.getString("image");

                namelol[j] = name1990;
                ulatitude[j]=Double.valueOf(latitude1990);
                ulongitude[j]=Double.valueOf(longitude1990);
                uIds[j] = name1990;
                udescription[j] = desc1990;
                uwarnings[j]=warn1990;
                uImages[j]=image1990;
                //name0007[j]=name1990;
                //description0007[j]=desc1990;
                check = check+1;
              //  text12345.append("\nNAME : -\t"+name1990+"\nLat/Long :-\t"+ulatitude[j] +"\t"+ulongitude[j]+  "\n\n");
            }
            final ArrayList<String> list = new ArrayList<String>();

            //for (int i = 0; i < jsonArry.length(); ++i) {

                mysimplearrayadapter adapter = new mysimplearrayadapter(this,namelol);
                listView.setAdapter(adapter);
          //  }
           // final StableArrayAdapter adapter = new StableArrayAdapter(this,
             //       android.R.layout.simple_list_item_1, list);
         //   listView.setAdapter(adapter);


        } catch (JSONException ex) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex);
        }


    }
}

