package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPicturesAdapterRV extends RecyclerView.Adapter<MyPicturesAdapterRV.MyViewHolder> {

    Context con;
    ArrayList<String> data;


    public MyPicturesAdapterRV(Context con, ArrayList<String> data)
    {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyPicturesAdapterRV.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, null);
        return new MyPicturesAdapterRV.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPicturesAdapterRV.MyViewHolder holder, int position) {
        String imgKey = data.get(position);

        byte[] imageBytes = Base64.decode(imgKey, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        holder.img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,200, false));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_contact);

        }
    }

}
