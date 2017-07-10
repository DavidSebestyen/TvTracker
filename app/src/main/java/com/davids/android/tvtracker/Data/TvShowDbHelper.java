package com.davids.android.tvtracker.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by krypt on 15/03/2017.
 */

public class TvShowDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 2;

    public TvShowDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " + TvShowContract.TvShowEntry.TABLE_NAME + " (" +
                TvShowContract.TvShowEntry._ID + " INTEGER PRIMARY KEY, " +
                TvShowContract.TvShowEntry.COLUMN_ID + " INTEGER NO NULL," +
                TvShowContract.TvShowEntry.COLUMN_TITLE + " TEXT, " +
                TvShowContract.TvShowEntry.COLUMN_POSTER_PATH + " TEXT, " +
                TvShowContract.TvShowEntry.COLUMN_SYNOPSIS + " TEXT, " +
                TvShowContract.TvShowEntry.COLUMN_RATING + " TEXT, " +
                TvShowContract.TvShowEntry.COLUMN_DATE + " TEXT, " +
                TvShowContract.TvShowEntry.COLUMN_COVER + " TEXT, " +
                TvShowContract.TvShowEntry.COLUMN_POPULAR + " INTEGER DEFAULT 0,  " +
                TvShowContract.TvShowEntry.COLUMN_TOP_RATED + " INTEGER DEFAULT 0,  " +
                TvShowContract.TvShowEntry.COLUMN_FAVORITE + " INTEGER DEFAULT 0  " + " );";
        db.execSQL(CREATE_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TvShowContract.TvShowEntry.TABLE_NAME);
        onCreate(db);

    }
}
