package com.kenplayschool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kenplayschool.data_model.PhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FullScreenPhotoActivity extends BaseActivity {

    private ImageView detailImage;
    ViewPager viewPager;
    Context context;


    List<PhotoModel> photoModels = new ArrayList<>();
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        try {
            toolbar.setTitle(getIntent().getStringExtra("title"));
        } catch (Exception e) {

        }*/


        //String image = getIntent().getStringExtra("images");
        //System.out.println("ResultImage"+ image);

        //Log.d("Message",data.toString());

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView captionBody = (TextView) findViewById(R.id.captionTextView);
        viewPager = (ViewPager) findViewById(R.id.pager);
        detailImage = (ImageView) findViewById(R.id.photo_detail);
        captionBody.setText(getIntent().getStringExtra("title"));
        captionBody.setTextColor(Color.WHITE);

        ImageAdapter adapter = new ImageAdapter(context,photoModels);
        viewPager.setAdapter(adapter);
       /* Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.default_img);
        int gradientStartColor = Color.argb(0, 0, 0, 0);
        int gradientEndColor = Color.argb(255, 0, 0, 0);
        GradientOverImageDrawable gradientDrawable = new GradientOverImageDrawable(getResources(), image);
        gradientDrawable.setGradientColors(gradientStartColor, gradientEndColor);

        detailImage.setImageDrawable(gradientDrawable);*/


        /*try {
            Picasso.with(FullScreenPhotoActivity.this)
                    .load(getIntent().getStringExtra("images"))
                    .placeholder(R.drawable.default_img)
                    .into(detailImage);

        } catch (Exception e) {

        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }
        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_search) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    private class ImageAdapter extends PagerAdapter {
        List<PhotoModel> photoModels;
        Context context;
        public ImageAdapter(Context context, List<PhotoModel> photoModels) {
            this.context=context;
            this.photoModels = photoModels;
        }


        @Override
        public int getCount() {
            return photoModels.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imgDisplay;
            Button btnClose;

            final Activity _activity = null;
            inflater = (LayoutInflater) _activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                    false);

            imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
            btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

            Picasso.with(FullScreenPhotoActivity.this)
                    //.load(getIntent().getStringExtra("images"))
                    .load(getIntent().getStringExtra("images"))
                    .placeholder(R.drawable.default_img)
                    .into(imgDisplay);


          /*  BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoModels.get(position).getUrl(), options);
            imgDisplay.setImageBitmap(bitmap);
*/
            // close button click event
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity.finish();
                }
            });

            ((ViewPager) container).addView(viewLayout);

            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }
}