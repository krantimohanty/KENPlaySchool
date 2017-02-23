package com.kenplayschool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kenplayschool.data_model.User;
import com.kenplayschool.network_utils.AsynTaskRunner;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import java.util.concurrent.ExecutionException;
//import com.slidingimages.net.NetworkAsynTask;


public class LoginActivity extends BaseActivity {
//    private Button login_button;
//    private TextView registerButton,forgotpwd_button;
//    EditText emailid, pass;
    //private CreateJsonData createJsonData;
    private static Context context;
    //TextView finalResult;
    private String response;
   // private PreferenceData mPreferenceData;
    private AppCompatEditText email_id, passWord;
    private AppCompatButton normalLogin, btnSignUp, btnForget_pass , skkip;
    private ContentLoadingProgressBar progressBar;
    private TextInputLayout inputLayoutEmail, inputLayoutPass;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private User user;
    private SignInButton btnSignIn;
    private AsyncTask<String, String, String> asyncTask;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

       /* try {
            int v = getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
            Log.v("version", ""+v);
            if(v<getResources().getInteger(R.integer.google_play_services_version)){
                Toast.makeText(this, "SERVICE_VERSION_UPDATE_REQUIRED", Toast.LENGTH_LONG).show();

            }
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int state = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

       /* if (state == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
            Toast.makeText(this, "SERVICE_VERSION_UPDATE_REQUIRED", Toast.LENGTH_LONG).show();
            //goAhead();
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(state, this, -1);
            dialog.show();
        }*/

        // User Session Manager Class


//        login_button = (Button) findViewById(R.id.btn_login);
//        emailid = (EditText) findViewById(R.id.editText1);
//        pass = (EditText) findViewById(R.id.editText2);
//        //registerButton = (TextView) findViewById(R.id.btn_signup);
//        forgotpwd_button=(TextView)findViewById(R.id.btn_forgotpwd);

//		login_button = (Button) findViewById(R.id.btn_login);
//		registerButton = (Button) findViewById(R.id.btn_signup);
//		forgotpwd_button = (Button) findViewById(R.id.btn_forgotpwd);
//		skip = (TextView)findViewById(R.id.sk_id);


        final TextView tv = new TextView(this);
//        tv.setText(response);

     /*   registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent in = new Intent(context, RegistrationActivity.class);
                context.startActivity(in);
            }
        });*/
        skkip = (AppCompatButton) findViewById(R.id.btn_skip);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        email_id = (AppCompatEditText) findViewById(R.id.email_id);
        // email_id.setCompoundDrawablesWithIntrinsicBounds(email, null, null, null);
        passWord = (AppCompatEditText) findViewById(R.id.password);
        // password.setCompoundDrawablesWithIntrinsicBounds(pass, null, null, null);
//        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progress);
        skkip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
        normalLogin = (AppCompatButton) findViewById(R.id.login);

        normalLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //sendLoginDataToServer();
//                Intent in = new Intent(LoginActivityOldie.this, HomeActivity.class);
//                startActivity(in);
//
//               String uname = emailid.getText().toString();
//              String password = pass.getText().toString();
                //sendLoginDataToServer();

                // Check below the code ----------->>>>>>>>>>>>>>>>>

                String userName = email_id.getText().toString();
                String password = passWord.getText().toString();
                AsynTaskRunner runner = new AsynTaskRunner();
                asyncTask = runner.execute(userName, password);

                try {
                    String asyncResultText = asyncTask.get();
                    response = asyncResultText.trim();
                } catch (InterruptedException e1) {
                    response = e1.getMessage();
                } catch (ExecutionException e1) {
                    response = e1.getMessage();
                } catch (Exception e1) {
                    response = e1.getMessage();
                }
                tv.setText(response);

                System.out.println("Response=========" + response);

                if (response.contains("Login successful")) {
                    Intent in = new Intent(context, HomeActivity.class);
                    context.startActivity(in);
                    Toast.makeText(context, "Succesfully Logged In", Toast.LENGTH_LONG).show();
                    //textView.setText("Welcome "+userName);
                } else {
                    Toast.makeText(context, "You have entered incorrect email id or password", Toast.LENGTH_LONG).show();
                }
                //tv.setText(response);
                //finalResult.setText(response);
                //result.setText(response);*/
            }

        });


//		registerButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent in = new Intent(context, RegistrationActivity.class);
//				context.startActivity(in);
//			}
//		});
        btnSignUp = (AppCompatButton) findViewById(R.id.sign_up);
        btnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        btnForget_pass = (AppCompatButton) findViewById(R.id.forget_pass);
        btnForget_pass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Force user to fill up the form

                // TODO
                Intent in = new Intent(context, ForgetPassActivity.class);
                context.startActivity(in);
            }
        });
    }

////				sendLoginDataToServer();
//				String userName=emailid.getText().toString();
//				String password  =pass.getText().toString();
//				AsynTaskRunner runner=new AsynTaskRunner();
//				asyncTask=runner.execute(userName,password);
//
//				try {
//				     String asyncResultText=asyncTask.get();
//				     response = asyncResultText.trim();
//				    } catch (InterruptedException e1) {
//				     response = e1.getMessage();
//				    } catch (ExecutionException e1) {
//				     response = e1.getMessage();
//				    } catch (Exception e1) {
//				     response = e1.getMessage();
//				    }
//				tv.setText(response);
//
//				if(response.contains("Login successful"))
//				{
//					 Intent in = new Intent(context,HomeActivity.class);
//             	     context.startActivity(in);
//             	     Toast.makeText(context, "Login Successfully Completed",Toast.LENGTH_LONG).show();
//				}else{
//					Toast.makeText(context, "Login Invalid",Toast.LENGTH_LONG).show();
//				}
//				  //tv.setText(response);
//				    //finalResult.setText(response);
//				//result.setText(response);
//				   }
//				  });

//    public static Context getAppContext() {
//        return LoginActivityOldie.context;
//    }

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


//	public static void updateUI(String result) {
//		Log.v("updateUI loginactivity=", "" + result);
//		if (result.equals(HotelConstants.SUCCESS)) {
//			((LoginActivityOldie) context).finish();
//
//			context.startActivity(new Intent(context, HomeActivity.class));
//		} else {
//
//			// show error dialog
//
//			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//		}
//
//	}


}






