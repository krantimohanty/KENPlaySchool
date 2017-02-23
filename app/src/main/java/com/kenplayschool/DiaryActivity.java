package com.kenplayschool;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.kenplayschool.fragment.CalendarMainFragment;

import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends BaseActivity {


//    Telerik Calender
private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new CalendarMainFragment(), "Calendar Event");
//            adapter.addFragment(new EnquiryFragment(), "Past Events");
            viewPager.setAdapter(adapter);
        }
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {


            // Log.e("ResultPosition", "getItem: "+position );
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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

// Telerik Calender controls ends here

//    private List<HolidayListModel> holidayList = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private HolidayListAdapter mAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_diary_list);
//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
//        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Diary Activity");
//        toolbar.setTitleTextColor(Color.WHITE);
//    }
//    public void onAddEventClicked(View view){
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType("vnd.android.cursor.item/event");
//
//        Calendar cal = Calendar.getInstance();
//        long startTime = cal.getTimeInMillis();
//        long endTime = cal.getTimeInMillis()  + 60 * 60 * 1000;
//
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//
//        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
//        intent.putExtra(CalendarContract.Events.DESCRIPTION,  "This is a sample description");
//        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
//        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
//
//        startActivity(intent);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id) {
//            case android.R.id.home:
//                finish();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//}
//
