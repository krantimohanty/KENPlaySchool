package com.kenplayschool.fragment.menu_fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.kenplayschool.app_adapter.BulletinAdapter;
import com.kenplayschool.data_model.BulletinModel;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BulletinFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private View rootView;
    List<BulletinModel> bulletinModels;
    BulletinAdapter bulletinAdapter;
    private SwipeRefreshLayout layoutSwipe;
    private ContentLoadingProgressBar pgBar;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_current_news, container, false);

        getBultieen();
        bulletinModels = new ArrayList<>();
        pgBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.progress);
        layoutSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.layoutSwipe);
        layoutSwipe.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.bg_button_login,
                R.color.colorAccent);

        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                getBultieen();
            }
        });

        //recycle view
        //
        //mRecyclerView = (RecyclerView) rootView.findViewById(R.id.upcoming_list);
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
       /* mRecyclerView.setHasFixedSize(true);*/

        return rootView;
    }


    // Serverside connection // Kranti
    public void getBultieen() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading.. Please wait..");
        progressDialog.show();
//        pgBar.setVisibility(View.VISIBLE);
        //String url = "http://babylonia.in/BabyLoniaWebApi/api/GetAllBulletin";
        String url = "http://babylonia.in/BabyLoniaWebApi/api/Bulletin";
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Result", response.toString());


                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                BulletinModel bulletinModel = new BulletinModel();
                                bulletinModel.setBulletinTitle(obj.getString("BulletinTitle"));
                                bulletinModel.setBulletinComments(obj.getString("BulletinComments"));
                                bulletinModel.setStartDate(obj.getString("StartDate"));
                                bulletinModel.setBulletinImage(obj.getString("BulletinImage"));

                                // Genre is json array


                                // adding eventModel to eventModels array
                                bulletinModels.add(bulletinModel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.upcoming_list);
                        mRecyclerView.setHasFixedSize(true);
                        mLinearLayoutManager = new LinearLayoutManager(getActivity());
                        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mLinearLayoutManager.scrollToPosition(0);
                        mRecyclerView.setLayoutManager(mLinearLayoutManager);

                        bulletinAdapter=new BulletinAdapter(getContext(),bulletinModels,mRecyclerView);
                        mRecyclerView.setAdapter(bulletinAdapter);
//                        pgBar.setVisibility(View.GONE);
//pgBar.hide();
                        layoutSwipe.setRefreshing(false);
                        progressDialog.hide();
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //adapter.notifyDataSetChanged() //

//
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Result Error", "Error: " + error.getMessage());

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(req);

    }
    public void  clearData()
    {
        bulletinModels.clear();
//        getBultieen();

    }

}