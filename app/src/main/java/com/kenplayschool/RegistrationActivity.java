package com.kenplayschool;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.kenplayschool.app_util.ValidationUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends BaseActivity {

//    //private ViewPager viewPager;
    private AppCompatEditText name, dob, father_name, mother_name, home_address, pincode, mobile_num, email_id;
   private Calendar myCalendar = Calendar.getInstance();
    private RadioGroup userTypeGroup, genderGroup, contact_check_group;
    private String utype, gender, residenttype;
    private AppCompatSpinner spinnerDistrict, spinnerBlock, spinnerPanchayat, spinnerVillage;
    private ArrayList<String> id1, id2, id3, id4;
    private String idDist, idBlock, idGP, idVillage;
    private AppCompatButton submit;
//    private AppCompatCheckBox declaration;
//
//    private Dialog dialog;
//
//    private String blockCharacterSet = "~#^|$%&*!";
//
//    private InputFilter filter = new InputFilter() {
//
//        @Override
//        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//
//            if (source != null && blockCharacterSet.contains(("" + source))) {
//                return "";
//            }
//            return null;
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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

        //  submit = (AppCompatButton) findViewById(R.id.submit);

        //convert font icon to drawabel
//        IconDrawable drawableUsername = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
//        IconDrawable drawableFathername = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
//        IconDrawable drawableMothername = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_user).colorRes(R.color.color_dark).sizeDp(18);
// IconDrawable drawableDob = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_calender).colorRes(R.color.color_dark).sizeDp(18);
//        icon = bitmap;
//        IconDrawable pin = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_location).colorRes(R.color.color_dark).sizeDp(18);
//        //   IconDrawable pancard = new IconDrawable(AddMembershipActivity.this, IcoMoonIcons.ic_pancard).colorRes(R.color.color_dark).sizeDp(18);
//        IconDrawable qual = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_qualification).colorRes(R.color.color_dark).sizeDp(18);
//        IconDrawable mob = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_mobile_no).colorRes(R.color.color_dark).sizeDp(18);
//        IconDrawable email = new IconDrawable(RegistrationActivity.this, IcoMoonIcons.ic_email).colorRes(R.color.color_dark).sizeDp(18);

//        // userTypeGroup = (RadioGroup) findViewById(R.id.radioGrpUserType);
//        genderGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
//        // contact_check_group = (RadioGroup) findViewById(R.id.contact_check_group);
//
        name = (AppCompatEditText) findViewById(R.id.name);
//        user_name.setCompoundDrawablesWithIntrinsicBounds(drawableUsername, null, null, null);
//        user_name.setFilters(new InputFilter[]{filter});

//        class_type = (AppCompatEditText) findViewById(R.id.class_type);
        father_name = (AppCompatEditText) findViewById(R.id.father_name);
//        faname.setCompoundDrawablesWithIntrinsicBounds(drawableFathername, null, null, null);
//        faname.setFilters(new InputFilter[]{filter});
        mother_name = (AppCompatEditText) findViewById(R.id.mother_name);
//        moname.setCompoundDrawablesWithIntrinsicBounds(drawableMothername, null, null, null);
//        moname.setFilters(new InputFilter[]{filter});
        dob = (AppCompatEditText) findViewById(R.id.dob);
////        dob.setCompoundDrawablesWithIntrinsicBounds(drawableDob, null, null, null);
////        dob.setFilters(new InputFilter[]{filter});
        home_address = (AppCompatEditText) findViewById(R.id.home_address);
//        address.setFilters(new InputFilter[]{filter});
        pincode = (AppCompatEditText) findViewById(R.id.pincode);
//        pincode.setCompoundDrawablesWithIntrinsicBounds(pin, null, null, null);
//        pincode.setFilters(new InputFilter[]{filter});
////        pancard_number = (AppCompatEditText) findViewById(R.id.pancard_number);
////        pancard_number.setCompoundDrawablesWithIntrinsicBounds(pancard, null, null, null);
////        pancard_number.setFilters(new InputFilter[]{filter});
////        qualification = (AppCompatEditText) findViewById(R.id.qualification);
////        qualification.setCompoundDrawablesWithIntrinsicBounds(qual, null, null, null);
////        qualification.setFilters(new InputFilter[]{filter});
        mobile_num = (AppCompatEditText) findViewById(R.id.mobile_num);
//        mobile_num.setCompoundDrawablesWithIntrinsicBounds(mob, null, null, null);
//        mobile_num.setFilters(new InputFilter[]{filter});
        email_id = (AppCompatEditText) findViewById(R.id.email_id);
//        email_id.setCompoundDrawablesWithIntrinsicBounds(email, null, null, null);
//        email_id.setFilters(new InputFilter[]{filter});
//
//        //declaration = (AppCompatCheckBox) findViewById(R.id.declaration);
//
//        //blank space validation
        ValidationUtil.removeWhiteSpaceFromFront(name);
        ValidationUtil.removeWhiteSpaceFromFront(dob);
//        ValidationUtil.removeWhiteSpaceFromFront(class_type);
        ValidationUtil.removeWhiteSpaceFromFront(father_name);
        ValidationUtil.removeWhiteSpaceFromFront(mother_name);
        ValidationUtil.removeWhiteSpaceFromFront(home_address);
        ValidationUtil.removeWhiteSpaceFromFront(pincode);
        ValidationUtil.removeWhiteSpaceFromFront(mobile_num);
        ValidationUtil.removeWhiteSpaceFromFront(email_id);

        //click listener to open calender
        dob.setKeyListener(null);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegistrationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        id1 = new ArrayList<>();
        id2 = new ArrayList<>();
        id3 = new ArrayList<>();
        id4 = new ArrayList<>();

        //district spinner
        spinnerDistrict = (AppCompatSpinner) findViewById(R.id.spinner_district);
        //block spinner
        spinnerBlock = (AppCompatSpinner) findViewById(R.id.spinner_block);
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
        dob.setText(sdf.format(myCalendar.getTime()));
    }
        //panchayat spinner
