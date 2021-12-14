package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static String apiURL = "http://102.157.97.246/";

    Button btnBlur, btnBW, btnFilterFinal, btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBlur = findViewById(R.id.btn_blur);
        btnBW = findViewById(R.id.btn_bw);
        btnFilterFinal = findViewById(R.id.btn_filterFinal);
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
                startActivity(i);
            }
        });

        /*btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(MainActivity.this, Acceuil.class);
                //startActivity(i);
            }
        });*/

    }

}
