package com.kenplayschool;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kenplayschool.app_util.AppCompatPreferenceActivity;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private Toolbar bar;
    private Preference changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.activity_settings, root, false);
            setSupportActionBar(bar);
            bar.setTitleTextColor(Color.WHITE);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
            upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            root.addView(bar, 0); // insert at top
        } else {
            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            ListView content = (ListView) root.getChildAt(0);

            root.removeAllViews();

            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.activity_settings, root, false);
            setSupportActionBar(bar);
            bar.setTitleTextColor(Color.WHITE);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
            upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            int height;
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            } else {
                height = bar.getHeight();
            }

            content.setPadding(0, height, 0, 0);

            root.addView(content);
            root.addView(bar);
        }

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addPreferencesFromResource(R.xml.settings);

        changePass = (Preference) findPreference("change_password");
//        if (CustomPreference.with(SettingsActivity.this).getString("user_id", "").equals("")) {
//            changePass.setEnabled(false);
//        } else
{
            changePass.setEnabled(true);
        }

    }

}
