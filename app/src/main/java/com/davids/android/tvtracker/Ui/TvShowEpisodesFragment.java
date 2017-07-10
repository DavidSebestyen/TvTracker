package com.davids.android.tvtracker.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davids.android.tvtracker.Model.PopularTvShowAdapter;
import com.davids.android.tvtracker.Model.Season;
import com.davids.android.tvtracker.Model.SeasonAdapter;
import com.davids.android.tvtracker.Model.SeasonList;
import com.davids.android.tvtracker.Model.TvShow;
import com.davids.android.tvtracker.Network.ApiClient;
import com.davids.android.tvtracker.Network.ApiInterface;
import com.davids.android.tvtracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by krypt on 04/06/2017.
 */

public class TvShowEpisodesFragment extends Fragment implements SeasonAdapter.SeasonItemClickListener {

    RecyclerView mEpisodeRecyclerView;


    private static String API_KEY = "0e9cd8218337c6909070f6bbdfe4dd83";

    int mId;

    ArrayList<Season> mSeasonArrayList;

    private static final int NUM_LIST_ITEMS = 100;




    public static TvShowEpisodesFragment newInstance(TvShow tvShow) {
        TvShowEpisodesFragment f = new TvShowEpisodesFragment();
        Bundle args = new Bundle();
        args.putParcelable("index", tvShow);
        f.setArguments(args);
        return f;

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_seasons, container, false);


        TvShow tvShow = getArguments().getParcelable("index");

        mId = tvShow.getId();

        String id = String.valueOf(mId);



        mEpisodeRecyclerView = (RecyclerView) v.findViewById(R.id.seasons_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mEpisodeRecyclerView.setLayoutManager(layoutManager);

        final SeasonAdapter.SeasonItemClickListener listener = this;
        mSeasonArrayList = new ArrayList<>();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SeasonList> call = apiService.tvSeasons(id, API_KEY);
        call.enqueue(new Callback<SeasonList>() {

            @Override
            public void onResponse(Call<SeasonList> call, Response<SeasonList> response) {
                List<Season> seasons = response.body().getSeasons();
                mSeasonArrayList.clear();
                List<Season> seasonResult = seasons;
                for (Season singleSeason : response.body().getSeasons()) {
                    mSeasonArrayList.add(singleSeason);

                }

                mEpisodeRecyclerView.setAdapter(new SeasonAdapter(getActivity(), NUM_LIST_ITEMS, seasonResult, listener));

            }

            @Override
            public void onFailure(Call<SeasonList> call, Throwable t) {
                Log.e(TAG, "ERROR + " + t.toString());
            }
        });



        return v;
    }

    @Override
    public void onPopPosterItemClick(int clickedPosterIndex) {

    }
}
