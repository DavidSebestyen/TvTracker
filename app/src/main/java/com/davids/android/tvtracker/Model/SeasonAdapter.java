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
 * Created by krypt on 13/06/2017.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>  {
    private int mNumberItems;

    final private SeasonAdapter.SeasonItemClickListener mOnClickListener;

    private Context context;
    private List<Season> mSeason;

    public interface SeasonItemClickListener {
        void onPopPosterItemClick(int clickedPosterIndex);
    }


    public SeasonAdapter(Context context, int numberOfItems, List<Season> episodes, SeasonAdapter.SeasonItemClickListener listener) {
        mNumberItems = numberOfItems;
        mSeason = episodes;
        this.context = context;
        mOnClickListener = listener;
    }
    

    @Override
    public SeasonAdapter.SeasonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.seasons_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        SeasonAdapter.SeasonViewHolder viewHolder = new SeasonAdapter.SeasonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeasonAdapter.SeasonViewHolder holder, int position) {

        Season season = mSeason.get(position);

        int seasonNumber = position + 1;

        ImageView imageView = holder.seasonPosterView;

        if (season.getSeasonNumber() == 0){
            holder.titleView.setText("Specials");
        } else {
            holder.titleView.setText("Season: " + season.getSeasonNumber());
        }




        holder.synopsisView.setText("Number of Episodes: " + season.getEpisode_count());

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w342/" + season.getPoster_path())
                .into(imageView);


    }

    @Override
    public int getItemCount() {
        mNumberItems = mSeason.size();
        return mNumberItems;
    }

    class SeasonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String mItem;

        TextView titleView;

        TextView synopsisView;

        ImageView seasonPosterView;

        public SeasonViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.season_title);

            synopsisView = (TextView) itemView.findViewById(R.id.season_synopsis);

            seasonPosterView = (ImageView) itemView.findViewById(R.id.season_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPopPosterItemClick(clickedPosition);
        }
    }
}
