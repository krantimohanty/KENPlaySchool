package com.kenplayschool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.kenplayschool.app_util.ValidationUtil;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.ConnectionDetector;
import com.kenplayschool.network_utils.PostJsonStringRequest;
import com.kenplayschool.network_utils.ServiceCalls;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.IconDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddMembershipActivity extends BaseActivity {

    //private ViewPager viewPager;
    private AppCompatEditText user_name, dob, address, pincode, pancard_number, qualification, mobile_num, email_id, faname, moname;
    private Calendar myCalendar = Calendar.getInstance();
    private RadioGroup userTypeGroup, genderGroup, contact_check_group;
    private String utype, gender, residenttype;
    private AppCompatSpinner spinnerDistrict, spinnerBlock, spinnerPanchayat, spinnerVillage;
    private ArrayList<String> id1, id2, id3, id4;
    private String idDist, idBlock, idGP, idVillage;
    private AppCompatButton submit;
    private AppCompatCheckBox declaration;

    private Dialog dialog;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        submit = (AppCompatButton) findViewById(R.id.submit);

        //convert font icon to drawabel
        IconDrawable drawableUsername = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable drawableFathername = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable drawableMothername = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
// IconDrawable drawableDob = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_calender).colorRes(R.color.color_dark).sizeDp(18);
//        icon = bitmap;
        IconDrawable pin = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_location).colorRes(R.color.color_dark).sizeDp(18);
        //   IconDrawable pancard = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_pancard).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable qual = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_qualification).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable mob = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_mobile_no).colorRes(R.color.color_dark).sizeDp(18);
        IconDrawable email = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_email).colorRes(R.color.color_dark).sizeDp(18);

        // userTypeGroup = (RadioGroup) findViewById(R.id.radioGrpUserType);
        genderGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        // contact_check_group = (RadioGroup) findViewById(R.id.contact_check_group);

        user_name = (AppCompatEditText) findViewById(R.id.name);
        user_name.setCompoundDrawablesWithIntrinsicBounds(drawableUsername, null, null, null);
        user_name.setFilters(new InputFilter[]{filter});
//        faname = (AppCompatEditText) findViewById(R.id.fname);
//        faname.setCompoundDrawablesWithIntrinsicBounds(drawableFathername, null, null, null);
//        faname.setFilters(new InputFilter[]{filter});
//        moname = (AppCompatEditText) findViewById(R.id.mname);
//        moname.setCompoundDrawablesWithIntrinsicBounds(drawableMothername, null, null, null);
//        moname.setFilters(new InputFilter[]{filter});
//        dob = (AppCompatEditText) findViewById(R.id.dob);
//        dob.setCompoundDrawablesWithIntrinsicBounds(drawableDob, null, null, null);
//        dob.setFilters(new InputFilter[]{filter});
        address = (AppCompatEditText) findViewById(R.id.home_address);
        address.setFilters(new InputFilter[]{filter});
        pincode = (AppCompatEditText) findViewById(R.id.pincode);
        pincode.setCompoundDrawablesWithIntrinsicBounds(pin, null, null, null);
        pincode.setFilters(new InputFilter[]{filter});
//        pancard_number = (AppCompatEditText) findViewById(R.id.pancard_number);
//        pancard_number.setCompoundDrawablesWithIntrinsicBounds(pancard, null, null, null);
//        pancard_number.setFilters(new InputFilter[]{filter});
//        qualification = (AppCompatEditText) findViewById(R.id.qualification);
//        qualification.setCompoundDrawablesWithIntrinsicBounds(qual, null, null, null);
//        qualification.setFilters(new InputFilter[]{filter});
        mobile_num = (AppCompatEditText) findViewById(R.id.mobile_num);
        mobile_num.setCompoundDrawablesWithIntrinsicBounds(mob, null, null, null);
        mobile_num.setFilters(new InputFilter[]{filter});
        email_id = (AppCompatEditText) findViewById(R.id.email_id);
        email_id.setCompoundDrawablesWithIntrinsicBounds(email, null, null, null);
        email_id.setFilters(new InputFilter[]{filter});

        //declaration = (AppCompatCheckBox) findViewById(R.id.declaration);

        //blank space validation
        ValidationUtil.removeWhiteSpaceFromFront(user_name);
        ValidationUtil.removeWhiteSpaceFromFront(dob);
        ValidationUtil.removeWhiteSpaceFromFront(address);
        ValidationUtil.removeWhiteSpaceFromFront(pincode);
        ValidationUtil.removeWhiteSpaceFromFront(pancard_number);
        ValidationUtil.removeWhiteSpaceFromFront(qualification);
        ValidationUtil.removeWhiteSpaceFromFront(mobile_num);
        ValidationUtil.removeWhiteSpaceFromFront(email_id);

        //click listener to open calender
//        dob.setKeyListener(null);
//        dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(AddMembershipActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        id1 = new ArrayList<>();
//        id2 = new ArrayList<>();
//        id3 = new ArrayList<>();
//        id4 = new ArrayList<>();

        //district spinner
        spinnerDistrict = (AppCompatSpinner) findViewById(R.id.spinner_district);
        //block spinner
        spinnerBlock = (AppCompatSpinner) findViewById(R.id.spinner_block);
        //panchayat spinner
