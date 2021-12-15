package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import javax.xml.bind.DatatypeConverter;

public class Acceuil extends AppCompatActivity {

    TextView tvusername;
    Button btnajout, btnaff, btnGuess;

    public static ArrayList<String> pictureData = new ArrayList<String>();
    public static String apiURL = "http://192.168.137.1/";

    public static boolean cameraPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        tvusername = findViewById(R.id.tvuser_acc);
        btnajout = findViewById(R.id.btnajout_acc);
        btnaff = findViewById(R.id.btnaff_acc);
        btnGuess = findViewById(R.id.btnClass_acc);

        cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!cameraPermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    100);
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String u = b.getString("user");

        tvusername.setText("Acceuil de " + u);

        String urlLabel = u.replaceAll("\\s+", "%20");
        //Log.e("urlLabel", urlLabel);
        //Log.e("newLinkAPI", apiURL);
        String url = apiURL + "my_pictures?label=" + urlLabel;

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jrq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("response", response.toString());
                        try {
                            JSONArray arr = response.getJSONArray("result");

                            pictureData.clear();

                            for (int i = 0; i < arr.length(); i++){
                                pictureData.add(arr.get(i).toString());
                            }

                            //Log.e("picData", "Size: " + pictureData.size());

                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("error", error.toString());
                    }
                }
        );

        rq.add(jrq);

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Acceuil.this, AddToSet.class);
                i.putExtra("user", u);
                startActivity(i);
            }
        });

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Acceuil.this, Classify.class);
                startActivity(i);
            }
        });

        btnaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Acceuil.this, Edition.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermission = true;
            }
            else {
                //finish();
            }
        }
    }

}
