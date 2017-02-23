package com.kenplayschool.custom_webview;

import android.net.Uri;

public interface LoadingInterceptor {
    public boolean validate(Uri uri);
    public void exec(Uri uri);
}
