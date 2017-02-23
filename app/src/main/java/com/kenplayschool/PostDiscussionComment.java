package com.kenplayschool;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class PostDiscussionComment extends AppCompatActivity {

    AppCompatTextView titleTxt;
    AppCompatEditText cmntEdtTxt;
    AppCompatButton postCmntBtn;
    String userid;
    String themeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_discussion_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Replay Forum");

        String title = getIntent().getStringExtra("title");

        titleTxt = (AppCompatTextView) findViewById(R.id.titleTxt);
        titleTxt.setText(title);
        cmntEdtTxt = (AppCompatEditText) findViewById(R.id.cmntEdtTxt);
        postCmntBtn = (AppCompatButton) findViewById(R.id.postCmntBtn);

        userid = CustomPreference.with(PostDiscussionComment.this).getString("userId", "");
        themeId=getIntent().getStringExtra("themeId");

        postCmntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmtData = cmntEdtTxt.getText().toString();

                JSONObject param = new JSONObject();

                try {
                    param.put("method", "postReplyForum");
                    param.put("user_id", userid);
                    param.put("theme_id",themeId);
                    param.put("comment", cmtData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                        AppServiceUrl.url,
                        param, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("atag", "hello  " + response.toString());
                        Snackbar.make(findViewById(android.R.id.content), "Your comment for this discussion forum is successfully registered.", Snackbar.LENGTH_LONG)
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("atag", "error  " + error.toString());
                    }
                });

                objectRequest.setRetryPolicy(new DefaultRetryPolicy(90000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(PostDiscussionComment.this).addToRequestQueue(objectRequest);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
