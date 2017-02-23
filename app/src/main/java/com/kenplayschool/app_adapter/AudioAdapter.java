package com.kenplayschool.app_adapter;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kenplayschool.R;
import com.kenplayschool.data_model.AudioModel;
import com.kenplayschool.network_utils.ServiceCalls;
import com.joanzapata.iconify.widget.IconButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.DataObjectHolder> {

    private List<AudioModel> audioModels = new ArrayList<>();
    private Context context;
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds;
    private final Handler handler = new Handler();

    public AudioAdapter(List<AudioModel> audioModels, Context context, RecyclerView recyclerView) {
        this.audioModels = audioModels;
        this.context = context;

        Log.d("Size", audioModels + "");

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        // holder.row_item_image.setImageResource(R.drawable.demo_image);
        //seekbar
        holder.seekbar.setMax(99);
        mediaPlayer = new MediaPlayer();
        holder.audio_title.setText(audioModels.get(position).getTitle());

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("URL", audioModels.get(position).getUrl());
                if (v.getId() == R.id.play_icon) {
                    /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
                    try {
                       // Log.d("single Audio Url",audioModels.get(position).getUrl());
                        mediaPlayer.setDataSource(audioModels.get(position).getUrl()); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                        mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        holder.play.setText("{ic-pause 20sp}");
                    } else {
                        mediaPlayer.pause();
                        holder.play.setText("{ic-play 20sp}");
                    }

                    primarySeekBarProgressUpdater(holder.seekbar);
                }
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                holder.seekbar.setSecondaryProgress(percent);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                holder.play.setText("{ic-play 20sp}");
            }
        });

        holder.seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.seekbar) {

                    if (mediaPlayer.isPlaying()) {
                        AppCompatSeekBar sb = (AppCompatSeekBar) v;
                        int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                        mediaPlayer.seekTo(playPositionInMillisecconds);
                    }
                }
                return false;
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceCalls.sharePost(context, "", audioModels.get(position).getTitle(), audioModels.get(position).getUrl());
            }
        });

    }

    private void primarySeekBarProgressUpdater(final AppCompatSeekBar seekBar) {
        seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater(seekBar);
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        private IconButton play;
        private AppCompatTextView audio_title;
        private AppCompatSeekBar seekbar;
        private IconButton share;
        private ContentLoadingProgressBar contentLoadingProgressBar;
        private Context context;

        public DataObjectHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            play = (IconButton) itemView.findViewById(R.id.play_icon);

            audio_title = (AppCompatTextView) itemView.findViewById(R.id.audio_title);
            seekbar = (AppCompatSeekBar) itemView.findViewById(R.id.seekbar);
            share = (IconButton) itemView.findViewById(R.id.share);


            Log.i("LOG_TAG", "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }

    @Override
    public int getItemCount() {
        return this.audioModels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}