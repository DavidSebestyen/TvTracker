package com.davids.android.tvtracker.Ui;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davids.android.tvtracker.Data.TvShowContract;
import com.davids.android.tvtracker.Model.TvShow;
import com.davids.android.tvtracker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by krypt on 04/06/2017.
 */

public class TvShowDetailFragment extends Fragment {
    TextView title;
    ImageView TvShowImage;

    TextView overview;


    TextView rating;

    FloatingActionButton favorite;

    FloatingActionButton unFavorite;

    public static TvShowDetailFragment newInstance(TvShow tvShow) {
        TvShowDetailFragment f = new TvShowDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("index", tvShow);
        f.setArguments(args);
        return f;

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        final TvShow tvShow = getArguments().getParcelable("index");
        title = (TextView) v.findViewById(R.id.movite_title);
        TvShowImage = (ImageView) v.findViewById(R.id.detail_tv_show_image_view);
        overview = (TextView) v.findViewById(R.id.movie_overview);
        favorite = (FloatingActionButton) v.findViewById(R.id.favorite_button);
        unFavorite = (FloatingActionButton) v.findViewById(R.id.unfavorite_button);
        final int id = tvShow.getId();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite(v, id, tvShow);
            }
        });

        unFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFavorite(tvShow);
            }
        });


        overview.setText(tvShow.getSynopsis());
        title.setText(tvShow.getName());
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w342/" + tvShow.getPoster()).into(TvShowImage);


        return v;
    }

    public void addToFavorite (View v, int id, final TvShow tvShow){
        final int tvId = id;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (!isFavorite()){
                    ContentValues movieValues = new ContentValues();
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_ID, tvId);
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_COVER, tvShow.getBackdrop());
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_DATE, tvShow.getReleaseDate());
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_POSTER_PATH, tvShow.getPoster());
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_RATING, tvShow.getVote());
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_SYNOPSIS, tvShow.getSynopsis());
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_TITLE, tvShow.getName());
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_POPULAR, 0);
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_TOP_RATED, 0);
                    movieValues.put(TvShowContract.TvShowEntry.COLUMN_FAVORITE, 1);

                    Uri uri = getActivity().getContentResolver().insert(TvShowContract.TvShowEntry.CONTENT_URI, movieValues);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateFavoriteButton();
            }
        }.execute();
    }

    public void deleteFavorite(final TvShow tvShow){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isFavorite()){
                    getActivity().getContentResolver().delete(TvShowContract.TvShowEntry.CONTENT_URI,
                            TvShowContract.TvShowEntry.COLUMN_ID + " = " + tvShow.getId(),
                            null);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateFavoriteButton();
            }
        }.execute();
    }

    private boolean isFavorite(){
        final TvShow tvShow = getArguments().getParcelable("index");

        Cursor favoriteCursor = getActivity().getContentResolver().query(TvShowContract.TvShowEntry.CONTENT_URI,
                new String[]{TvShowContract.TvShowEntry.COLUMN_ID},
                TvShowContract.TvShowEntry.COLUMN_ID + " = " + tvShow.getId(),
                null,
                null);

        if(favoriteCursor != null && favoriteCursor.moveToFirst()){
            favoriteCursor.close();
            return true;
        } else {
            return false;
        }
    }

    public void updateFavoriteButton(){
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return isFavorite();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(!isFavorite()){
                    favorite.setVisibility(View.VISIBLE);
                    unFavorite.setVisibility(View.GONE);
                } else {
                    favorite.setVisibility(View.GONE);
                    unFavorite.setVisibility(View.VISIBLE);

                }
            }
        }.execute();
    }


}
