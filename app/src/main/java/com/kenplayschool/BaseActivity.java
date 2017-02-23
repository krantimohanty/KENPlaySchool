package com.kenplayschool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.app_util.GooglePlusLoginUtils;
import com.kenplayschool.icon_util.IcoMoonIcons;
import com.kenplayschool.icon_util.IcoMoonModule;
import com.kenplayschool.network_utils.ServiceCalls;
import com.google.android.gms.common.api.GoogleApiClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;

import java.io.File;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private ImageView profile_pic;
    private AppCompatTextView user_name, welcome_txt;
    private AppCompatTextView user_points;
    private ContentLoadingProgressBar progressBar;
    private AppCompatButton loginButton;
    private GoogleApiClient mGoogleApiClient;
    private GetCatSubCatDataEventListener getCatSubCatDataEventListener;
    private GooglePlusLoginUtils gLogin;

    public void setGetCatSubCatDataEventListener(GetCatSubCatDataEventListener eventListener) {
        getCatSubCatDataEventListener = eventListener;
    }

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     *
     * @return true
     */
    protected boolean useToolbar() {
        return true;
    }


    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     *
     * @return
     */
    protected boolean useDrawerToggle() {
        return true;
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        // reloadActivity();
        getActionBarToolbar();

        setupNavDrawer();
    }//end setContentView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new IcoMoonModule());


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                if (useToolbar()) {
                    setSupportActionBar(mActionBarToolbar);
                } else {
                    mActionBarToolbar.setVisibility(View.GONE);
                }

            }
        }

        return mActionBarToolbar;
    }


    private void setupNavDrawer() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        // use the hamburger menu
        if (useDrawerToggle()) {
            mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(mToggle);
            mToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
                    .getDrawable(this, R.drawable.ic_action_navigation_arrow_back));
        }


        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View headerView = mNavigationView.getHeaderView(0);
        loginButton = (AppCompatButton) headerView.findViewById(R.id.loginButton);
        profile_pic = (ImageView) headerView.findViewById(R.id.profilePic);
        profile_pic.setImageDrawable(new IconDrawable(this, IcoMoonIcons.ic_user)
                .colorRes(R.color.grey)
                .actionBarSize());

        progressBar = (ContentLoadingProgressBar) headerView.findViewById(R.id.progress);
        welcome_txt = (AppCompatTextView) headerView.findViewById(R.id.welcome_txt);
        user_name = (AppCompatTextView) headerView.findViewById(R.id.user_name);
        user_points = (AppCompatTextView) headerView.findViewById(R.id.points);

       /* if (CustomPreference.with(BaseActivity.this).getString("LoginUserId", "").equals("")) {

        } else {

        }*/

        //getting user information
        // Toast.makeText(BaseActivity.this, CustomPreference.with(BaseActivity.this).getString("LoginUserId", ""), Toast.LENGTH_LONG).show();
        if (CustomPreference.with(BaseActivity.this).getInt("LoginUserId", 0) == 0) {
            //user_name.setText("Guest");
            user_name.setVisibility(View.GONE);
            welcome_txt.setText("Welcome Guest");
            user_points.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentGetMessage = new Intent(BaseActivity.this, LoginActivity.class);
                    intentGetMessage.putExtra("activityFrom", "BaseActivity");
                    startActivityForResult(intentGetMessage, 2);
                    //finish();
                    //startActivity(new Intent(BaseActivity.this, LoginActivityOldie.class).putExtra("pageFrom", "BaseActivity"));
                    //finish();
                }
            });
        } else {
            // ServiceCalls.getUserInfo(BaseActivity.this, CustomPreference.with(BaseActivity.this).getInt("LoginUserId", 0), progressBar, user_name, profile_pic, user_points, "");
            user_points.setVisibility(View.GONE);
            welcome_txt.setText("Welcome to Babylonia");
            loginButton.setVisibility(View.GONE);
            user_name.setVisibility(View.VISIBLE);
            user_name.setText(CustomPreference.with(BaseActivity.this).getString("name", ""));
        }
        //changing group item color in nav menu
        // SpannableString s = new SpannableString(mNavigationView.getMenu().findItem(R.id.news_group).getTitle().toString());
        //SpannableString s1 = new SpannableString(mNavigationView.getMenu().findItem(R.id.get_involved_group).getTitle().toString());
        //SpannableString s2 = new SpannableString(mNavigationView.getMenu().findItem(R.id.others).getTitle().toString());
        //s.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9800")), 0, s.length(), 0);
        //s1.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9800")), 0, s1.length(), 0);
        //s2.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9800")), 0, s2.length(), 0);
        //mNavigationView.getMenu().findItem(R.id.news_group).setTitle(s);
        //mNavigationView.getMenu().findItem(R.id.get_involved_group).setTitle(s1);
        //mNavigationView.getMenu().findItem(R.id.others).setTitle(s2);

        //settings icon for navigation menu
        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.home).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_home)
                        .colorRes(R.color.color_white)
                        .actionBarSize());

        menu.findItem(R.id.about).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_chat)
                        .colorRes(R.color.color_white)
                        .actionBarSize());

        menu.findItem(R.id.facilities).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_sandesh)
                        .colorRes(R.color.color_white)
                        .actionBarSize());

