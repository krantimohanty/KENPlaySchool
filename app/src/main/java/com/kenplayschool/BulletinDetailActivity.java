package com.kenplayschool;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.PostJsonStringRequest;
import com.kenplayschool.network_utils.ServiceCalls;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Kranti on 19/4/2016.
 */

public class BulletinDetailActivity extends BaseActivity {

    private AppCompatTextView bulletin_head, bulletin_date, bulletin_place, bulletin_detail;
    private ImageView bulletin_image;
    private IconButton will_go, like, share;
    private String will_go_stat, like_stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bulletin_head = (AppCompatTextView) findViewById(R.id.bulletin_head);
        bulletin_date = (AppCompatTextView) findViewById(R.id.bulletin_date);
        bulletin_place = (AppCompatTextView) findViewById(R.id.bulletin_place);
        bulletin_detail = (AppCompatTextView) findViewById(R.id.bulletin_detail);
        bulletin_image = (ImageView) findViewById(R.id.bulletin_image);

        bulletin_head.setText(getIntent().getStringExtra("bulletin_title"));
        bulletin_date.setText(getIntent().getStringExtra("bulletin_date"));
        bulletin_detail.setText(getIntent().getStringExtra("bulletin_comment"));



        //load image
        Picasso.with(BulletinDetailActivity.this)
                .load(getIntent().getStringExtra("bulletin_image"))
                .into(bulletin_image);

