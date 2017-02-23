package com.kenplayschool.app_adapter;


import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kenplayschool.R;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.AnnouncementDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class AnnouncementAdapter extends RecyclerView.Adapter {


    private List<AnnouncementDataModel> annFeedModel = new ArrayList<>();
    private Context context;
    RecyclerView mRecyclerView;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount, visibleItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public AnnouncementAdapter(List<AnnouncementDataModel> annFeedModel, Context context, RecyclerView mRecyclerView) {
        this.annFeedModel = annFeedModel;
        this.context = context;
        this.mRecyclerView = mRecyclerView;

        try {
            Log.d("dataFrag", this.annFeedModel.size() + "");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.announcement_list, parent, false);
            vh = new DataObjectHolder(view);
            // DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);

            if (annFeedModel.size() == 0) {
                v.setVisibility(View.GONE);
            } else {
                v.setVisibility(View.VISIBLE);
            }
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DataObjectHolder) {
            try {
                ((DataObjectHolder) holder).row_item_title.setText(Html.fromHtml(annFeedModel.get(position).getDescription()));
                ((DataObjectHolder) holder).row_item_date.setText(Html.fromHtml(annFeedModel.get(position).getDate()));
            } catch (Exception e) {
            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        private ImageView row_item_image;
        private AppCompatTextView row_item_title;
        private AppCompatTextView row_item_date;
        private Context context;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.context = context;
            row_item_title = (AppCompatTextView) itemView.findViewById(R.id.announcement_list_text);
            row_item_date = (AppCompatTextView) itemView.findViewById(R.id.announcement_date);
            Log.i("LOG_TAG", "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //*********************************(IMPORTANT)
            //get Flag n get flag to get the respective activity content

            /*Intent intent = new Intent(context, EventzDetailActivity.class);
            context.startActivity(intent);*/
            //context.startActivity(new Intent(context, EventzDetailActivity.class));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return annFeedModel.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return this.annFeedModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ContentLoadingProgressBar) v.findViewById(R.id.progress1);
        }
    }
}