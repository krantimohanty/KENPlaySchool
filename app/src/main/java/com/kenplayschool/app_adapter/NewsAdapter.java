package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kenplayschool.DialogActivity;
import com.kenplayschool.R;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.component.ParallaxListView;
import com.kenplayschool.data_model.LatestFeedDataModel;
import com.kenplayschool.listviewpagging.PagingBaseAdapter;
import com.kenplayschool.network_utils.ServiceCalls;
import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/4/2016.
 */
public class NewsAdapter extends PagingBaseAdapter<String> {

    private List<LatestFeedDataModel> latestFeedModel = new ArrayList<>();
    //  private Context context;


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private String type;
    private String flag;
    private String sector_id;
    private int likeType;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount, visibleItemCount;
    private boolean loading;
    private String user_id;
    private OnLoadMoreListener onLoadMoreListener;


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


    public NewsAdapter(List<LatestFeedDataModel> latestFeedModel, Context context, ParallaxListView recyclerView, String type, String user_id, String sector_id, String flag) {

        this.latestFeedModel = latestFeedModel;
        this.type = type;
        this.user_id = user_id;
        this.context = context;
        this.sector_id = sector_id;
        this.flag = flag;
       /* if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                   *//* if (dy > 0) //check for scroll down
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
                    }*//*
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
        }*/
    }

    @Override
    public int getCount() {
        return latestFeedModel.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.event_feed_row_item, null);
        row_item_image = (ImageView) view.findViewById(R.id.row_item_image);
        row_item_head = (AppCompatTextView) view.findViewById(R.id.row_item_head);
        row_item_date = (AppCompatTextView) view.findViewById(R.id.row_item_date);
        row_item_subtitle = (AppCompatTextView) view.findViewById(R.id.row_item_subtitle);
        row_item_views = (AppCompatTextView) view.findViewById(R.id.row_item_views);
        row_item_likes = (AppCompatTextView) view.findViewById(R.id.row_item_likes);
        row_item_comments = (AppCompatTextView) view.findViewById(R.id.row_item_comments);
        cardView = (CardView) view.findViewById(R.id.card_view);
        //btnLike = (IconButton) view.findViewById(R.id.like);
        btnShare = (IconButton) view.findViewById(R.id.share);