        will_go = (IconButton) findViewById(R.id.will_go);
//        like = (IconButton) findViewById(R.id.like);
        share = (IconButton) findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceCalls.sharePost(BulletinDetailActivity.this, getIntent().getStringExtra("bulletin_date"), getIntent().getStringExtra("bulletin_title"), "Shared via KenReach - Play School app\n" +
                        ".  https://play.google.com/store/apps/details?id=com.kenplayschool");
            }
        });

        //callServiceMethod();

    }


    /*public void callServiceMethod() {
        //networkUnavailable.setVisibility(View.GONE);
        if (ConnectionDetector.staticisConnectingToInternet(getApplicationContext())) {
            getBulletinDetail(Integer.parseInt(getIntent().getStringExtra("userid")), Integer.parseInt(getIntent().getStringExtra("bulletinId")));

        } else {
            loadOfflineLocationData(getIntent().getIntExtra("userid", 0), getIntent().getIntExtra("bulletinId", 0));
            Snackbar.make(findViewById(android.R.id.content), "Network connection is not available.", Snackbar.LENGTH_LONG).show();
            // pgBar.setVisibility(View.GONE);
        }
    }*/


    private void getBulletinDetail(int userId, int bulletinId) {


       /* JSONObject params = new JSONObject();
        try {
            params.put("method", "getEventDetail");
            params.put("event_id", eventId);
            params.put("user_id", userId);
        } catch (JSONException e) {

        }
*/
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, AppServiceUrl.url + "/getBulletinDetail1/" + bulletinId + "/" + userId, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("getBulletinResult");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        bulletin_detail.setText(Html.fromHtml(jsonArray.getJSONObject(i).getString("description")));

                        will_go_stat = jsonArray.getJSONObject(i).getString("is_will_go");
                        like_stat = jsonArray.getJSONObject(i).getString("is_like");
                        //load image
                        Picasso.with(BulletinDetailActivity.this)
                                .load(jsonArray.getJSONObject(i).getString("photo"))
                                .into(bulletin_image);

                        //will go status
                        if (jsonArray.getJSONObject(i).getString("is_will_go").equals("1")) {
                            will_go.setTextColor(getResources().getColor(R.color.blue));
                        } else {
                            will_go.setTextColor(getResources().getColor(R.color.color_dark));
                        }
                        //like status
                       /* if (jsonArray.getJSONObject(i).getString("is_like").equals("1")) {
                            like.setTextColor(getResources().getColor(R.color.blue));
                        } else {
                            like.setTextColor(getResources().getColor(R.color.color_dark));
                        }*/

                    }
                   /* bulletin_head.setText(getIntent().getStringExtra("bulletin_title"));
                    bulletin_date.setText(getIntent().getStringExtra("bulletin_date"));
                    bulletin_place.setText(getIntent().getStringExtra("bulletin_comment"));*/
                } catch (JSONException e) {
                    e.printStackTrace();

                }


                /*will_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postWillGo(BulletinDetailActivity.this, getIntent().getStringExtra("userid"), getIntent().getStringExtra("bulletinId"), will_go_stat);
                        if (will_go.getCurrentTextColor() == getResources().getColor(R.color.blue)) {
                            will_go.setTextColor(getResources().getColor(R.color.color_dark));
                        } else {
                            will_go.setTextColor(getResources().getColor(R.color.blue));
                        }
                    }
                });*/
                //Like button comment==========
                /*like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CustomPreference.with(BulletinDetailActivity.this).getString("userId", "").equals("")) {
                            Intent intentGetMessage = new Intent(BulletinDetailActivity.this, LoginActivityOldie.class);
                            startActivityForResult(intentGetMessage, 2);
                        } else {
                            ServiceCalls.postLikes(BulletinDetailActivity.this, null, 0, null, "", "", CustomPreference.with(BulletinDetailActivity.this).getString("userId", ""), like_stat, like);
                        }
                    }
                });*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (null != data) {
                ServiceCalls.postLikes(BulletinDetailActivity.this, null, 0, null, "", "", CustomPreference.with(BulletinDetailActivity.this).getString("userId", ""), like_stat, like);
            }
        }
    }


    //post will go
    public void postWillGo(final Context context, final String userId, final String bulletinId, final String will_go_status) {

        JSONObject params = new JSONObject();
        try {
//            params.put("method", "postWillGo");
//            params.put("user_id", userId);
//            params.put("event_id", eventId);
//            params.put("will_go", will_go_status);
           // params.put("method", "postWillGo");
            params.put("BulletinTitle", userId);
            params.put("BulletinComments", bulletinId);
            params.put("StartDate", will_go_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("params", params + "");
        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d("postwillgoresult", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {
                        will_go.setTextColor(context.getResources().getColor(R.color.blue));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }
        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_search) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }


    //offline data store
    public void loadOfflineLocationData(int userId, int bulletinId) {

        Cache cache = VolleySingleton.getInstance(BulletinDetailActivity.this).getRequestQueue().getCache();
        //Url
        String url = AppServiceUrl.url + "/getBulletinDetail1/" + bulletinId + "/" + userId;
        Cache.Entry entry = cache.get(url);
        Log.d("URL", url);
        Log.d("JSON DATA:", entry + "");
//        cache.invalidate(url, true);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    JSONArray locDt = new JSONObject(data).getJSONArray("getBulletinResult");

                    for (int i = 0; i < locDt.length(); i++) {
                        bulletin_detail.setText(Html.fromHtml(locDt.getJSONObject(i).getString("description")));

                        will_go_stat = locDt.getJSONObject(i).getString("is_will_go");
                        like_stat = locDt.getJSONObject(i).getString("is_like");
                        //load image
                        Picasso.with(BulletinDetailActivity.this)
                                .load(locDt.getJSONObject(i).getString("photo"))
                                .into(bulletin_image);

                        //will go status
                        if (locDt.getJSONObject(i).getString("is_will_go").equals("1")) {
                            will_go.setTextColor(getResources().getColor(R.color.blue));
                        } else {
                            will_go.setTextColor(getResources().getColor(R.color.color_dark));
                        }
                        //like status
                        if (locDt.getJSONObject(i).getString("is_like").equals("1")) {
                            like.setTextColor(getResources().getColor(R.color.blue));
                        } else {
                            like.setTextColor(getResources().getColor(R.color.color_dark));
                        }

                    }
                    /*bulletin_head.setText(getIntent().getStringExtra("bulletin_title"));
                    bulletin_date.setText(getIntent().getStringExtra("bulletin_date"));
                    bulletin_place.setText(getIntent().getStringExtra("bulletin_place"));*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // linearLayout.setVisibility(View.VISIBLE);
        }
    }
}
