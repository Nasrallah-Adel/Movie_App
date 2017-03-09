package com.example.nasrallah.movies.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.models.review_model;


import java.util.ArrayList;


/**
 * Created by root on 10/20/16.
 */

public class custom_review extends ArrayAdapter<review_model> {


    public custom_review(Context context) {
        super(context, 0);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rev_item, parent, false);
        }

        TextView aut = (TextView) convertView.findViewById(R.id.aut);
        TextView cont = (TextView) convertView.findViewById(R.id.con);
        aut.setText(getItem(position).getName());
        cont.setText(getItem(position).getContent());


        return convertView;
    }
}
