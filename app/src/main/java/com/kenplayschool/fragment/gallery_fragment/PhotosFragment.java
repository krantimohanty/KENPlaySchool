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
import com.kenplayschool.app_adapter.PhotoGalleryAdapter;

import com.kenplayschool.data_model.PhotoModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {

    private View rootView;
    private ArrayList<String> images;
    private RecyclerView mRecyclerView;
    private List<PhotoModel> photoModels;
    PhotoGalleryAdapter imageGalleryAdapter;
    private String type ="photo";
    private int row_id=0;
    private int page_count=10;
    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_photos, container, false);

        //images = new ArrayList<>();
        photoModels = new ArrayList<>();

        //callServiceMethod();
        //get phtotos
        getGalleryTypePhoto();
        //recycle view
      /*  mRecyclerView = (RecyclerView) rootView.findViewById(R.id.photo_gallery);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(ImageGalleryUtils.dp2px(getActivity(), 2), 2));
        PhotoGalleryAdapter imageGalleryAdapter = new PhotoGalleryAdapter(images);
        imageGalleryAdapter.setOnImageClickListener(new PhotoGalleryAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                *//**//*Intent intent = new Intent(getActivity(), FullScreenImageGalleryActivity.class);

                intent.putStringArrayListExtra("images", mImages);
                intent.putExtra("position", position);
                if (mPaletteColorType != null) {
                    intent.putExtra("palette_color_type", mPaletteColorType);
                }

                startActivity(intent);*//**//*
            }
        });
*/
        //mRecyclerView.setAdapter(imageGalleryAdapter);*/
        return rootView;
    }

    public void getGalleryTypePhoto() {
        final ArrayList<String> album_id = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();

        JSONObject params = new JSONObject();
        try {
            params.put("method", "getalbumdata");
            params.put("type", "photo");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Url
       // String url = "http://192.168.11.124:7001/Babylonia/Application/mobileProxy/getAlbumData/photo";
       /* VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
        Cache cache = VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache();
        //Cache cache=VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
        //Cache.Entry entry = cache.get(url);

        Cache.Entry entry = cache.get(url);
        System.out.println("URL================" + url);
        System.out.println("JSONDATA====" + entry +"");

        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
                System.out.println("DATA================" + data);
                //VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
                try {
                    JSONArray jsonArray = new JSONObject(data).getJSONArray("getAlbumDataResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<PhotoModel>>() {
                    }.getType();
                    List<PhotoModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    Log.d("Sizeof PhotoModel", tempArrayList.size() + "");
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        photoModels.add(tempArrayList.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.photo_gallery);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                PhotoGalleryAdapter imageGalleryAdapter = new PhotoGalleryAdapter(photoModels, getActivity());
                mRecyclerView.setAdapter(imageGalleryAdapter);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // Cached response doesn't exists. Make network call here
            //callServiceMethod();
        }
*/
          //String url="http://192.168.11.124:7001/Babylonia/Application/mobileProxy/getAlbumData/photo/" + row_id + "/" + page_count;

        //Url
       String url = "http://192.168.11.124:7001/Babylonia/App/mobileProxy/getAlbumData/" + row_id + "/" + page_count;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("AlbumResult", response.toString());
                /*try {                                     //"getGalleryDataResult"
                    JSONArray jsonArray = response.getJSONArray("getAlbumDataResult");
                           for (int i = 0; i < jsonArray.length(); i++) {
                               album_id.add(jsonArray.getJSONObject(i).getString("album_id"));//"album_id"
                        title.add(jsonArray.getJSONObject(i).getString("title"));
                        //set Image urls
                        images.add(jsonArray.getJSONObject(i).getString("album_photo"));//"album_photo"
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }*/


                try {
                    JSONArray jsonArray = response.getJSONArray("getAlbumDataResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<PhotoModel>>() {
                    }.getType();
                    List<PhotoModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    Log.d("Sizeof Photo", tempArrayList.size() + "");
                    for (int i = 0; i < tempArrayList.size(); i++) {

                        photoModels.add(tempArrayList.get(i));
                    }
                    // Sorting of sectoral data
                    Collections.sort(photoModels, new Comparator<PhotoModel>() {
                        @Override
                        public int compare(PhotoModel photoModel1, PhotoModel photoModel2) {
                            return photoModel1.getTitle().compareTo(photoModel2.getTitle());
                        }
                    });
                   // imageGalleryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                //recycle view
                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.photo_gallery);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                // mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                //mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(ImageGalleryUtils.dp2px(getActivity(), 2), 2));
               /* PhotoGalleryAdapter imageGalleryAdapter = new PhotoGalleryAdapter(images, title, getActivity());
                mRecyclerView.setAdapter(imageGalleryAdapter);
*/
              imageGalleryAdapter = new PhotoGalleryAdapter(photoModels, getActivity());
                mRecyclerView.setAdapter(imageGalleryAdapter);

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
