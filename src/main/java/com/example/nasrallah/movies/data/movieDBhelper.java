package com.example.nasrallah.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 11/10/16.
 */

public class movieDBhelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movie.db";
    static final int DATABASE_VERSION = 4;

    public movieDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DETAILS_TABLE = " CREATE TABLE " + movieContract.DetailsEntry.TABLE_NAME + " (" +
                movieContract.DetailsEntry._ID + " INTEGER PRIMARY KEY," +
                movieContract.DetailsEntry.COLUMN_title+ " VARCHAR(50) NOT NULL," +
                movieContract.DetailsEntry.COLUMN_date + " VARCHAR(50) NOT NULL," +
                movieContract.DetailsEntry.COLUMN_image + " VARCHAR(100) NOT NULL," +
                movieContract.DetailsEntry.COLUMN_overview+ " VARCHAR(300) NOT NULL," +
                movieContract.DetailsEntry.COLUMN_id+ " VARCHAR(300) NOT NULL," +
                movieContract.DetailsEntry.COLUMN_vote_average + " VARCHAR(100) NOT NULL ,"+
                movieContract.DetailsEntry.COLUMN_background+ " VARCHAR(300) NOT NULL" +" )";

        db.execSQL(SQL_CREATE_DETAILS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + movieContract.DetailsEntry.TABLE_NAME);
        onCreate(db);
    }



}
