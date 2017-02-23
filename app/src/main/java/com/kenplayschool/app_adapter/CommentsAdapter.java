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

import com.kenplayschool.R;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.CommentModel;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.joanzapata.iconify.IconDrawable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter {

    private List<CommentModel> commentModel = new ArrayList<>();
    private Context context;

    public CommentsAdapter() {
    }

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public CommentsAdapter(List<CommentModel> commentModel, Context context, RecyclerView recyclerView) {
        this.commentModel = commentModel;
        this.context = context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return commentModel.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_list, parent, false);
            DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataObjectHolder) {
            try {
                ((DataObjectHolder) holder).comment.setText(Html.fromHtml(commentModel.get(position).getComment()));
                ((DataObjectHolder) holder).commentBy.setText("By " + Html.fromHtml(commentModel.get(position).getComment_by()));
                ((DataObjectHolder) holder).commentDate.setText(Html.fromHtml(commentModel.get(position).getDate()));

                /*((DataObjectHolder) holder).comment_img.setImageDrawable(new IconDrawable(context, IcoMoonIcons.ic_user)
                        .colorRes(R.color.color_white)
                        .sizePx(80));*/

                //load image
                try {
                    Picasso.with(context)
                            .load(commentModel.get(position).getCommentor_photo())
                            .error(new IconDrawable(context, IcoMoonIcons.ic_user)
                                    .colorRes(R.color.color_white)
                                    .sizePx(60))
                            .placeholder(new IconDrawable(context, IcoMoonIcons.ic_user)
                                    .colorRes(R.color.color_white)
                                    .sizePx(60))
                            .into(((DataObjectHolder) holder).comment_img);
                } catch (Exception e) {
                    Picasso.with(context)
                            .load(commentModel.get(position).getCommentor_photo())
                            .error(new IconDrawable(context, IcoMoonIcons.ic_user)
                                    .colorRes(R.color.color_white)
                                    .sizePx(60))
                            .placeholder(new IconDrawable(context, IcoMoonIcons.ic_user)
                                    .colorRes(R.color.color_white)
                                    .sizePx(60))
                            .into(((DataObjectHolder) holder).comment_img);
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {

            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private ImageView comment_img;
        private AppCompatTextView comment;
        private AppCompatTextView commentBy;
        private AppCompatTextView commentDate;
        private Context context;

        public DataObjectHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            comment_img = (ImageView) itemView.findViewById(R.id.comment_user_pic);
            comment = (AppCompatTextView) itemView.findViewById(R.id.comment_text);
            commentBy = (AppCompatTextView) itemView.findViewById(R.id.commentedBy);
            commentDate = (AppCompatTextView) itemView.findViewById(R.id.comment_date);

            Log.i("LOG_TAG", "Adding Listener");
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return this.commentModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ContentLoadingProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ContentLoadingProgressBar) v.findViewById(R.id.progress1);
        }
    }
}