package com.kenplayschool;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.JsonArrayRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.IconDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ChangePassActivity extends BaseActivity {

    private AppCompatEditText oldpass, newPass, confirm_pass;
    private AppCompatButton submit;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        toolbar.setTitleTextColor(Color.WHITE);

        IconDrawable pass = new IconDrawable(ChangePassActivity.this, IcoMoonIcons.ic_password).colorRes(R.color.color_dark).sizeDp(18);

        oldpass = (AppCompatEditText) findViewById(R.id.old_pass);
        oldpass.setCompoundDrawablesWithIntrinsicBounds(pass, null, null, null);


        newPass = (AppCompatEditText) findViewById(R.id.new_pass);
        newPass.setCompoundDrawablesWithIntrinsicBounds(pass, null, null, null);
        confirm_pass = (AppCompatEditText) findViewById(R.id.confirm_pass);
        confirm_pass.setCompoundDrawablesWithIntrinsicBounds(pass, null, null, null);

        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progress);

        submit = (AppCompatButton) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldpass.getText().toString().equals("")) {
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Enter your old password", Snackbar.LENGTH_SHORT);
                    ViewGroup group = (ViewGroup) snack.getView();
                    group.setBackgroundColor(ContextCompat.getColor(ChangePassActivity.this, R.color.light_green));
                    snack.setActionTextColor(Color.WHITE).show();
                    oldpass.setError("Enter your old password");
                } else if (newPass.getText().toString().equals("")) {
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Enter your new password", Snackbar.LENGTH_SHORT);
                    ViewGroup group = (ViewGroup) snack.getView();
                    group.setBackgroundColor(ContextCompat.getColor(ChangePassActivity.this, R.color.light_green));
                    snack.setActionTextColor(Color.WHITE).show();
                    newPass.setError("Enter your new password");
                } else if (confirm_pass.getText().toString().equals("")) {
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Enter your new password again", Snackbar.LENGTH_SHORT);
                    ViewGroup group = (ViewGroup) snack.getView();
                    group.setBackgroundColor(ContextCompat.getColor(ChangePassActivity.this, R.color.light_green));
                    snack.setActionTextColor(Color.WHITE).show();
                    confirm_pass.setError("Enter your new password again");
                } else if (newPass.getText().toString().trim().equals(confirm_pass.getText().toString().trim())) {
                    changePass(oldpass.getText().toString().trim(), newPass.getText().toString().trim());
                } else {
                    confirm_pass.setError("Password is not matching");
                }
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

    //change password
    private void changePass(final String old_Pass, final String new_Pass) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Changing Password...");
        pDialog.show();
        final JSONObject params = new JSONObject();
        try {

            params.put("oldpwd", old_Pass);
            params.put("newpwd", new_Pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://babylonia.in/BabyLoniaWebApi/api/ChangePassword";
        Log.d("params", params.toString());

        JsonArrayRequest request = new JsonArrayRequest(url, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result======", response.toString());
                System.out.println("ResultOut" + params.toString());
                oldpass.setText("".trim());
                newPass.setText("".trim());
                confirm_pass.setText("".trim());
                Toast.makeText(ChangePassActivity.this, "Password changed successfully", Toast.LENGTH_LONG)
                        .show();
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                Toast.makeText(ChangePassActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(ChangePassActivity.this).addToRequestQueue(request);

    }
}