//        menu.findItem(R.id.menu).setIcon(
//                new IconDrawable(this, IcoMoonIcons.ic_news)
//                        .colorRes(R.color.color_white)
//                        .actionBarSize());
        menu.findItem(R.id.social_updates).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_social)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
//        menu.findItem(R.id.get_involve).setIcon(
//                new IconDrawable(this, IcoMoonIcons.ic_manifesto)
//                        .colorRes(R.color.color_white)
//                        .actionBarSize());
        /*menu.findItem(R.id.baby_sandesh).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_sandesh)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.announcements).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_announcement)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.achievements).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_achievement)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.party_members).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_party_member)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.volunteer_registration).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_registration)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.online_donation).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_donation)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        *//*menu.findItem(R.id.members_activity).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_blog)
                        .colorRes(R.color.color_white)
                        .actionBarSize());*//*
        menu.findItem(R.id.discussion).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_discussion)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.events).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_events)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.gallery).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_gallery)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
        menu.findItem(R.id.know_your_representative).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_representative)
                        .colorRes(R.color.color_white)
                        .actionBarSize());*/
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_settings)
                        .colorRes(R.color.color_white)
                        .actionBarSize());
//        menu.findItem(R.id.feedback).setIcon(
//                new IconDrawable(this, IcoMoonIcons.ic_feedback)
//                        .colorRes(R.color.color_white)
//                        .actionBarSize());
        /*menu.findItem(R.id.voter_id_problem).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_voter_id)
                        .colorRes(R.color.color_white)
                        .actionBarSize());*/
        menu.findItem(R.id.share).setIcon(
                new IconDrawable(this, IcoMoonIcons.ic_share)
                        .colorRes(R.color.color_white)
                        .actionBarSize());

        //menu.findItem(R.id.members_activity).setIcon(null).setTitle("").setEnabled(false);
//        if (CustomPreference.with(BaseActivity.this).getInt("LoginUserId", 0) == 0) {
//            menu.findItem(R.id.logout).setIcon(null).setTitle("").setEnabled(false);
//        } else {
            menu.findItem(R.id.logout).setIcon(
                    new IconDrawable(this, IcoMoonIcons.ic_logout)
                            .colorRes(R.color.color_white)
                            .actionBarSize());
        }




    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            //ServiceCalls.getUserInfo(BaseActivity.this, CustomPreference.with(BaseActivity.this).getString("userId", null), progressBar, user_name, profile_pic, user_points, "");
            if (CustomPreference.with(BaseActivity.this).getString("LoginUserId", "").equals("")) {

            } else {
                user_points.setVisibility(View.INVISIBLE);
                loginButton.setVisibility(View.GONE);
                user_name.setText(CustomPreference.with(BaseActivity.this).getString("name", ""));
            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            startActivity(new Intent(BaseActivity.this, HomeActivity.class));

        }
        else if (id == R.id.about) {
            startActivity(new Intent(BaseActivity.this, AboutUsActivity.class));

        }

        else if (id == R.id.facilities) {
            startActivity(new Intent(BaseActivity.this, FacilitiesActivity.class));

        }

