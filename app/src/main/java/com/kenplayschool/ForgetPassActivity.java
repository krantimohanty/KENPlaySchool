package com.kenplayschool;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.network_utils.JsonArrayRequest;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassActivity extends BaseActivity {

    private AppCompatButton reset;
    private AppCompatEditText emailId;
    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");
        toolbar.setTitleTextColor(Color.WHITE);

        emailId = (AppCompatEditText) findViewById(R.id.email_id);
        reset = (AppCompatButton) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailId.getText().toString().trim())) {
                    Custom_app_util.customSnackbar("Please enter a valid email ID", ForgetPassActivity.this, true, null);
                    return;
                } else {
                    //Call services
                    forgetPass(emailId.getText().toString().trim());
                }

//                if( Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString().trim()).matches()) {
//                    //forgetPass(emailId.getText().toString().trim());
                //
//                Intent intent =new Intent(ForgetPassActivity.this,LoginActivityOldie.class);
//                startActivity(intent);
//                finish();
//                }
//                /*Intent intent =new Intent(ForgetPassActivity.this,LoginActivityOldie.class);
//                startActivity(intent);
//                finish();*/

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
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
        finish();
    }

    //forget password
    private void forgetPass(final String email) {

    final JSONObject params = new JSONObject();
        try {

            params.put("UserName", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.d("params", params.toString());
//
//        String url="http://babylonia.in/BabyLoniaWebApi/api/ForgotPassword";
        String url = "http://babylonia.in/BabyLoniaWebApi/api/Forgotpassword";

        JsonArrayRequest request = new JsonArrayRequest(url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result======", response.toString());
                System.out.println("ResultOut" + params.toString());

                Toast.makeText(ForgetPassActivity.this, "Check your inbox to reset the password", Toast.LENGTH_LONG)
                        .show();
//                progressDialog.hide();
                Intent intent=new Intent(ForgetPassActivity.this,LoginActivityOldie.class);
                startActivity(intent);

                //dialog.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
                Toast.makeText(ForgetPassActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(ForgetPassActivity.this).addToRequestQueue(request);

    }
}
