package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.kenplayschool.DiscussionDetailActivity;
import com.kenplayschool.R;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.DiscussionDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kranti on 22/3/2016.
 */
public class DiscussionAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private ArrayList<DiscussionDataModel> mDataset;
    Context context;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public DiscussionAdapter(ArrayList<DiscussionDataModel> data, Context c, RecyclerView recyclerView) {
        mDataset = data;
        context = c;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }else {
                           // Custom_app_util.customSnackbar("No more information", context, false, "");
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_row_item, parent, false);
//        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
//        return dataObjectHolder;
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_row_item, parent, false);
            vh = new DataObjectHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataObjectHolder) {

            Picasso.with(context)
                    .load(mDataset.get(position).getThumnail_photo())
//                    .placeholder(R.drawable.strips)
//                    .error(R.drawable.strips)
                    .into(((DataObjectHolder) holder).pic);

            ((DataObjectHolder) holder).discussion_name.setText(Html.fromHtml(mDataset.get(position).getTheme_title()));
            ((DataObjectHolder) holder).discussion_date.setText(mDataset.get(position).getPosted_on());
            ((DataObjectHolder) holder).discussion_views.setText(mDataset.get(position).getView_count() + " views");
            ((DataObjectHolder) holder).discussion_replies.setText(mDataset.get(position).getReplies_count() + " replies");
            //((DataObjectHolder) holder).discussion_likes.setText(mDataset.get(position).getLike_count() + " likes");
            ((DataObjectHolder) holder).lastMsg.setText("Last message by: " + mDataset.get(position).getLast_message_by() + " on " + mDataset.get(position).getLast_message_on());

            ((DataObjectHolder) holder).parentLayout.setOnClickListener(layoutItemClick(mDataset.get(position).getTheme_title(),
                    mDataset.get(position).getView_count(), mDataset.get(position).getReplies_count(), mDataset.get(position).getLike_count(),
                    mDataset.get(position).getTheme_id()));
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        AppCompatTextView discussion_name;
        AppCompatTextView discussion_date;
        AppCompatTextView discussion_views;
        AppCompatTextView discussion_replies;
        AppCompatTextView discussion_likes;
        AppCompatTextView lastMsg;
        RelativeLayout parentLayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            discussion_name = (AppCompatTextView) itemView.findViewById(R.id.discussion_name);
            discussion_date = (AppCompatTextView) itemView.findViewById(R.id.discussion_date);
            discussion_views = (AppCompatTextView) itemView.findViewById(R.id.discussion_views);
            discussion_replies = (AppCompatTextView) itemView.findViewById(R.id.discussion_replies);
            //discussion_likes = (AppCompatTextView) itemView.findViewById(R.id.discussion_likes);
            lastMsg = (AppCompatTextView) itemView.findViewById(R.id.lastMsg);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);
        }


//            context.startActivity(new Intent(context, DiscussionDetailActivity.class));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public View.OnClickListener layoutItemClick(final String title, final String views_count, final String reply_count, final String like_count, final String theme_id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DiscussionDetailActivity.class).putExtra("title", title).putExtra("views", views_count).putExtra("like", like_count).putExtra("reply", reply_count).putExtra("themeId", theme_id));
            }
        };
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ContentLoadingProgressBar) v.findViewById(R.id.progress1);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

}