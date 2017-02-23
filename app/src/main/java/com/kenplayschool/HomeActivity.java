package com.kenplayschool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class HomeActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private RelativeLayout bulletin_layout, feedback_layout;
    private LinearLayout attendance_layout,diary_layout, event_layout, enquiry_layout,fees_layout, holiday_list_layout, videocall_layout, contact_layout, gallery_layout, performance_layout, about_us_layout;
    private AppCompatTextView news_press_text;
    private static int flag=0;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Play School");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


//        Sliding view

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");

//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Banner1",R.drawable.listview_pic_2);
        file_maps.put("Banner2",R.drawable.listview_pic_4);
        file_maps.put("Banner3",R.drawable.listview_pic_8);
        file_maps.put("Banner4", R.drawable.listview_pic_7);
        file_maps.put("Banner5", R.drawable.listview_pic_6);
        file_maps.put("Banner6", R.drawable.listview_pic_5);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);





//        admission_layout, registration_layout,  manifesto_layout = (LinearLayout) findViewById(R.id.manifesto_layout);

        attendance_layout=(LinearLayout) findViewById(R.id.attandance_layout);
        //about_us_layout = (LinearLayout) findViewById(R.id.aboutus_layout);
//      event_layout = (LinearLayout) findViewById(R.id.event_layout);
        holiday_list_layout = (LinearLayout) findViewById(R.id.holiday_layout);
        event_layout = (LinearLayout) findViewById(R.id.event_layout);
//      registration_layout = (LinearLayout) findViewById(R.id.registration_layout);
        bulletin_layout = (RelativeLayout) findViewById(R.id.bulletin_layout);
        enquiry_layout = (LinearLayout) findViewById(R.id.enquiry_layout);
        diary_layout = (LinearLayout) findViewById(R.id.diary_layout);
//      admission_layout = (LinearLayout) findViewById(R.id.admission_layout);
        //contact_layout = (LinearLayout) findViewById(R.id.contact_layout);
        gallery_layout = (LinearLayout) findViewById(R.id.gallery_layout);
        feedback_layout = (RelativeLayout) findViewById(R.id.feedback_layout);
        fees_layout = (LinearLayout) findViewById(R.id.fees_layout);
//      performance_layout = (LinearLayout) findViewById(R.id.performance_layout);
        videocall_layout = (LinearLayout) findViewById(R.id.videocall_layout);

//        demoa_layout = (LinearLayout) findViewById(R.id.demoa_layout);
//        demob_layout = (LinearLayout) findViewById(R.id.demob_layout);
//        democ_layout = (LinearLayout) findViewById(R.id.democ_layout);
//        demob_layout = (LinearLayout) findViewById(R.id.demob_layout);
//      calender_layout = (LinearLayout) findViewById(R.id.calender_layout);

//        news_press_text = (AppCompatTextView) findViewById(R.id.news_press_text);
//        IconDrawable arrow = new IconDrawable(HomeActivity.this, IcoMoonIcons.ic_arrow).colorRes(R.color.color_white).sizeDp(18);
//        news_press_text.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow, null);

        //navigate to manifesto page

        //navigate to news page
//        about_us_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
//            }
//        });

        event_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EventzActivity.class));
            }
        });

        //navigate to representative page
//        registration_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
//            }
//        });

//        diary_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, DiaryActivity.class));
//            }
//        });

//        admission_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, AdmissionActivity.class));
//            }
//        });
        //navigate to discussion page
        enquiry_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EnquiryActivity.class));
            }
        });
        bulletin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BulletinActivity.class));
            }
        });
        diary_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DiaryActivity.class));
            }
        });
        //navigate to representative page
//        contact_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
//            }
//        });
        //navigate to representative page
        gallery_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
            }
        });
        //navigate to contribute page
        fees_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FeesActivity.class));
            }
        });
        //navigate to getinvolve page
        feedback_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
            }
        });
        attendance_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AttendanceActivity.class));
            }
        });
        holiday_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HolidayListActivity.class));
            }
        });

//        //navigate to social page
//        social_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, SocialUpdatesActivity.class));
//            }
//        });

        //navigate to contribute page


        //navigate to gallery page
        videocall_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, VideoCallActivity.class));
            }
        });


        if(flag == 1)
        {
            this.finish();
            flag=0;
        }



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBoolean(ORIENTATION, mHorizontal);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit ?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onYesClick();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void onYesClick(){
 moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);


    }



//
//    public void trimCache() {
//        try {
//            File dir = getCacheDir();
//            if (dir != null && dir.isDirectory()) {
//                deleteDir(dir);
//                reloadActivity();
//
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        return dir.delete();
//    }

//    protected boolean isNavDrawerOpen() {
//        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
//    }
//
//    protected void closeNavDrawer() {
//        if (mDrawerLayout != null) {
//            mDrawerLayout.closeDrawer(GravityCompat.START);
//        }
//    }
//


//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//

}
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home_menu, menu);
//
//        //notification
//        menu.findItem(R.id.action_notification).setIcon(
//                new IconDrawable(this, IcoMoonIcons.ic_notification)
//                        .colorRes(R.color.color_white)
//                        .sizeDp(20));
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_notification) {
//            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


