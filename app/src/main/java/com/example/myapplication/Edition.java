package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Edition extends AppCompatActivity {

    RecyclerView mylist; //it was a ListView Prior
    EditText edrech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);

        mylist = findViewById(R.id.rv_edition);
        //edrech = findViewById(R.id.edrech_edition);

        //ArrayAdapter ad = new ArrayAdapter(Edition.this, android.R.layout.simple_list_item_1, Acceuil.data);
        //MyAdapter ad = new MyAdapter(this, Acceuil.data);

        //MyContactAdapterRV ad = new MyContactAdapterRV(this, Acceuil.data);
        //MyPicturesAdapterRV ad = new MyPicturesAdapterRV(this, Acceuil.pictureData);
        MyPicturesAdapterRV ad = new MyPicturesAdapterRV(this, MainActivity.savedImages);

        LinearLayoutManager manager = new GridLayoutManager(this, 1);
        //LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mylist.setLayoutManager(manager);

        mylist.setAdapter(ad);

    }

}
