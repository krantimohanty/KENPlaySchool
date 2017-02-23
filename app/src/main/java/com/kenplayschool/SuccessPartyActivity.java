package com.kenplayschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class SuccessPartyActivity extends BaseActivity {

    private AppCompatButton btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        btnExit = (AppCompatButton) findViewById(R.id.exit);
        btnExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessPartyActivity.this, EventzActivity.class));
                finish();
            }
        });
    }
}
