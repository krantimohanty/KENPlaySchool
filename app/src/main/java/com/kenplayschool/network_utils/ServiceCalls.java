package com.kenplayschool.network_utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.BuildConfig;
import com.kenplayschool.EventzActivity;
import com.kenplayschool.R;
import com.kenplayschool.app_adapter.BulletinAdapter;
import com.kenplayschool.app_adapter.LatestFeedsAdapter;
import com.kenplayschool.app_adapter.LocationSpinnerAdapter;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.data_model.BulletinModel;
import com.kenplayschool.data_model.LatestFeedDataModel;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kranti on 18/3/2016.
 */
public class ServiceCalls {

    static ArrayList<String> list = new ArrayList<>();
    static ArrayList<String> id = new ArrayList<>();

    //post likes----------------------------------------------------------------------------------------------------------
    public static void postLikes(final Context context, final List<LatestFeedDataModel> latestFeedModel, final int pos, final AppCompatTextView likeText, String type, String post_id, String LoginUserId, String like_type, final IconButton btnLike) {

        JSONObject params = new JSONObject();
        try {
            params.put("method", "postLikes");
            params.put("type", type);
            params.put("post_id", post_id);
            params.put("LoginUserId", LoginUserId);
            params.put("like_type", like_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.login_url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {
                        // int cnt = Integer.parseInt(latestFeedModel.get(pos).getLike_count());
                        if (btnLike.getCurrentTextColor() == context.getResources().getColor(R
                                .color.blue)) {
                            btnLike.setTextColor(context.getResources().getColor(R
                                    .color.dark_grey));
                            // cnt = (cnt + 1) - 1;
                            if (likeText.getText().toString() != "0") {
                                likeText.setText((Integer.parseInt(likeText.getText().toString()) - 1) + " likes");
                            }
                        } else {
                            btnLike.setTextColor(context.getResources().getColor(R.color.blue));
                            //  cnt = cnt + 1;
                            likeText.setText((Integer.parseInt(likeText.getText().toString()) + 1) + " likes");
                        }

//                        latestFeedModel.get(pos).setLike_count(String.valueOf(cnt));

                        //refresh Page
                        //getNewsDetail(getIntent().getStringExtra("post_id"), "1", "0", "0");
                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                //pgBar.setVisibility(View.GONE);
                Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
                        .setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

        jsonString.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonString);
    }
    //--------------------------------------------------------------------------------------------------

    //getNews feed--------------------------------------------------------------------------------------
    public static void getLatestNewsFeeds(final Context context, final ContentLoadingProgressBar bar, final AppCompatTextView txt1, final AppCompatTextView txt2, final SwipeRefreshLayout swipe1, final SwipeRefreshLayout swipe2, final IconTextView icTxt, final List<LatestFeedDataModel> latestFeedModel, final RecyclerView mRecyclerView, final LatestFeedsAdapter latestFeedsAdaptercmn, String row_id, final String typ, final String LoginUserId, final String sector_id, final String page_count, final String search_text) {
        final ArrayList<String> searchTitle = new ArrayList<>();
        final Map<String, String> map = new HashMap<String, String>();
        try {
            JSONObject params = new JSONObject();
            try {
                params.put("method", "getNews");
                params.put("LoginUserId", LoginUserId);
                params.put("type", typ);//for latest news
                params.put("sector_id", sector_id);
                params.put("search_text", search_text);
                params.put("row_id", row_id);
                params.put("page_count", page_count);

                String url = "http://203.129.207.117:81/bbbportal/Application/mobileProxy/getNews1?LoginUserId=" + LoginUserId + "&type=" + typ + "&sector_id=" + sector_id + "&search_text=" + search_text + "&row_id=" + row_id + "&page_count=" + page_count;

                Iterator iter = params.keys();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    String value = params.getString(key);
                    map.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    Log.d("result", response.toString());
                    try {
                        JSONArray jsonArray = response.getJSONArray("getNewsResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            searchTitle.add(jsonArray.getJSONObject(i).getString("title"));
                        }
                        Gson converter = new Gson();
                        Type type = new TypeToken<List<LatestFeedDataModel>>() {
                        }.getType();
                        List<LatestFeedDataModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);

                        if (tempArrayList.size() == 0) {
                            Custom_app_util.customSnackbar("No more information", context, false, "");
                        } else {
                            try {
                                for (int i = 0; i < tempArrayList.size(); i++) {
                                    latestFeedModel.add(tempArrayList.get(i));
                                }
                            } catch (NullPointerException e) {

                            }
                        }

//                        LatestFeedsAdapter latestFeedsAdapter = new LatestFeedsAdapter(latestFeedModel, context, mRecyclerView, typ, LoginUserId);
//                        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(latestFeedsAdapter);
//                        mRecyclerView.setAdapter(alphaAdapter);
                        latestFeedsAdaptercmn.notifyDataSetChanged();

                        if (latestFeedModel.isEmpty()) {
                            //latestFeedModel.clear();
                            //latestFeedsAdapter.notifyDataSetChanged();
                            icTxt.setText("{ic-noconnection 70dp #8C8989}");
                            txt2.setText("No updates.");
                            icTxt.setVisibility(View.VISIBLE);
                            txt1.setVisibility(View.GONE);
                            txt2.setVisibility(View.VISIBLE);
                            swipe2.setRefreshing(false);
                            swipe2.setVisibility(View.VISIBLE);

                        } else {
                            icTxt.setVisibility(View.GONE);
                            txt1.setVisibility(View.GONE);
                            txt2.setVisibility(View.GONE);
                            swipe2.setRefreshing(false);
                            swipe2.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // latestFeedModel.clear();
                        //latestFeedsAdapter1.notifyDataSetChanged();
                        icTxt.setText("{ic-noconnection 70dp #8C8989}");
                        txt1.setText("Something Went wrong");
                        txt2.setText("Please try after sometimes.");
                        icTxt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);
                        swipe2.setRefreshing(false);
                        swipe2.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {

                    }
                    try {
                        swipe1.setRefreshing(false);
                    } catch (NullPointerException e) {

                    }

                    //binding data to recycleview
                    try {
                        bar.setVisibility(View.GONE);
                    } catch (NullPointerException n) {

                    }

                    //Applying animation
                    try {
                        ((AppCompatActivity) context).setExitSharedElementCallback(new SharedElementCallback() {
                            @Override
                            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                                int bitmapWidth = Math.round(screenBounds.width());
                                int bitmapHeight = Math.round(screenBounds.height());
                                Bitmap bitmap = null;
                                if (bitmapWidth > 0 && bitmapHeight > 0) {




                                    Matrix matrix = new Matrix();
                                    matrix.set(viewToGlobalMatrix);
                                    matrix.postTranslate(-screenBounds.left, -screenBounds.top);
                                    bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(bitmap);
                                    canvas.concat(matrix);
                                    sharedElement.draw(canvas);
                                }
                                return bitmap;
                            }
                        });

                    } catch (Exception e) {

                    }

                    list = searchTitle;
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "error" + error.toString());
                    try {
                        bar.setVisibility(View.GONE);
                        icTxt.setText("{ic-noconnection 70dp #8C8989}");
                        txt1.setText("Something Went wrong");
                        txt2.setText("Please try after sometimes.");
                        icTxt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        swipe1.setRefreshing(false);
                        swipe2.setVisibility(View.VISIBLE);
                        swipe2.setRefreshing(false);
                    } catch (NullPointerException n) {

                    }
                    //latestFeedModel.clear();
                    //latestFeedsAdapter1.notifyDataSetChanged();

                    Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }
    }

    //getNews feed--------------------------------------------------------------------------------------
    public static void getLatestNewsFeedsUsingGet(final Context context, final ContentLoadingProgressBar bar, final AppCompatTextView txt1, final AppCompatTextView txt2, final SwipeRefreshLayout swipe1, final SwipeRefreshLayout swipe2, final IconTextView icTxt, final List<LatestFeedDataModel> latestFeedModel, final RecyclerView mRecyclerView, final LatestFeedsAdapter latestFeedsAdaptercmn, int row_id, final int typ, final int LoginUserId, final int sector_id, final int page_count, final String search_text) {
        final ArrayList<String> searchTitle = new ArrayList<>();

        try {
            //Url
            String url = AppServiceUrl.url + "/getNews1/" + LoginUserId + "/" + typ + "/" + sector_id + "/" + search_text + "/" + row_id + "/" + page_count;
            Log.d("NewsUrl", url);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    Log.d("result", response.toString());
                    try {
                        JSONArray jsonArray = response.getJSONArray("getNewsResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            searchTitle.add(jsonArray.getJSONObject(i).getString("title"));
                        }
                        Gson converter = new Gson();
                        Type type = new TypeToken<List<LatestFeedDataModel>>() {
                        }.getType();
                        List<LatestFeedDataModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);

                        if (tempArrayList.size() == 0) {
                            Custom_app_util.customSnackbar("No more information", context, false, "");
                        } else {
                            try {
                                for (int i = 0; i < tempArrayList.size(); i++) {
                                    latestFeedModel.add(tempArrayList.get(i));
                                }
                            } catch (NullPointerException e) {

                            }
                        }

//                        LatestFeedsAdapter latestFeedsAdapter = new LatestFeedsAdapter(latestFeedModel, context, mRecyclerView, typ, LoginUserId);
//                        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(latestFeedsAdapter);
//                        mRecyclerView.setAdapter(alphaAdapter);
                        latestFeedsAdaptercmn.notifyDataSetChanged();

                        if (latestFeedModel.isEmpty()) {
                            //latestFeedModel.clear();
                            //latestFeedsAdapter.notifyDataSetChanged();
                            icTxt.setText("{ic-noconnection 70dp #8C8989}");
                            txt2.setText("No updates.");
                            icTxt.setVisibility(View.VISIBLE);
                            txt1.setVisibility(View.GONE);
                            txt2.setVisibility(View.VISIBLE);
                            swipe2.setRefreshing(false);
                            swipe2.setVisibility(View.VISIBLE);

                        } else {
                            icTxt.setVisibility(View.GONE);
                            txt1.setVisibility(View.GONE);
                            txt2.setVisibility(View.GONE);
                            swipe2.setRefreshing(false);
                            swipe2.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // latestFeedModel.clear();
                        //latestFeedsAdapter1.notifyDataSetChanged();
                        icTxt.setText("{ic-noconnection 70dp #8C8989}");
                        txt1.setText("Something Went wrong");
                        txt2.setText("Please try after sometimes.");
                        icTxt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);
                        swipe2.setRefreshing(false);
                        swipe2.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {

                    }
                    try {
                        swipe1.setRefreshing(false);
                    } catch (NullPointerException e) {

                    }

                    //binding data to recycleview
                    try {
                        bar.setVisibility(View.GONE);
                    } catch (NullPointerException n) {

                    }

                    //Applying animation
                    try {
                        ((AppCompatActivity) context).setExitSharedElementCallback(new SharedElementCallback() {
                            @Override
                            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                                int bitmapWidth = Math.round(screenBounds.width());
                                int bitmapHeight = Math.round(screenBounds.height());
                                Bitmap bitmap = null;
                                if (bitmapWidth > 0 && bitmapHeight > 0) {
                                    Matrix matrix = new Matrix();
                                    matrix.set(viewToGlobalMatrix);
                                    matrix.postTranslate(-screenBounds.left, -screenBounds.top);
                                    bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(bitmap);
                                    canvas.concat(matrix);
                                    sharedElement.draw(canvas);
                                }
                                return bitmap;
                            }
                        });

                    } catch (Exception e) {

                    }

                    list = searchTitle;
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "error" + error.toString());
                    try {
                        bar.setVisibility(View.GONE);
                        icTxt.setText("{ic-noconnection 70dp #8C8989}");
                        txt1.setText("Something Went wrong");
                        txt2.setText("Please try after sometimes.");
                        icTxt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        swipe1.setRefreshing(false);
                        swipe2.setVisibility(View.VISIBLE);
                        swipe2.setRefreshing(false);
                    } catch (NullPointerException n) {

                    }
                    //latestFeedModel.clear();
                    //latestFeedsAdapter1.notifyDataSetChanged();

                    Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }
    }


    //--------------------------------------------------------------------------------------------------
    //share--------------------------------------------------------------
    public static void sharePost(Context context, String picUrl, String subject, String message) {
        //create the send intent
        Intent shareIntent =
                new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                message);
        context.startActivity(Intent.createChooser(shareIntent,
                "BabyLonia App"));
    }
    //-------------------------------------------------------------------

    //get user information
    public static void getUserInfo(final Context context, final String LoginUserId, final ContentLoadingProgressBar bar, final AppCompatTextView user_name, final ImageView profile_pic, final AppCompatTextView user_points, final String screenFlag) {

        try {
            JSONObject params = new JSONObject();
            if (CustomPreference.with(context).getString("LoginUserId", "").equals("")) {
               /* try {
                    params.put("method", "getUserById");
                    params.put("LoginUserId", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            } else {
                bar.setVisibility(View.VISIBLE);
                try {
                    params.put("method", "getUserById");
                    params.put("LoginUserId", LoginUserId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    Log.d("result", response.toString());
                    try {
                        JSONArray jsonArray = response.getJSONArray("getUserByIdResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            CustomPreference.with(context).save("LoginUserId", Integer.parseInt(jsonArray.getJSONObject(i).getString("LoginUserId")));
                            CustomPreference.with(context).save("name", jsonArray.getJSONObject(i).getString("name"));
                            CustomPreference.with(context).save("photo", jsonArray.getJSONObject(i).getString("photo"));
                            CustomPreference.with(context).save("email", jsonArray.getJSONObject(i).getString("email"));
                            CustomPreference.with(context).save("mobile", jsonArray.getJSONObject(i).getString("mobile"));
                            CustomPreference.with(context).save("dob", jsonArray.getJSONObject(i).getString("dob"));
                            CustomPreference.with(context).save("points", jsonArray.getJSONObject(i).getString("points"));
                            CustomPreference.with(context).save("earn_badges", jsonArray.getJSONObject(i).getString("earn_badges"));
                            CustomPreference.with(context).save("user_type", jsonArray.getJSONObject(i).getString("user_type"));
                            CustomPreference.with(context).save("photoPath", jsonArray.getJSONObject(i).getString("photoPath"));
                            try {
                                CustomPreference.with(context).save("type", Integer.parseInt(jsonArray.getJSONObject(i).getString("type")));
                            } catch (NumberFormatException e) {
                            }
                        }
                    } catch (JSONException e) {

                    }
                    try {
                        bar.setVisibility(View.GONE);
                    } catch (NullPointerException e) {

                    }
                    try {
                        bar.setVisibility(View.GONE);
                    } catch (NullPointerException e) {

                    }

                    try {
                        if (CustomPreference.with(context).getString("type", "").equalsIgnoreCase("G"))
                            Picasso.with(context)
                                    .load(CustomPreference.with(context).getString("photoPath", ""))
                                    .placeholder(new IconDrawable(context, IcoMoonIcons.ic_user)
                                            .colorRes(R.color.grey)
                                            .actionBarSize())
                                    .error(new IconDrawable(context, IcoMoonIcons.ic_user)
                                            .colorRes(R.color.grey)
                                            .actionBarSize())
                                    .into(profile_pic);
                        else if (CustomPreference.with(context).getString("type", "").equalsIgnoreCase("F")) {
                            Picasso.with(context)
                                    .load(CustomPreference.with(context).getString("photoPath", ""))
                                    .placeholder(new IconDrawable(context, IcoMoonIcons.ic_user)
                                            .colorRes(R.color.grey)
                                            .sizePx(80))
                                    .error(new IconDrawable(context, IcoMoonIcons.ic_user)
                                            .colorRes(R.color.grey)
                                            .sizePx(80))
                                    .into(profile_pic);
                        } else {
                            Picasso.with(context)
                                    .load(CustomPreference.with(context).getString("photo", ""))
                                    .placeholder(new IconDrawable(context, IcoMoonIcons.ic_user)
                                            .colorRes(R.color.grey)
                                            .sizeDp(70))
                                    .error(new IconDrawable(context, IcoMoonIcons.ic_user)
                                            .colorRes(R.color.grey)
                                            .sizeDp(70))
                                    .into(profile_pic);
                        }
                        Log.d("Photo", CustomPreference.with(context).getString("photo", ""));
                    } catch (Exception e) {

                    }
                    user_name.setText(CustomPreference.with(context).getString("name", ""));
                    // user_points.setText(CustomPreference.with(context).getString("points", "") + " points");
                    if (screenFlag.equals("signup")) {
                        context.startActivity(new Intent(context, EventzActivity.class));
                    } else {

                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "error" + error.toString());
                    try {
                        bar.setVisibility(View.GONE);
                    } catch (NullPointerException e) {

                    }

                    //latestFeedModel.clear();
                    //latestFeedsAdapter1.notifyDataSetChanged();
                    Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
                            .show();
                }
            });

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    90000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }
    }

    //--------------------------------------------------------------------------------------------
    //get event
    public static void getEvents(final Context context, final ContentLoadingProgressBar bar, final AppCompatTextView txt1, final AppCompatTextView txt2, final SwipeRefreshLayout swipe1, final SwipeRefreshLayout swipe2, final IconTextView icTxt, final List<BulletinModel> eventModels, final RecyclerView mRecyclerView, final BulletinAdapter eventsAdapter, final int typ, int row_id, final int page_count) {
        final ArrayList<String> searchTitle = new ArrayList<>();
        try {
           /* JSONObject params = new JSONObject();
            try {
                params.put("method", "getEvents");
                params.put("type", typ);//for latest news
                params.put("row_id", row_id);
                params.put("page_count", page_count);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            //Log.d("Event Url", params.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppServiceUrl.url + "/getEvents1/" + typ + "/" + row_id + "/" + page_count, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    Log.d("result", response.toString());
                    try {
                        JSONArray jsonArray = response.getJSONArray("getEventsResult");
                        Gson converter = new Gson();
                        Type type = new TypeToken<List<BulletinModel>>() {
                        }.getType();
                        List<BulletinModel> tempArrayList = converter.fromJson(String.valueOf(jsonArray), type);

                        if (tempArrayList.size() == 0) {
                            Custom_app_util.customSnackbar("No more information", context, false, "");
                        } else {
                            try {
                                for (int i = 0; i < tempArrayList.size(); i++) {
                                    eventModels.add(tempArrayList.get(i));
                                }
                            } catch (NullPointerException e) {

                            }
                        }

                        eventsAdapter.notifyDataSetChanged();

                        if (eventModels.isEmpty()) {
                            //latestFeedModel.clear();
                            //latestFeedsAdapter.notifyDataSetChanged();
                            icTxt.setText("{ic-noconnection 70dp #8C8989}");
                            txt2.setText("No updates.");
                            icTxt.setVisibility(View.VISIBLE);
                            txt1.setVisibility(View.GONE);
                            txt2.setVisibility(View.VISIBLE);
                            swipe2.setRefreshing(false);
                            swipe2.setVisibility(View.VISIBLE);

                        } else {
                            icTxt.setVisibility(View.GONE);
                            txt1.setVisibility(View.GONE);
                            txt2.setVisibility(View.GONE);
                            swipe2.setRefreshing(false);
                            swipe2.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // latestFeedModel.clear();
                        //latestFeedsAdapter1.notifyDataSetChanged();
                        icTxt.setText("{ic-noconnection 70dp #8C8989}");
                        txt1.setText("Something Went wrong");
                        txt2.setText("Please try after sometimes.");
                        icTxt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);
                        swipe2.setRefreshing(false);
                        swipe2.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {

                    }
                    try {
                        swipe1.setRefreshing(false);
                    } catch (NullPointerException e) {

                    }

                    //binding data to recycleview
                    try {
                        bar.setVisibility(View.GONE);
                    } catch (NullPointerException n) {

                    }

                    //Applying animation
                    try {
                        ((AppCompatActivity) context).setExitSharedElementCallback(new SharedElementCallback() {
                            @Override
                            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                                int bitmapWidth = Math.round(screenBounds.width());
                                int bitmapHeight = Math.round(screenBounds.height());
                                Bitmap bitmap = null;
                                if (bitmapWidth > 0 && bitmapHeight > 0) {
                                    Matrix matrix = new Matrix();
                                    matrix.set(viewToGlobalMatrix);
                                    matrix.postTranslate(-screenBounds.left, -screenBounds.top);
                                    bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(bitmap);
                                    canvas.concat(matrix);
                                    sharedElement.draw(canvas);
                                }
                                return bitmap;
                            }
                        });

                    } catch (Exception e) {

                    }

                    list = searchTitle;
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "error" + error.toString());
                    try {
                        bar.setVisibility(View.GONE);
                        icTxt.setText("{ic-noconnection 70dp #8C8989}");
                        txt1.setText("Something Went wrong");
                        txt2.setText("Please try after sometimes.");
                        icTxt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        swipe1.setRefreshing(false);
                        swipe2.setVisibility(View.VISIBLE);
                        swipe2.setRefreshing(false);
                    } catch (NullPointerException n) {

                    }
                    //latestFeedModel.clear();
                    //latestFeedsAdapter1.notifyDataSetChanged();

                    Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }
    }


    //get demography
    public static ArrayList<String> getDemography(final String level_name, final String pid, final Context context, final AppCompatSpinner spinner, final String default_text) {

        final ArrayList<String> name = new ArrayList<>();
        final ArrayList<String> id = new ArrayList<>();

        name.add(default_text);
        id.add("0");

        final JSONObject params = new JSONObject();
        try {
            params.put("method", "getDemography");
            params.put("level_name", level_name);
            params.put("pid", pid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("params", params.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("getDemographyResult");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        name.add(jsonArray.getJSONObject(i).getString("name"));
                        id.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                //district spinner
//                spinner = (AppCompatSpinner) rootView.findViewById(R.id.spinner_district);
                spinner.setAdapter(new LocationSpinnerAdapter(context, R.layout.spinner_text, name));

                // for (int j = 0; j < id.size(); j++) {
                //to get block


                 /*   //to get gramapanchayat
                    getDemography("g", id.get(j));

                    //to get village
                    getDemography("v", id.get(j));*/
                // }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);

        return id;
    }


    //--------------------------------
    //offline news data store
    public static void loadOfflineLocationData(Context context, List<LatestFeedDataModel> latestFeedDataModel, LatestFeedsAdapter latestFeedsAdapter, RecyclerView recyclerView, LinearLayout linearLayout, int row_id, final int typ, final int LoginUserId, final int sector_id, final int page_count, final String search_text) {
        Cache cache = VolleySingleton.getInstance(context).getRequestQueue().getCache();
        //Url
        String url = AppServiceUrl.url + "/getNews1/" + LoginUserId + "/" + typ + "/" + sector_id + "/" + search_text + "/" + row_id + "/" + page_count;
        Cache.Entry entry = cache.get(url);
        Log.d("URL", url);
        Log.d("JSON DATA:", entry + "");
//        cache.invalidate(url, true);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    JSONArray locDt = new JSONObject(data).getJSONArray("getNewsResult");

                    Log.d("JSON DATA:", locDt + "");
                    // bindDataToAdapter(locDt);
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<LatestFeedDataModel>>() {
                    }.getType();
                    List<LatestFeedDataModel> tempArrayList = converter.fromJson(String.valueOf(locDt), type);
                    latestFeedDataModel.clear();
                    for (int i = 0; i < tempArrayList.size(); i++) {
                        latestFeedDataModel.add(tempArrayList.get(i));
                    }
                    recyclerView.setAdapter(latestFeedsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    //offline events data store
    public static void loadOfflineLocationEventData(Context context, final List<BulletinModel> eventModels, final RecyclerView mRecyclerView, final BulletinAdapter eventsAdapter, final int typ, int row_id, final int page_count) {
        Cache cache = VolleySingleton.getInstance(context).getRequestQueue().getCache();
        //Url
        String url = AppServiceUrl.url + "/getEvents1/" + typ + "/" + row_id + "/" + page_count;
        Cache.Entry entry = cache.get(url);
        Log.d("URL", url);
        Log.d("JSON DATA:", entry + "");
//        cache.invalidate(url, true);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    JSONArray locDt = new JSONObject(data).getJSONArray("getEventsResult");

                    Log.d("JSON DATA:", locDt + "");
                    // bindDataToAdapter(locDt);
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<BulletinModel>>() {
                    }.getType();
                    List<BulletinModel> tempArrayList = converter.fromJson(String.valueOf(locDt), type);

                    if (tempArrayList.size() == 0) {
                        Custom_app_util.customSnackbar("No more information", context, false, "");
                    } else {
                        try {
                            for (int i = 0; i < tempArrayList.size(); i++) {
                                eventModels.add(tempArrayList.get(i));
                            }
                        } catch (NullPointerException e) {

                        }
                    }
                    mRecyclerView.setAdapter(eventsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            //linearLayout.setVisibility(View.VISIBLE);
        }
    }

    //get search data
    public static ArrayList<String> getSearchFeeds(final Context context, String row_id, final String typ, final int LoginUserId, final String sector_id, final String search_text) {
        final ArrayList<String> searchTitle = new ArrayList<>();
        final ArrayList<String> idList = new ArrayList<>();
        try {
            JSONObject params = new JSONObject();
            try {
                params.put("method", "getNews");
                params.put("LoginUserId", LoginUserId);
                params.put("type", typ);//for latest news
                params.put("sector_id", sector_id);
                params.put("search_text", search_text);
                params.put("row_id", 1);
                params.put("page_count", 100);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("search_params", params + "");
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    Log.d("searesult", response.toString());
                    try {

                        JSONArray jsonArray = response.getJSONArray("getNewsResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            searchTitle.add(jsonArray.getJSONObject(i).getString("title"));
                            idList.add(jsonArray.getJSONObject(i).getString("post_id"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (NullPointerException e) {

                    }

                    list = searchTitle;
                    id = idList;
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "error" + error.toString());
                    //latestFeedModel.clear();
                    //latestFeedsAdapter1.notifyDataSetChanged();

                    Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }

        return list;
    }


    //post views
    public static void postViews(final Context context, final String type, final String post_id, final String LoginUserId) {

        JSONObject params = new JSONObject();
        try {
            params.put("method", "postViews");
            params.put("type", type);
            params.put("post_id", post_id);
            params.put("LoginUserId", LoginUserId);
        } catch (JSONException e) {

        }
        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {

                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                //pgBar.setVisibility(View.GONE);
                Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
                        .setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

        jsonString.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonString);
    }

    //post Device Details
    public static void postDeviceDetails(final Context context) {

        String LoginUserId = "";
        if (TextUtils.isEmpty(CustomPreference.with(context).getString("LoginUserId", ""))) {
            LoginUserId = "0";
        } else {
            LoginUserId = CustomPreference.with(context).getString("LoginUserId", "");
            CustomPreference.with(context.getApplicationContext()).save("REGD_GCM_UID", LoginUserId);
        }

        String gcm_id = CustomPreference.with(context).getString("GCM_ID", "");
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String PhoneModel = android.os.Build.MODEL;
        String app_ver = String.valueOf(BuildConfig.VERSION_CODE);

        JSONObject params = new JSONObject();

        try {
            params.put("method", "postGCMData");
            params.put("gcm_id", gcm_id);
            params.put("device_id", deviceId);
            params.put("LoginUserId", LoginUserId);
            params.put("platform_name", "Android");
            params.put("device_model", PhoneModel);
            params.put("app_version", app_ver);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("rtag", params.toString());

        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {
                        CustomPreference.with(context.getApplicationContext()).save("REGD_GCM_ID", "Success");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonString.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonString);
    }


}