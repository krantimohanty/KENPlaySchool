package com.kenplayschool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kenplayschool.app_adapter.LatestFeedsAdapter;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.app_util.OnLoadMoreListener;
import com.kenplayschool.data_model.LatestFeedDataModel;
import com.kenplayschool.network_utils.ConnectionDetector;
import com.kenplayschool.network_utils.ServiceCalls;
import com.kenplayschool.recycle_animator.ScaleInAnimationAdapter;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 05/05/16.
 */
public class SectorialDetails extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<LatestFeedDataModel> latestFeedModel = new ArrayList<LatestFeedDataModel>();
    private LatestFeedsAdapter latestFeedsAdapter;
    private ContentLoadingProgressBar pgBar;
    private int page_count = 10;
    private int row_id = 0;
    protected Handler loadMorehandler;
    private String LoadStatus = "DEFAULT";
    private int type = 3;
    static Context context;
    private int user_id;
    private IconTextView batchIcon;
    private AppCompatTextView heading1;
    private AppCompatTextView heading2;
    private SwipeRefreshLayout ViewSwipe;
    private SwipeRefreshLayout layoutSwipe;
    public String pageFrom = "";
    String toolbarTitle;
    private LinearLayout networkUnavailable;
    private int sectorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectorial_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* try {
             Log.d("Title",getIntent().getStringExtra("title"));
          // if (getIntent().getStringExtra("toolbar_title").equals("")) {
                toolbarTitle = getIntent().getStringExtra("title");
                Log.d("Title",toolbarTitle);
                getSupportActionBar().setTitle(toolbarTitle);

        } catch (Exception e) {

        }*/

        Bundle extras = getIntent().getExtras();   //check the bundel
        if (extras != null) {
            if (extras.containsKey("pageFrom")) {
                pageFrom = extras.getString("pageFrom").toString();

            }
            //Log.d("Title44", extras.getString("sector_name"));
            try {
                if (getIntent().getStringExtra("sector_name").equals("")) {
                    Log.d("Title", getIntent().getStringExtra("title"));
                    // if (getIntent().getStringExtra("toolbar_title").equals("")) {
                    toolbarTitle = getIntent().getStringExtra("title");
                    Log.d("Title", toolbarTitle);
                    getSupportActionBar().setTitle(toolbarTitle);
                } else {
                    getSupportActionBar().setTitle(getIntent().getStringExtra("sector_name"));
                }
            } catch (Exception e) {
                toolbarTitle = getIntent().getStringExtra("title");
                Log.d("Title", toolbarTitle);
                getSupportActionBar().setTitle(toolbarTitle);
            }


        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        networkUnavailable = (LinearLayout) findViewById(R.id.networkUnavailable);
        batchIcon = (IconTextView) findViewById(R.id.batchIcon);
        heading1 = (AppCompatTextView) findViewById(R.id.heading1);
        heading2 = (AppCompatTextView) findViewById(R.id.heading2);
        ViewSwipe = (SwipeRefreshLayout) findViewById(R.id.ViewSwipe);
        layoutSwipe = (SwipeRefreshLayout) findViewById(R.id.layoutSwipe);

        //progressbar
        pgBar = (ContentLoadingProgressBar) findViewById(R.id.progress);

        mRecyclerView = (RecyclerView) findViewById(R.id.details_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        if (CustomPreference.with(getActivity()).getInt("user_id", 0) == 0) {
            user_id = 0;
        } else {
            user_id = CustomPreference.with(getActivity()).getInt("user_id", 0);
        }

        Log.d("SectorId", getIntent().getIntExtra("sector_id", 0) + "");

        sectorId = getIntent().getIntExtra("sector_id", 0);

        //get sector details
        // ServiceCalls.getLatestNewsFeeds(getActivity(), pgBar, latestFeedModel, mRecyclerView, String.valueOf(row_id), type, user_id, getIntent().getStringExtra("sector_id"), String.valueOf(page_count));

        latestFeedsAdapter = new LatestFeedsAdapter(latestFeedModel, SectorialDetails.this, mRecyclerView, type, user_id, sectorId, "sectorial", getIntent().getStringExtra("sector_name"));


        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(latestFeedsAdapter);
        mRecyclerView.setAdapter(latestFeedsAdapter);

        callServiceMethod();

        ViewSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                row_id = 0;
                LoadStatus = "DEFAULT";
                clearData();
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
        latestFeedsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                latestFeedModel.add(null);
                latestFeedsAdapter.notifyItemInserted(latestFeedModel.size() - 1);
                loadMorehandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        latestFeedModel.remove(null);
                        latestFeedsAdapter.notifyItemRemoved(latestFeedModel.size());
                        callingMoreUpdates();
                        latestFeedsAdapter.setLoaded();
                    }
                }, 2000);
            }
        });

    }

    public void callServiceMethod() {
        networkUnavailable.setVisibility(View.GONE);
        if (ConnectionDetector.staticisConnectingToInternet(getApplicationContext())) {
            callingUpdates();
        } else {
            ServiceCalls.loadOfflineLocationData(SectorialDetails.this, latestFeedModel, latestFeedsAdapter, mRecyclerView, networkUnavailable, row_id, type, user_id, 0, page_count, "invalid");
            Snackbar.make(findViewById(android.R.id.content), "Network connection is not available.", Snackbar.LENGTH_LONG).show();
            pgBar.setVisibility(View.GONE);
        }
    }

    private void callingUpdates() {

        layoutSwipe.setVisibility(View.GONE);
        //getUpcoming wish List
        if (ConnectionDetector.staticisConnectingToInternet(SectorialDetails.this)) {
            batchIcon.setVisibility(View.GONE);
            heading1.setVisibility(View.GONE);
            heading2.setVisibility(View.GONE);
            //clearData();
            ServiceCalls.getLatestNewsFeedsUsingGet(SectorialDetails.this, pgBar, heading1, heading2, ViewSwipe, layoutSwipe, batchIcon, latestFeedModel, mRecyclerView, latestFeedsAdapter, row_id, type, user_id, sectorId, page_count, "invalid");
        } else {
            //clearData();
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


    //clear
    public void clearData() {
        latestFeedModel.clear();
        latestFeedsAdapter.notifyDataSetChanged();
    }


    private Context getActivity() {
        return context;
    }


    public void onBackPressed() {
        if (pageFrom.equalsIgnoreCase("Sectoral")) {
            startActivity(new Intent(SectorialDetails.this, EventzActivity.class).putExtra("pageFrom", "Sectoral"));
            finish();
        }
    }
}