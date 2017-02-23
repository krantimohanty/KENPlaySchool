package com.kenplayschool.app_adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kenplayschool.R;
import com.kenplayschool.data_model.DiscussionCommentModel;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.PostJsonStringRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kranti on 22/3/2016.
 */
public class DiscussionDetailAdapter extends RecyclerView.Adapter {

    ArrayList<DiscussionCommentModel> mDataSet;
    Context context;
    String theme_id;

    public DiscussionDetailAdapter(ArrayList<DiscussionCommentModel> data, Context c, RecyclerView recycleView, String theme_id) {

        mDataSet = data;
        context = c;
        this.theme_id = theme_id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_detail_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DataObjectHolder) {

            Picasso.with(context)
                    .load(mDataSet.get(position).getCommentor_photo())
//                    .placeholder(R.drawable.strips)
//                    .error(R.drawable.strips)
                    .into(((DataObjectHolder) holder).pic);

            ((DataObjectHolder) holder).discussion_name.setText(mDataSet.get(position).getComment_by());
            ((DataObjectHolder) holder).discussion_date.setText(mDataSet.get(position).getDate());
            ((DataObjectHolder) holder).discussion_detail.setText(mDataSet.get(position).getComment());

           /* ((DataObjectHolder) holder).like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CustomPreference.with(context).getString("user_id", "").equals("")) {
                        context.startActivity(new Intent(context, LoginActivityOldie.class));
                        ((AppCompatActivity) context).finish();
                    } else {
                        postThemeCommentsLikes(mDataSet.get(position).getUserType(), mDataSet.get(position).getTheme_id(), mDataSet.get(position).getComment_id(), CustomPreference.with(context).getString("user_id", ""), mDataSet.get(position).getIs_like(), ((DataObjectHolder) holder).like);
                    }
                }
            });*/
        }

    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView pic;
        AppCompatTextView discussion_name;
        AppCompatTextView discussion_date;
        AppCompatTextView discussion_detail;
        IconButton like;

        public DataObjectHolder(View itemView) {
            super(itemView);

            pic = (ImageView) itemView.findViewById(R.id.pic);
            discussion_name = (AppCompatTextView) itemView.findViewById(R.id.discussion_name);
            discussion_date = (AppCompatTextView) itemView.findViewById(R.id.discussion_date);
            discussion_detail = (AppCompatTextView) itemView.findViewById(R.id.discussion_detail);
           // like = (IconButton) itemView.findViewById(R.id.like);

        }


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    void postThemeCommentsLikes(String type, String theme_id, String theme_cmts_id, String user_id, final String like_type, final IconButton btnLike) {

        JSONObject params = new JSONObject();
        try {
            params.put("method", "themeCmtsLikes");
            params.put("type", type);
            params.put("theme_id", theme_id);
            params.put("theme_cmts_id", theme_cmts_id);
            params.put("user_id", user_id);
            params.put("like_type", like_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("params", params.toString());
        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {
                        if (btnLike.getCurrentTextColor() == context.getResources().getColor(R.color.blue)) {
                            btnLike.setTextColor(context.getResources().getColor(R.color.dark_grey));
                            // int cnt = Integer.parseInt(latestFeedModel.get(pos).getLike_count()) - 1;
                            // likeText.setText(cnt + " Likes");
                            //getSupportActionBar().setSubtitle(getIntent().getStringExtra("views") + " views " + getIntent().getStringExtra("reply") + " replies " + (Integer.parseInt(getIntent().getStringExtra("like")) - 1) + " likes");
                        } else {
                            btnLike.setTextColor(context.getResources().getColor(R.color.blue));
                            //getSupportActionBar().setSubtitle(getIntent().getStringExtra("views") + " views " + getIntent().getStringExtra("reply") + " replies " + (Integer.parseInt(getIntent().getStringExtra("like")) + 1) + " likes");
                            //  int cnt = Integer.parseInt(latestFeedModel.get(pos).getLike_count()) + 1;
                            // likeText.setText(cnt + " Likes");
                        }

//                        latestFeedModel.get(pos).setLike_count(String.valueOf(cnt));

                        //refresh Page
                        //getNewsDetail(getIntent().getStringExtra("post_id"), "1", "0", "0");
                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                //pgBar.setVisibility(View.GONE);
                Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
                        .setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

        jsonString.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(jsonString);
    }

}