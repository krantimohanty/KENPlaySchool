package com.kenplayschool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kenplayschool.app_adapter.SearchAdapter;
import com.kenplayschool.app_search_util.SharedPreference;
import com.kenplayschool.app_search_util.Utils;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.app_util.FragmentCommunicator;
import com.kenplayschool.fragment.EventzFragment;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.network_utils.AppServiceUrl;
import com.kenplayschool.network_utils.ServiceCalls;
import com.kenplayschool.network_utils.VolleySingleton;
import com.joanzapata.iconify.IconDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventzActivity extends BaseActivity {

    public FragmentCommunicator fragmentCommunicator;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppCompatTextView ui_notification_num = null;
    private int notification_count = 0;
    private String type;
    ArrayList<String> searchList = new ArrayList<>();
    public String pageFrom = "";
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    SearchAdapter searchAdapter;
    ListView listSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event List");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        Bundle extras = getIntent().getExtras();   //check the bundel
        if (extras != null) {
            if (extras.containsKey("pageFrom")) {
                pageFrom = extras.getString("pageFrom").toString();
                if (pageFrom.equalsIgnoreCase("Latest"))
                    viewPager.setCurrentItem(0);
                else if (pageFrom.equalsIgnoreCase("Popular"))
                    viewPager.setCurrentItem(1);
                else if (pageFrom.equalsIgnoreCase("Sectoral"))
                    viewPager.setCurrentItem(2);

            }
        }


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        // Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
                        type = String.valueOf(1);
                        break;
                    case 1:
                        type = String.valueOf(2);
                        break;

                }
            }

            @Override
            public void onPageSelected(int position) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                supportInvalidateOptionsMenu();
            }
        });

        //check gcm is generated
        if (TextUtils.isEmpty(CustomPreference.with(EventzActivity.this).getString("GCM_ID", ""))) {

        } else {
            //check gcm is send to server with user id
            if (TextUtils.isEmpty(CustomPreference.with(EventzActivity.this).getString("REGD_GCM_UID", ""))) {
                ServiceCalls.postDeviceDetails(EventzActivity.this);
            }

        }

    }

    private void setupViewPager(ViewPager viewPager) {
        {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new EventzFragment(), "Latest");
//            adapter.addFragment(new EventzFragment() {
////                @Override
//                public void passDataToFragment(String someValue) {
//
//                }
//            }, "Latest");
 //           adapter.addFragment(new PopularFragment(), "Popular");
//            adapter.addFragment(new SectoralFragment(), "Sectoral");
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


   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //search
        searchView.setMenuItem(menu.findItem(R.id.action_search).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_search)
                        .colorRes(R.color.color_white)
                        .actionBarSize()));

        //notification
        MenuItem item = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(item, R.layout.menu_badge_view);
        View view = MenuItemCompat.getActionView(item);
        ui_notification_num = (AppCompatTextView) view.findViewById(R.id.notification_count);
        ui_notification_num.setVisibility(View.GONE);
        // new GetUpcomingWish().execute();
        //calling wish inbox count service
        //getWishInboxCount();
        //int totalRecords = 0;
        //updateWishCount(notification_count);
        new MyMenuItemStuffListener(view, "Notification") {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventzActivity.this, NotificationActivity.class));
