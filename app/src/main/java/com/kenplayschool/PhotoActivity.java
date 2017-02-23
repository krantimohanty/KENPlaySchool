package com.kenplayschool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_adapter.PhotoAdapter;
import com.kenplayschool.app_util.GridSpacesItemDecoration;
import com.kenplayschool.app_util.ImageGalleryUtils;
import com.kenplayschool.data_model.PhotoModel;
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

public class PhotoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    public static Context context;
    PhotoAdapter photoAdapter;
    private List<PhotoModel> photoModels;
    int position;
    int row_id=0;
    int page_count=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        try {
            toolbar.setTitle(getIntent().getStringExtra("title"));
        } catch (Exception e) {

        }

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar backNavigation button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // get intent data
         //Intent i = getIntent();
        // Selected image id
        //position = i.getExtras().getInt("albumId",0);
       // position =Integer.parseInt(getIntent().getStringExtra("albumId"));
       // String title=getIntent().getStringExtra("title");
        //getIntent().getIntExtra("Id", 0);

        photoModels = new ArrayList<>();
        //call services method
        //getPhotoImage();
        callServiceMethod();

        //getPhotoGalleryImage(position,title);

    }

    public void callServiceMethod() {
        //networkUnavailable.setVisibility(View.GONE);
        //getPhotoImage(Integer.parseInt(getIntent().getStringExtra("userid")), Integer.parseInt(getIntent().getStringExtra("albumId")));
        //getPhotoImage(getIntent().getStringExtra("userid"),getIntent().getStringExtra("albumId"));
        //getPhotoImage(Integer.parseInt(getIntent().getStringExtra("albumId")));
        position =Integer.parseInt(getIntent().getStringExtra("albumId"));

        Log.d("**Result==============", String.valueOf(position));
        Log.d("===============Result",getIntent().getStringExtra("albumId"));

        getPhotoImage(position, row_id, page_count);

           }



    private void getPhotoGalleryImage(int position, String title) {

    }

    private void getPhotoImage(int albumId, int row_id,final int page_count) {
       /* String url = "http://192.168.11.124:7001/Babylonia/App/mobileProxy/getAlbumData/photo";
        //Cache cache = AppController.getInstance().getRequestQueue().getCache();
        VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
        Cache cache = VolleySingleton.getInstance(PhotoActivity.this).getRequestQueue().getCache();
        //Cache cache=VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
       // String url = "http://192.168.11.124:7001/BJDPortal_PHP/Application/mobileProxy/getAlbumData/photo";
        //Cache.Entry entry = cache.get(url);
         VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
        Cache.Entry entry = cache.get(url);
        Log.d("URL", url);
        Log.d("JSON DATA:", entry + "");

        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                //VolleySingleton.getInstance(getActivity()).getRequestQueue().getCache().remove(url);
                // handle data, like converting it to xml, json, bitmap etc.,
                try {
                    JSONArray jsonArray = new JSONObject(data).getJSONArray("getAlbumDataResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<PhotoModel>>() {
                    }.getType();
                    List<PhotoModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    Log.d("Sizeof Photo", tempArrayList.size() + "");
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        photoModels.add(tempArrayList.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.recycle_viewid);
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(ImageGalleryUtils.dp2px(PhotoActivity.this, 2), 2));
                photoAdapter = new PhotoAdapter(photoModels, getActivity());
                mRecyclerView.setAdapter(photoAdapter);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else
        {
            // Cached response doesn't exists. Make network call here
        }
*/
        //Url
        //String url="http://192.168.11.124:7001/BJDPortal_PHP/Application/mobileProxy/getAlbumData/photo";
          //String url="http://192.168.11.124:7001/BJDPortal_PHP/Application/mobileProxy/getGalleryData1/photo/4";
        //String url="http://192.168.11.124:7001/BJDPortal_PHP/Application/mobileProxy/getgallerydata1/photo/4/+eventId";

        //Request.Method.GET, AppServiceUrl.url + "/getGalleryData1/" + albumId + "/" + userId;
        //"/getGalleryData1/photo/" + albumId + "/" + row_id  + "/" + page_count
        //http://192.168.11.124:7001/BJDPortal_PHP/Application/mobileProxy/getGalleryData1/photo/4/0/5

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppServiceUrl.url + "/getGalleryData1/photo/" + albumId + "/" + row_id  + "/" + page_count, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("GalleryData", response.toString());
               /* try {                                     //"getGalleryDataResult"
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
                    JSONArray jsonArray = response.getJSONArray("getGalleryDataResult");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<PhotoModel>>() {
                    }.getType();
                    List<PhotoModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);
                    Log.d(" PhotoModel", tempArrayList.size() + "");
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        photoModels.add(tempArrayList.get(i));
                    }
                    //photoAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                mRecyclerView= (RecyclerView) findViewById(R.id.recycle_viewid);
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(ImageGalleryUtils.dp2px(PhotoActivity.this, 2), 2));
                photoAdapter = new PhotoAdapter(photoModels,PhotoActivity.this);
                mRecyclerView.setAdapter(photoAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                Snackbar.make(PhotoActivity.this.findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent intent = new Intent(this, TodaysActivity.class);
        startActivity(intent);*/
        finish();
    }
    private Context getActivity() {
        return context;
    }
}
