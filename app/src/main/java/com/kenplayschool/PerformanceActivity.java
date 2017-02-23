package com.kenplayschool;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.babyloniaapp.app_adapter.PerformanceAdapter;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.data_model.SectoralModel;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PerformanceActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private View rootView;
    private List<SectoralModel> sectoralModels = new ArrayList<SectoralModel>();
   // private PerformanceAdapter achievementsAdapter;
    private ContentLoadingProgressBar pgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //progressbar
        pgBar = (ContentLoadingProgressBar) findViewById(R.id.progress);

        //recylcerview
        mRecyclerView = (RecyclerView) findViewById(R.id.performance_list);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(PerformanceActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);

       // achievementsAdapter = new PerformanceAdapter();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(PerformanceActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //get sector information
        getSectors();
    }

    //get Sectoral
    public void getSectors() {
        /*JSONObject params = new JSONObject();
        try {
            params.put("method", "getSectors");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppServiceUrl.url + "/getSectors1", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());
                try {
                    clearData();
                    JSONArray jsonArray = response.getJSONArray("getSectorsResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<SectoralModel>>() {
                    }.getType();
                    List<SectoralModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        sectoralModels.add(tempArrayList.get(i));
                    }


                    // Sorting of sectoral data
                    Collections.sort(sectoralModels, new Comparator<SectoralModel>() {
                        @Override
                        public int compare(SectoralModel sectoralModel1, SectoralModel sectoralModel2) {
                            return sectoralModel1.getSector_name().compareTo(sectoralModel2.getSector_name());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                //binding data to recycleview
                pgBar.setVisibility(View.GONE);
//                achievementsAdapter = new PerformanceAdapter(sectoralModels, PerformanceActivity.this, mRecyclerView);
//                mRecyclerView.setAdapter(achievementsAdapter);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                pgBar.setVisibility(View.GONE);
                Custom_app_util.customSnackbar("Something went wrong!!!", PerformanceActivity.this, false, "");
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(PerformanceActivity.this).addToRequestQueue(jsonObjReq);
    }

    //clear
    public void clearData() {
        sectoralModels.clear(); //clear list
        //achievementsAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

}
