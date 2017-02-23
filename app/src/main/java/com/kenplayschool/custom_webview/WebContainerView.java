package com.kenplayschool.custom_webview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.kenplayschool.R;
import java.util.Map;

public class WebContainerView extends RelativeLayout {

    private AppWebView webView;

    private ProgressBar progressBar;

    private ViewGroup errorView;

    private Button reloadButton;

    private static final Animation animation = new AlphaAnimation(1f, 0f);

    public WebContainerView(Context context) {
        super(context);
        initialize();
    }

    public WebContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
        setupWebSettings(attrs);
    }

    public WebContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
        setupWebSettings(attrs);
    }

    private void setupWebSettings(AttributeSet attrs) {
        TypedArray args = getContext().obtainStyledAttributes(attrs, R.styleable.app);
        webView.setupWebSettings(args);
        args.recycle();
    }

    private void initialize() {
        bindViews();
        bindWebViewState();
        animation.setDuration(1000);
    }

    private void bindWebViewState() {
        webView.addOnWebViewStateListener(new WebViewStateListener() {
            @Override
            public void onStartLoading(String url, Bitmap favicon) {
                progressBar.clearAnimation();
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinishLoaded(String loadedUrl) {
                progressBar.startAnimation(animation);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (webView.getVisibility() != View.VISIBLE && progress > 80) {
                    webView.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(progress);
            }
        });
    }

    private void bindViews() {
        View.inflate(getContext(), R.layout.webview_container, this);
        webView = (AppWebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorView = (ViewGroup) findViewById(R.id.error_view);
        reloadButton = (Button) findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView != null) {
                    webView.reload();
                }
            }
        });
    }

    public void addOnWebViewStateListener(WebViewStateListener webViewStateListener) {
        webView.addOnWebViewStateListener(webViewStateListener);
    }

    public void addLoadingInterceptor(LoadingInterceptor loadingInterceptor) {
        webView.addLoadingInterceptor(loadingInterceptor);
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        webView.loadUrl(url, additionalHttpHeaders);
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
    }

    public String getTitle() {
        return webView.getTitle();
    }

    public String getUrl() {
        return webView.getUrl();
    }

    public String getUserAgentString() {
        return webView.getSettings().getUserAgentString();
    }

    public void setUserAgentString(String ua) {
        webView.getSettings().setUserAgentString(ua);

    }

    public android.webkit.WebSettings getSettings() {
        return webView.getSettings();
    }
}
