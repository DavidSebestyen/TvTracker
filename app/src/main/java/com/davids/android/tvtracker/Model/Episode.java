package com.davids.android.tvtracker.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krypt on 13/06/2017.
 */

public class Episode implements Parcelable {

    private int id;
    private String name;
    private String release_date;
    private Double vote_average;
    private String overview;
    private int season_number;

    public Episode(int id, String name, String poster_path, Double vote_average, String overview, int season_number) {
        this.id = id;
        this.name = name;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
        this.season_number = season_number;
    }

    public static Episode fromCursor(Cursor cursor) {
        return new Episode(cursor.getInt(cursor.getColumnIndex("movie_id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("release_date")),
                cursor.getDouble(cursor.getColumnIndex("vote_average")),
                cursor.getString(cursor.getColumnIndex("overview")),
                cursor.getInt(cursor.getColumnIndex("season_number")));

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

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(release_date);
        out.writeDouble(vote_average);
        out.writeString(overview);
        out.writeInt(season_number);
    }

    // Creator
    public static final Parcelable.Creator<Episode> CREATOR
            = new Parcelable.Creator<Episode>() {

        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public Episode(Parcel in) {
        id = in.readInt();
        name = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        season_number = in.readInt();

    }


}