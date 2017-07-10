package com.davids.android.tvtracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krypt on 26/06/2017.
 */

public class Season implements Parcelable {

    private int id;
    private int season_number;
    private int episode_count;
    private String air_date;
    private String poster_path;

    public Season(int id, int seasonNumber, int episodeCount, String airDate, String poster_path) {
        this.id = id;
        this.season_number = seasonNumber;
        this.episode_count = episodeCount;
        this.air_date = airDate;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeasonNumber() {
        return season_number;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.season_number = seasonNumber;
    }

    public String getAirDate() {
        return air_date;
    }

    public void setAirDate(String airDate) {
        this.air_date = airDate;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public static final Parcelable.Creator<Season> CREATOR
            = new Parcelable.Creator<Season>() {

        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        public Season[] newArray(int size) {
            return new Season[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(season_number);
        out.writeInt(episode_count);
        out.writeString(air_date);
        out.writeString(poster_path);



    }

    public Season(Parcel in){
        in.writeInt(id);
        in.writeInt(season_number);
        in.writeInt(episode_count);
        in.writeString(air_date);
        in.writeString(poster_path);
    }
}
