package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kenplayschool.R;
import com.kenplayschool.RegistrationChildActivity;

/**
 * Created by Kranti on 22/3/2016.
 */
public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.DataObjectHolder> {


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_row_item, parent, false);

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

            //*********************************(IMPORTANT)
            //get Flag n get flag to get the respective activity content

            Intent intent = new Intent(context, RegistrationChildActivity.class);
            context.startActivity(intent);
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