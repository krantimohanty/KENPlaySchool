package com.kenplayschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class VideoCallActivity extends AppCompatActivity {

    Button submit, exit;
    String babylonia, admin, admin1;
//    '1E00E42PAEK4LLL'
    EditText userinput, passinput;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mm.android.direct.gdmssphoneLite "));
        intent.setData(Uri.parse("market://details?id=com.mm.android.direct.gdmssphoneLite"));
        startActivity(intent);}

        public void sharedPrefernces() {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
            toEdit = sh_Pref.edit();

//            toEdit.putString("Username", username);
//            toEdit.putString("Password", password);
            toEdit.putString("Name", babylonia);
//            toEdit.putString("SN", '1E00E42PAEK4LLL');
            toEdit.putString("Username", admin);
            toEdit.putString("Password", admin1);
            toEdit.commit(); }


    }

