package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kenplayschool.EventzDetailActivity;
import com.kenplayschool.R;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.LatestFeedDataModell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class LatestFeedsAdapterr extends RecyclerView.Adapter {

    private List<LatestFeedDataModell> latestFeedModell = new ArrayList<>();
    private Context context;


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int type;
    private String flag;
    private int sector_id;
    private int likeType;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount, visibleItemCount;
    private boolean loading;
    private int user_id;
    private OnLoadMoreListener onLoadMoreListener;
    private String toolbar_title;
    RecyclerView mRecyclerView;


    public LatestFeedsAdapterr(List<LatestFeedDataModell> latestFeedModell, final Context context,
                               RecyclerView mRecyclerView) {

        this.context = context;
        this.latestFeedModell = latestFeedModell;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        return latestFeedModell.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_feed_row_item, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        /*holder.head_name.setText(photoModels.get(position).getTitle());
        holder.head_name.setTextColor(Color.WHITE);*/

        holder.row_item_head.setText(latestFeedModell.get(position).getBulletinTitle());
        holder.row_item_date.setText(latestFeedModell.get(position).getStartDate());
        holder.row_item_location.setText(latestFeedModell.get(position).getBulletinComments());
        //load image
		
		try{
        Picasso.with(context)
                .load(latestFeedModell.get(position).getBulletinImage())
                           
                .into(holder.mPhotoView);
		}catch(Exception e){
                 }		
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,EventzDetailActivity.class);
                intent.putExtra("comments", latestFeedModell.get(position).getBulletinTitle());
                intent.putExtra("title",latestFeedModell.get(position).getBulletinComments());
                intent.putExtra("date",latestFeedModell.get(position).getStartDate());
                intent.putExtra("images",latestFeedModell.get(position).getBulletinImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return latestFeedModell.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        // region Member Variables
        private final ImageView mPhotoView;
        //private final TextView head_name;
        //private final FrameLayout mFrameLayout;

        private ImageView row_item_image;
        private AppCompatTextView row_item_head;
        private AppCompatTextView row_item_date;
        private AppCompatTextView row_item_location;
        private LinearLayout event_row;
        private CardView cardView;
        // endregion

        // region Constructors
        public ImageViewHolder(final View view) {
            super(view);


            mPhotoView = (ImageView) view.findViewById(R.id.row_item_image);
            //head_name=(TextView)view.findViewById(R.id.caption);
            //mFrameLayout = (FrameLayout) view.findViewById(R.id.fl);
            cardView = (CardView)view.findViewById(R.id.card_view);
            row_item_head = (AppCompatTextView) itemView.findViewById(R.id.row_item_head);
            row_item_date = (AppCompatTextView) itemView.findViewById(R.id.row_item_date);
            row_item_location = (AppCompatTextView) itemView.findViewById(R.id.row_item_subtitle);


        }

    }


}