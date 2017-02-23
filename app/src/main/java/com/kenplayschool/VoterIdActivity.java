package com.kenplayschool;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.kenplayschool.custom_webview.UnsupportedProtcolInterceptor;
import com.kenplayschool.custom_webview.WebContainerView;
import com.kenplayschool.custom_webview.WebViewStateListener;

public class VoterIdActivity extends BaseActivity {

    //private RecyclerView mRecyclerView;
    private WebContainerView webContainerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        webContainerView = (WebContainerView) findViewById(R.id.webview_view);
        webContainerView.addLoadingInterceptor(new UnsupportedProtcolInterceptor(this));
//        webContainerView.loadUrl(AppServiceUrl.voterId_url);
        webContainerView.addOnWebViewStateListener(new WebViewStateListener() {
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
        });

    }

    @Override
    public void onBackPressed() {
        if (webContainerView.canGoBack()) {
            webContainerView.goBack();
        } else {
            finish();
        }
    }
}
