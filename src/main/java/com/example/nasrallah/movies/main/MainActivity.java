package com.example.nasrallah.movies.main;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;


import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.detail.detail;
import com.example.nasrallah.movies.detail.detail_fragment;
import com.example.nasrallah.movies.listener_for_frag;
import com.example.nasrallah.movies.models.movie_model;

public class MainActivity extends AppCompatActivity implements listener_for_frag {
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout flPanel2 = (FrameLayout) findViewById(R.id.f_m_panel2);
        if (null == flPanel2) {
            mTwoPane = false;
        } else {
            mTwoPane = true;
        }
        Main_Fragment main_fragment = new Main_Fragment();

        if (null == savedInstanceState) {
            if (mTwoPane) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.f_m_panel1, main_fragment, "main list")
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.activity_main, main_fragment, "main list").commit();
            }
        }


    }

    @Override
    public void set_movie_model(movie_model s) {

        Log.e("vvbvbvbv", s.getTitle().toString());

        if (mTwoPane) {
            detail_fragment detail_fragment = new detail_fragment();
            Log.e("main_activity", "detail");
            Bundle extra = new Bundle();
            extra.putSerializable("title", s);
            detail_fragment.setArguments(extra);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.f_m_panel2, detail_fragment, "main list")
                    .commit();

        } else {
            Intent i = new Intent(this, detail.class);
            i.putExtra("title", s);
            startActivity(i);
        }
    }
}

