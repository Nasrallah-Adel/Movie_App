package com.example.nasrallah.movies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 11/10/16.
 */

public class movieContract {
    public static final String CONTENT_AUTHORITY = "com.example.nasr.movie.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_DETAILS = "details";

    public static final class DetailsEntry implements BaseColumns {
        public static  final String COLUMN_vote_average="vote_average";
        public static final String COLUMN_overview = "overview";
        public static final String COLUMN_title = "title";
        public static final String COLUMN_date = "date";
        public static final String COLUMN_image = "image";
        public static final String COLUMN_id = "id";
        public static final String TABLE_NAME = "fav_movie";
        public static final String COLUMN_background= "back";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAILS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAILS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAILS;


        public static Uri buildDetailsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}