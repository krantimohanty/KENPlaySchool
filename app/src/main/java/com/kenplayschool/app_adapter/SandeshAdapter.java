package com.kenplayschool.app_adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kenplayschool.EventzDetailActivity;
import com.kenplayschool.R;

/**
 * Created by Kranti on 22/3/2016.
 */
public class SandeshAdapter extends RecyclerView.Adapter<SandeshAdapter.DataObjectHolder> {

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_feed_row_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
       // holder.row_item_image.setImageResource(R.drawable.demo_image);
      /*  holder._img.setText(iconicFontName[position]);
        holder._category.setText(name[position]);
        if (position == 2 || position == 5) {
            holder._status.setText("{ico-exclamation @color/color_red}");
        } else {
            holder._status.setText("{ico-check @color/color_green}");
        }*/
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        private ImageView row_item_image;
        /*private IconTextView _img;
        private AppCompatTextView _category;
        private IconTextView _status;*/
        private Context context;

        public DataObjectHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            row_item_image = (ImageView) itemView.findViewById(R.id.row_item_image);
           /* _img = (IconTextView) itemView.findViewById(R.id.list_icons);
            _status = (IconTextView) itemView.findViewById(R.id.status);
            _category = (AppCompatTextView) itemView.findViewById(R.id.list_name);*/
            Log.i("LOG_TAG", "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EventzDetailActivity.class);
            intent.putExtra("flag","sandesh");
            Pair<View, String> p1 = Pair.create((View) row_item_image, "row_item_image");
            /*Pair<View, String> p2 = Pair.create(vPalette, "palette");
            Pair<View, String> p3 = Pair.create((View) tvName, "text");*/
            /*ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1, p2, p3);*/
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, p1);
            //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, Pair.create((View) row_item_image, "row_item_image"));
            context.startActivity(intent, options.toBundle());
            //context.startActivity(new Intent(context, EventzDetailActivity.class));
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}