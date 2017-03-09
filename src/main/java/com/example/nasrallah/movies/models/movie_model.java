package com.example.nasrallah.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by root on 10/20/16.
 */

public class movie_model implements Serializable {
int id;
    String orignal_title, title, poster_path, vote_count, vote_average, backdrop_path, release_date, overview, adult;

    public movie_model() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrignal_title(String orignal_title) {
        this.orignal_title = orignal_title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOrignal_title() {
        return orignal_title;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getVote_count() {
        return vote_count;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getAdult() {
        return adult;
    }


}
