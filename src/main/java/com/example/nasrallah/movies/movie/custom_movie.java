package com.example.nasrallah.movies.movie;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.models.movie_model;
import com.squareup.picasso.Picasso;

/**
 * Created by root on 10/20/16.
 */

public class custom_movie extends ArrayAdapter<movie_model> {

    public custom_movie(Context context) {
        super(context, 0);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.item1);
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185" + getItem(position).getPoster_path()).resize(185,278)
                .into(imageView);


        return convertView;
    }
}
