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
 * Created by krypt on 25/05/2017.
 */

public class SearchTvShowAdapter extends RecyclerView.Adapter<SearchTvShowAdapter.SearchTvShowViewHolder>  {
    private int mNumberItems;

    final private PosterItemClickListener mOnClickListener;

    private Context context;
    private List<TvShow> mTvHows;

    public interface PosterItemClickListener {
        void onPopPosterItemClick(int clickedPosterIndex);
    }


    public SearchTvShowAdapter(Context context, int numberOfItems, List<TvShow> movies, PosterItemClickListener listener) {
        mNumberItems = numberOfItems;
        mTvHows = movies;
        this.context = context;
        mOnClickListener = listener;
    }


    @Override
    public SearchTvShowAdapter.SearchTvShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.search_results_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        SearchTvShowAdapter.SearchTvShowViewHolder viewHolder = new SearchTvShowAdapter.SearchTvShowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchTvShowViewHolder holder, int position) {

        TvShow movie = mTvHows.get(position);

        ImageView imageView = holder.posterView;

        Picasso.with(this.context)
                .load("http://image.tmdb.org/t/p/w342/" + movie.getPoster())
                .into(imageView);
        holder.titleView.setText(movie.getName());

        holder.synopsisView.setText(movie.getSynopsis());
    }

    @Override
    public int getItemCount() {
        mNumberItems = mTvHows.size();
        return mNumberItems;
    }

    class SearchTvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String mItem;

        ImageView posterView;

        TextView titleView;

        TextView synopsisView;

        public SearchTvShowViewHolder(View itemView) {
            super(itemView);

            posterView = (ImageView) itemView.findViewById(R.id.show_search_image_view);
            titleView = (TextView) itemView.findViewById(R.id.show_search_tv);
            synopsisView = (TextView) itemView.findViewById(R.id.search_synopsis);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPopPosterItemClick(clickedPosition);
        }
    }
}
