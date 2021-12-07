package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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

    public static ArrayList<Contact> data = new ArrayList<Contact>();
    public static ArrayList<String> pictureData = new ArrayList<String>();
    public static String apiURL = "http://197.1.111.148/";

    public static boolean callPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        tvusername = findViewById(R.id.tvuser_acc);
        btnajout = findViewById(R.id.btnajout_acc);
        btnaff = findViewById(R.id.btnaff_acc);
        btnGuess = findViewById(R.id.btnClass_acc);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String u = b.getString("user");

        tvusername.setText("Acceuil de " + u);

        //set data here
        String urlLabel = u.replaceAll("\\s+", "%20");
        Log.e("urlLabel", urlLabel);
        String url = apiURL + "my_pictures?label=" + urlLabel;

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jrq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        try {
                            JSONArray arr = response.getJSONArray("result");

                            pictureData.clear();

                            for (int i = 0; i < arr.length(); i++){
                                pictureData.add(arr.get(i).toString());
                            }

                            Log.e("picData", "Size: " + pictureData.size());

                            /*String imgKey = arr.get(0).toString();
                            byte[] imageBytes = Base64.decode(imgKey, Base64.DEFAULT);
                            Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            ImageView image = (ImageView) findViewById(R.id.imageView1);
                            image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150,150, false));*/

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                //Intent i = new Intent(Acceuil.this, Ajout.class);
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

        callPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        if (!callPermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callPermission = true;
                }
                else finish();
            }
        }

    }

}
