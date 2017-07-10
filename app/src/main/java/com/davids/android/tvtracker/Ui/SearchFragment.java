package com.davids.android.tvtracker.Ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.davids.android.tvtracker.Data.TvShowContract;
import com.davids.android.tvtracker.Model.SearchTvShowAdapter;
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
 * Created by krypt on 21/05/2017.
 */

public class SearchFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor>, TvShowCursorAdapter.PosterCursorItemClickListener, SearchTvShowAdapter.PosterItemClickListener {

    RecyclerView mResultsRecycleView;



    ImageButton mSearchButton;

    private static final int NUM_LIST_ITEMS = 100;

    private static String API_KEY = "0e9cd8218337c6909070f6bbdfe4dd83";

    private TvShowCursorAdapter mAdapter;

    ArrayList<TvShow> mTvShowArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);




        mResultsRecycleView = (RecyclerView) rootView.findViewById(R.id.results_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mResultsRecycleView.setLayoutManager(layoutManager);



        mTvShowArrayList = new ArrayList<>();
        ButterKnife.bind(getActivity());

        search();


        return rootView;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void search(){


        String searchQuery = getArguments().getString("query_string");

        final SearchTvShowAdapter.PosterItemClickListener listener = this;


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TvShowResponse> call = apiService.searchTvShow(API_KEY, searchQuery);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                final List<TvShow> movies = response.body().getResults();
                mTvShowArrayList.clear();
                List<TvShow> moviesResult = movies;
                for (TvShow singleMovie : response.body().getResults()) {
                    mTvShowArrayList.add(singleMovie);
                }
                mResultsRecycleView.setAdapter(new SearchTvShowAdapter(getActivity(), NUM_LIST_ITEMS, moviesResult, listener));
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.e("ERROR", t.toString());

            }
        });





    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), TvShowContract.TvShowEntry.CONTENT_URI, null, "isFavorite = 1", null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        TvShowCursorAdapter.PosterCursorItemClickListener listenerCursor = null;

        listenerCursor = (TvShowCursorAdapter.PosterCursorItemClickListener) getActivity();

        if (data.moveToFirst()) {
            mTvShowArrayList.clear();
            data.moveToFirst();

            TvShow movie = new TvShow(data.getInt(data.getColumnIndex("movie_id")),
                    data.getString(data.getColumnIndex("title")),
                    data.getString(data.getColumnIndex("poster_path")),
                    data.getString(data.getColumnIndex("backdrop_path")),
                    data.getString(data.getColumnIndex("release_date")),
                    data.getDouble(data.getColumnIndex("vote_average")),
                    data.getString(data.getColumnIndex("overview")));
            mTvShowArrayList.add(movie);

            while (data.moveToNext()) {
                movie = new TvShow(data.getInt(data.getColumnIndex("movie_id")),
                        data.getString(data.getColumnIndex("title")),
                        data.getString(data.getColumnIndex("poster_path")),
                        data.getString(data.getColumnIndex("backdrop_path")),
                        data.getString(data.getColumnIndex("release_date")),
                        data.getDouble(data.getColumnIndex("vote_average")),
                        data.getString(data.getColumnIndex("overview")));
                mTvShowArrayList.add(movie);
            }

            this.mAdapter = new TvShowCursorAdapter(getActivity(), data, listenerCursor);
            mResultsRecycleView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onPosterItemCursorClick(int clickedPosterCursorIndex) {

    }

    @Override
    public void onPopPosterItemClick(int clickedPosterIndex) {

        Context context = getActivity();
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra("TV_SHOW", mTvShowArrayList.get(clickedPosterIndex));
        startActivity(i);

    }
}
