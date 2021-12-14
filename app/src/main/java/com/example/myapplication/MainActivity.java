package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static String apiURL = "http://41.62.103.62/";

    Button btnFilters, btnViewSaved, btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFilters = findViewById(R.id.btn_filters);
        btnViewSaved = findViewById(R.id.btn_viewSaved);
        btnQuit = findViewById(R.id.btn_quit);


        String path = Environment.getExternalStorageDirectory().toString() + "/myFilters/";
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files.length != 0 && files != null){

            for (int i = 0; i < files.length; i++)
            {
                Log.e("Files", "FileName:" + files[i].getName());
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
                Intent i = new Intent(MainActivity.this, Blur.class);
                startActivity(i);
            }
        });

        btnViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

    }

}
