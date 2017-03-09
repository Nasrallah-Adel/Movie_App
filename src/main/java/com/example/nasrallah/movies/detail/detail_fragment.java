package com.example.nasrallah.movies.detail;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasrallah.movies.R;
import com.example.nasrallah.movies.data.movieContract;
import com.example.nasrallah.movies.main.MainActivity;
import com.example.nasrallah.movies.models.movie_model;
import com.example.nasrallah.movies.models.review_model;
import com.example.nasrallah.movies.models.trailer_model;
import com.example.nasrallah.movies.movie.custom_movie;
import com.example.nasrallah.movies.movie.custom_review;
import com.example.nasrallah.movies.movie.custom_trailer;
//import com.example.nasrallah.movies.recycleview.MainActivity;
import com.squareup.picasso.Picasso;

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
 * Created by root on 10/21/16.
 */

public class detail_fragment extends Fragment {
    custom_trailer tra_list_obj;
    custom_review rev_list_obj;
    public Switch mySwitch;
    movie_model s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        //
        Log.e("test", view.toString());


        if (getActivity().getIntent().hasExtra("title")) {
            Log.e("eror type", "1");
            s = (movie_model) getActivity().getIntent().getSerializableExtra("title");
        } else if (getArguments() != null) {
            Log.e("eror type", "2");
            s = (movie_model) getArguments().getSerializable("title");
        }

        //Intent intent = getActivity().getIntent();
        //s = (movie_model) intent.getSerializableExtra("title");
        Log.e("detail_fragment", "detail");
        Log.e("sdfghjklkjhgf", s.getTitle().toString());
        //Log.e("intent ", s.getAdult());
        ((TextView) view.findViewById(R.id.date)).setText(s.getRelease_date());
        ((TextView) view.findViewById(R.id.over)).setText(s.getOverview());
        ((TextView) view.findViewById(R.id.title)).setText(s.getTitle());
        ((TextView) view.findViewById(R.id.avg)).setText(s.getVote_average());
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185" + s.getPoster_path()).resize(185, 278)
                .into(imageView);

