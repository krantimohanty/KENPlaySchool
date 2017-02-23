package com.kenplayschool;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.kenplayschool.fragment.ListViewSlideFragment;

import java.util.ArrayList;
import java.util.List;

public class AboutUsActivity extends BaseActivity {

//    private RecyclerView mRecyclerView;
//    private WebView webContainerView;
//    private WebContainerView webContainerView;
//    private static String URL = "";
//    private static String type = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

    // Telerik Slide Controls-- Suchismita

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new ListViewSlideFragment(), "About Us");
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

// Telerik Slide controls ends here



        /*webContainerView = (WebView) findViewById(R.id.webview_view);
        //webContainerView.addLoadingInterceptor(new UnsupportedProtcolInterceptor(this));
        webContainerView.getSettings().setJavaScriptEnabled(true);
        Bundle extras = getIntent().getExtras();   //check the bundel
        if (extras != null) {
            if (extras.containsKey("type")) {
                type = extras.getString("type").toString();
                if (extras.getString("type").toString().equalsIgnoreCase("AboutUsActivity")) {
                    URL = AppServiceUrl.manifesto_url;
                    getSupportActionBar().setTitle("Manifesto");
                } else {
                    URL = AppServiceUrl.donate_url + CustomPreference.with(AboutUsActivity.this).getString("user_id", "");
                    getSupportActionBar().setTitle("Online Donation");
                }
            }
        }

        Log.i("rtagffff", "loginResult: " + URL);
        webContainerView.setWebChromeClient(new WebChromeClient());
        final ProgressDialog[] progressDialog = {new ProgressDialog(AboutUsActivity.this)};
        progressDialog[0].setMessage("Loading...");
        progressDialog[0].show();
        webContainerView.setWebViewClient(new WebViewClient() {

            *//* //Show loader on url load
             public void onLoadResource (WebView view, String url) {
                 if (progressDialog[0] == null) {
                     Log.i("errrrrrrr", "loginResult33333333333: ");
                     // in standard case YourActivity.this
                     progressDialog[0] = new ProgressDialog(AboutUsActivity.this);
                     progressDialog[0].setMessage("Loading...");
                     progressDialog[0].show();
                 }
             }*//*
            @Override
            public void onPageFinished(WebView view, String url) {

                view.loadUrl("javascript:(function() { " + "document.getElementsByTagName('header')[0].style.display='none';" +
                        "document.getElementsByTagName('body')[0].style.paddingTop='0px';" +
                        "document.getElementsByTagName('footer')[0].style.display='none'; " +
                        "document.getElementsByClassName('col-sm-4')[0].style.display='none'; " +
                        "document.getElementsByClassName('navigation-sec')[0].style.display='none'; " +
                        "document.getElementsByClassName('link-section')[0].style.display='none';" + "})()");
                try {
                    if (progressDialog[0].isShowing() || progressDialog[0] != null) {
                        Log.i("errrrrrrr", "loginResult: ");
                        progressDialog[0].dismiss();
                        progressDialog[0] = null; *//*** Add ***//*

                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

        });*/

        //class="link-section"
        // refreshWebView();
//            webContainerView = (WebContainerView) findViewById(R.id.webview_view);
//            webContainerView.addLoadingInterceptor(new UnsupportedProtcolInterceptor(AboutUsActivity.this));
//            webContainerView.loadUrl(AppServiceUrl.website_url);
//            webContainerView.getSettings().setSupportZoom(false);
//            webContainerView.addOnWebViewStateListener(new WebViewStateListener() {
//            @Override
//            public void onStartLoading(String url, Bitmap favicon) {
//
//            }
//
//            @Override
//            public void onError(int errorCode, String description, String failingUrl) {
//
//            }
//
//            @Override
//            public void onFinishLoaded(String loadedUrl) {
//
//            }
//
//            @Override
//            public void onProgressChanged(WebView view, int progress) {
//
//            }
//        });
//
//    }

    /*private void refreshWebView() {
        webContainerView.loadUrl(URL);
    }*/




       /* webContainerView.addOnWebViewStateListener(new WebViewStateListener() {
            @Override
            public void onStartLoading(String url, Bitmap favicon) {

            }

            @Override
            public void onError(int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onFinishLoaded(String loadedUrl) {


            }

            @Override
            public void onProgressChanged(WebView view, int progress) {

            }
        });*/




