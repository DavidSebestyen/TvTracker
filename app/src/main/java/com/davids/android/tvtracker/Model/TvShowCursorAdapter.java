package com.davids.android.tvtracker.Model;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davids.android.tvtracker.R;
import com.squareup.picasso.Picasso;

/**
 * Created by krypt on 20/03/2017.
 */

public class TvShowCursorAdapter extends CursorRecyclerViewAdapter<TvShowCursorAdapter.ViewHolder> {

    final private PosterCursorItemClickListener mOnClickListener;

    private Context context;

    public interface PosterCursorItemClickListener {
        void onPosterItemCursorClick(int clickedPosterCursorIndex);
    }

    public TvShowCursorAdapter(Context context, Cursor cursor, PosterCursorItemClickListener cursorListener) {
        super(context, cursor);
        mOnClickListener = cursorListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.home_tv_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        TvShow tvShow = null;
        tvShow = TvShow.fromCursor(cursor);

        ImageView imageView = holder.posterView;
        holder.titleView.setText(tvShow.getName());

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w342/" + tvShow.getPoster())
                .into(imageView);



    }

    @Override
    public int getItemCount() {
        Cursor cursor = getCursor();
        return cursor.getCount();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private String mItem;
        // Create a ImageView variable called posterView
        ImageView posterView;

        TextView titleView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         */
        public ViewHolder(View itemView) {
            super(itemView);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            posterView = (ImageView) itemView.findViewById(R.id.movie_image_view);
            titleView = (TextView) itemView.findViewById(R.id.tv_title_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPosterItemCursorClick(clickedPosition);
        }
    }
}
