package com.example.nasrallah.movies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by root on 11/10/16.
 */

public class movieProvider extends ContentProvider {

    movieDBhelper movieDBhelper;

    static final UriMatcher uriMatcher = buildUriMatcher();




    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = movieContract.CONTENT_AUTHORITY;


        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDBhelper = new movieDBhelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

                return movieContract.DetailsEntry.CONTENT_TYPE;

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = movieDBhelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);


                cursor = db.query(
                        movieContract.DetailsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e("insert method","ddddddddddddddddddddd");
        final SQLiteDatabase db =movieDBhelper.getWritableDatabase();
        int match  = uriMatcher.match(uri);
        Uri returnedUri;


                long _id = db.insert(movieContract.DetailsEntry.TABLE_NAME, null, values);
                if(_id>0){
                    returnedUri = movieContract.DetailsEntry.buildDetailsUri(_id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnedUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db =movieDBhelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted



                rowsDeleted = db.delete(
                        movieContract.DetailsEntry.TABLE_NAME, selection, selectionArgs);




        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
