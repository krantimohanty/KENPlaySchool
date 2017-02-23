package com.kenplayschool.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kenplayschool.R;
import com.telerik.android.common.Util;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SlideLayoutManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class ListViewSlideFragment extends ExampleFragmentBase {
    private RadListView listView;
    private SlideLayoutManager slideLayoutManager;
    private DestinationsAdapter adapter;
    private View rootView;

    private LruCache<String, Bitmap> mMemoryCache;

    public ListViewSlideFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_list_view_slide, container, false);
        listView = (RadListView)rootView.findViewById(R.id.listView);

        slideLayoutManager = new SlideLayoutManager(getActivity());

        adapter = new DestinationsAdapter(getData());
        updateListViewLayoutParams();
        listView.setAdapter(adapter);

        listView.addItemClickListener(new RadListView.ItemClickListener() {
            @Override
            public void onItemClick(int itemPosition, MotionEvent motionEvent) {
                if (itemPosition == slideLayoutManager.getCurrentPosition()) {
                    navigateToDetailsFragment();
                }
            }

            @Override
            public void onItemLongClick(int itemPosition, MotionEvent motionEvent) {

            }
        });

        listView.setLayoutManager(slideLayoutManager);

        if (savedInstanceState != null) {
            int currentPosition = savedInstanceState.getInt("currentPosition", 0);
            slideLayoutManager.scrollToPosition(currentPosition);
        }

        return rootView;
    }

    private void performClick(int position) {
        if(position > slideLayoutManager.getCurrentPosition()) {
            slideLayoutManager.scrollToNext();
        } else if(position < slideLayoutManager.getCurrentPosition()) {
            slideLayoutManager.scrollToPrevious();
        } else {
            navigateToDetailsFragment();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(slideLayoutManager != null) {
            int currentPosition = slideLayoutManager.getCurrentPosition();
            outState.putInt("currentPosition", currentPosition);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        RetainFragment retainFragment =
                RetainFragment.findOrCreateRetainFragment(getFragmentManager());
        mMemoryCache = retainFragment.mRetainedCache;
        if (mMemoryCache == null) {
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
            retainFragment.mRetainedCache = mMemoryCache;
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public class DestinationsAdapter extends ListViewAdapter {
        private int width;
        private int height;

        public DestinationsAdapter(List items) {
            super(items);
        }

        public void setDimens(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.list_view_destination_layout, parent, false);
            return new DestinationViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            Destination item = (Destination) this.getItem(position);

            final DestinationViewHolder typedVh = (DestinationViewHolder) holder;

            loadBitmap(item.src, typedVh.destinationImage, width, height);
            typedVh.position = position;
            typedVh.separator.setBackgroundColor(item.color);
            typedVh.destinationTitle.setText(item.title);
            typedVh.destinationTitle.setTextColor(item.color);
            typedVh.destinationInfo.setText(item.info);
            typedVh.destinationEnquiryLayout.setBackgroundColor(item.color);
            typedVh.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performClick(typedVh.position);
                }
            });
        }
    }

    public void loadBitmap(int resId, ImageView imageView, int reqWidth, int reqHeight) {

        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(0);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView, reqWidth, reqHeight);
            task.execute(resId);
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    private void navigateToDetailsFragment() {
        Fragment newFragment = new ListViewSlideItemFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ((OnDestinationSelectedListener)newFragment).onDestinationSelected((Destination)adapter.getItem(slideLayoutManager.getCurrentPosition()));
        transaction.replace(R.id.exampleContainer, newFragment);

        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;
        private int width;
        private int height;
        private Resources resources;

        public BitmapWorkerTask(ImageView imageView, int width, int height) {
            imageViewReference = new WeakReference<>(imageView);
            resources = getResources();
            this.width = width;
            this.height = height;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            Bitmap bitmap = decodeSampledBitmapFromResource(resources, data, width, height);
            addBitmapToMemoryCache(String.valueOf(data), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public class Destination implements Parcelable {

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(info);
            dest.writeInt(src);
            dest.writeInt(color);
            dest.writeStringList(attractions);
        }

        public Destination(Parcel source) {
            title = source.readString();
            info = source.readString();
            src = source.readInt();
            color = source.readInt();
            source.readStringList(attractions);
        }

        public final Creator<Destination> CREATOR = new Creator<Destination>() {
            @Override
            public Destination[] newArray(int size) {
                return new Destination[size];
            }

            @Override
            public Destination createFromParcel(Parcel source) {
                return new Destination(source);
            }
        };

        public Destination(String title, String info, int src, int color) {
            this.title = title;
            this.info = info;
            this.src = src;
            this.color = color;
        }

        public String title;
        public String info;
        public int src;
        public int color;
        public ArrayList<String> attractions = new ArrayList<String>();

        public void addAttractions(String... attractions) {
            this.attractions.addAll(Arrays.asList(attractions));
        }
    }

    public class DestinationViewHolder extends ListViewHolder {
        public ImageView destinationImage;
        public TextView destinationTitle;
        public TextView destinationInfo;
        public ViewGroup destinationEnquiryLayout;
        public Button destinationEnquiry;
        public View separator;
        public ViewGroup layout;
        public int position;

        private Context context;

        public DestinationViewHolder(final View itemView) {
            super(itemView);

            this.context = itemView.getContext();
            this.destinationImage = (ImageView) itemView.findViewById(R.id.destinationImage);
            this.destinationTitle = (TextView) itemView.findViewById(R.id.destinationTitle);
            this.destinationInfo = (TextView) itemView.findViewById(R.id.destinationInfo);
            this.destinationEnquiry = (Button) itemView.findViewById(R.id.destinationEnquiry);
            this.destinationEnquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEnquiry();
                }
            });
            this.layout = (ViewGroup)itemView.findViewById(R.id.scrollingLayout);
            this.destinationEnquiryLayout = (ViewGroup) itemView.findViewById(R.id.destinationEnquiryLayout);
            this.separator = itemView.findViewById(R.id.separator);
        }

        private void sendEnquiry() {
            Toast.makeText(this.context, "Thanks.. We will contact you soon.", Toast.LENGTH_SHORT).show();
        }
    }

    private List getData() {
        ArrayList<Destination> destinations = new ArrayList<>();

        Destination costaRica = new Destination("KenReach", "Our main objective is to enhance the child’s development in a nurturing and safe environment while having fun. Leave your children at Babylonia in the care of our experienced and trained staff, where they will participate in a combination of directed and free activities. Our entire program is premeditated to introduce an atmosphere where a simple routine is in place ", R.drawable.listview_slide_barra_honda, Color.parseColor("#D19B33"));
        costaRica.addAttractions("Bus Tracking", "Library", "Attendance", "School Diary", "Fees", "Results");
        destinations.add(costaRica);

        Destination italy = new Destination("KenReach","We make our best effort to pamper even the smallest needs of toddlers whose childhood are exquisite and deserve to be full of lovely memories, happy learning, love, play and joy all of which could make their childhood unique. We offer each child with space and time to explore, observe, interact with, manipulate, control and appreciate the environment through play. ", R.drawable.listview_slide_grand_canal, Color.parseColor("#D06744"));
        italy.addAttractions("Bus Tracking", "Library", "Attendance", "School Diary", "Fees", "Results");
        destinations.add(italy);

        Destination peru = new Destination("KenReach", "Whatever your family’s requirements, we are more than happy to offer a heartening experience for your child, and we look forward to make your life a little easier, as well. Through constant, open communication with you, we want to make Babylonia feel like a home away from home for your child.", R.drawable.listview_slide_titicaca_lake, Color.parseColor("#558430"));
        peru.addAttractions("Bus Tracking", "Library", "Attendance", "School Diary", "Fees", "Results");
        destinations.add(peru);

        Destination africa = new Destination("KenReach", "With the intention of ensuring a comprehensive learning experience, we also keep the parents in the loop. We communicate with them at regular intervals regarding any concerns, the progress of their kids, and provide recommendation on how to make their child a better learner. ", R.drawable.listview_slide_featherbed, Color.parseColor("#018CB0"));
        africa.addAttractions("Bus Tracking", "Library", "Attendance", "School Diary", "Fees", "Results");
        destinations.add(africa);

//        Destination china = new Destination("Imperial China", "The ancient sights of China are otherworldly. From the Terracotta Army to the cave carvings at Dragon Gate, the immensity of these Chinese attractions is matched only by the diligence in their details. ", R.drawable.listview_slide_forbidden_city, Color.parseColor("#9064BD"));
//        china.addAttractions("Forbidden City", "The Great Wall of China", "Museum of Qin Terracotta Warriors and Horses", "Temple of Heaven", "The Longmen (Dragon’s Gate)");
//        destinations.add(china);

        return destinations;
    }

    private void updateListViewLayoutParams() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true))
        {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            height -= actionBarHeight;
        }
        int offset;
        int cardHeight;
        int cardWidth;
        int maxWidth = 1067;
        int maxHeight = 5 * maxWidth / 4;

        int itemSpacing = (int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 12);
        if (width > height) {
            cardHeight = height - 4 * itemSpacing;
            cardWidth = 4 * cardHeight / 5;
            offset = (width - cardWidth) / 2;
        } else {
            offset = width / 10;
            cardWidth = offset * 8;
            cardHeight = 5 * cardWidth / 4;
        }
        if(cardWidth > maxWidth) {
            cardWidth = maxWidth;
            cardHeight = maxHeight;
            offset = (width - cardWidth) / 2;
        }

        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, cardHeight));

        adapter.setDimens(cardWidth, cardHeight);

        slideLayoutManager.setPreviousItemPreview(offset);
        slideLayoutManager.setNextItemPreview(offset);
        slideLayoutManager.setItemSpacing(itemSpacing);
    }

    public static class RetainFragment extends Fragment {
        private static final String TAG = "RetainFragment";
        public LruCache<String, Bitmap> mRetainedCache;

        public RetainFragment() {}

        static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
            RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new RetainFragment();
                fm.beginTransaction().add(fragment, TAG).commit();
            }
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }

    public interface OnDestinationSelectedListener {
        void onDestinationSelected(Destination destination);
    }

}
