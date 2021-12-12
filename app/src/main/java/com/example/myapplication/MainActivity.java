package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    EditText ednom; //edmp;
    Button btnval, btnquit;
    FloatingActionButton settBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednom = findViewById(R.id.ednom_auth);
        //edmp = findViewById(R.id.edmp_auth);
        btnval = findViewById(R.id.btnval_auth);
        btnquit = findViewById(R.id.btnquit_auth);
        settBtn = findViewById(R.id.settingsBtn_auth);

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
                /*String mp = edmp.getText().toString();
                if (nom.equalsIgnoreCase("Aziz") && mp.equals("123"))
                {
                    Intent i = new Intent(MainActivity.this, Acceuil.class);
                    i.putExtra("user", nom);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Valeur(s) non valide(s)", Toast.LENGTH_SHORT).show();
                }*/

                Intent i = new Intent(MainActivity.this, Acceuil.class);
                i.putExtra("user", nom);
                startActivity(i);

            }
        });

        settBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.dialog_settings, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.ed_api_dialog);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        Acceuil.apiURL = userInput.getText().toString();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

    }

}
