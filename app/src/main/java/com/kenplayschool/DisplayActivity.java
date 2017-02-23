package com.kenplayschool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayActivity extends BaseActivity {

         Button exit;
         Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

      exit=(Button)findViewById(R.id.btn_exit);


       exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),EventzActivity.class);
               /* intent.putExtra("flag", "news");*/
                startActivity(intent);
                finish();
            }
        });
    }
}
