
        package com.kenplayschool;

        import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.app_util.Custom_app_util;
import com.kenplayschool.app_util.GooglePlusLoginUtils;
import com.kenplayschool.app_util.ValidationUtil;
import com.kenplayschool.data_model.User;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.JsonArrayRequest;
import com.kenplayschool.network_utils.VolleySingleton;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

        public class LoginActivityOldie extends BaseActivity implements GooglePlusLoginUtils.GPlusLoginStatus {

            private GooglePlusLoginUtils gLogin;
            private AppCompatEditText email_id, password;
            private AppCompatButton normalLogin, btnSignUp, btnForget_pass , skkip;
            private ContentLoadingProgressBar progressBar;
            private TextInputLayout inputLayoutEmail, inputLayoutPass;
            JSONObject jresponse;
            private LoginButton loginButton;
            private CallbackManager callbackManager;
            private User user;
            private SignInButton btnSignIn;
            // Google client to interact with Google API
            private GoogleApiClient mGoogleApiClient;
            //google plus
            private static final int RC_SIGN_IN = 0;
            // Logcat tag
            private static final String TAG = "LoginActivityOldie";

            public String pageFrom = "";
            public String type = "";

            // Profile pic image size in pixels
            private static final int PROFILE_PIC_SIZE = 400;

            /**
             * A flag indicating that a PendingIntent is in progress and prevents us
             * from starting further intents.
             */
            private boolean mIntentInProgress;

            private boolean mSignInClicked;

            private ConnectionResult mConnectionResult;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                FacebookSdk.sdkInitialize(getApplicationContext()); //Facebook Login
                callbackManager = CallbackManager.Factory.create();
                setContentView(R.layout.activity_login_oldie);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

                Bundle extras = getIntent().getExtras();   //check the bundel
                if (extras != null) {
                    if (extras.containsKey("pageFrom")) {
                        pageFrom = extras.getString("pageFrom").toString();
                    }
                    if (extras.containsKey("type")) {
                        type = extras.getString("type").toString();
                    }
                }

                //IconDrawable email = new IconDrawable(LoginActivityOldie.this, IcoMoonIcons.ic_email).colorRes(R.color.color_dark).sizeDp(16);
                // IconDrawable pass = new IconDrawable(LoginActivityOldie.this, IcoMoonIcons.ic_password).colorRes(R.color.color_dark).sizeDp(18);
                skkip = (AppCompatButton) findViewById(R.id.skip);
                inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
                inputLayoutPass = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
                email_id = (AppCompatEditText) findViewById(R.id.email_id);
                // email_id.setCompoundDrawablesWithIntrinsicBounds(email, null, null, null);
                password = (AppCompatEditText) findViewById(R.id.password);
                // password.setCompoundDrawablesWithIntrinsicBounds(pass, null, null, null);
                progressBar = (ContentLoadingProgressBar) findViewById(R.id.progress);
                skkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivityOldie.this, HomeActivity.class));
                    }
                });
                normalLogin = (AppCompatButton) findViewById(R.id.login);
                normalLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email_id.getText().toString().equals("")) {
                            // inputLayoutEmail.setErrorEnabled(true);
                            //inputLayoutEmail.setError("Invalid email id");
                            Custom_app_util.customSnackbar("Invalid email id", LoginActivityOldie.this, true, "");
                            email_id.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                        } else if (!ValidationUtil.isValidPassword(password.getText().toString())) {
                            Custom_app_util.customSnackbar("Invalid password", LoginActivityOldie.this, true, "");
                            //password.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                        } else {
                            //inputLayoutEmail.setErrorEnabled(false);
                            //inputLayoutPass.setErrorEnabled(false);
                            emailLogin(email_id.getText().toString(),password.getText().toString());
                            Toast.makeText(LoginActivityOldie.this, "Successfully Logged In", Toast
                                    .LENGTH_LONG).show();

                            Intent intent=new Intent(LoginActivityOldie.this,HomeActivity.class);
                            startActivity(intent);
                            email_id.setText("");
                            password.setText("");
                        }
                    }
                });


                btnSignUp = (AppCompatButton) findViewById(R.id.sign_up);
                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivityOldie.this, SignUpActivity.class));
                    }
                });

                btnForget_pass = (AppCompatButton) findViewById(R.id.forget_pass);
                btnForget_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivityOldie.this, ForgetPassActivity.class));
                    }
                });
            }

            //FB Login
