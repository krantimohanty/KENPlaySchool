package com.kenplayschool.fragment.gallery_fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.R;
import com.kenplayschool.app_util.DividerItemDecoration;
import com.kenplayschool.data_model.AudioModel;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> audioUrlList;
    private List<AudioModel> audioModels;
   // private AudioAdapter audioAdapter;

    public AudioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_audio, container, false);

        //recycle view
        audioModels = new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.audio_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //getting audio from server
        getGalleryTypeAudio();


        return rootView;
    }

    public void getGalleryTypeAudio() {
        final ArrayList<String> id = new ArrayList<>();

        JSONObject params = new JSONObject();
        try {
            params.put("method", "getGalleryData");
            params.put("type", "audio");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("getGalleryDataResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<AudioModel>>() {
                    }.getType();
                    List<AudioModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    Log.d("Size", tempArrayList.size() + "");
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        audioModels.add(tempArrayList.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

//                Log.d("Size", audioModels.size() + "");
                //recycle view
             //   AudioAdapter adapter = new AudioAdapter(audioModels, getActivity(), mRecyclerView);
               // mRecyclerView.setAdapter(adapter);
                // audioAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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

}
