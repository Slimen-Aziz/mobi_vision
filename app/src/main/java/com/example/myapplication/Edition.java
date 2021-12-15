package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Edition extends AppCompatActivity {

    RecyclerView mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);

        mylist = findViewById(R.id.rv_edition);


        MyPicturesAdapterRV ad = new MyPicturesAdapterRV(this, Acceuil.pictureData);

        LinearLayoutManager manager = new GridLayoutManager(this, 3);
        mylist.setLayoutManager(manager);

        mylist.setAdapter(ad);

    }

}
