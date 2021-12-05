package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ednom, edmp;
    Button btnval, btnquit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednom = findViewById(R.id.ednom_auth);
        edmp = findViewById(R.id.edmp_auth);
        btnval = findViewById(R.id.btnval_auth);
        btnquit = findViewById(R.id.btnquit_auth);

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = ednom.getText().toString();
                String mp = edmp.getText().toString();
                if (nom.equalsIgnoreCase("Aziz") && mp.equals("123"))
                {
                    Intent i = new Intent(MainActivity.this, Acceuil.class);
                    i.putExtra("user", nom);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Valeur(s) non valide(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