        mySwitch = (Switch) view.findViewById(R.id.fav);
        mySwitch.setChecked(false);
        Log.e("1", "cf");
        get_fav();
        check_fav();

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    Log.e("1", "check");
                    insertNewMovie(getView());

                } else {
                    Log.e("disfav", "check");
                    delete_movie(getView());
                    Log.e("disfav", "check");
                }
            }
        });
        tra_list_obj = new custom_trailer(getContext(), s.getBackdrop_path());
        rev_list_obj = new custom_review(getContext());
        //check the current state before we display the screen


        String id = s.getId() + "";
        System.out.println(s.getId() + "ana aho");
        Log.e(s.getId() + "ana aho", "fav");
        final ListView tra = (ListView) view.findViewById(R.id.traa);
        tra.setAdapter(tra_list_obj);
        tra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                watchYoutubeVideo(tra_list_obj.getItem(position).getKey());
            }
        });

        new fetch_trailer().execute(id);
        //Log.e("fetch tr", "dfdf");
        new fetch_reviews().execute(id);
        ListView rev = (ListView) view.findViewById(R.id.rev);
        rev.setAdapter(rev_list_obj);

        /*

        */

        return view;
    }


    private void check_fav() {
        int i = 0;

        i = 0;

        while (i < arr.length) {
            Log.e(String.valueOf(i), i + "");

            if (arr[i].getTitle().equals(s.getTitle())) {
                mySwitch.setChecked(true);
                break;
            }
            i++;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void watchYoutubeVideo(String id) {
        Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent web = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(app);
        } catch (ActivityNotFoundException ex) {
            startActivity(web);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    public void insertNewMovie(View view) {
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView over = (TextView) view.findViewById(R.id.over);
        TextView vot = (TextView) view.findViewById(R.id.avg);

        ContentValues values = new ContentValues();
        values.put(movieContract.DetailsEntry.COLUMN_date, date.getText().toString());
        values.put(movieContract.DetailsEntry.COLUMN_title, title.getText().toString());
        values.put(movieContract.DetailsEntry.COLUMN_overview, over.getText().toString());
        values.put(movieContract.DetailsEntry.COLUMN_image, s.getPoster_path());
        values.put(movieContract.DetailsEntry.COLUMN_id, s.getId());
        Log.e(s.getId() + "   da elle betsagle", " data base faverot");
        values.put(movieContract.DetailsEntry.COLUMN_vote_average, vot.getText().toString());
        values.put(movieContract.DetailsEntry.COLUMN_background, s.getBackdrop_path());

        Log.e("Output date: ", date.getText().toString());

        Uri uri = this.getActivity().getContentResolver().insert(movieContract.DetailsEntry.CONTENT_URI, values);

        Log.e("Output Uri: ", uri.toString());
        Toast toast = Toast.makeText(getContext(), title.getText() + " Saved as favourite", Toast.LENGTH_LONG);
        toast.show();

    }

    public void delete_movie(View view) {
        Log.e("disfav", "check");
        TextView title = (TextView) view.findViewById(R.id.title);


        String sel[] = new String[]{title.getText().toString()};
        //  int uri = this.getActivity().getContentResolver().delete(movieContract.DetailsEntry.CONTENT_URI,movieContract.DetailsEntry.COLUMN_title+"="+title.getText().toString(),null);
        // ContentResolver cr=this.getActivity().getContentResolver();
        int uri = this.getActivity().getContentResolver().delete(
                movieContract.DetailsEntry.CONTENT_URI,
                movieContract.DetailsEntry.COLUMN_title + " = ?",
                new String[]{title.getText().toString()});

        Log.e("disfav", uri + "");
        Toast toast = Toast.makeText(getContext(), title.getText() + " delete from favourite", Toast.LENGTH_LONG);
        toast.show();

    }

    movie_model[] arr;
    custom_movie mov_list_obj;

    void get_fav() {
        Cursor cursor = getActivity().getContentResolver().query(movieContract.DetailsEntry.CONTENT_URI, null, null, null, null);
        arr = new movie_model[cursor.getCount()];

        int i = 0;
        while (cursor.moveToNext()) {

            arr[i] = new movie_model();
            arr[i].setId(cursor.getInt(cursor.getColumnIndex(movieContract.DetailsEntry._ID)));
            arr[i].setPoster_path(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_image)));
            arr[i].setTitle(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_title)));
            arr[i].setOverview(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_overview)));
            arr[i].setRelease_date(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_date)));
            arr[i].setVote_average(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_vote_average)));
            arr[i].setBackdrop_path(cursor.getString(cursor.getColumnIndex(movieContract.DetailsEntry.COLUMN_background)));
            i++;
        }


    }

    public class fetch_trailer extends AsyncTask<String, Void, ArrayList<trailer_model>> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String Json;

        @Override
        protected void onPostExecute(ArrayList<trailer_model> trailer_models) {
            Log.e("start of ", "onpostexecute trailer");
            tra_list_obj.clear();
            if (trailer_models != null) {
                for (trailer_model m : trailer_models) {

                    tra_list_obj.add(m);
                }
                Log.e("end of ", "onpostexecute before notifay change trailer");
                tra_list_obj.notifyDataSetChanged();
                Log.e("end of ", "onpostexecute after notifay change trailer");

            }
        }

        @Override
        protected ArrayList<trailer_model> doInBackground(String... params) {

            try {
                Log.e("start of ", "doinbackground trailer");
                Uri builder = Uri.parse("http://api.themoviedb.org/3/movie/" + params[0] + "/videos").buildUpon()
                        .appendQueryParameter("api_key", "9a6d8bdc648d7a681c5fd64f296f0766").build();
                URL url = new URL(builder.toString());


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
                Log.e("end of ", "doinbackground trailer");
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

        public ArrayList<trailer_model> pars_json(String json) throws JSONException {
            Log.e("start of ", "parse json trailer");
            ArrayList<trailer_model> mov = new ArrayList<trailer_model>();
            Log.e("yyyy", Json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = (JSONArray) jsonObject.get("results");
            trailer_model[] arr = new trailer_model[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonMovieObject = array.getJSONObject(i);
                arr[i] = new trailer_model();
                arr[i].setId(jsonMovieObject.getString("id"));
                arr[i].setKey(jsonMovieObject.getString("key"));
                arr[i].getName(jsonMovieObject.getString("name"));

                mov.add(arr[i]);
            }
            list_tr = mov;
            Log.e("end of ", "parse trailer");
            return mov;
        }


    }

    public class fetch_reviews extends AsyncTask<String, Void, ArrayList<review_model>> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String Json;


        @Override
        protected void onPostExecute(ArrayList<review_model> review_models) {
            Log.e("start of ", "onpostexecute review");
            rev_list_obj.clear();
            if (review_models != null) {
                for (review_model m : review_models) {

                    rev_list_obj.add(m);
                }
                Log.e("befor notifay", "review");
                rev_list_obj.notifyDataSetChanged();
                Log.e("after notifay ", "review");
            }
        }

        @Override
        protected ArrayList<review_model> doInBackground(String... params) {

            try {
                Log.e("start of ", "doinbackground review review");
                Uri builder = Uri.parse("http://api.themoviedb.org/3/movie/" + params[0] + "/reviews").buildUpon()
                        .appendQueryParameter("api_key", "9a6d8bdc648d7a681c5fd64f296f0766").build();
                URL url = new URL(builder.toString());


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
                Log.e("end of ", "doonbackground review");
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

        public ArrayList<review_model> pars_json(String json) throws JSONException {
            Log.e("start of ", "parse review");
            ArrayList<review_model> mov = new ArrayList<review_model>();
            Log.e("yyyy", Json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = (JSONArray) jsonObject.get("results");
            review_model[] arr = new review_model[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonMovieObject = array.getJSONObject(i);
                arr[i] = new review_model();
                arr[i].setName(jsonMovieObject.getString("author"));
                arr[i].setContent(jsonMovieObject.getString("content"));


                mov.add(arr[i]);
            }
            list_rev = mov;
            Log.e("end of ", "parse review");
            return mov;
        }


    }

    ArrayList<trailer_model> list_tr;
    ArrayList<review_model> list_rev;

}