//                finish();
            }
        };
        return true;
    }*/

    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //search
        menu.findItem(R.id.action_search).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_search)
                        .colorRes(R.color.color_white)
                        .sizeDp(20));

        int pageNum = viewPager.getCurrentItem();
        if (pageNum == 2) {
            menu.findItem(R.id.action_search).setVisible(false);
        } else if (pageNum == 0) {
            menu.findItem(R.id.action_search).setVisible(true);
            type = String.valueOf(1);
            searchList = ServiceCalls.getSearchFeeds(EventzActivity.this, "", type, CustomPreference.with(EventzActivity.this).getInt("user_id", 0), "", "");
        } else if (pageNum == 1) {
            menu.findItem(R.id.action_search).setVisible(true);
            searchList = ServiceCalls.getSearchFeeds(EventzActivity.this, "", "2", CustomPreference.with(EventzActivity.this).getInt("user_id", 0), "", "");

        }

        //notification
       /* MenuItem item = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(item, R.layout.menu_badge_view);
        View view = MenuItemCompat.getActionView(item);
        ui_notification_num = (AppCompatTextView) view.findViewById(R.id.notification_count);
        ui_notification_num.setVisibility(View.GONE);
        // new GetUpcomingWish().execute();
        //calling wish inbox count service
        //getWishInboxCount();
        //int totalRecords = 0;
        //updateWishCount(notification_count);
        new MyMenuItemStuffListener(view, "Notification") {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventzActivity.this, NotificationActivity.class));
//                finish();
            }
        };*/
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            loadToolBarSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //update notification count
    public void updateWishCount(final int _count) {
        notification_count = _count;
        //final int[] totalRecords = {0};
        if (ui_notification_num == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (_count == 0)
                    ui_notification_num.setVisibility(View.VISIBLE);
                else {
                    ui_notification_num.setVisibility(View.VISIBLE);
                    ui_notification_num.setText(Integer.toString(_count));
                }
            }
        });
    }

  /*  public void getNotification() {
        JSONObject params = new JSONObject();
        try {
            params.put("method", "getNotifications");
            params.put("user_id", "0");
            params.put("row_id", "0");
            params.put("page_count", "10");//for newNotification  news
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Result=====", response.toString());
                try {
                    String strCount = response.getString("totalNotifications");
                    if (strCount.equals("")) {
                        ui_notification_num.setVisibility(View.GONE);
                    } else {
                        ui_notification_num.setVisibility(View.GONE);
                        //updateWishCount(Integer.parseInt(strCount));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);

    }*/

    /*private Context getActivity() {
        return getApplicationContext();
    }*/


    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        abstract public void onClick(View v);

        @Override
        public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }

    public void loadToolBarSearch() {

        //final EventzFragment latestFragment = (EventzFragment)getSupportFragmentManager().findFragmentByTag("latestFragment");


        ArrayList<String> countryStored = SharedPreference.loadList(EventzActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES);
        ArrayList<String> imgLinks = SharedPreference.loadList(EventzActivity.this, Utils.PREFS_IMG, Utils.KEY_IMGLINKS);

        View view = EventzActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final AppCompatTextView searchId = (AppCompatTextView) view.findViewById(R.id.search_id);
        final AppCompatEditText edtToolSearch = (AppCompatEditText) view.findViewById(R.id.edt_tool_search);
        listSearch = (ListView) view.findViewById(R.id.list_search);
        final AppCompatTextView txtEmpty = (AppCompatTextView) view.findViewById(R.id.txt_empty);

        Utils.setListViewHeightBasedOnChildren(listSearch);

        edtToolSearch.setHint("Search in events");

        final Dialog toolbarSearchDialog = new Dialog(EventzActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Log.d("imgLinks", img.toString());

        countryStored = (countryStored != null && countryStored.size() > 0) ? countryStored : new ArrayList<String>();
        try {
            searchAdapter = new SearchAdapter(EventzActivity.this, countryStored, id, false);
            listSearch.setVisibility(View.VISIBLE);
            listSearch.setAdapter(searchAdapter);
        } catch (Exception e) {

        }


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String country = String.valueOf(adapterView.getItemAtPosition(position));
                SharedPreference.addList(EventzActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, country);
                SharedPreference.addList(EventzActivity.this, Utils.PREFS_IMG, Utils.KEY_IMGLINKS, country);
                edtToolSearch.setText(country);
                listSearch.setVisibility(View.VISIBLE);
                final AppCompatTextView searchId = (AppCompatTextView) view.findViewById(R.id.search_id);
                /*startActivity(new Intent(EventzActivity.this, EventzDetailActivity.class)
                        .putExtra("post_id", searchId.getText().toString())
                        .putExtra("type", type));*/
            }
        });

        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // String[] country = EventzActivity.this.getResources().getStringArray(R.array.countries_array);
                // mCountries = new ArrayList<String>(Arrays.asList(country));
                //searchList = ServiceCalls.getSearchFeeds(EventzActivity.this, "", type, CustomPreference.with(EventzActivity.this).getInt("user_id", 0), "", String.valueOf(s));

                searchList = getSearchFeed(EventzActivity.this, "", type, CustomPreference.with(EventzActivity.this).getInt("user_id", 0), "", String.valueOf(s));
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(searchList, true);
                fragmentCommunicator.passDataToFragment(edtToolSearch.getText().toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<String>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < searchList.size(); i++) {


                        if (searchList.get(i).toLowerCase().startsWith(s.toString().trim().toLowerCase())) {

                            filterList.add(searchList.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtToolSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    //Bundle bundle = new Bundle();
                   /* if (type.equals("1")) {
                        bundle.putString("search_text", v.getText().toString());
                        //set Fragmentclass Arguments
                        EventzFragment latestFragment = new EventzFragment();
                        latestFragment.setArguments(bundle);
                    } else if (type.equals("2")) {
                        bundle.putString("search_text", v.getText().toString());
                        //set Fragmentclass Arguments
                        PopularFragment popularFragment = new PopularFragment();
                        popularFragment.setArguments(bundle);
                    }*/

                    fragmentCommunicator.passDataToFragment(edtToolSearch.getText().toString());


                }
                return false;
            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

    }


    //get Search feeds
    //get search data
    public ArrayList<String> getSearchFeed(final Context context, String row_id, final String typ, final int user_id, final String sector_id, final String search_text) {
        final ArrayList<String> searchTitle = new ArrayList<>();
        final ArrayList<String> idList = new ArrayList<>();
        final ArrayList<String> imgLinks = new ArrayList<>();
        try {
            JSONObject params = new JSONObject();
            try {
                params.put("method", "getNews");
                params.put("user_id", user_id);
                params.put("type", typ);//for latest news
                params.put("sector_id", sector_id);
                params.put("search_text", search_text);
                params.put("row_id", 1);
                params.put("page_count", 100);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("search_params", params + "");
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppServiceUrl.url, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // TODO Auto-generated method stub
                    Log.d("searesult", response.toString());
                    try {

                        JSONArray jsonArray = response.getJSONArray("getNewsResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            searchTitle.add(jsonArray.getJSONObject(i).getString("title"));
                            idList.add(jsonArray.getJSONObject(i).getString("post_id"));
                            imgLinks.add(jsonArray.getJSONObject(i).getString("photo"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (NullPointerException e) {

                    }

                    list = searchTitle;
                    id = idList;
                    img = imgLinks;

                    searchAdapter = new SearchAdapter(EventzActivity.this, searchTitle, idList, false);
                    listSearch.setVisibility(View.VISIBLE);
                    listSearch.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();


                    listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            String country = String.valueOf(adapterView.getItemAtPosition(position));
                            SharedPreference.addList(EventzActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, country);
                            SharedPreference.addList(EventzActivity.this, Utils.PREFS_IMG, Utils.KEY_IMGLINKS, country);
                            //edtToolSearch.setText(country);
                            //listSearch.setVisibility(View.GONE);
                            startActivity(new Intent(EventzActivity.this, EventzDetailActivity.class)
                                    .putExtra("post_id", idList.get(position))
                                    .putExtra("type", type));
                        }
                    });
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("response", "error" + error.toString());
                    //latestFeedModel.clear();
                    //latestFeedsAdapter1.notifyDataSetChanged();

                    Snackbar.make(((AppCompatActivity) context).findViewById(android.R.id.content), "Something went wrong, try again!!", Snackbar.LENGTH_LONG)
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
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
        } catch (Exception e) {

        }

        return list;
    }

   /* @Override
    protected void onResume() {
        super.onResume();
       reloadActivity();
    }*/
}
