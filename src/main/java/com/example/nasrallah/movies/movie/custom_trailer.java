package com.example.nasrallah.movies.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.models.movie_model;
import com.example.nasrallah.movies.models.review_model;
import com.example.nasrallah.movies.models.trailer_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/20/16.
 */

public class custom_trailer extends ArrayAdapter<trailer_model> {
    //ArrayList<trailer_model> list;
String pos;
    int i;
    public custom_trailer(Context context,String poster) {
        super(context, 0);
        pos=poster;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tra_item, parent, false);
        }

        TextView tra = (TextView) convertView.findViewById(R.id.trae);


        ImageView imageView2 = (ImageView) convertView.findViewById(R.id.item2);
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185" + pos).resize(185, 278)
                .into(imageView2);
        tra.setText("Trailer "+(position+1));


        return convertView;
    }


}
