package com.kenplayschool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.PostJsonStringRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FeesActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText edit_amount, user_name, dob, amount, pan_num, message, city, pincode, address;
    private Calendar myCalendar = Calendar.getInstance();
    private AppCompatSpinner spinnerCountry;
    private AppCompatButton submit;
    private RadioGroup genderGroup;
    private CardView card_view_100, card_view_500, card_view_5000, card_view_10000, card_view_custom;
    private IconTextView rupees_100, rupees_500, rupees_5000, rupees_10000;

    private Dialog dialog;

    private String money, country_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //convert font icon to drawabel
        IconDrawable currency = new IconDrawable(FeesActivity.this, IcoMoonIcons.ic_rupee).colorRes(R.color.color_dark).sizeDp(16);
        IconDrawable drawableUsername = new IconDrawable(FeesActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable drawableDob = new IconDrawable(FeesActivity.this, IcoMoonIcons.ic_calender).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable drawableamount = new IconDrawable(FeesActivity.this, IcoMoonIcons.ic_rupee).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable drawablePan = new IconDrawable(FeesActivity.this, IcoMoonIcons.ic_pancard).colorRes(R.color.color_dark).sizeDp(18);

//        edit_amount = (AppCompatEditText) findViewById(R.id.rupees_custom);
        //edit_amount.setCompoundDrawablesWithIntrinsicBounds(currency, null, null, null);
        user_name = (AppCompatEditText) findViewById(R.id.name);
        user_name.setCompoundDrawablesWithIntrinsicBounds(drawableUsername, null, null, null);
        dob = (AppCompatEditText) findViewById(R.id.dob);
        dob.setCompoundDrawablesWithIntrinsicBounds(drawableDob, null, null, null);
        amount = (AppCompatEditText) findViewById(R.id.amount);
        amount.setCompoundDrawablesWithIntrinsicBounds(drawableamount, null, null, null);
        pan_num = (AppCompatEditText) findViewById(R.id.pancard_number);
        pan_num.setCompoundDrawablesWithIntrinsicBounds(drawablePan, null, null, null);
        message = (AppCompatEditText) findViewById(R.id.message);
        submit = (AppCompatButton) findViewById(R.id.submit);
        genderGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        city = (AppCompatEditText) findViewById(R.id.city_town);
        pincode = (AppCompatEditText) findViewById(R.id.pincode);
        address = (AppCompatEditText) findViewById(R.id.home_address);

//        card_view_100 = (CardView) findViewById(R.id.card_view_100);
//        card_view_500 = (CardView) findViewById(R.id.card_view_500);
//        card_view_5000 = (CardView) findViewById(R.id.card_view_5000);
//        card_view_10000 = (CardView) findViewById(R.id.card_view_10000);
//        card_view_custom = (CardView) findViewById(R.id.card_view_custom);

        // Choose here for price options
//        rupees_100 = (IconTextView) findViewById(R.id.rupees_100);
//        rupees_500 = (IconTextView) findViewById(R.id.rupees_500);
//        rupees_5000 = (IconTextView) findViewById(R.id.rupees_5000);
//        rupees_10000 = (IconTextView) findViewById(R.id.rupees_10000);


//        card_view_100.setOnClickListener(this);
//        card_view_500.setOnClickListener(this);
//        card_view_5000.setOnClickListener(this);
//        card_view_10000.setOnClickListener(this);
//        card_view_custom.setOnClickListener(this);


        //click listener to open calender
        dob.setKeyListener(null);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FeesActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //spinner country
        spinnerCountry = (AppCompatSpinner) findViewById(R.id.spinner_country);
        spinnerCountry.setSelection(101);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_id = spinnerCountry.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //post donation
                donateToParty(
                        CustomPreference.with(FeesActivity.this).getString("user_id", ""),
                        money,
                        user_name.getText().toString(),
                        String.valueOf(((AppCompatRadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText()),
                        dob.getText().toString(),
                        country_id,
                        city.getText().toString(),
                        pincode.getText().toString(),
                        address.getText().toString(),
                        pan_num.getText().toString(),
                        message.getText().toString()
                );

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == card_view_100) {
            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            card_view_500.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_5000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_10000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            money = rupees_100.getText().toString();
            amount.setText(money);
        } else if (v == card_view_500) {
            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            card_view_100.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_5000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_10000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            money = rupees_500.getText().toString();
            amount.setText(money);
        } else if (v == card_view_5000) {
            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            card_view_100.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_500.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_10000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            money = rupees_5000.getText().toString();
            amount.setText(money);
        } else if (v == card_view_10000) {
            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            card_view_100.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_500.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_5000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            money = rupees_10000.getText().toString();
            amount.setText(money);
        } else {
            card_view_100.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_500.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_5000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            card_view_10000.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            money = edit_amount.getText().toString();
            amount.setText(money);
        }
    }

    //Date Picker
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        dob.setText(sdf.format(myCalendar.getTime()));
    }


    //donate to party
    public void donateToParty(final String user_id, final String amount, final String name, final String gender, final String dob, final String country_id, final String city, final String pin_code, final String address, final String pan_card, final String message) {

        //progress dialog
        dialog = new Dialog(FeesActivity.this, R.style.Theme_D1NoTitleDim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_view);
        dialog.show();

        JSONObject params = new JSONObject();
        try {
            params.put("method", "postDonation");
            params.put("user_id", user_id);
            params.put("amount", amount);
            params.put("name", name);
            params.put("gender", gender);
            params.put("dob", dob);
            params.put("country_id", country_id);
            params.put("city", city);
            params.put("pin_code", pin_code);
            params.put("address", address);
            params.put("pan_card", pan_card);
            params.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("params", params.toString());
        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d("result", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Success")) {
                        Snackbar.make(findViewById(android.R.id.content), "Donated Successfully", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                        startActivity(new Intent(FeesActivity.this, SuccessActivity.class));
                        finish();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e) {

                }

                dialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                //pgBar.setVisibility(View.GONE);
                dialog.dismiss();
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

        jsonString.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(FeesActivity.this).addToRequestQueue(jsonString);
    }
}
