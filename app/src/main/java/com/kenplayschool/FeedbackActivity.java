package com.kenplayschool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private Button submitFeedback;
    public static Context context;
    private AppCompatEditText edit_name;
    private AppCompatEditText edit_Email;
    private AppCompatEditText edit_phone;
    private AppCompatEditText edit_desc;
    private RadioGroup userTypeGroup, genderGroup, contact_check_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");

        userTypeGroup = (RadioGroup) findViewById(R.id.radioGroup);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        contact_check_group = (RadioGroup) findViewById(R.id.radioGroup3);
        userTypeGroup = (RadioGroup) findViewById(R.id.radioGroup4);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroup7);
        submitFeedback = (Button) findViewById(R.id.btn_send);
        submitFeedback.setOnClickListener(this);



//        FEEDBACK:
//        http://babylonia.in/BabyLoniaWebApi/api/GetfeedbackAnswer
//        http://babylonia.in/BabyLoniaWebApi/api/GetfeedbackQuestion
//        http://babylonia.in/BabyLoniaWebApi/api/insertFeedback
//
//        Parameters:
//        FeedbackId
//                StudentEnrollmentId
//        LoginUserId
//                FeedbackQuestionId
//        FeedbackParameterId
//                Description
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getActionBarToolbar().setTitle("Feedback");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        edit_name = (AppCompatEditText) findViewById(R.id.edit_name);
//        edit_Email = (AppCompatEditText) findViewById(R.id.edit_emilid);
//        edit_phone = (AppCompatEditText) findViewById(R.id.edit_mob);
//        edit_desc = (AppCompatEditText) findViewById(R.id.edit_desc);
//        submitFeedback = (AppCompatButton) findViewById(R.id.btn_submit);
//        submitFeedback.setOnClickListener(this);
        //setupFloatingLabelError();

        //toolbar backNavigation button
//        toolbar.setNavigationOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        //WhiteSpaceRemoveFromFront
//        ValidationUtil.removeWhiteSpaceFromFront(edit_name);
//        ValidationUtil.removeWhiteSpaceFromFront(edit_Email);
//        ValidationUtil.removeWhiteSpaceFromFront(edit_phone);
//        ValidationUtil.removeWhiteSpaceFromFront(edit_desc);

    }

    //call postFeedBack services
//    public void postFeedBack() {
//        final JSONObject params = new JSONObject();
//
//        try {
//            //postWillGo
//            params.put("method", "postFeedback");
//            params.put("name", edit_name.getText().toString().trim());
//            params.put("email", edit_Email.getText().toString().trim());
//            params.put("phone", edit_phone.getText().toString().trim());
//            params.put("description", edit_desc.getText().toString().trim());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.d("FeedBackResult", response.toString());
//                Log.d("params***", params.toString());
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("message").equals("Success")) {
//                        Intent intent = new Intent(FeedbackActivity.this, DisplayActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(FeedbackActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        });
//        jsonString.setRetryPolicy(new DefaultRetryPolicy(
//                90000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonString);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if (TextUtils.isEmpty(edit_name.getText().toString().trim())) {
//            Custom_app_util.customSnackbar("Please enter a valid Name",FeedbackActivity.this,true,null);
//            return;
//        } else {
//           // edit_name.getText().toString().trim();
//            ValidationUtil.isValidName(edit_name.getText().toString().trim());
//        }
//        if (TextUtils.isEmpty(edit_Email.getText().toString().trim())) {
//            //email = edt_email.getText().toString().trim();
//            Custom_app_util.customSnackbar("Please enter a valid Email-ID",FeedbackActivity.this,true,null);
//            return;
//        } else {
//            ValidationUtil.isValidEmail(edit_Email.getText().toString().trim());
//        }
//
//        if (TextUtils.isEmpty(edit_phone.getText().toString().trim())) {
//            Custom_app_util.customSnackbar("Please enter a valid Mobile number",FeedbackActivity.this,true,null);
//            return;
//        } else {
//            ValidationUtil.isValidMobile(edit_phone.getText().toString().trim());
//          /*  //call postFeedBack services
//            postFeedBack();*/
//        }
//        if (TextUtils.isEmpty(edit_desc.getText().toString().trim())) {
//            //email = edt_email.getText().toString().trim();
//            Custom_app_util.customSnackbar("Please enter a Description",FeedbackActivity.this,true,null);
//            return;
//        } else {
//            edit_desc.getText().toString().trim();
//            //ValidationUtil.isValidEmail(edit_desc.getText().toString().trim());
//
//            //call postFeedBack services
//            postFeedBack();
//        }


       /* if (ValidationUtil.isValidName(edit_name.getText().toString()) ==true) {

           // Custom_app_util.customSnackbar("Name should not be blank",context,true,"Name.");

        } else if (ValidationUtil.isValidEmail(edit_Email.getText().toString()) ==true) {
            //Custom_app_util.customSnackbar("Should be valid email-id",context,true,"Email-Id.");

        } else if (ValidationUtil.isValidMobile(edit_phone.getText().toString()) ==true) {
            //Custom_app_util.customSnackbar("should be valid mobile number",context,true,"Mobile No.");


        } else {

            //call postFeedBack services
            postFeedBack();
        }*/



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

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {



        
        //call postFeedBack services
        postFeedBack();
    }

    private void postFeedBack() {



    }
}