//       loginButton = (LoginButton) findViewById(R.id.fb_login);
//        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                Log.i("rtag", "loginResult: " + loginResult);
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                if (response.getError() != null) {
//                                    // handle error
//                                    System.out.println("ERROR");
//                                } else {
//                                    System.out.println("Success");
//                                    try {
//                                        user = new User();
//                                        String jsonresult = String.valueOf(object);
//                                        System.out.println("JSON Result" + jsonresult);
//                                        //Toast.makeText(LoginActivityOldie.this, jsonresult + "", Toast.LENGTH_LONG).show();
//                                        user.email = object.getString("email").toString();
//                                        user.facebookID = object.getString("id").toString();
//                                        user.name = object.getString("name").toString();
//                                        user.fname = object.getString("first_name").toString();
//                                        user.lname = object.getString("last_name").toString();
//                                        user.gender = object.getString("gender").toString();
//                                        user.userPhoto = "https://graph.facebook.com/" + user.facebookID + "/picture?type=large";
//
//                                        //  AppSharedPreference.setCurrentUser(user, LoginActivityOldie.this);
//                                        //String urlProfilePic = "https://graph.facebook.com/" + user.facebookID + "/picture?type=large";
//                                        // String device_id="";
//                                        fbLogin(user);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    //Toast.makeText(LoginActivityOldie.this, "welcome " + user.name + "\n" + user.email + "\n" + user.facebookID + "\n" + user.gender, Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                        });
//
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,first_name,last_name,email,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.i("rtag", "cancel:");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Log.i("rtag", "exception:" + exception);
//            }
//        });
//
//        gLogin = new GooglePlusLoginUtils(this, R.id.btn_sign_in);
//        gLogin.setLoginStatus(this);
//    }
//
//    protected void onStart() {
//        super.onStart();
//        gLogin.connect();
//    }
//
//    protected void onStop() {
//        super.onStop();
//        gLogin.disconnect();
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        gLogin.onActivityResult(requestCode, resultCode, data);
//    }



            //Email Id Login // Kranti
            private void emailLogin(String emailId, String pass) {

//                progressBar.setVisibility(View.VISIBLE);
                final JSONObject params = new JSONObject();
                try {

                    params.put("UserName", emailId);
                    params.put("Password", pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("params", params + "");


                 // final  String url = "http://192.168.1.10:8088/api/Login";
//                  final  String url = "http://babylonia.in/BabyLoniaWebApi/api/Login";
                final  String url = "http://babylonia.in/BabyLoniaWebApi/api/Login";
                JsonArrayRequest jsonArrReq=new JsonArrayRequest(url, params, new Response
                        .Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("ResultResponse",response.toString());

                        Log.d("ResultOutput", params.toString());


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

               /* JsonArrayRequest jsonArrReq=new JsonArrayRequest
                        (Request.Method.POST,url, params, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                Log.d("ResultResponse",response.toString());
                                Log.d("ResultOutput", params.toString());

                                Intent intent=new Intent(LoginActivityOldie.this,MenuActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                VolleyLog.d("Login request", "Error: " + error.getMessage());
                                Log.d("Volley Error:", "Volley Error:" + error.getMessage());
                            }
                        });*/

                jsonArrReq.setRetryPolicy(new DefaultRetryPolicy(
                        90000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrReq);

       /* final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result===",response.toString());

                  //RESULT_OK=response.equals("OK"){
                      if(response.equals("OK")){
                          Toast.makeText(LoginActivityOldie.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                }
                try {
                    if(response==null) {
                        //pDialog.hide();
                    }
                    for (int i = 0; i < response.length(); i++) {
                        jresponse = response.getJSONObject(i);
                    }
                    //String service_response = response.getString("SvcTypeDsc");
                    Toast.makeText(LoginActivityOldie.this.getApplicationContext(), "services" + jresponse, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("soservices", "sos" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Login request", "Error: " + error.getMessage());
                Log.d("Volley Error:", "Volley Error:" + error.getMessage());
                Toast.makeText(LoginActivityOldie.this, "Unable to connect to server, try again later", Toast.LENGTH_LONG).show();
                //pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("UserName","Admin");
                params.put("Password","Test@1234");
                return super.getParams();
            }

            @Override
            public int getMethod() {
                try {
                    getParams();
                } catch (AuthFailureError authFailureError) {
                    authFailureError.printStackTrace();
                }
                return super.getMethod();
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(LoginActivityOldie.this).addToRequestQueue(jsonObjReq);
*/
       /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                //Log.d("result=====", response.toString());

                if (response.toString().contains("Invalid username or password")) {
                    Snackbar.make(findViewById(android.R.id.content), "Invalid username or password", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else if (response.toString().contains("Some inputs are missing. Please check.")) {
                    Snackbar.make(findViewById(android.R.id.content), "Some inputs are missing. Please check.", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else {

                    Toast.makeText(LoginActivityOldie.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                    try {
                        JSONArray jsonArray = response.getJSONArray("Babylonia_LoginResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            CustomPreference.with(LoginActivityOldie.this).save("user_id", jsonArray.getJSONObject(i).getString("user_id"));
                            CustomPreference.with(LoginActivityOldie.this).save("type", "N");
                            ServiceCalls.getUserInfo(LoginActivityOldie.this, String.valueOf(Integer.parseInt(jsonArray.getJSONObject(i).getString("user_id"))), progressBar, null, null, null, "");*/
                           /* if (pageFrom.equalsIgnoreCase("BaseActivity")) {
                                startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                finish();
                            } else if (pageFrom.equalsIgnoreCase("latest")) {
                                startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class).putExtra("pageFrom", "latest"));
                                finish();
                            } else if (pageFrom.equalsIgnoreCase("Popular")) {
                                startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class).putExtra("pageFrom", "Popular"));
                                finish();
                            } else if (pageFrom.equalsIgnoreCase("Sectoral")) {
                                startActivity(new Intent(LoginActivityOldie.this, SectorialDetails.class)
                                        .putExtra("pageFrom", "Sectoral")
                                        .putExtra("sector_id", CustomPreference.with(LoginActivityOldie.this).getString("sector_id", "")));
                                finish();
                            } else if (pageFrom.equalsIgnoreCase("EventzDetailActivity")) {
                                startActivity(new Intent(LoginActivityOldie.this, EventzDetailActivity.class)
                                        .putExtra("flag", "news")
                                        .putExtra("type", type)
                                        .putExtra("post_id", CustomPreference.with(LoginActivityOldie.this).getString("post_id", "")));
                                finish();
                            } else {
                                startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                finish();
                            }*/

                            /*if (getIntent().getStringExtra("activityFrom").equalsIgnoreCase("BaseActivity")) {
                                // BaseActivity.baseActivity.finish();
                                Intent intent = new Intent(LoginActivityOldie.this, MenuActivity.class);
                                setResult(2, intent);
                                finish();//finishing activity
                            }*/
                            /*Intent intent = new Intent();
                            setResult(2, intent);
                            finish();*///finishing activity
                       /* }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error" + error.toString());
                //content.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);*/
            }


            //login using fb
            public void fbLogin(final User user) {

                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                JSONObject params = new JSONObject();

                try {
                    params.put("method", "FB_login");
                    params.put("Fbid", user.facebookID);
                    params.put("name", user.name);
                    params.put("email", user.email);
                    params.put("photopath", user.userPhoto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("rtag", params.toString());
                pDialog.show();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.login_url, params,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("App", response.toString());
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = response.getJSONArray("FB_LoginResult");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        CustomPreference.with(LoginActivityOldie.this).save("user_id", jsonArray.getJSONObject(i).getString("user_id"));
                               /* startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                finish();*/

                                        if (pageFrom.equalsIgnoreCase("BaseActivity")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("latest")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class).putExtra("pageFrom", "latest"));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("Popular")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class).putExtra("pageFrom", "Popular"));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("Sectoral")) {
                                            startActivity(new Intent(LoginActivityOldie.this, SectorialDetails.class)
                                                    .putExtra("pageFrom", "Sectoral")
                                                    .putExtra("sector_id", CustomPreference.with(LoginActivityOldie.this).getString("sector_id", "")));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("EventzDetailActivity")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzDetailActivity.class)
                                                    .putExtra("flag", "news")
                                                    .putExtra("type", type)
                                                    .putExtra("post_id", CustomPreference.with(LoginActivityOldie.this).getString("post_id", "")));
                                            finish();
                                        } else {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                            finish();
                                        }


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
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

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
            }

            //login using Google
            public void googleLogin(final User user) {

                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                JSONObject params = new JSONObject();

                try {
                    params.put("method", "G_Login");
                    params.put("gid", user.gPlusId);
                    params.put("name", user.name);
                    params.put("email", user.email);
                    params.put("photopath", user.userPhoto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("rtag", params.toString());
                pDialog.show();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.login_url, params,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("App", response.toString());
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = response.getJSONArray("G_LoginResult");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        CustomPreference.with(LoginActivityOldie.this).save("user_id", jsonArray.getJSONObject(i).getString("user_id"));
                               /* startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                finish();*/

                                        if (pageFrom.equalsIgnoreCase("BaseActivity")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("latest")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class).putExtra("pageFrom", "latest"));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("Popular")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class).putExtra("pageFrom", "Popular"));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("Sectoral")) {
                                            startActivity(new Intent(LoginActivityOldie.this, SectorialDetails.class)
                                                    .putExtra("pageFrom", "Sectoral")
                                                    .putExtra("sector_id", CustomPreference.with(LoginActivityOldie.this).getString("sector_id", "")));
                                            finish();
                                        } else if (pageFrom.equalsIgnoreCase("EventzDetailActivity")) {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzDetailActivity.class)
                                                    .putExtra("flag", "news")
                                                    .putExtra("type", type)
                                                    .putExtra("post_id", CustomPreference.with(LoginActivityOldie.this).getString("post_id", "")));
                                            finish();
                                        } else {
                                            startActivity(new Intent(LoginActivityOldie.this, EventzActivity.class));
                                            finish();
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
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

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
            }

            @Override
            public void OnSuccessGPlusLogin(Bundle profile) {
                user = new User();
                user.name = profile.getString(GooglePlusLoginUtils.NAME);
                user.email = profile.getString(GooglePlusLoginUtils.EMAIL);
                user.userPhoto = profile.getString(GooglePlusLoginUtils.PHOTO);
                user.gPlusId = profile.getString(GooglePlusLoginUtils.GID);
                googleLogin(user);
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

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }
        }
