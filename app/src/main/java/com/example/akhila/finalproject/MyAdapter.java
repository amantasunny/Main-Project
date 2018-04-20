package com.example.akhila.finalproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Person> {

    Activity activity;
    int resource;
    List<Person> list;

    public MyAdapter(Activity activity, int resource, List<Person> list) {
        super(activity, resource,list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);

        ImageView imageView = (ImageView) view.findViewById(R.id.getImages);
        TextView location = (TextView) view.findViewById(R.id.getLocation);
        TextView address = (TextView) view.findViewById(R.id.getAddress);
        TextView contact = (TextView) view.findViewById(R.id.getContact);
        TextView rate = (TextView) view.findViewById(R.id.getRate);

        location.setText(list.get(position).getLocation());
        address.setText(list.get(position).getAddress());
        contact.setText(list.get(position).getContact());
        rate.setText(list.get(position).getRate());
        Glide.with(activity).load(list.get(position).getImageUri()).into(imageView);

        return view;
    }
}