//        spinnerPanchayat = (AppCompatSpinner) findViewById(R.id.spinner_panchayat);
//        //village spinner
//        spinnerVillage = (AppCompatSpinner) findViewById(R.id.spinner_village);


        //to get district
        id1 = ServiceCalls.getDemography("d", "0", AddMembershipActivity.this, spinnerDistrict, "Select District");

        //district
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    id2 = ServiceCalls.getDemography("b", id1.get(position), AddMembershipActivity.this, spinnerBlock, "Select Block");
                    idDist = id1.get(position);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //block
        spinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    id3 = ServiceCalls.getDemography("g", id2.get(position), AddMembershipActivity.this, spinnerPanchayat, "Select Panchayat");
                    idBlock = id2.get(position);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //gp
//        spinnerPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    id4 = ServiceCalls.getDemography("v", id3.get(position), AddMembershipActivity.this, spinnerVillage, "Select Village");
//                    idGP = id3.get(position);
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        //village
//        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    idVillage = id4.get(position);
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }



//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        contact_check_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.yes:
//                        spinnerDistrict.setVisibility(View.VISIBLE);
//                        spinnerBlock.setVisibility(View.VISIBLE);
//                        spinnerPanchayat.setVisibility(View.VISIBLE);
//                        spinnerVillage.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.no:
//                        spinnerDistrict.setVisibility(View.GONE);
//                        spinnerBlock.setVisibility(View.GONE);
//                        spinnerPanchayat.setVisibility(View.GONE);
//                        spinnerVillage.setVisibility(View.GONE);
//                        break;
//                }
//            }
//        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (declaration.isChecked()) {
                    if (ConnectionDetector.staticisConnectingToInternet(AddMembershipActivity.this)) {
                        //user_type
                        if (((AppCompatRadioButton) findViewById(userTypeGroup.getCheckedRadioButtonId())).getText().equals("Add Yourself")) {
                            utype = String.valueOf(1);
                        } else if (((AppCompatRadioButton) findViewById(userTypeGroup.getCheckedRadioButtonId())).getText().equals("Add Other")) {
                            utype = String.valueOf(2);
                        }
                        //gender
                        if (String.valueOf(((AppCompatRadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText()).equals("Male")) {
                            gender = String.valueOf(1);
                        } else {
                            gender = String.valueOf(2);
                        }
                        // gender = String.valueOf(((AppCompatRadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText());
                        //address type
                        if (((AppCompatRadioButton) findViewById(contact_check_group.getCheckedRadioButtonId())).getText().equals("Yes")) {
                            residenttype = String.valueOf(1);
                        } else if (((AppCompatRadioButton) findViewById(contact_check_group.getCheckedRadioButtonId())).getText().equals("No")) {
                            residenttype = String.valueOf(0);
                        }


                        partyMemberRegister(utype,
                                CustomPreference.with(AddMembershipActivity.this).getString("user_id", ""),
                                user_name.getText().toString(),
                                gender,
                                dob.getText().toString(),
                                residenttype,
                                address.getText().toString(),
                                idDist,
                                idBlock,
                                idGP,
                                idVillage,
                                pincode.getText().toString(),
                                pancard_number.getText().toString(),
                                qualification.getText().toString(),
                                mobile_num.getText().toString(),
                                email_id.getText().toString());
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "No network connection detected", Snackbar.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "You need to check the declaration in order to continue", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        });
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

    public void partyMemberRegister(final String type, final String user_id, final String m_name, final String gender, final String dob, final String resident_odisha, final String home_address, final String dist_id, final String block_id, final String panchayat_id, final String vill_id, final String pin_code, final String pan_card, final String highest_qualification, final String mobile, final String email) {

        //progress dialog
        dialog = new Dialog(AddMembershipActivity.this, R.style.Theme_D1NoTitleDim);
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
            params.put("method", "registerPartyMembers");
            params.put("type", type);
            params.put("user_id", user_id);
            params.put("m_name", m_name);
            params.put("gender", gender);
            params.put("dob", dob);
            params.put("resident_odisha", resident_odisha);
            params.put("home_address", home_address);
            params.put("dist_id", dist_id);
            params.put("block_id", block_id);
            params.put("panchayat_id", panchayat_id);
            params.put("vill_id", vill_id);
            params.put("pin_code", pin_code);
            params.put("pan_card", pan_card);
            params.put("highest_qualification", highest_qualification);
            params.put("mobile", mobile);
            params.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.d("params", params.toString());
        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                //Log.d("result$$$$$$$$$$$", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if ((jsonObject.getString("message")).equals("success")) {
                        //Log.d("result+++++++++", (jsonObject.getString("message")));
                        Intent i = new Intent(AddMembershipActivity.this, SuccessActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (Exception e) {

                }

                dialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                //pgBar.setVisibility(View.GONE);
                dialog.hide();
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
        VolleySingleton.getInstance(AddMembershipActivity.this).addToRequestQueue(jsonString);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