//        spinnerPanchayat = (AppCompatSpinner) findViewById(R.id.spinner_panchayat);
//        //village spinner
//        spinnerVillage = (AppCompatSpinner) findViewById(R.id.spinner_village);


        //to get district
//        id1 = ServiceCalls.getDemography("d", "0", RegistrationActivity.this, spinnerDistrict, "Select District");
//
//        //district
//
//
//        ;
//            params.put("pan_card", pan_card);
//            params.put("highest_qualification", highest_qualification);
//            params.put("mobile", mobile);
//            params.put("email", email);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //Log.d("params", params.toString());
//        PostJsonStringRequest jsonString = new PostJsonStringRequest(AppServiceUrl.url, params, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                // TODO Auto-generated method stub
//                //Log.d("result$$$$$$$$$$$", response.toString());
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    if ((jsonObject.getString("message")).equals("success")) {
//                        //Log.d("result+++++++++", (jsonObject.getString("message")));
//                        Intent i = new Intent(RegistrationActivity.this, SuccessActivity.class);
//                        startActivity(i);
//                        finish();
//                    } else {
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                } catch (Exception e) {
//
//                }
//
//                dialog.hide();
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("response", "error" + error.toString());
//                //content.setVisibility(View.GONE);
//                //pgBar.setVisibility(View.GONE);
//                dialog.hide();
//                Snackbar.make(findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
//                        .setAction("Dismiss", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        })
//                        .setActionTextColor(Color.RED)
//                        .show();
//            }
//        });
//
//        jsonString.setRetryPolicy(new DefaultRetryPolicy(
//                90000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance(RegistrationActivity.this).addToRequestQueue(jsonString);
//    }
//
//
//    }
//}
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            return true;
//        }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    }
