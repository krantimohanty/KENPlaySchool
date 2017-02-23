package com.kenplayschool;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class EnquiryDetailActivity extends BaseActivity {
    private AppCompatTextView person_name, enquiryType, queries , mobNo, city ;
   private AppCompatButton post;
private AppCompatEditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
toolbar.setTitle("Enquiry Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        person_name = (AppCompatTextView) findViewById(R.id.person_name);
        enquiryType = (AppCompatTextView) findViewById(R.id.enquiryFor);
        queries = (AppCompatTextView) findViewById(R.id.source_enquiry);
        mobNo = (AppCompatTextView) findViewById(R.id.enquiry_phno);
        city = (AppCompatTextView) findViewById(R.id.enquiry_area);
post= (AppCompatButton)findViewById(R.id.btnPost);
        comment =(AppCompatEditText)findViewById(R.id.edit_enquiry_comment) ;
        person_name.setText(getIntent().getStringExtra("person_name"));
        enquiryType.setText(getIntent().getStringExtra("enquiry_for"));
        queries.setText(getIntent().getStringExtra("enquiry_query"));
mobNo.setText(getIntent().getStringExtra("enquiry_phno"));
city.setText(getIntent().getStringExtra("enquiry_area"));
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
        switch (id) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


