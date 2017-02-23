package com.kenplayschool.app_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenplayschool.FullScreenPhotoActivity;
import com.kenplayschool.R;
import com.kenplayschool.app_util.ImageGalleryUtils;
import com.kenplayschool.data_model.PhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  static List<PhotoModel> photoModels = new ArrayList<>();

    public static Context context;
    String imgg;
    String  title;

    private int mGridItemWidth;
    private int mGridItemHeight;
    //private PhotoAdapter.OnImageClickListener mOnImageClickListener;


    public PhotoAdapter(List<PhotoModel> photoModels, Context context) {
        this.photoModels = photoModels;
        this.context = context;

        Log.d("SizeOf Model", photoModels + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_list_item, viewGroup, false);
        v.setLayoutParams(getGridItemLayoutParams(v));

       /*if (photoModels.size() == 0) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }*/
              return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        holder.head_name.setText(photoModels.get(position).getTitle());
        holder.head_name.setTextColor(Color.WHITE);


        //setUpImage(ImageView iv, final String imageUrl);
        //load image
        Picasso.with(context)
                .load(photoModels.get(position).getUrl())
                .error(R.drawable.default_img)
                .placeholder(R.drawable.default_img)
                .resize(mGridItemWidth, mGridItemHeight)
                .centerCrop()
                .into(holder.mPhotoView);
        /*imgg=photoModels.get(position).getalbum_photo();
        title=photoModels.get(position).getTitle();*/

        holder.mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context,FullScreenPhotoActivity.class);
                intent.putExtra("images", photoModels.get(position).getUrl());
                intent.putExtra("title", photoModels.get(position).getTitle());
                context.startActivity(intent);

              /*  Pair<View, String> p1 = Pair.create((View) holder.mPhotoView, "img_detail");
                ActivityOptionsCompat option=ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context,p1);
                ((AppCompatActivity) context).startActivity(intent, option.toBundle());

                context.startActivity(intent);*/

            }
        });


       /* holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenPhotoActivity.class);
                intent.putExtra("images", photoModels.get(position).getalbum_photo());
                //intent.putExtra("images", String.valueOf(holder.mPhotoView));
                intent.putExtra("title", photoModels.get(position).getTitle());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (photoModels != null) {
            return photoModels.size();
        } else {
            return 0;
        }
    }
    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int screenWidth = ImageGalleryUtils.getScreenWidth(view.getContext());
        int numOfColumns;
        if (ImageGalleryUtils.isInLandscapeMode(view.getContext())) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        mGridItemWidth = screenWidth / numOfColumns;
        mGridItemHeight = screenWidth / numOfColumns;

        layoutParams.width = mGridItemWidth;
        layoutParams.height = mGridItemHeight;

        return layoutParams;
    }

   /* private void setUpImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .error(R.drawable.default_img)
                    .placeholder(R.drawable.default_img)
                    .resize(mGridItemWidth, mGridItemHeight)
                    .centerCrop()
                    .into(iv);
        } else {
            iv.setImageDrawable(null);
        }
    }*/
    // region Helper Methods
   /* public void setOnImageClickListener(PhotoAdapter.OnImageClickListener listener) {
        this.mOnImageClickListener = listener;
    }*/

    public interface OnImageClickListener {
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder  {

        // region Member Variables
        private final ImageView mPhotoView;
        private final TextView head_name;
       //private final FrameLayout mFrameLayout;

        // endregion

        // region Constructors
        public ImageViewHolder(final View view) {
            super(view);


            mPhotoView = (ImageView) view.findViewById(R.id.photos);
            head_name=(TextView)view.findViewById(R.id.caption);
            //mFrameLayout = (FrameLayout) view.findViewById(R.id.fl);


        }


        // endregion


    }

    /*private class OnImageClickListener {
    }*/
}
