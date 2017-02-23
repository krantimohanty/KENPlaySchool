package com.kenplayschool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_adapter.DiscussionDetailAdapter;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.data_model.DiscussionCommentModel;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.PostJsonStringRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.widget.IconButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DiscussionDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    String theme_id = "";
    String userid = "";
    AppCompatTextView discussion_date, discussion_title;
    AppCompatTextView discussion_detail;
    ContentLoadingProgressBar progress, progress1;
    IconButton like;
//    AppCompatTextView discussion_cnt;

    ArrayList<DiscussionCommentModel> m_data;
    LinearLayoutManager mLinearLayoutManager;
    DiscussionDetailAdapter adapter;
    FloatingActionButton fab;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        title = getIntent().getStringExtra("title");
        String count = getIntent().getStringExtra("cnt");
        theme_id = getIntent().getStringExtra("themeId");
        userid = CustomPreference.with(DiscussionDetailActivity.this).getString("user_id", "");

        discussion_title = (AppCompatTextView) findViewById(R.id.discussion_title);
        discussion_date = (AppCompatTextView) findViewById(R.id.discussion_date);
        discussion_date.setText("");
        discussion_detail = (AppCompatTextView) findViewById(R.id.discussion_detail);
        discussion_detail.setText("");
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);
        progress1 = (ContentLoadingProgressBar) findViewById(R.id.progress1);
//        discussion_cnt = (AppCompatTextView) findViewById(R.id.discussion_cnt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Discussion Detail");
        // getSupportActionBar().setSubtitle(getIntent().getStringExtra("views") + " views " + getIntent().getStringExtra("reply") + " replies " + getIntent().getStringExtra("like") + " likes");

        discussion_title.setText(title + "\n" + getIntent().getStringExtra("views") + " views " + getIntent().getStringExtra("reply") + " replies " + getIntent().getStringExtra("like") + " likes");

        fab = (FloatingActionButton) findViewById(R.id.fab);

        IconDrawable drawable = new IconDrawable(this, IcoMoonIcons.ic_edit).colorRes(R.color.color_white).sizeDp(35);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        fab.setImageBitmap(bitmap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userid.equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), "You are not a logged in user.Please login.", Snackbar.LENGTH_LONG)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DiscussionDetailActivity.this, LoginActivityOldie.class));
                                }
                            })
                            .setActionTextColor(Color.RED)
                            .show();

//                    ServiceCalls.postLikes();
                } else {
                    startActivity(new Intent(DiscussionDetailActivity.this, PostDiscussionComment.class).putExtra("title", title).putExtra("themeId", theme_id));
                }

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.discussion_detail_list);
        mRecyclerView.setHasFixedSize(true);

        m_data = new ArrayList<>();


        mLinearLayoutManager = new LinearLayoutManager(DiscussionDetailActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new DiscussionDetailAdapter(m_data, this, mRecyclerView, getIntent().getStringExtra("themeId"));
        mRecyclerView.setAdapter(adapter);

        getCommentDetailData();
        getDiscussionDetailData();

        //like = (IconButton) findViewById(R.id.like);

    }

    private void getCommentDetailData() {
        progress1.setVisibility(View.VISIBLE);
        JSONObject param = new JSONObject();

        try {
            param.put("method", "getThemeComments");
            param.put("theme_id", theme_id);
            param.put("row_id", "0");
            param.put("page_count", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                AppServiceUrl.url,
                param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("atag", "hello  " + response.toString());
                try {

                    JSONArray discussionCmntData = response.getJSONArray("getThemeCommentsResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<DiscussionCommentModel>>() {
                    }.getType();
                    List<DiscussionCommentModel> tempDataModel = converter.fromJson(String.valueOf(discussionCmntData), type);

                    if (tempDataModel.size() == 0) {
                        Custom_app_util.customSnackbar("No more information", DiscussionDetailActivity.this, false, "");
                    }
//                    if (LoadStatus.equals("DEFAULT")) {
//                        d_model.clear();
//                    }


                    for (int i = 0; i < tempDataModel.size(); i++) {
                        m_data.add(tempDataModel.get(i));
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress1.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("atag", "error   " + error.toString());
                progress1.setVisibility(View.GONE);
            }
        });

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(90000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(objectRequest);

    }

    private void getDiscussionDetailData() {
        JSONObject param = new JSONObject();

        progress.setVisibility(View.VISIBLE);
        try {
            param.put("method", "getDiscussionForumDetailData");
            param.put("user_id", userid);
            param.put("theme_id", theme_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                AppServiceUrl.url,
                param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("atag", response.toString());
                try {
                    final JSONArray data = response.getJSONArray("getDiscussionForumDetailDataResult");
                    discussion_date.setText(data.getJSONObject(0).getString("posted_on"));
                    discussion_detail.setText(data.getJSONObject(0).getString("short_text"));


                    //like button comment=======================
                   /* if (data.getJSONObject(0).getString("is_like").equals("1")) {
                        like.setTextColor(getResources().getColor(R.color.blue));
                    } else {
                        like.setTextColor(getResources().getColor(R.color.color_dark));
                    }*/

                   /* like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Log.d("clicked", "clicked");
                                if (CustomPreference.with(DiscussionDetailActivity.this).getString("user_id", "").equals("")) {
                                    Custom_app_util.customSnackbar("Please Login to like", DiscussionDetailActivity.this, false, "");
                                } else {
                                    postThemeLikes(
                                            CustomPreference.with(DiscussionDetailActivity.this).getString("type", ""),
                                            data.getJSONObject(0).getString("theme_id"),
                                            "0",
                                            CustomPreference.with(DiscussionDetailActivity.this).getString("user_id", ""),
                                            data.getJSONObject(0).getString("is_like"),
                                            like);
                                }

                            } catch (JSONException e) {

                            }
                        }
                    });*/

//                    discussion_cnt.setText(data.getJSONObject(0).getString("view_count") + " views " +
//                            data.getJSONObject(0).getString("replies_count") + " replies " + data.getJSONObject(0).getString("like_count") + " likes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("atag", "error   " + error.toString());
                progress.setVisibility(View.GONE);
            }
        });

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(90000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(objectRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_search) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    void postThemeLikes(String type, String theme_id, String theme_cmts_id, String user_id, final String like_type, final IconButton btnLike) {

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
                        if (btnLike.getCurrentTextColor() == getResources().getColor(R.color.blue)) {
                            btnLike.setTextColor(getResources().getColor(R.color.dark_grey));
                            // int cnt = Integer.parseInt(latestFeedModel.get(pos).getLike_count()) - 1;
                            // likeText.setText(cnt + " Likes");
                            getSupportActionBar().setSubtitle(getIntent().getStringExtra("views") + " views " + getIntent().getStringExtra("reply") + " replies " + (Integer.parseInt(getIntent().getStringExtra("like") + 1) - 1) + " likes");
                        } else {
                            btnLike.setTextColor(getResources().getColor(R.color.blue));
                            getSupportActionBar().setSubtitle(getIntent().getStringExtra("views") + " views " + getIntent().getStringExtra("reply") + " replies " + (Integer.parseInt(getIntent().getStringExtra("like")) + 1) + " likes");
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
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonString);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
