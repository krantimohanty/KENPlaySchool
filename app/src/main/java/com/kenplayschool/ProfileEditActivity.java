package com.kenplayschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kenplayschool.network_utils.JsonArrayRequest;
import com.kenplayschool.network_utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditActivity extends AppCompatActivity {
    EditText editname, editemail, editpassword,editmobileno ;
    Button button15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
        editname = (EditText) findViewById(R.id.txtname);
        editemail = (EditText) findViewById(R.id.txtemail);
        editpassword = (EditText) findViewById(R.id.txtpass);
        editmobileno= (EditText) findViewById(R.id.txtmobileno);

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editprofile(editname.getText().toString(),editemail.getText().toString(),
                        editpassword.getText().toString(),editmobileno.getText().toString());

            }


        });


    }

    private void Editprofile(String name, String email, String pass, String mobile) {

        final JSONObject param=new JSONObject();
        try {
            param.put("",name);
            param.put("",email);
            param.put("",pass);
            param.put("",mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

  String url="";
        JsonArrayRequest jsonReq=new JsonArrayRequest(null, param, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Result Out",response.toString());
                Log.d("Param Result",param.toString());

                Toast.makeText(ProfileEditActivity.this,"Y Have Sucessful Profile Edit",Toast
                        .LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonReq);


    }

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
}