package com.kenplayschool;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_adapter.AnnouncementAdapter;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.AnnouncementDataModel;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.ConnectionDetector;
import com.kenplayschool.network_utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    static Context context;
    private List<AnnouncementDataModel> annFeedModel = new ArrayList<AnnouncementDataModel>();
    private AnnouncementAdapter announcementadapter;
    private ContentLoadingProgressBar pgBar;
    private int page_count = 20;
    private int row_id = 0;
    protected Handler loadMorehandler;
    private String LoadStatus = "DEFAULT";
    private IconTextView batchIcon;
    private AppCompatTextView heading1;
    private AppCompatTextView heading2;
    private SwipeRefreshLayout ViewSwipe;
    private SwipeRefreshLayout layoutSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //progressbar
        pgBar = (ContentLoadingProgressBar) findViewById(R.id.progress);

        batchIcon = (IconTextView) findViewById(R.id.batchIcon);
        heading1 = (AppCompatTextView) findViewById(R.id.heading1);
        heading2 = (AppCompatTextView) findViewById(R.id.heading2);
        ViewSwipe = (SwipeRefreshLayout) findViewById(R.id.ViewSwipe);
        layoutSwipe = (SwipeRefreshLayout) findViewById(R.id.layoutSwipe);

        //recylcerview
        mRecyclerView = (RecyclerView) findViewById(R.id.announcement_list);
        mRecyclerView.setHasFixedSize(true);
        //calling the service to get Announcement news
        annFeedModel = new ArrayList<AnnouncementDataModel>();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(AnnouncementActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        announcementadapter = new AnnouncementAdapter(annFeedModel, AnnouncementActivity.this, mRecyclerView);
        mRecyclerView.setAdapter(announcementadapter);

        callServiceMethod();

        ViewSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                row_id = 0;
                LoadStatus = "DEFAULT";
                callingUpdates();
            }
        });

        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                row_id = 0;
                LoadStatus = "DEFAULT";
                callingUpdates();
            }
        });

        /* Load More Items on Infinite Scroll*/
        loadMorehandler = new Handler();
        announcementadapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                annFeedModel.add(null);
                announcementadapter.notifyItemInserted(annFeedModel.size() - 1);

                loadMorehandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        annFeedModel.remove(null);
                        announcementadapter.notifyItemRemoved(annFeedModel.size());
                        callingMoreUpdates();
                        announcementadapter.setLoaded();
                    }
                }, 2000);
            }
        });

    }

    public void callServiceMethod() {
        //networkUnavailable.setVisibility(View.GONE);
        if (ConnectionDetector.staticisConnectingToInternet(getApplicationContext())) {
            callingUpdates();
        } else {
            loadOfflineLocationData(row_id, page_count);
            Snackbar.make(findViewById(android.R.id.content), "Network connection is not available.", Snackbar.LENGTH_LONG).show();
            pgBar.setVisibility(View.GONE);
        }
    }

    private void callingUpdates() {

        layoutSwipe.setVisibility(View.GONE);
        //getUpcoming wish List
        if (ConnectionDetector.staticisConnectingToInternet(AnnouncementActivity.this)) {
            batchIcon.setVisibility(View.GONE);
            heading1.setVisibility(View.GONE);
            heading2.setVisibility(View.GONE);
//            clearData();
            getNewAnnouncement(row_id, page_count);
        } else {
//            clearData();
            batchIcon.setText("{ic-noconnection 70dp #8C8989}");
            heading1.setText("Can't Connect");
            heading2.setText("Please swipe top to refresh");
            batchIcon.setVisibility(View.VISIBLE);
            heading1.setVisibility(View.VISIBLE);
            heading2.setVisibility(View.VISIBLE);
            pgBar.setVisibility(View.GONE);
            ViewSwipe.setRefreshing(false);
            layoutSwipe.setVisibility(View.VISIBLE);
            layoutSwipe.setRefreshing(false);
        }
    }

    private void callingMoreUpdates() {
        row_id = row_id + page_count;
        LoadStatus = "MORE";
        callingUpdates();
    }


    public void getNewAnnouncement(int rowId, int pageCount) {

        pgBar.setVisibility(View.VISIBLE);
       /* final JSONObject params = new JSONObject();
        try {
            params.put("method", "getAnnouncements");
            params.put("row_id", rowId);
            params.put("page_count", pageCount);//for latest announcementNews


        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppServiceUrl.url + "/getAnnouncements1/" + rowId + "/" + pageCount, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("output", response.toString());


                try {
                    JSONArray jsonArray = response.getJSONArray("getAnnouncementsResult");

                    Gson converter = new Gson();
                    Type type = new TypeToken<List<AnnouncementDataModel>>() {
                    }.getType();
                    List<AnnouncementDataModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);

                    if (tempArrayList.size() == 0) {
                        Custom_app_util.customSnackbar("No more information", context, false, "");
                    }
                    if (LoadStatus.equals("DEFAULT")) {
                        annFeedModel.clear();
                    }
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        annFeedModel.add(tempArrayList.get(i));

                    }

                    announcementadapter.notifyDataSetChanged();
                    pgBar.setVisibility(View.GONE);

                    if (annFeedModel.isEmpty()) {
                        annFeedModel.clear();
                        announcementadapter.notifyDataSetChanged();
                        batchIcon.setText("{ic-noconnection 70dp #8C8989}");
                        heading2.setText("No updates.");
                        batchIcon.setVisibility(View.VISIBLE);
                        heading1.setVisibility(View.GONE);
                        heading2.setVisibility(View.VISIBLE);
                        layoutSwipe.setRefreshing(false);
                        layoutSwipe.setVisibility(View.VISIBLE);

                    } else {
                        batchIcon.setVisibility(View.GONE);
                        heading1.setVisibility(View.GONE);
                        heading2.setVisibility(View.GONE);
                        layoutSwipe.setRefreshing(false);
                        layoutSwipe.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    annFeedModel.clear();
                    announcementadapter.notifyDataSetChanged();
                    batchIcon.setText("{ic-noconnection 70dp #8C8989}");
                    heading1.setText("Something Went wrong");
                    heading2.setText("Please try after sometimes.");
                    batchIcon.setVisibility(View.VISIBLE);
                    heading1.setVisibility(View.VISIBLE);
                    heading2.setVisibility(View.VISIBLE);
                    pgBar.setVisibility(View.GONE);
                    layoutSwipe.setRefreshing(false);
                    layoutSwipe.setVisibility(View.VISIBLE);
                }

                ViewSwipe.setRefreshing(false);

               /* //binding data to recycleview
                // pgBar.setVisibility(View.GONE);
                announcementadapter = new AnnouncementAdapter(annFeedModel, getActivity(), mRecyclerView);
                //Applying animation
                pgBar.setVisibility(View.GONE);
                //ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(announcementadapter);
                mRecyclerView.setAdapter(announcementadapter);*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                annFeedModel.clear();
                announcementadapter.notifyDataSetChanged();
                batchIcon.setText("{ic-noconnection 70dp #8C8989}");
                heading1.setText("Something Went wrong");
                heading2.setText("Please try after sometimes.");
                batchIcon.setVisibility(View.VISIBLE);
                heading1.setVisibility(View.VISIBLE);
                heading2.setVisibility(View.VISIBLE);
                pgBar.setVisibility(View.GONE);
                ViewSwipe.setRefreshing(false);
                layoutSwipe.setVisibility(View.VISIBLE);
                layoutSwipe.setRefreshing(false);

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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);


    }

    private Context getActivity() {
        return context;
    }


    //offline data store
    public void loadOfflineLocationData(int rowId, int pageCount) {
        Cache cache = VolleySingleton.getInstance(context).getRequestQueue().getCache();
        //Url
        String url = AppServiceUrl.url + "/getAnnouncements1/" + rowId + "/" + pageCount;
        Cache.Entry entry = cache.get(url);
        Log.d("URL", url);
        Log.d("JSON DATA:", entry + "");
//        cache.invalidate(url, true);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    JSONArray locDt = new JSONObject(data).getJSONArray("getAnnouncementsResult");

                    Gson converter = new Gson();
                    Type type = new TypeToken<List<AnnouncementDataModel>>() {
                    }.getType();
                    List<AnnouncementDataModel> tempArrayList = converter.fromJson(String.valueOf(locDt), type);
                    if (tempArrayList.size() == 0) {
                        Custom_app_util.customSnackbar("No more information", context, false, "");
                    }
                    if (LoadStatus.equals("DEFAULT")) {
                        annFeedModel.clear();
                    }
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        annFeedModel.add(tempArrayList.get(i));

                    }

                    mRecyclerView.setAdapter(announcementadapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // linearLayout.setVisibility(View.VISIBLE);
        }
    }

}
