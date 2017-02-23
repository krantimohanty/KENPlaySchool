package com.kenplayschool.fragment.menu_fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.kenplayschool.app_adapter.EnquiryAdapter;
import com.kenplayschool.data_model.EnquiryModel;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnquiryFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private View rootView;
    List<EnquiryModel> enquiryModels;
    EnquiryAdapter enquiryAdapter;
    private SwipeRefreshLayout layoutSwipe;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_enquiry, container, false);
        enquiryModels = new ArrayList<>();
        enquiryAdapter=new EnquiryAdapter(getContext(),enquiryModels,mRecyclerView);
        layoutSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.enquirySwipe);
//        layoutSwipe.setColorSchemeResources(R.color.colorPrimaryDark,
//                R.color.colorPrimary,
//                R.color.colorAccent,
//                R.color.bg_button_login,
//                R.color.colorAccent);
       layoutSwipe.setColorSchemeResources(R.color.colorAccent);
        layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                getEnquiry();
            }
        });
//
        //mRecyclerView = (RecyclerView) rootView.findViewById(R.id.upcoming_list);
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
       /* mRecyclerView.setHasFixedSize(true);*/
        //
        getEnquiry();
        return rootView;
    }

// Serverside connection // Kranti
    public void getEnquiry() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...please wait..");
        progressDialog.show();
        enquiryModels.clear();
       // String url = "http://192.168.1.10:8088/api/GetEnquiry";
        String url = "http://babylonia.in/BabyLoniaWebApi/api/Enquiry";
//        String url = "http://kencloudcustomer-staging.azurewebsites.net/CheckSubscription.svc/rest/GetAllCity";
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Result", response.toString());


                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                EnquiryModel enquiryModel = new EnquiryModel();
                                //enquiryModel.setName(obj.getString("EnquiryId"));

                                enquiryModel.setEnquiryNo(obj.getString("EnquiryNo"));
                                enquiryModel.setName(obj.getString("Name"));
                                enquiryModel.setEmailId(obj.getString("EmailId"));
                                enquiryModel.setEnquiryType(obj.getString("EnquiryTypeName"));
                                enquiryModel.setPhoneNo(obj.getString("MobileNo"));
                                enquiryModel.setArea(obj.getString("Area"));
                                enquiryModel.setQueries(obj.getString("Queries"));
                                enquiryModel.setSource(obj.getString("Source"));



//                                enquiryModel.setName(obj.getString("CityName"));
//                                enquiryModel.setEmailId(obj.getString("ClientId"));
//                                enquiryModel.setEnquiryType(obj.getString("DistictId"));
//                                enquiryModel.setPhoneNo(obj.getString("StateId"));
//                                enquiryModel.setArea(obj.getString("T_cln_cd_Distict"));
//                                enquiryModel.setQueries(obj.getString("T_cln_cd_State"));




                                // Genre is json array


                                // adding eventModel to eventModels array
                                enquiryModels.add(enquiryModel);
                                progressDialog.hide();

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

                        mRecyclerView.setAdapter(enquiryAdapter);
                        layoutSwipe.setRefreshing(false);
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


        req.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(req);

    }
    public void  clearData()
    {
        enquiryModels.clear();

    }

    }