package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactAdapterRV extends RecyclerView.Adapter<MyContactAdapterRV.MyViewHolder> {

    Context con;
    ArrayList<Contact> data;


    public MyContactAdapterRV(Context con, ArrayList<Contact> data)
    {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyContactAdapterRV.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, null);
        return new MyViewHolder(v);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyContactAdapterRV.MyViewHolder holder, int position) {
        Contact c = data.get(position);
        holder.tvnomprenom.setText(c.getNom() + " " + c.getPrenom());
        holder.tvnum.setText(c.getNumero());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvnomprenom, tvnum;
        ImageButton btndel, btnmod, btncall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvnomprenom = itemView.findViewById(R.id.tvnom_contact);
            //tvnum = itemView.findViewById(R.id.tvnum_contact);

            //btndel = itemView.findViewById(R.id.btndel_contact);
            //btnmod = itemView.findViewById(R.id.btnmod_contact);
            //btncall = itemView.findViewById(R.id.btndel_contact);

            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    data.remove(index);
                    MyContactAdapterRV.this.notifyDataSetChanged();
                }
            });

            btnmod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });

            btncall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });

        }
    }
}
