/*
Create By-Kranti
Edited Date-09-APL-2016
Description-Show all news
 */

package com.kenplayschool.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kenplayschool.R;
import com.kenplayschool.app_adapter.LatestFeedsAdapterr;
import com.kenplayschool.data_model.LatestFeedDataModell;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.babyloniaapp.app_adapter.EventsAdapter;
//import com.babyloniaapp.data_model.EventModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventzFragment extends Fragment  {

    public Context context;
    private RecyclerView mRecyclerView;
    private View rootView;
    private List<LatestFeedDataModell> latestFeedModell;
    private LatestFeedsAdapterr latestFeedsAdapterr;
    private ContentLoadingProgressBar pgBar;
    private int page_count = 5;
    private int row_id = 0;

    private JSONObject params;
    private Map<String, String> map = new HashMap<String, String>();
    private String search_text;

    LinearLayoutManager mLinearLayoutManager;
//    EventsAdapter   eventAdapter;


    public EventzFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_latest, container, false);
        latestFeedModell=new ArrayList<>();

		    getBultieen();
        return rootView;
    }

       public void getBultieen() {
           String url = "http://babylonia.in/BabyLoniaWebApi/api/Bulletin";
//        String url = "http://192.168.1.10:8088/";
        JsonArrayRequest reqq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Result", response.toString());


                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                LatestFeedDataModell latestFeedModels = new LatestFeedDataModell();
                                latestFeedModels.setBulletinTitle(obj.getString("BulletinTitle"));
                                latestFeedModels.setBulletinImage(obj.getString("BulletinImage"));
                                latestFeedModels.setBulletinComments(obj.getString
                                        ("BulletinComments"));
                                latestFeedModels.setStartDate(obj.getString("StartDate"));
                                // Genre is json array


                                // adding eventModel to eventModels array
                                latestFeedModell.add(latestFeedModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_listt);
                        //mRecyclerView.setHasFixedSize(true);
                        mLinearLayoutManager = new LinearLayoutManager(getActivity());
                        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mLinearLayoutManager.scrollToPosition(0);
                        mRecyclerView.setLayoutManager(mLinearLayoutManager);

                        //eventAdapter=new EventsAdapter(getContext(),eventModels,mRecyclerView);
                        latestFeedsAdapterr = new LatestFeedsAdapterr(latestFeedModell,getContext()
                                ,mRecyclerView);
                        mRecyclerView.setAdapter(latestFeedsAdapterr);
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Result Error", "Error: " + error.getMessage());

            }
        });


        reqq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(reqq);

       }

       

    

}
