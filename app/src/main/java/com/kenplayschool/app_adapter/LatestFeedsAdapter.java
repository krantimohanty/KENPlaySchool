package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kenplayschool.DialogActivity;
import com.kenplayschool.EventzDetailActivity;
import com.kenplayschool.R;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.LatestFeedDataModel;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.ServiceCalls;
import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class LatestFeedsAdapter extends RecyclerView.Adapter {

    private List<LatestFeedDataModel> latestFeedModel = new ArrayList<>();
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

    public LatestFeedsAdapter(List<LatestFeedDataModel> latestFeedModel, final Context context, RecyclerView recyclerView, int type, int user_id, int sector_id, String flag, String toolbar_title) {

        this.latestFeedModel = latestFeedModel;
        this.type = type;
        this.user_id = user_id;
        this.context = context;
        this.sector_id = sector_id;
        this.flag = flag;
        this.toolbar_title = toolbar_title;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                   /* if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                        if (loading) {
                            if ((visibleItemCount + lastVisibleItem) >= totalItemCount) {
                                 loading = false;
                                Log.v("...", "Last Item Wow !");
                                //Do pagination.. i.e. fetch new data
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    }*/
                    if (dy > 0) {
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            } else {
                                //Custom_app_util.customSnackbar("No more information", context, false, "");
                            }
                            loading = true;
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return latestFeedModel.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_feed_row_item, parent, false);
            vh = new DataObjectHolder(view);
            // DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);

            if (latestFeedModel.size() == 0) {
                v.setVisibility(View.GONE);
            } else {
                v.setVisibility(View.VISIBLE);
            }
        }

       /* View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_feed_row_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);*/
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DataObjectHolder) {
            try {
                ((DataObjectHolder) holder).row_item_head.setText(Html.fromHtml(latestFeedModel.get(position).getTitle()));
                ((DataObjectHolder) holder).row_item_date.setText(Html.fromHtml(latestFeedModel.get(position).getDate()));
                ((DataObjectHolder) holder).row_item_subtitle.setText(Html.fromHtml(latestFeedModel.get(position).getShort_text()));
                ((DataObjectHolder) holder).row_item_views.setText(Html.fromHtml(latestFeedModel.get(position).getViews_count()) + " views");
                //((DataObjectHolder) holder).row_item_likes.setText(Html.fromHtml(latestFeedModel.get(position).getLike_count()) + " likes");
                ((DataObjectHolder) holder).row_item_comments.setText(Html.fromHtml(latestFeedModel.get(position).getComment_count()) + " comments");


                ((DataObjectHolder) holder).cardView.setOnClickListener(sendPostId(Integer.parseInt(latestFeedModel.get(position).getPost_id()), ((DataObjectHolder) holder).row_item_image, ((DataObjectHolder) holder).row_item_head, ((DataObjectHolder) holder).row_item_subtitle, ((DataObjectHolder) holder).row_item_date));


                //Like button
              /*  if (latestFeedModel.get(position).getIs_like().equals("1")) {
                    ((DataObjectHolder) holder).btnLike.setTextColor(context.getResources().getColor(R.color.blue));
                    //likeType = 1;
                } else {
                    //likeType = 0;
                }*/
                // setPostId(latestFeedModel.get(position).getPost_id());

                //button like comment

          /*      ((DataObjectHolder) holder).btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CustomPreference.with(context).getString("user_id", "").equals("")) {
                            Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Please Login to like..", Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (type == 1)
                                        ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                                .putExtra("type", type)
                                                .putExtra("pageFrom", "latest"));
                                    else if (type == 2)
                                        ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                                .putExtra("type", type)
                                                .putExtra("pageFrom", "Popular"));
                                    else if (type == 3)
                                        ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                                .putExtra("type", type)
                                                .putExtra("pageFrom", "Sectoral"));
                                    ((AppCompatActivity) context).finish();
                                    *//*Intent intentGetMessage = new Intent(context, LoginActivityOldie.class);
                                    ((AppCompatActivity) context).startActivity(intentGetMessage);*//*
                                }
                            }).
                                    show();
                        } else {
                           // ServiceCalls.postLikes(context, latestFeedModel, position, ((DataObjectHolder) holder).row_item_likes, type, latestFeedModel.get(position).getPost_id(), user_id, latestFeedModel.get(position).getIs_like(), ((DataObjectHolder) holder).btnLike);
                        }
                    }
                });*/

                try {
                    ((DataObjectHolder) holder).btnShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ServiceCalls.sharePost(context, latestFeedModel.get(position).getPhoto(), ((DataObjectHolder) holder).row_item_head.getText().toString(), "URL:" + AppServiceUrl.share_url + "news-details/" + latestFeedModel.get(position).getPost_id());
                        }
                    });
                } catch (Exception e) {
                    ((DataObjectHolder) holder).btnShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ServiceCalls.sharePost(context, latestFeedModel.get(position).getPhoto(), ((DataObjectHolder) holder).row_item_head.getText().toString(), "URL:" + AppServiceUrl.share_url + "news-details/" + latestFeedModel.get(position).getPost_id());
                        }
                    });
                }


                Log.d("TYOPE", type + "" + latestFeedModel.get(position).getPost_id());
                ((DataObjectHolder) holder).row_item_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (context).startActivity(new Intent(context, DialogActivity.class)
                                .putExtra("post_id", latestFeedModel.get(position).getPost_id())
                                .putExtra("type", type)
                                .putExtra("title", latestFeedModel.get(position).getTitle()));
                    }
                });

                ((DataObjectHolder) holder).row_item_views.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                //like button comment
                /*((DataObjectHolder) holder).row_item_likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/

                if (latestFeedModel.get(position).getPhoto() == null) {
                    ((DataObjectHolder) holder).row_item_image.setVisibility(View.GONE);
                } else if (latestFeedModel.get(position).getPhoto().equals("")) {
                    ((DataObjectHolder) holder).row_item_image.setVisibility(View.GONE);
                } else {
                    //load image
                    ((DataObjectHolder) holder).row_item_image.setVisibility(View.VISIBLE);
                    try {
                        Picasso.with(context)
                                .load(latestFeedModel.get(position).getPhoto())
                                .error(R.drawable.default_img)
                                .placeholder(R.drawable.default_img)
                                .into(((DataObjectHolder) holder).row_item_image);
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        Picasso.with(context)
                                .load(latestFeedModel.get(position).getPhoto())
                                .error(R.drawable.default_img)
                                .placeholder(R.drawable.default_img)
                                .into(((DataObjectHolder) holder).row_item_image);
                    }

                }


            } catch (Exception e) {

            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private ImageView row_item_image;
        private AppCompatTextView row_item_head;
        private AppCompatTextView row_item_date;
        private AppCompatTextView row_item_subtitle;
        private AppCompatTextView row_item_views;
        private AppCompatTextView row_item_likes;
        private AppCompatTextView row_item_comments;
        private IconButton btnLike;
        private IconButton btnShare;
        private CardView cardView;
        private Context context;

        public DataObjectHolder(View itemView) {
            super(itemView);
            //this.context = context;
            row_item_image = (ImageView) itemView.findViewById(R.id.row_item_image);
            row_item_head = (AppCompatTextView) itemView.findViewById(R.id.row_item_head);
            row_item_date = (AppCompatTextView) itemView.findViewById(R.id.row_item_date);
            row_item_subtitle = (AppCompatTextView) itemView.findViewById(R.id.row_item_subtitle);
            row_item_views = (AppCompatTextView) itemView.findViewById(R.id.row_item_views);
            //row_item_likes = (AppCompatTextView) itemView.findViewById(R.id.row_item_likes);
            row_item_comments = (AppCompatTextView) itemView.findViewById(R.id.row_item_comments);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            //btnLike = (IconButton) itemView.findViewById(R.id.like);
            btnShare = (IconButton) itemView.findViewById(R.id.share);

            Log.i("LOG_TAG", "Adding Listener");
        }
    }

    private View.OnClickListener sendPostId(final int postId, final ImageView row_img, final AppCompatTextView title, final AppCompatTextView shortText, final AppCompatTextView date) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Data", postId + "");
                Intent intent = new Intent(context, EventzDetailActivity.class);
                intent.putExtra("flag", flag);
                intent.putExtra("post_id", postId);
                intent.putExtra("type", type);
                intent.putExtra("toolbar_title", toolbar_title);
                intent.putExtra("sector_id", sector_id);
                // Pair<View, String> p1 = Pair.create((View) row_img, "row_item_image");
                // ActivityOptionsCompat options = ActivityOptionsCompat.
                // makeSceneTransitionAnimation((Activity) context, p1);
                ((AppCompatActivity) context).startActivity(intent);
                ((AppCompatActivity) context).finish();
            }
        };
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return this.latestFeedModel.size();
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