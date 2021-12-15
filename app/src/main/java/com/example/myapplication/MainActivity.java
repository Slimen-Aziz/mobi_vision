package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String apiURL = "http://192.168.137.1/";

    Button btnFilters, btnViewSaved, btnQuit;

    public static ArrayList<String> savedImages = new ArrayList<>();

    /*public static boolean readPermission = false;
    public static boolean writePermission = false;
    public static boolean internetPermission = false;
    public static boolean netStatePermission = false;
    public static boolean cameraPermission = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFilters = findViewById(R.id.btn_filters);
        btnViewSaved = findViewById(R.id.btn_viewSaved);
        btnQuit = findViewById(R.id.btn_quit);


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

        /*writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!writePermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }

        readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!readPermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2);
        }

        internetPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
        if (!internetPermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    3);
        }

        netStatePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
        if (!netStatePermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    4);
        }

        cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!cameraPermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    5);
        }*/

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    writePermission = true;
                }
                else finish();
            }
        }

        if (requestCode == 2)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    readPermission = true;
                }
                else finish();
            }
        }

        if (requestCode == 3)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    internetPermission = true;
                }
                else finish();
            }
        }

        if (requestCode == 4)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    netStatePermission = true;
                }
                else finish();
            }
        }

        if (requestCode == 5)
        {
            if (grantResults.length > 0)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    cameraPermission = true;
                }
                else finish();
            }
        }

    }*/

}
