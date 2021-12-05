package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajout extends AppCompatActivity {

    EditText ednom, edprenom, ednum;
    Button btnval, btnquit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        ednom = findViewById(R.id.ednom_ajout);
        edprenom = findViewById(R.id.edprenom_ajout);
        ednum = findViewById(R.id.ednum_ajout);
        btnval = findViewById(R.id.btnval_ajout);
        btnquit = findViewById(R.id.btnquit_ajout);

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
                String prenom = edprenom.getText().toString();
                String num = ednum.getText().toString();

                Contact c = new Contact(nom, prenom, num);
                Acceuil.data.add(c);

                String toPrint = "data count: " + Acceuil.data.size();
                Toast.makeText(Ajout.this, toPrint, Toast.LENGTH_SHORT).show();

                //Intent i = new Intent(Acceuil.this, Edition.class);
                //startActivity(i);

            }
        });

    }

}
