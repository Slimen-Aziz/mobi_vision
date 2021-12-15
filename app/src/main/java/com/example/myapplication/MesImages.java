package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MesImages extends AppCompatActivity {

    RecyclerView mylist; //it was a ListView Prior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_images);

        mylist = findViewById(R.id.rv_edition);

        MyPicturesAdapterRV ad = new MyPicturesAdapterRV(this, MainActivity.savedImages);

        LinearLayoutManager manager = new GridLayoutManager(this, 3);
        mylist.setLayoutManager(manager);

        mylist.setAdapter(ad);

    }

}
