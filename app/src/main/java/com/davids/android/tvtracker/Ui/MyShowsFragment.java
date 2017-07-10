package com.davids.android.tvtracker.Ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davids.android.tvtracker.Data.TvShowContract;
import com.davids.android.tvtracker.Model.TvShow;
import com.davids.android.tvtracker.Model.TvShowCursorAdapter;
import com.davids.android.tvtracker.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by krypt on 05/07/2017.
 */

public class MyShowsFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView myShowRecyclerView;

    ArrayList<TvShow> mTvShowArrayList;

    private TvShowCursorAdapter mAdapter;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myshows, container, false);


        mTvShowArrayList = new ArrayList<>();

        myShowRecyclerView = (RecyclerView) rootView.findViewById(R.id.myshows_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myShowRecyclerView.setLayoutManager(layoutManager);




        getFavorites(myShowRecyclerView);


        return rootView;
    }

    public void getFavorites(RecyclerView mRecyclerView) {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), TvShowContract.TvShowEntry.CONTENT_URI, null, "isFavorite = 1", null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        TvShowCursorAdapter.PosterCursorItemClickListener listenerCursor = null;



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
            myShowRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
