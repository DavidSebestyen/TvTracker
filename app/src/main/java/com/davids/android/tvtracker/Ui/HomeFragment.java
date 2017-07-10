package com.davids.android.tvtracker.Ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.davids.android.tvtracker.Data.TvShowContract;
import com.davids.android.tvtracker.Model.TopRatedTvShowAdapter;
import com.davids.android.tvtracker.Model.TvShow;
import com.davids.android.tvtracker.Model.PopularTvShowAdapter;
import com.davids.android.tvtracker.Model.TvShowCursorAdapter;
import com.davids.android.tvtracker.Model.TvShowResponse;
import com.davids.android.tvtracker.Network.ApiClient;
import com.davids.android.tvtracker.Network.ApiInterface;
import com.davids.android.tvtracker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by krypt on 20/05/2017.
 */

public class HomeFragment extends Fragment implements PopularTvShowAdapter.PosterItemClickListener, TopRatedTvShowAdapter.TopPosterItemClickListener {

    RecyclerView mMostPopularRecyclerView;

    RecyclerView mTopRatedRecyclerView;

    ArrayList<TvShow> mTopTvShowArrayList;

    ArrayList<TvShow> mPopTvShowArrayList;

    private final String TAG = HomeFragment.class.getSimpleName();

    private static final int NUM_LIST_ITEMS = 100;

    private static String API_KEY = "0e9cd8218337c6909070f6bbdfe4dd83";

    private TvShowCursorAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        final PopularTvShowAdapter.PosterItemClickListener popListener = this;
        final TopRatedTvShowAdapter.TopPosterItemClickListener topListener = this;

        mMostPopularRecyclerView = (RecyclerView) v.findViewById(R.id.most_popular_recycler_view);
        mTopRatedRecyclerView = (RecyclerView) v.findViewById(R.id.top_rated_recycler_view);

        GridLayoutManager mostPopularGridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager topRatedGridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);

        mMostPopularRecyclerView.setLayoutManager(mostPopularGridLayoutManager);
        mMostPopularRecyclerView.setHasFixedSize(true);
        mTopRatedRecyclerView.setLayoutManager(topRatedGridLayoutManager);
        mTopRatedRecyclerView.setHasFixedSize(true);



        ButterKnife.bind(getActivity());


        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_SHORT).show();

        }
        mTopTvShowArrayList = new ArrayList<>();
        mPopTvShowArrayList = new ArrayList<>();
        getPopular(mMostPopularRecyclerView);
        getTopRated(mTopRatedRecyclerView);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void getPopular(RecyclerView mRecyclerView) {
        final RecyclerView recyclerView = mRecyclerView;

        final PopularTvShowAdapter.PosterItemClickListener listener = this;

        mRecyclerView.setVisibility(View.VISIBLE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TvShowResponse> call = apiService.getPopularTvShows(API_KEY);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                final List<TvShow> movies = response.body().getResults();
                mPopTvShowArrayList.clear();
                List<TvShow> moviesResult = movies;
                for (TvShow singleMovie : response.body().getResults()) {
                    mPopTvShowArrayList.add(singleMovie);
                }
                recyclerView.setAdapter(new PopularTvShowAdapter(getActivity(), NUM_LIST_ITEMS, moviesResult, listener));
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.e("ERROR", t.toString());

            }
        });

    }

    public void getTopRated(RecyclerView mRecyclerView) {

        final RecyclerView recyclerView = mRecyclerView;

        final TopRatedTvShowAdapter.TopPosterItemClickListener listener = this;

        mRecyclerView.setVisibility(View.VISIBLE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TvShowResponse> call = apiService.getTopRatedTvShows(API_KEY);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                final List<TvShow> movies = response.body().getResults();
                mTopTvShowArrayList.clear();
                List<TvShow> moviesResult = movies;
                for (TvShow singleMovie : response.body().getResults()) {
                    mTopTvShowArrayList.add(singleMovie);
                }
                recyclerView.setAdapter(new TopRatedTvShowAdapter(getActivity(), NUM_LIST_ITEMS, movies, listener));
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.e("ERROR", t.toString());

            }
        });
    }

    @Override
    public void onPosterItemClick(int clickedPosterIndex) {

        Context context = getActivity();
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra("TV_SHOW", mPopTvShowArrayList.get(clickedPosterIndex));
        startActivity(i);

    }



    @Override
    public void onTopPosterItemClick(int clickedPosterIndex) {
        Context context = getActivity();
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra("TV_SHOW", mTopTvShowArrayList.get(clickedPosterIndex));
        startActivity(i);
    }
}