        try {
            row_item_head.setText(Html.fromHtml(latestFeedModel.get(position).getTitle()));
            row_item_date.setText(Html.fromHtml(latestFeedModel.get(position).getDate()));
            row_item_subtitle.setText(Html.fromHtml(latestFeedModel.get(position).getShort_text()));
            row_item_views.setText(Html.fromHtml(latestFeedModel.get(position).getViews_count()) + " views");
            //row_item_likes.setText(Html.fromHtml(latestFeedModel.get(position).getLike_count()) + " likes");
            row_item_comments.setText(Html.fromHtml(latestFeedModel.get(position).getComment_count()) + " comments");
            //load image
            Picasso.with(context)
                    .load(latestFeedModel.get(position).getPhoto())
                    .into(row_item_image);

           /* if (latestFeedModel.get(position).getIs_like().equals("1")) {
                btnLike.setTextColor(context.getResources().getColor(R.color.blue));
                //likeType = 1;
            } else {
                //likeType = 0;
            }*/
            // setPostId(latestFeedModel.get(position).getPost_id());
            //  cardView.setOnClickListener(sendPostId(latestFeedModel.get(position).getPost_id(), row_item_image, row_item_head, row_item_subtitle, row_item_date));

                 //Like button comment==============
            /*btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CustomPreference.with(context).getString("user_id", "").equals("")) {
                        Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Please Login to like..", Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (type.equals("1"))
                                    ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                            .putExtra("type", type)
                                            .putExtra("pageFrom", "latest"));
                                else if (type.equals("2"))
                                    ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                            .putExtra("type", type)
                                            .putExtra("pageFrom", "Popular"));
                                else if (type.equals("3"))
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
                        ServiceCalls.postLikes(context, latestFeedModel, position, row_item_likes, type, latestFeedModel.get(position).getPost_id(), user_id, latestFeedModel.get(position).getIs_like(), btnLike);
                    }
                }
            });*/

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServiceCalls.sharePost(context, "", row_item_head.getText().toString(), row_item_subtitle.getText().toString());
                }
            });

            row_item_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (context).startActivity(new Intent(context, DialogActivity.class)
                            .putExtra("post_id", ((AppCompatActivity) context).getIntent().getStringExtra("post_id"))
                            .putExtra("type", type)
                            .putExtra("title", row_item_head.getText().toString()));
                }
            });

            row_item_views.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
           /* row_item_likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/

        } catch (Exception e) {

        }


        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return latestFeedModel.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


  /*  @Override
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

       *//* View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_feed_row_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);*//*
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
                ((DataObjectHolder) holder).row_item_likes.setText(Html.fromHtml(latestFeedModel.get(position).getLike_count()) + " likes");
                ((DataObjectHolder) holder).row_item_comments.setText(Html.fromHtml(latestFeedModel.get(position).getComment_count()) + " comments");
                //load image
                Picasso.with(context)
                        .load(latestFeedModel.get(position).getPhoto())
                        .into(((DataObjectHolder) holder).row_item_image);

                if (latestFeedModel.get(position).getIs_like().equals("1")) {
                    ((DataObjectHolder) holder).btnLike.setTextColor(context.getResources().getColor(R.color.blue));
                    //likeType = 1;
                } else {
                    //likeType = 0;
                }
                // setPostId(latestFeedModel.get(position).getPost_id());
                ((DataObjectHolder) holder).cardView.setOnClickListener(sendPostId(latestFeedModel.get(position).getPost_id(), ((DataObjectHolder) holder).row_item_image, ((DataObjectHolder) holder).row_item_head, ((DataObjectHolder) holder).row_item_subtitle, ((DataObjectHolder) holder).row_item_date));

                ((DataObjectHolder) holder).btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CustomPreference.with(context).getString("user_id", "").equals("")) {
                            Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Please Login to like..", Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (type.equals("1"))
                                        ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                                .putExtra("type", type)
                                                .putExtra("pageFrom", "latest"));
                                    else if (type.equals("2"))
                                        ((AppCompatActivity) context).startActivity(new Intent(context, LoginActivityOldie.class)
                                                .putExtra("type", type)
                                                .putExtra("pageFrom", "Popular"));
                                    else if (type.equals("3"))
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
                            ServiceCalls.postLikes(context, latestFeedModel, position, ((DataObjectHolder) holder).row_item_likes, type, latestFeedModel.get(position).getPost_id(), user_id, latestFeedModel.get(position).getIs_like(), ((DataObjectHolder) holder).btnLike);
                        }
                    }
                });

                ((DataObjectHolder) holder).btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServiceCalls.sharePost(context, ((DataObjectHolder) holder).row_item_head.getText().toString(), ((DataObjectHolder) holder).row_item_subtitle.getText().toString());
                    }
                });

                ((DataObjectHolder) holder).row_item_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (context).startActivity(new Intent(context, DialogActivity.class)
                                .putExtra("post_id", ((AppCompatActivity) context).getIntent().getStringExtra("post_id"))
                                .putExtra("type", type)
                                .putExtra("title", ((DataObjectHolder) holder).row_item_head.getText().toString()));
                    }
                });

                ((DataObjectHolder) holder).row_item_views.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ((DataObjectHolder) holder).row_item_likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

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
            row_item_likes = (AppCompatTextView) itemView.findViewById(R.id.row_item_likes);
            row_item_comments = (AppCompatTextView) itemView.findViewById(R.id.row_item_comments);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            btnLike = (IconButton) itemView.findViewById(R.id.like);
            btnShare = (IconButton) itemView.findViewById(R.id.share);

            Log.i("LOG_TAG", "Adding Listener");
        }
    }

    private View.OnClickListener sendPostId(final String postId, final ImageView row_img, final AppCompatTextView title, final AppCompatTextView shortText, final AppCompatTextView date) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Data", postId);
                Intent intent = new Intent(context, EventzDetailActivity.class);
                intent.putExtra("flag", flag);
                intent.putExtra("post_id", postId);
                intent.putExtra("type", type);
                intent.putExtra("sector_id", sector_id);
                Pair<View, String> p1 = Pair.create((View) row_img, "row_item_image");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1);
                ((AppCompatActivity) context).startActivity(intent, options.toBundle());
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
    }*/

}