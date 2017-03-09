package com.example.nasrallah.movies.detail;


import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.models.movie_model;


/**
 * Created by root on 10/21/16.
 */

public class detail extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.detail);
        if (savedInstanceState == null) {
            detail_fragment detail_fragment = new detail_fragment();
            movie_model s = new movie_model();
            s = (movie_model) getIntent().getSerializableExtra("title");
            Log.e("detail", "detail");
            detail_fragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().add(R.id.detail, detail_fragment).commit();
        }

    }


}
