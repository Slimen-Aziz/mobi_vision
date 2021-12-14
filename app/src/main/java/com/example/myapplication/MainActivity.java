package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static String apiURL = "http://41.62.103.62/";

    Button btnBlur, btnBW, btnFilterFinal, btnViewSaved, btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBlur = findViewById(R.id.btn_blur);
        btnBW = findViewById(R.id.btn_bw);
        btnFilterFinal = findViewById(R.id.btn_filterFinal);
        btnViewSaved = findViewById(R.id.btn_viewSaved);
        btnQuit = findViewById(R.id.btn_quit);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Blur.class);
                i.putExtra("filterType", "Blur");
                startActivity(i);
            }
        });

        btnBW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Blur.class);
                i.putExtra("filterType", "Black & White");
                startActivity(i);
            }
        });

        btnFilterFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Blur.class);
                i.putExtra("filterType", "Final");
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
