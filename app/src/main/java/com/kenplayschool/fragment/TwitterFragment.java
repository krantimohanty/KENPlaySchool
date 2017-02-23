package com.kenplayschool.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.kenplayschool.R;
import com.kenplayschool.custom_webview.UnsupportedProtcolInterceptor;
import com.kenplayschool.custom_webview.WebContainerView;
import com.kenplayschool.custom_webview.WebViewStateListener;
import com.kenplayschool.network_utils.AppServiceUrl;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFragment extends Fragment {

    private View rootView;
    private WebContainerView webContainerView;

    public TwitterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_twitter, container, false);
        webContainerView = (WebContainerView) rootView.findViewById(R.id.webview_view);
        webContainerView.addLoadingInterceptor(new UnsupportedProtcolInterceptor(getActivity()));
        webContainerView.loadUrl(AppServiceUrl.twitter_url);
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

        return rootView;
    }

}