//        } else if (id == R.id.menu) {
//            startActivity(new Intent(BaseActivity.this, MenuActivity.class));
         else if (id == R.id.social_updates) {
            startActivity(new Intent(BaseActivity.this, SocialUpdatesActivity.class));
        } /*else if (id == R.id.get_involve) {
            startActivity(new Intent(BaseActivity.this, AboutUsActivity.class)
                    .putExtra("type", "AboutUsActivity"));
            finish();


//        } else if (id == R.id.get_involve) {
////            startActivity(new Intent(BaseActivity.this, AdmissionActivity.class));
////        }

        /*else if (id == R.id.announcements) {
            startActivity(new Intent(BaseActivity.this, AnnouncementActivity.class));
        } else if (id == R.id.achievements) {
            startActivity(new Intent(BaseActivity.this, PerformanceActivity.class));
        } else if (id == R.id.party_members) {
            if (CustomPreference.with(BaseActivity.this).getString("LoginUserId", "").equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "You need to login to continue", Snackbar.LENGTH_LONG)
                        .setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(BaseActivity.this, LoginActivityOldie.class));
                                finish();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            } else {
                startActivity(new Intent(BaseActivity.this, AddMembershipActivity.class));

            }

        } else if (id == R.id.volunteer_registration) {
            if (CustomPreference.with(BaseActivity.this).getString("LoginUserId", "").equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "You need to login to continue", Snackbar.LENGTH_LONG)
                        .setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(BaseActivity.this, LoginActivityOldie.class));
                                finish();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            } else {
                startActivity(new Intent(BaseActivity.this, AdmissionChildActivity.class));

            }

        } else if (id == R.id.online_donation) {
            if (CustomPreference.with(BaseActivity.this).getString("LoginUserId", "").equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "You need to login to continue", Snackbar.LENGTH_LONG)
                        .setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(BaseActivity.this, LoginActivityOldie.class));
                                finish();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            } else {
                // startActivity(new Intent(BaseActivity.this, FeesActivity.class));
                startActivity(new Intent(BaseActivity.this, AboutUsActivity.class)
                        .putExtra("type", "FeesActivity"));

                finish();

            }

        }
        //else if (id == R.id.members_activity) {
        // startActivity(new Intent(BaseActivity.this, BlogActivity.class));
        //}
        else if (id == R.id.discussion) {
            startActivity(new Intent(BaseActivity.this, DiscussionActivity.class));
            finish();
        } else if (id == R.id.events) {
            startActivity(new Intent(BaseActivity.this, BulletinActivity.class));
        } else if (id == R.id.gallery) {
            startActivity(new Intent(BaseActivity.this, TodaysActivity.class));
        } else if (id == R.id.know_your_representative) {
            startActivity(new Intent(BaseActivity.this, ContactUsOldActivity.class));
        }*/
        else if (id == R.id.settings) {
            startActivity(new Intent(BaseActivity.this, SettingsActivity.class));

        }
//        else if (id == R.id.feedback) {
//            startActivity(new Intent(BaseActivity.this, FeedbackActivity.class));
//
//        }
        else if (id == R.id.share) {
            ServiceCalls.sharePost(BaseActivity.this, "", "Download the App", "https://play.google.com/store/apps/details?id=com.babyloniaapp");
        } else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Logout");
            builder.setMessage("Do you want to logout ?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //gLogin.googlePlusLogout();
                  /*  mGoogleApiClient = getApiClient();


                        Plus.AccountApi.clearDefaultAccount(myApp.mGoogleApiClient);
                        myApp.mGoogleApiClient.disconnect();
                        myApp.mGoogleApiClient.connect();
                        Log.d("App", "isConnected");*/
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

                            }












            });

            builder.show();

        }

        closeNavDrawer();
        return true;
    }

    public void trimCache() {
        try {
            File dir = getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
                reloadActivity();

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     *
     * @param intent
     */
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    public interface GetCatSubCatDataEventListener {
        void onDataReceive();
    }

    public void reloadActivity() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}//end BaseActivity

