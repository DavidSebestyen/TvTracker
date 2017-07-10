package com.davids.android.tvtracker.Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davids.android.tvtracker.R;

/**
 * Created by krypt on 20/05/2017.
 */

public class DrawerItemAdapter {

    public static class DrawerItemCustomAdapter extends ArrayAdapter<DrawerModel> {

        Context mContext;
        int layoutResourceId;
        DrawerModel data[] = null;

        public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, DrawerModel[] data) {

            super(mContext, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItem = convertView;

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            listItem = inflater.inflate(layoutResourceId, parent, false);
            TextView textViewName = (TextView) listItem.findViewById(R.id.title_tv);

            DrawerModel folder = data[position];
            textViewName.setText(folder.name);

            return listItem;
        }
}}
