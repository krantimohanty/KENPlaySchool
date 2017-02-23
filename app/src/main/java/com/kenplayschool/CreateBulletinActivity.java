package com.kenplayschool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.JsonArrayRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.joanzapata.iconify.IconDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateBulletinActivity extends AppCompatActivity {
    private AppCompatEditText title, comment, startdate, enddate;
    private AppCompatButton add;
    private Dialog dialog;
    private Calendar myCalendar = Calendar.getInstance();

    private String blockCharacterSet = "~#^|$%&*!";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bulletin);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getActionBarToolbar().setTitle("Create Enquiry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IconDrawable drawableDob = new IconDrawable(CreateBulletinActivity.this, IcoMoonIcons.ic_calender).colorRes(R.color.color_dark).sizeDp(18);
        title = (AppCompatEditText) findViewById(R.id.notice_title);
        comment = (AppCompatEditText) findViewById(R.id.notice_comment);
        startdate = (AppCompatEditText) findViewById(R.id.start_date);
        startdate.setCompoundDrawablesWithIntrinsicBounds(drawableDob, null, null, null);
        enddate = (AppCompatEditText) findViewById(R.id.end_date);
        enddate.setCompoundDrawablesWithIntrinsicBounds(drawableDob, null, null, null);
        add = (AppCompatButton) findViewById(R.id.add_notice);
        startdate.setKeyListener(null);
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateBulletinActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                enddate.setKeyListener(null);
                enddate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(CreateBulletinActivity.this, date1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });

                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Call services method

                                addBulletin();
                                startActivity(new Intent(CreateBulletinActivity.this,BulletinActivity.class));
                            }
                        });

                    }


                    public void addBulletin() {

                        final ProgressDialog progressDialog = new ProgressDialog(CreateBulletinActivity.this);
                        progressDialog.setMessage("Posting the Bulletin.. Please wait..");
                        progressDialog.show();

                        final JSONObject params = new JSONObject();
                        try {


                            //postWillGo
                            //BulletinTypeName
                            params.put("BulletinTitle", title.getText().toString().trim());
                            params.put("BulletinComments", comment.getText().toString().trim());
                            params.put("StartDate", startdate.getText().toString().trim());
                            params.put("Enddate", enddate.getText().toString().trim());
//                           / params.put("BulletinImage", enddate.getText().toString().trim());
                            //            params.put("Queries", edit_queries.getText().toString().trim());
                            //            params.put("EmailId", edit_email.getText().toString().trim());
                            //            params.put("Area", edit_area.getText().toString().trim());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url = "http://babylonia.in/BabyLoniaWebApi/api/InsertBulletin";

                        JsonArrayRequest request = new JsonArrayRequest(url, params, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                Log.d("Result======", response.toString());
                                System.out.println("ResultOut" + params.toString());

                                Toast.makeText(CreateBulletinActivity.this, "Thank you for submission", Toast.LENGTH_LONG)
                                        .show();
                                progressDialog.hide();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(CreateBulletinActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        request.setRetryPolicy(new DefaultRetryPolicy(
                                90000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        VolleySingleton.getInstance(CreateBulletinActivity.this).addToRequestQueue(request);

                    }
                });
            }

        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

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
//        enddate.setText(sdf.format(myCalendar.getTime()));
        startdate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        enddate.setText(sdf.format(myCalendar.getTime()));
//        startdate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateBulletin Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.babyloniaapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateBulletin Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.babyloniaapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}







