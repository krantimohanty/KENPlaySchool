package com.kenplayschool;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.babyloniaapp.app_adapter.CommentsAdapter;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.data_model.CommentModel;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.PostJsonStringRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.widget.IconButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private View rootView;
    private List<CommentModel> commentModels = new ArrayList<CommentModel>();
   // private CommentsAdapter commentsAdapter;
    private ContentLoadingProgressBar pgBar, post_progress;
    private int page_count = 100;
    private int row_id = 0;
    protected Handler handler;
    private IconButton btnPost;
    private AppCompatEditText edit_comment;
    private LinearLayout footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //progressbar
        pgBar = (ContentLoadingProgressBar) findViewById(R.id.progress);
        post_progress = (ContentLoadingProgressBar) findViewById(R.id.post_progress);
        //recycle view
        mRecyclerView = (RecyclerView) findViewById(R.id.comment_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(DialogActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        footer = (LinearLayout) findViewById(R.id.footer);
        if (CustomPreference.with(DialogActivity.this).getString("user_id", "").equals("")) {
            footer.setVisibility(View.GONE);
        } else {
            footer.setVisibility(View.VISIBLE);
        }
        //get comments
        Log.d("TYPE", getIntent().getStringExtra("post_id") + "");
        getComments(Integer.parseInt(getIntent().getStringExtra("post_id")), getIntent().getIntExtra("type", 0), row_id, page_count);
       // commentsAdapter = new CommentsAdapter();
       // commentsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //add null , so the adapter will check view_type and show progress bar at bottom
//                commentModels.add(null);
//                commentsAdapter.notifyItemInserted(commentModels.size() - 1);
//
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //   remove progress item
//                        commentModels.remove(commentModels.size() - 1);
//                        commentsAdapter.notifyItemRemoved(commentModels.size());
//                        //add items one by one
//                        int start = commentModels.size();
//                        int end = start + page_count;
//                        row_id = start;
//                        for (int i = start + 1; i <= end; i++) {
//                            //get comments
//                            getComments(Integer.parseInt(getIntent().getStringExtra("post_id")), getIntent().getIntExtra("type", 0), row_id, page_count);
//                            commentsAdapter.notifyItemInserted(commentModels.size());
//                        }
//                        commentsAdapter.setLoaded();
//                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
//                        commentsAdapter.notifyDataSetChanged();
//                    }
//                }, 2000);
//
//            }
//        });
//
//        btnPost = (IconButton) findViewById(R.id.post_comment);
//        edit_comment = (AppCompatEditText) findViewById(R.id.edit_comment);
//        ValidationUtil.removeWhiteSpaceFromFront(edit_comment);
//        btnPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (edit_comment.getText().toString().equals("")) {
//
//                } else {
//                    postComments(getIntent().getStringExtra("type"), getIntent().getStringExtra("post_id"), "1", edit_comment.getText().toString());
//                }
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }
        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_search) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    //get comments
    public void getComments(int post_id, int type, int row_id, int page_count) {
        pgBar.setVisibility(View.VISIBLE);
        //JSONObject params = new JSONObject();
       /* try {
            params.put("method", "getComments");
            params.put("type", type);
            params.put("post_id", post_id);
            params.put("row_id", row_id);
            params.put("page_count", page_count);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Log.d("CommentsURL", AppServiceUrl.url + "/getComments1/" + type + "/" + post_id + "/" + row_id + "/" + page_count);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppServiceUrl.url + "/getComments1/" + type + "/" + post_id + "/" + row_id + "/" + page_count, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("getCommentsResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<CommentModel>>() {
                    }.getType();
                    List<CommentModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        commentModels.add(tempArrayList.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                Collections.reverse(commentModels);
//                commentsAdapter = new CommentsAdapter(commentModels, DialogActivity.this, mRecyclerView);
//                mRecyclerView.setAdapter(commentsAdapter);
                pgBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                pgBar.setVisibility(View.GONE);
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

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    //post comment
    public void postComments(String type, String post_id, String user_id, String comment) {

        btnPost.setVisibility(View.GONE);
        post_progress.setVisibility(View.VISIBLE);

        JSONObject params = new JSONObject();
        try {
            params.put("method", "postComments");
            params.put("type", type);
            params.put("post_id", post_id);
            params.put("user_id", user_id);
            params.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {
                        edit_comment.setText("");
                        post_progress.setVisibility(View.GONE);
                        btnPost.setVisibility(View.VISIBLE);
                        Snackbar.make(findViewById(android.R.id.content), "Comment posted successfully", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.WHITE)
                                .show();
                        //refresh Page
                        clearData();
                        getComments(getIntent().getIntExtra("post_id", 0), getIntent().getIntExtra("type", 0), row_id, page_count);


                    } else {
                        post_progress.setVisibility(View.GONE);
                        btnPost.setVisibility(View.VISIBLE);
                        Snackbar.make(findViewById(android.R.id.content), "Error while posting!!", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.WHITE)
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

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

    //clear
    public void clearData() {
        commentModels.clear(); //clear list
       // commentsAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

}
