package com.kenplayschool.fragment.gallery_fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.kenplayschool.app_adapter.VideoGalleryAdapter;
import com.kenplayschool.data_model.VideoModel;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private ArrayList<String> images;
    private RecyclerView mRecyclerView;
    private String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
    private List<VideoModel> videoModels;
    private SwipeRefreshLayout layoutSwipe;
    private int row_id = 0;
    private int page_count=10;
    private String LoadStatus = "DEFAULT";

    VideoGalleryAdapter imageGalleryAdapter;


    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        videoModels = new ArrayList<>();
        //images = new ArrayList<>();
        //recycle view
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.photo_gallery);
        layoutSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        layoutSwipe.setOnRefreshListener(this);
        //call services
      /* *//* getGalleryTypeVideo();*//*
        return rootView;
*/
        layoutSwipe.post(new Runnable() {
                             @Override
                             public void run() {
                                 layoutSwipe.setRefreshing(true);

                                 //call services
                                 getGalleryTypeVideo();
                             }
                         }
        );

        return rootView;

    }

    @Override
    public void onRefresh() {
        //call services
        getGalleryTypeVideo();
    }

    public void getGalleryTypeVideo() {
        final ArrayList<String> id = new ArrayList<>();

        final JSONObject params = new JSONObject();
        try {
            params.put("method", "getGalleryData1");
            params.put("type", "video");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Output+==============" + params);

        //Url
        String url = "http://192.168.11.124:7001/Babylonia/App/mobileProxy/getGalleryData1/video";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("result***", response.toString());

               /* try {
                    JSONArray jsonArray = response.getJSONArray("getGalleryDataResult");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        id.add(jsonArray.getJSONObject(i).getString("id"));
                        //set Image urls
                        images.add(getVideoId(jsonArray.getJSONObject(i).getString("url")));
                        //images.set(jsonArray.getJSONObject(i).getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e) {

                }*/

                //Log.d("VideoLIst", images.toString());
                /*try {
                    JSONArray jsonArray = response.getJSONArray("getGalleryDataResult");
                    Gson converter = new Gson();

                    Type type = new TypeToken<List<VideoModel>>() {
                    }.getType();

                    List<VideoModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);

                   for (int i = 0; i < tempArrayList.size(); i++) {
                        videoModels.add(tempArrayList.get(i));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                try {
                    JSONArray jsonArray = response.getJSONArray("getGalleryDataResult");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        VideoModel model = new VideoModel();
                        JSONObject jresult = jsonArray.getJSONObject(i);
                        model.setId(jresult.getString("id"));
                        model.setTitle(jresult.getString("title"));
                        model.setUrl(jresult.getString("url"));
                        videoModels.add(model);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Load complete
                onItemsLoadComplete();


                ///imageGalleryAdapter.notifyDataSetChanged();
                // stopping swipe refresh
                //layoutSwipe.setRefreshing(false);

                //set the adapter recyclerview======
                 imageGalleryAdapter = new VideoGalleryAdapter(videoModels, getActivity());
                mRecyclerView.setAdapter(imageGalleryAdapter);

                //set the adapter recyclerview======
              /*  VideoGalleryAdapter imageGalleryAdapter = new VideoGalleryAdapter(images, getActivity());
                mRecyclerView.setAdapter(imageGalleryAdapter);*/
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

                // stopping swipe refresh
                //layoutSwipe.setRefreshing(false);
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
    }

    private void onItemsLoadComplete() {
        //imageGalleryAdapter.notifyDataSetChanged();
        // Stop refresh animation
        layoutSwipe.setRefreshing(false);

        //layoutSwipe.setEnabled(false);
    }

   /* layoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            row_id = 0;
            LoadStatus = "DEFAULT";
            //clearData();
            callingUpdates();
        }
    });*/

    //extract youtube id
    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }

    public String getVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().length() <= 0)
            return null;

        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }


}
