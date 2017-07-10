package com.davids.android.tvtracker.Ui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.davids.android.tvtracker.Model.TvShow;
import com.davids.android.tvtracker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by krypt on 04/06/2017.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_show_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.tv_show_viewpager)
    ViewPager mViewPager;

    TvShow tvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        tvShow = bundle.getParcelable("TV_SHOW");


        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager mViewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private int NUM_ITEMS = 2;

        private String[] titles = {
                "Details", "Seasons and Episodes"
        };




        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];

        }

        @Override
        public Fragment getItem(int position) {




            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return TvShowDetailFragment.newInstance(tvShow);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return TvShowEpisodesFragment.newInstance(tvShow);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
