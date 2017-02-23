package com.kenplayschool.app_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenplayschool.R;
import com.kenplayschool.app_util.Config;

import com.kenplayschool.data_model.VideoModel;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Member Variables
   // private final List<String> videoslist;
    private List<VideoModel> videoModels = new ArrayList<>();
    private int mGridItemWidth;
    private int mGridItemHeight;
    private OnImageClickListener mOnImageClickListener;
    private Context context;

    VideoModel image;
    // endregion

    // region Interfaces
    public interface OnImageClickListener {
        void onImageClick(int position);
    }
    // endregion

    // region Constructors
    public VideoGalleryAdapter(List<VideoModel> videoModels, Context context) {
        this.videoModels = videoModels;
        this.context = context;

        //Log.d("Videos", videoslist.toString());
    }
    // endregion

    /*public VideoGalleryAdapter(List<String> videos, Context context) {
        videoslist = videos;
        this.context = context;

        Log.d("Videos", videoslist.toString());
    }
*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_list_item, viewGroup, false);
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        //v.setLayoutParams(getGridItemLayoutParams(v));
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        holder.head_name.setText(videoModels.get(position).getTitle());
        holder.date_name.setText(videoModels.get(position).getDate());
       //setUpImage(holder.youtube_thumbnail, videoModels);

        Picasso.with(context)
                .load("http://img.youtube.com/vi/" + videoModels + "/0.jpg")
                //.load(videoModels.get(position).getUrl())
                .into(holder.youtube_thumbnail);



       /* String image = videoslist.get(position);

        setUpImage(holder.youtube_thumbnail, image);
*/
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((AppCompatActivity) context,
                        Config.GOOGLE_DEVELOPER_KEY,
                        String.valueOf(videoModels.get(position)),//video id
                        100,     //after this time, video will start automatically
                        true,               //autoplay or not
                        false);             //lightbox mode or not; show the video in a small box
                context.startActivity(intent);
            }
        });
      /*  holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((AppCompatActivity) context,
                        Config.GOOGLE_DEVELOPER_KEY,
                        videoslist.get(position),//video id
                        100,     //after this time, video will start automatically
                        true,               //autoplay or not
                        false);             //lightbox mode or not; show the video in a small box
                context.startActivity(intent);
            }
        });*/


       /* holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((AppCompatActivity) context,
                        Config.GOOGLE_DEVELOPER_KEY,
                        videoslist.get(position),//video id
                        100,     //after this time, video will start automatically
                        true,               //autoplay or not
                        true);             //lightbox mode or not; show the video in a small box
                context.startActivity(intent);

            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (videoModels != null) {
            return videoModels.size();
        } else {
            return 0;
        }
    }

  /*  @Override
    public int getItemCount() {
        if (videoslist != null) {
            return videoslist.size();
        } else {
            return 0;
        }
    }*/
    // region Helper Methods
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mOnImageClickListener = listener;

    }




   /* private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
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

     /* private void setUpImage(ImageView iv, final List<VideoModel> imageUrl) {

          Picasso.with(iv.getContext())
                  //.load("http://img.youtube.com/vi/" + imageUrl + "/0.jpg")
                  //.load(videoModels.get)
                  //.resize(mGridItemWidth, mGridItemHeight)
                  //.centerCrop()
                  .into(iv);
    }*/




  /*  private void setUpImage(ImageView iv, final String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load("http://img.youtube.com/vi/" + imageUrl + "/0.jpg")
                    //.resize(mGridItemWidth, mGridItemHeight)
                    //.centerCrop()
                    .into(iv);
        } else {
            iv.setImageDrawable(null);
        }
    }*/
    // endregion

    // region Inner Classes

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        // region Member Variables
        private final ImageView youtube_thumbnail;
        private final FrameLayout mFrameLayout;
        //private final IconButton playButton;
        private final TextView head_name;
        private final TextView date_name;
        private CardView cardView;
        //private final TextView date;
        // endregion

        // region Constructors
        public ImageViewHolder(final View view) {
            super(view);

            youtube_thumbnail=(ImageView)view.findViewById(R.id.youtube_thumbnail);
            mFrameLayout = (FrameLayout) view.findViewById(R.id.fl);
            //playButton = (IconButton)view.findViewById(R.id.btnYoutube_player);
            cardView = (CardView)view.findViewById(R.id.cart_list);
            head_name=(TextView)view.findViewById(R.id.name_list);
            date_name=(TextView)view.findViewById(R.id.video_date);
            //date=(TextView)view.findViewById(R.id.date_list);
            /* view.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             });*/
        }
        // endregion
    }

    // endregion
}
