package com.kenplayschool;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kenplayschool.app_adapter.HolidayListAdapter;
import com.kenplayschool.data_model.HolidayListModel;

import java.util.ArrayList;
import java.util.List;

public class HolidayListActivity extends BaseActivity {
    private List<HolidayListModel> holidayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HolidayListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Holiday List 2017");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new HolidayListAdapter(holidayList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
////                Movie movie = movieList.get(position);
////                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        prepareHolidayData();
    }

    private void prepareHolidayData() {
        HolidayListModel holidaylist = new HolidayListModel("26th", "Thursday", "Republic Day","January");
        holidayList.add(holidaylist);
       holidaylist = new HolidayListModel("1st", "Wednesday", "Vasant Panchami", "February");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("24th", "Friday", "Maha Shivaratri", "March");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("13th", "Monday", "Holi", "March");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("5th", "Wednesday", "Rama Navami", "April");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("10th", "Wednesday", "Buddha Purnima", "May");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("26th", "Monday", "Eid al-Fitr", "June");
        holidayList.add(holidaylist);
//        holidaylist = new HolidayListModel("7th", "Monday", "Raksha Bandhan", "August");
//        holidayList.add(holidaylist);
//        holidaylist = new HolidayListModel("14th", "Monday", "Janmashtami", "August");
//        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("15th", "Tuesday", "Independence Day", "August");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("30th", "Saturday", "Dussehra", "September");
        holidayList.add(holidaylist);
//        holidaylist = new HolidayListModel("2nd", "Monday", "Gandhi Jayanti", "October");
//        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("19th", "Thursday", "Diwali, Lakshmi Puja", "October");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("14th", "Tuesday", "Nehru Jayanti", "November");
        holidayList.add(holidaylist);
        holidaylist = new HolidayListModel("25th", "Monday", "Christmas Day", "December");
        holidayList.add(holidaylist);
    }
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
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }

        return super.onOptionsItemSelected(item);
    }

}
