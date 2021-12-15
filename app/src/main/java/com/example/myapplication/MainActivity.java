package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String apiURL = "http://192.168.137.1/";

    Button btnFilters, btnViewSaved, btnQuit;

    public static ArrayList<String> savedImages = new ArrayList<>();

    public static boolean cameraPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFilters = findViewById(R.id.btn_filters);
        btnViewSaved = findViewById(R.id.btn_viewSaved);
        btnQuit = findViewById(R.id.btn_quit);

        cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!cameraPermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }

        String path = Environment.getExternalStorageDirectory().toString() + "/myFilters/";
        File directory = new File(path);
        if (directory.exists())
        {
            File[] files = directory.listFiles();

            if (files.length != 0 && files != null){

                savedImages.clear();

                for (int i = 0; i < files.length; i++)
                {
                    String save_path = files[i].getPath();
                    Log.e("Files", "FilePath: " + save_path);
                    savedImages.add(save_path);
                }

            }
        }

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Filters.class);
                startActivity(i);
            }
        });

        btnViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MesImages.class);
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

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermission = true;
            }
        }
    }

}
