package com.davids.android.tvtracker.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by krypt on 15/03/2017.
 */

public class TvShowContract {

    public static final String CONTENT_AUTHORITY = "com.davids.android.tvtracker";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TV_SHOW = "tv_show";

    public static final class TvShowEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SHOW).build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_COVER = "backdrop_path";
        public static final String COLUMN_SYNOPSIS = "overview";
        public static final String COLUMN_RATING = "vote_average";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_POPULAR = "isPopular";
        public static final String COLUMN_TOP_RATED = "isTopRated";
        public static final String COLUMN_FAVORITE = "isFavorite";

    }
}
