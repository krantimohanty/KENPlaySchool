
package com.kenplayschool;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.app_util.ValidationUtil;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.JsonArrayRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.IconDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private AppCompatEditText editFirstName, editLastName, editEmailId, editMobile, editPass;
    private AppCompatButton btnSignUp, btnLogin;
    private ImageView profile_Pic;
    private AppCompatTextView editprofilePic;
    //private ContentLoadingProgressBar progressBar;
    private TextInputLayout inputName, inputEmail, inputPass, inputDob, inputMobile;
    private Calendar myCalendar = Calendar.getInstance();
    private Dialog dialog;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String imgString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
//        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Profile");
        //toolbar.setTitleTextColor(Color.WHITE);

        inputName = (TextInputLayout) findViewById(R.id.inputLayoutFirstName);
        inputName = (TextInputLayout) findViewById(R.id.inputLayoutlastName);
        inputEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);
//        inputPass = (TextInputLayout) findViewById(R.id.inputLayoutPass);
        //inputDob = (TextInputLayout) findViewById(R.id.inputLayoutDob);



        profile_Pic = (ImageView) findViewById(R.id.profilepic);
        profile_Pic.setImageDrawable(new IconDrawable(this, IcoMoonIcons.ic_user)
                .colorRes(R.color.color_white)
                .sizePx(70));

        editprofilePic = (AppCompatTextView) findViewById(R.id.editProfilePic);
        editprofilePic.setVisibility(View.GONE);
        editFirstName = (AppCompatEditText) findViewById(R.id.firstname);
        ValidationUtil.removeWhiteSpaceFromFront(editFirstName);
        editFirstName.setFilters(new InputFilter[]{ValidationUtil.filter});
        editFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        editFirstName.setLongClickable(false);

        editLastName = (AppCompatEditText) findViewById(R.id.lastname);
        ValidationUtil.removeWhiteSpaceFromFront(editLastName);
        editLastName.setFilters(new InputFilter[]{ValidationUtil.filter});
        editLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        editLastName.setLongClickable(false);

        editEmailId = (AppCompatEditText) findViewById(R.id.email);
        ValidationUtil.removeWhiteSpaceFromFront(editEmailId);
        editFirstName.setFilters(new InputFilter[]{ValidationUtil.filter});
        editFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        editFirstName.setLongClickable(false);

        editMobile = (AppCompatEditText) findViewById(R.id.mobile);
        ValidationUtil.removeWhiteSpaceFromFront(editMobile);
        editFirstName.setFilters(new InputFilter[]{ValidationUtil.filter});
        editFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        editFirstName.setLongClickable(false);

        //editPass = (AppCompatEditText) findViewById(R.id.edit_mobile);
        //ValidationUtil.removeWhiteSpaceFromFront(editPass);
        //editDob = (AppCompatEditText) findViewById(R.id.dob);
        //ValidationUtil.removeWhiteSpaceFromFront(editDob);
        // progressBar = (ContentLoadingProgressBar) findViewById(R.id.progress);


        profile_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        //click listener to open calender
       /* editDob.setKeyListener(null);
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/


        btnSignUp = (AppCompatButton) findViewById(R.id.signUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignupActivity", "Success: " + imgString);
//                if (imgString.equals("")) {
//
//                    Custom_app_util.customSnackbar("Profile picture field cannot be empty", SignUpActivity.this, false, "");
//
//                }
                //else
                if (ValidationUtil.isValidName(editFirstName.getText().toString()) == false) {
                    //inputName.setErrorEnabled(true);
                    //inputName.setError("Invalid user name");
                    Custom_app_util.customSnackbar("Enter Your First Name", SignUpActivity
                                    .this,
                            false, "");
                    editFirstName.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                }else if(editLastName.getText().toString().equals("")){
                    Custom_app_util.customSnackbar("Enter Your Last Name", SignUpActivity
                            .this, false, "");
                    editLastName.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

                }

                else if (Patterns.EMAIL_ADDRESS.matcher(editEmailId.getText().toString()).matches() == false) {
                    // inputEmail.setErrorEnabled(true);
                    // inputEmail.setError("Invalid email id");
                    Custom_app_util.customSnackbar("Invalid email id", SignUpActivity.this, false, "");
                    editEmailId.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                }
                /*else if (editPass.getText().toString().equals("")) {
                    //  inputPass.setErrorEnabled(true);
                    //  inputPass.setError("Invalid password");
                    Custom_app_util.customSnackbar("Invalid password", SignUpActivity.this, false, "");
                    editPass.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                } */
                else if (android.util.Patterns.PHONE.matcher(editMobile.getText().toString()).matches() == false) {
                    // inputMobile.setErrorEnabled(true);
                    // inputMobile.setError("Invalid phone number");
                    Custom_app_util.customSnackbar("Invalid phone number", SignUpActivity.this, false, "");
                    editMobile.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                }

              /*  else if (editDob.getText().toString().equals("")) {
                    // inputDob.setErrorEnabled(true);
                    //  inputDob.setError("Invalid date of birth");
                    Custom_app_util.customSnackbar("Invalid date of birth", SignUpActivity.this, false, "");
                    editDob.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                } */

                else {
                    signUp(imgString, editFirstName.getText().toString(), editLastName.getText().toString(), editEmailId.getText()
                            .toString(), editMobile.getText().toString());

                    Toast.makeText(SignUpActivity.this,"Successfully Signup ",Toast.LENGTH_LONG)
                            .show();

                    Intent intent=new Intent(SignUpActivity.this,LoginActivityOldie.class);
                    startActivity(intent);
                    editFirstName.setText("");
                    editLastName.setText("");
                    editEmailId.setText("");
                    editMobile.setText("");

                }
            }
        });

        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
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
            //updateLabel();
        }
    };

   /* private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editDob.setText(sdf.format(myCalendar.getTime()));
    }*/

    //getting profile pic
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_Pic.setImageBitmap(thumbnail);

        imgString = getImageString(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        profile_Pic.setImageBitmap(bm);
        imgString = getImageString(bm);
    }

    //Sign Up
    private void signUp(String image, String firstname, String lastname, String email, String mobile) {

        //progress dialog
      /*  dialog = new Dialog(SignUpActivity.this, R.style.Theme_D1NoTitleDim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_view);
        dialog.show();
*/
        final JSONObject param = new JSONObject();
        try {

            param.put("CustomerFirstName",firstname);
            param.put("CustomerLastName",lastname);
            param.put("Email", email);
            param.put("Mobile", mobile);
            param.put("Photo", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
                //String url="http://babylonia.in/BabyLoniaWebApi/api/SignUp";
//                String url="http://babylonia.in/BabyLoniaWebApi/api/TestUpload";
              String url="http://babylonia.in/BabyLoniaWebApi/api/TestUpload";

//        HttpContext localContext = new BasicHttpContext();

        JsonArrayRequest request=new JsonArrayRequest(url,param, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result======", response.toString());
                System.out.println("ResultOut" + param.toString());


                //dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(SignUpActivity.this).addToRequestQueue(request);


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
        finish();
    }

    //converting bitmap to base64 string
    private String getImageString(Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String img = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return img;
    }
}


