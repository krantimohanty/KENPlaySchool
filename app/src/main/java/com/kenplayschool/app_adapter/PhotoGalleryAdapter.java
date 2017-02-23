package com.kenplayschool.app_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenplayschool.PhotoActivity;
import com.kenplayschool.R;
import com.kenplayschool.data_model.PhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Member Variables
   /* private final List<String> mImages;
    private final List<String> title;*/



    private static List<PhotoModel> photoModels = new ArrayList<>();
    private int mGridItemWidth;
    private int mGridItemHeight;
    private OnImageClickListener mOnImageClickListener;
    static Context context;
    private String type;
    private String user_id;
    // endregion

    // region Interfaces
    public interface OnImageClickListener {
        void onImageClick(int position);
    }
    // endregion

    // region Constructors
   /* public PhotoGalleryAdapter(List<String> images, List<String> title, Context context) {
        mImages = images;
        this.title = title;
        this.context = context;
    }*/


    public PhotoGalleryAdapter(List<PhotoModel> photoModels, Context context) {
        this.photoModels = photoModels;
        this.context = context;
        this.type = type;
        this.user_id = user_id;

        Log.d("Size", photoModels + "");

    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_list_item_row, viewGroup, false);
        //v.setLayoutParams(getGridItemLayoutParams(v));

        if (photoModels.size() == 0) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        //final String image = photoModels.get(position).getalbum_photo();
        holder.head_name.setText(photoModels.get(position).getTitle());
        holder.date_name.setText(photoModels.get(position).getDate());
        holder.page_name.setText("Photo " + photoModels.get(position).getPhoto_count());
        //load image
        Picasso.with(context)
                .load(photoModels.get(position).getalbum_photo())
                .into(holder.mImageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  //int position=v.getId();

                Intent intent = new Intent(context, PhotoActivity.class);
                //intent.putExtra("id",  photoModels.get(position).getalbum_id());
                //intent.putExtra("albumId",photoModels.get(position).getalbum_id());
                //intent.putExtra("userid", String.valueOf(user_id));
                //intent.putExtra("userid", CustomPreference.with(context).getInt("user_id",0));
                //intent.putExtra("albumId", CustomPreference.with(context).getInt("albumId",0));
                intent.putExtra("albumId",photoModels.get(position).getalbum_id());
                //intent.putExtra("id", Integer.parseInt(photoModels.get(position).getalbum_id()));
                intent.putExtra("images", photoModels.get(position).getalbum_photo());
                intent.putExtra("title", photoModels.get(position).getTitle());
                context.startActivity(intent);


            }
        });

      /*  holder.cardView.setOnClickListener(sendPostId(photoModels.get(position).getalbum_id()),CustomPreference.with(context).getInt("user_id", 0));
*/
        //holder.itemView.

        /*setUpImage(holder.mImageView, image);
        if (image == null || image.equals("")) {
            Custom_app_util.customSnackbar("No image to display", context, false, "");
        } else {

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenPhotoActivity.class);
                    intent.putExtra("images", image);
                    intent.putExtra("title",photoModels.get(position).getTitle());
                    Pair<View, String> p1 = Pair.create((View) holder.mImageView, "img_detail");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, p1);
                    ((AppCompatActivity) context).startActivity(intent, options.toBundle());
                }
            });

        }*/

       /* holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (mOnImageClickListener != null) {
                        mOnImageClickListener.onImageClick(adapterPos);
                    }
                }
            }
        });*/


    }



   /* private View.OnClickListener sendPostId( final String album_id,final int user_id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PhotoActivity.class);



            }
        };
    }*/


    @Override
    public int getItemCount() {
        if (photoModels != null) {
            return photoModels.size();
        } else {
            return 0;
        }
    }


   /* @Override
    public int getItemCount() {
        if (mImages != null) {
            return mImages.size();
        } else {
            return 0;
        }
    }*/

    // region Helper Methods
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mOnImageClickListener = listener;
    }

    /*private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
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
    }*/

  /*  private void setUpImage(ImageView iv, String imageUrl) {
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
    // endregion

    // region Inner Classes

    public static class ImageViewHolder extends RecyclerView.ViewHolder  {

        // region Member Variables
        private final ImageView mImageView;
        private final FrameLayout mFrameLayout;
        private final TextView head_name;
        private CardView cardView;
        private final TextView date_name;
        private final TextView page_name;

        // endregion

        // region Constructors
        public ImageViewHolder(final View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image_title);
            head_name= (TextView) itemView.findViewById(R.id.image_name);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.fl);
            cardView = (CardView) itemView.findViewById(R.id.cart);
            date_name= (TextView) itemView.findViewById(R.id.date_list);
            page_name= (TextView) itemView.findViewById(R.id.page_count);

            /*view.setOnClickListener(new View.OnClickListener() {
                public int position;

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PhotoActivity.class);
                   *//* intent.putExtra("images",photoModels.get(position).getalbum_photo());
                    intent.putExtra("title",photoModels.get(position).getTitle());*//*
                    context.startActivity(intent);
                }
            });*/
        }
        // endregion
    }

    // endregion
}
