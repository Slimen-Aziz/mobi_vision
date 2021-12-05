package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context con;
    ArrayList<Contact> data;


    public MyAdapter(Context con, ArrayList<Contact> data)
    {
        this.con = con;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, null);

        Contact c = data.get(i);

        TextView tvnom = v.findViewById(R.id.tvnom_contact);
        tvnom.setText(c.getNom() + " " + c.getPrenom());

        TextView tvnum = v.findViewById(R.id.tvnum_contact);
        tvnum.setText(c.getNumero());

        return v;

    }

}
