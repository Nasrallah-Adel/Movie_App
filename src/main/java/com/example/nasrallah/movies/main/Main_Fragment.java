package com.example.nasrallah.movies.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.data.movieContract;
import com.example.nasrallah.movies.detail.detail;
import com.example.nasrallah.movies.listener_for_frag;
import com.example.nasrallah.movies.models.movie_model;
import com.example.nasrallah.movies.models.trailer_model;
import com.example.nasrallah.movies.movie.custom_movie;
import com.example.nasrallah.movies.movie.custom_trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by root on 10/20/16.
 */

public class Main_Fragment extends Fragment {
    custom_movie mov_list_obj;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //


        if (id == R.id.pop) {

            editor.putString("menu", "popular");
            Log.e("pop", "referesh Clicked");

            update_data("popular");

        }
        if (id == R.id.top) {
            Log.e("top", "referesh Clicked");
            editor.putString("menu", "top_rated");
            update_data("top_rated");

        }
        if (id == R.id.fav) {
            Log.e("fav", "fav Clicked");
            editor.putString("menu", "fav");
            get_fav();

        }
        editor.apply();
        //
        return super.onOptionsItemSelected(item);
    }

    movie_model[] arr;

    void get_fav() {
        Cursor cursor = getActivity().getContentResolver().query(movieContract.DetailsEntry.CONTENT_URI, null, null, null, null);
        arr = new movie_model[cursor.getCount()];
        ArrayList<movie_model> mov = new ArrayList<movie_model>();
        int i = 0;
        while (cursor.moveToNext()) {

            arr[i] = new movie_model();
            arr[i].setId(cursor.getInt(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_id)));
            arr[i].setPoster_path(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_image)));
            arr[i].setTitle(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_title)));
            arr[i].setOverview(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_overview)));
            arr[i].setRelease_date(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_date)));
            arr[i].setVote_average(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_vote_average)));
            arr[i].setBackdrop_path(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_background)));
            mov.add(arr[i]);

        }
        mov_list_obj.clear();

        for (movie_model m : mov) {

            mov_list_obj.add(m);
        }
        mov_list_obj.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getContext().getSharedPreferences("men", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("menu", "popular");
        editor.apply();
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        String s = pref.getString("menu", "");
        if (s == "fav") {
            get_fav();
        } else {
            update_data(s);
        }
    }

    listener_for_frag listener_for_frag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        mov_list_obj = new custom_movie(getContext());

        GridView GView = (GridView) view.findViewById(R.id.Gridview);
        GView.setAdapter(mov_list_obj);
        GView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                movie_model s = mov_list_obj.getItem(position);
                Log.e("intent main fragment ", s.getTitle().toString());
                Toast.makeText(getContext(), s.getTitle(), Toast.LENGTH_LONG).show();

                listener_for_frag = (com.example.nasrallah.movies.listener_for_frag) getActivity();
                listener_for_frag.set_movie_model(s);

            }


        });
        return view;

    }


    void update_data(String d) {

        fetch_data fetchMovieData = new fetch_data();
        fetchMovieData.execute(d);
    }

    public class fetch_data extends AsyncTask<String, Void, ArrayList<movie_model>> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String Json;

        @Override
        protected ArrayList<movie_model> doInBackground(String... params) {

            try {

                Uri builder = Uri.parse("http://api.themoviedb.org/3/movie/" + params[0]).buildUpon()
                        .appendQueryParameter("api_key", "9a6d8bdc648d7a681c5fd64f296f0766").build();
                URL url = new URL(builder.toString());
                Log.e("point 3", builder.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer st = new StringBuffer();
                while ((line = reader.readLine()) != null) {

                    st.append(line + "\n");
                }
                line = st.toString();
                Json = line;


                Log.e("Point1", Json);
                Log.e("jsjsj", line);
                return pars_json(Json);
            } catch (Exception e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }


        }

        public ArrayList<movie_model> pars_json(String json) throws JSONException {
            ArrayList<movie_model> mov = new ArrayList<movie_model>();
            Log.e("yyyy", Json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = (JSONArray) jsonObject.get("results");
            movie_model[] arr = new movie_model[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonMovieObject = array.getJSONObject(i);
                arr[i] = new movie_model();
                arr[i].setAdult(jsonMovieObject.getString("adult"));
                arr[i].setTitle(jsonMovieObject.getString("title"));
                arr[i].setOrignal_title(jsonMovieObject.getString("original_title"));
                arr[i].setPoster_path(jsonMovieObject.getString("poster_path"));
                arr[i].setRelease_date(jsonMovieObject.getString("release_date"));
                arr[i].setOverview(jsonMovieObject.getString("overview"));
                arr[i].setId(Integer.parseInt(jsonMovieObject.getString("id")));
                arr[i].setVote_average(jsonMovieObject.getString("vote_average") + " / 10");
                arr[i].setBackdrop_path(jsonMovieObject.getString("backdrop_path"));
                mov.add(arr[i]);
            }

            return mov;
        }

        @Override
        protected void onPostExecute(ArrayList<movie_model> movie_models) {
            mov_list_obj.clear();

            for (movie_model m : movie_models) {

                mov_list_obj.add(m);
            }
            mov_list_obj.notifyDataSetChanged();
        }
    }

}
