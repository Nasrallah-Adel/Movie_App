package com.example.nasrallah.movies.movie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.data.movieContract;
import com.example.nasrallah.movies.models.movie_model;

/**
 * Created by root on 11/10/16.
 */

public class movieAdapter extends CursorAdapter {

    public movieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        int col_details_id = cursor.getColumnIndex(movieContract.DetailsEntry._ID);
        int col_details_title = cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_title);
        int col_details_date = cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_date);
        int col_details_image = cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_image);
        int col_details_overview = cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_overview);
        int col_back = cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_background);

        int id = cursor.getInt(col_details_id);
        String title = cursor.getString(col_details_title);
        String date = cursor.getString(col_details_date);
        String over = cursor.getString(col_details_overview);
        String img = cursor.getString(col_details_image);
        String back = cursor.getString(col_back);
        movie_model m = new movie_model();
        m.setTitle(title);
        m.setOverview(over);
        m.setRelease_date(date);
        m.setPoster_path(img);
        m.setBackdrop_path(back);

    }

}
