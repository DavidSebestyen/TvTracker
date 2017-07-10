package com.davids.android.tvtracker.Network;

import com.davids.android.tvtracker.Model.SeasonList;
import com.davids.android.tvtracker.Model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by krypt on 20/03/2017.
 */

public interface ApiInterface {

    @GET("tv/top_rated")
    Call<TvShowResponse> getTopRatedTvShows(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvShowResponse> getPopularTvShows(@Query("api_key") String apiKey);

    @GET("search/tv")
    Call<TvShowResponse> searchTvShow (@Query("api_key") String apiKey, @Query("query") String query);

    @GET("tv/{tv_id}/videos")
    Call<TvShowResponse> tvShowVideos (@Path("id") String id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<SeasonList> tvSeasons (@Path("tv_id") String id, @Query("api_key") String apiKey);


}
