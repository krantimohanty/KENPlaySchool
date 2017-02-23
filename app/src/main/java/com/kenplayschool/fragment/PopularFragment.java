package com.kenplayschool.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kenplayschool.R;
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
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View rootView;
    private List<LatestFeedDataModel> latestFeedModel = new ArrayList<LatestFeedDataModel>();
    private LatestFeedsAdapter latestFeedsAdapter;
    private ContentLoadingProgressBar pgBar;
    private int page_count = 10;
    private int row_id = 0;
    protected Handler loadMorehandler;
    private String LoadStatus = "DEFAULT";
    private int type = 2;
    private int user_id;
    private IconTextView batchIcon;
    private AppCompatTextView heading1;
    private AppCompatTextView heading2;
    private SwipeRefreshLayout ViewSwipe;
    private SwipeRefreshLayout layoutSwipe;
    private LinearLayout networkUnavailable;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_popular, container, false);

        networkUnavailable = (LinearLayout) rootView.findViewById(R.id.networkUnavailable);
        batchIcon = (IconTextView) rootView.findViewById(R.id.batchIcon);
        heading1 = (AppCompatTextView) rootView.findViewById(R.id.heading1);
        heading2 = (AppCompatTextView) rootView.findViewById(R.id.heading2);
        ViewSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.ViewSwipe);
        layoutSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.layoutSwipe);

        //progressbar
        pgBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.progress);
        //recycle view
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.popular_list);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (CustomPreference.with(getActivity()).getInt("user_id", 0) == 0) {
                user_id = 0;
            } else {
                user_id = CustomPreference.with(getActivity()).getInt("user_id", 0);
            }
        } catch (ClassCastException e) {

        }

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        latestFeedsAdapter = new LatestFeedsAdapter(latestFeedModel, getContext(), mRecyclerView, type, user_id, 0, "popular", "");
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
        if (ConnectionDetector.staticisConnectingToInternet(getActivity().getApplicationContext())) {
            callingUpdates();
        } else {
            ServiceCalls.loadOfflineLocationData(getActivity(), latestFeedModel, latestFeedsAdapter, mRecyclerView, networkUnavailable, row_id, type, user_id, 0, page_count, "invalid");
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Network connection is not available.", Snackbar.LENGTH_LONG).show();
        }
    }

    private void callingUpdates() {

        layoutSwipe.setVisibility(View.GONE);
        //getUpcoming wish List
        if (ConnectionDetector.staticisConnectingToInternet(rootView.getContext())) {
            batchIcon.setVisibility(View.GONE);
            heading1.setVisibility(View.GONE);
            heading2.setVisibility(View.GONE);
            //clearData();
            ServiceCalls.getLatestNewsFeedsUsingGet(getActivity(), pgBar, heading1, heading2, ViewSwipe, layoutSwipe, batchIcon, latestFeedModel, mRecyclerView, latestFeedsAdapter, row_id, type, user_id, 0, page_count, "invalid");
        } else {
            // clearData();
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
}
