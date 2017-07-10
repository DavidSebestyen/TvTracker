package com.davids.android.tvtracker.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by krypt on 20/05/2017.
 */

public class TvShow implements Parcelable {

    private int id;
    private String name;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private Double vote_average;
    private String overview;

    public TvShow(int id, String name, String poster_path, String backdrop_path, String release_date, Double vote_average, String overview) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public static TvShow fromCursor(Cursor cursor) {
        return new TvShow(cursor.getInt(cursor.getColumnIndex("movie_id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("poster_path")),
                cursor.getString(cursor.getColumnIndex("backdrop_path")),
                cursor.getString(cursor.getColumnIndex("release_date")),
                cursor.getDouble(cursor.getColumnIndex("vote_average")),
                cursor.getString(cursor.getColumnIndex("overview")));

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public String getPoster() {
        return poster_path;
    }

    public void setPoster(String poster_path) {

        this.poster_path = "http://image.tmdb.org/t/p/w342/" + poster_path;
    }

    public String getBackdrop() {
        return backdrop_path;
    }

    public void setBackdrop(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public Double getVote() {
        return vote_average;
    }

    public void setVote(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getSynopsis() {
        return overview;
    }

    public void setSynopsis(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(poster_path);
        out.writeString(backdrop_path);
        out.writeString(release_date);
        out.writeDouble(vote_average);
        out.writeString(overview);
    }

    // Creator
    public static final Parcelable.Creator<TvShow> CREATOR
            = new Parcelable.Creator<TvShow>() {

        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public TvShow(Parcel in) {
        id = in.readInt();
        name = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();

    }


}
