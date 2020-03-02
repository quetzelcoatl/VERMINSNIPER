package com.example.camera;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.camera.Configuration.LIST_COMPLAIN_URL;
import static com.example.camera.Configuration.KEY_UID;
import static com.example.camera.Configuration.KEY_DESC;
import static com.example.camera.Configuration.KEY_WARN;
import static com.example.camera.Configuration.KEY_IMAGE;
import static com.example.camera.Configuration.KEY_LOCATION;
import static com.example.camera.Configuration.KEY_USERS;

import static com.example.camera.Configuration.uIds;
import static com.example.camera.Configuration.udescription;
import static com.example.camera.Configuration.uwarnings;
import static com.example.camera.Configuration.uImages;
import static com.example.camera.Configuration.ulocation;

public class JSONParser extends AppCompatActivity {
    TextView text0008;
    ListView listView;
    ListAdapter adapter;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
//        text0008 = findViewById(R.id.textViewlocation);
     //   listView = (ListView)findViewById(R.id.lv_items);
        sendRequest();
    }

    private void sendRequest() {

        final ProgressDialog loading = ProgressDialog.show(this,"Downloading","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(LIST_COMPLAIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JSONParser.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
  /** private JSONArray items = null;
    private String json;

    private void showJSON(String json) {
    this.json = json;
    parseJSON();
    }*/
    public JSONArray items = null;
    public void parseJSON(String jsonrespose){
        JSONObject jsonObject=null;
     //   ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            JSONObject jobj = new JSONObject(jsonrespose);
            jsonObject = new JSONObject(jsonrespose);
            JSONArray jarray = jobj.getJSONArray("items");
            items = jsonObject.getJSONArray(KEY_USERS);

            uIds = new String[items.length()];
            udescription = new String[items.length()];
            uwarnings = new String[items.length()];
            uImages = new String[items.length()];
            ulocation = new String[items.length()];

            for(int i=0;i<jarray.length();i++) {
                JSONObject jo = jarray.getJSONObject(i);
                count=count+1;
                uIds[i] = jo.getString(KEY_UID);
                udescription[i] = jo.getString(KEY_DESC);
                uwarnings[i] = jo.getString(KEY_WARN);
                uImages[i] = jo.getString(KEY_IMAGE);
                ulocation[i]= jo.getString(KEY_LOCATION);
                text0008.setText( ulocation[i]);
               /** String uIds = jo.getString(KEY_UID);
                String udescription = jo.getString(KEY_DESC);
                String uwarning = jo.getString(KEY_WARN);
                String uimages = jo.getString(KEY_IMAGE);
                String ulocation = jo.getString(KEY_LOCATION);
                HashMap<String,String> item = new HashMap<>();
                item.put(KEY_UID,uIds);
                item.put(KEY_DESC,udescription);
                item.put(KEY_WARN,uwarning);
                item.put(KEY_IMAGE,uimages);
                item.put(KEY_LOCATION,ulocation);*/
                printjson();
              //  list.add(item);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
     //   adapter = new SimpleAdapter(this,list,R.layout.json,
        //        new String[]{KEY_LOCATION}, new int[]{R.id.textViewlocation});
     //   listView.setAdapter(adapter);
    }
   public void printjson() {
        for(int i=0;i<count;i++){
            text0008.setText( ulocation[4]);
            System.out.println(ulocation[i]);
        }

    }
}


