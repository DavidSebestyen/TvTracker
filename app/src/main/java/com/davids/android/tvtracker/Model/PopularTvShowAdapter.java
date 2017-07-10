package com.davids.android.tvtracker.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davids.android.tvtracker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by krypt on 20/05/2017.
 */

public class PopularTvShowAdapter extends RecyclerView.Adapter<PopularTvShowAdapter.TvShowViewHolder>  {
    private int mNumberItems;

    final private PosterItemClickListener mOnClickListener;

    private Context context;
    private List<TvShow> mTvHows;

    public interface PosterItemClickListener {
        void onPosterItemClick(int clickedPosterIndex);
    }


    public PopularTvShowAdapter(Context context, int numberOfItems, List<TvShow> movies, PosterItemClickListener listener) {
        mNumberItems = numberOfItems;
        mTvHows = movies;
        this.context = context;
        mOnClickListener = listener;
    }


    @Override
    public TvShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.home_tv_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        TvShowViewHolder viewHolder = new TvShowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TvShowViewHolder holder, int position) {

        TvShow movie = mTvHows.get(position);

        ImageView imageView = holder.posterView;



        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w342/" + movie.getPoster())
                .into(imageView);

        holder.titleView.setText(movie.getName());

    }

    @Override
    public int getItemCount() {
        mNumberItems = mTvHows.size();
        return mNumberItems;
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String mItem;

        ImageView posterView;

        TextView titleView;

        public TvShowViewHolder(View itemView) {
            super(itemView);

            posterView = (ImageView) itemView.findViewById(R.id.movie_image_view);
            titleView = (TextView) itemView.findViewById(R.id.tv_title_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPosterItemClick(clickedPosition);
        }
    }
}
