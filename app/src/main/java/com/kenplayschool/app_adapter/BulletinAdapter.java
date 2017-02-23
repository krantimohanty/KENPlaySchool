package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kenplayschool.BulletinDetailActivity;
import com.kenplayschool.data_model.BulletinModel;
import com.squareup.picasso.Picasso;
import com.kenplayschool.R;
import java.util.List;

/**
 * Created by Kranti on 19/4/2016.
 */
public class BulletinAdapter extends RecyclerView.Adapter {


    private List<BulletinModel> bulletinModels;
    Context context;
    private Bitmap bitmap = null;
    RecyclerView mRecyclerView;
    public BulletinAdapter(Context context, List<BulletinModel> bulletinModels, RecyclerView mRecyclerView) {

         this.context=context;
         this.bulletinModels=bulletinModels;
         this.mRecyclerView=mRecyclerView;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bulletin_list_row, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        /*holder.head_name.setText(photoModels.get(position).getTitle());
        holder.head_name.setTextColor(Color.WHITE);*/
//        holder .row_item_image.setImageBitmap(bulletinModels.get(position).getBulletinImage());
        holder .row_item_head.setText(bulletinModels.get(position).getBulletinTitle());
        holder.row_item_date.setText(bulletinModels.get(position).getStartDate());
        holder.row_item_contents.setText(bulletinModels.get(position).getBulletinComments());

        try {
            Picasso.with(context)
                    .load(bulletinModels.get(position).getBulletinImage())
                    .into(holder.row_item_image);

        }catch (Exception e){

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BulletinDetailActivity.class);
                intent.putExtra("bulletin_title", bulletinModels.get(position).getBulletinTitle());
                intent.putExtra("bulletin_date", bulletinModels.get(position).getStartDate());
                intent.putExtra("bulletin_comment", bulletinModels.get(position).getBulletinComments());
                intent.putExtra("bulletin_image", bulletinModels.get(position).getBulletinImage());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bulletinModels.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder  {

        // region Member Variables
//        private final ImageView mPhotoView;
        //private final TextView head_name;
        //private final FrameLayout mFrameLayout;

        private ImageView row_item_image;
//        private AppCompatTextView row_item_location;
        private AppCompatTextView row_item_head;
        private AppCompatTextView row_item_date;
        private AppCompatTextView row_item_contents;
        private AppCompatTextView row_item_imgtxt;
        private LinearLayout event_row;
        private CardView cardView;

        // endregion

        // region Constructors
        public ImageViewHolder(final View view) {
            super(view);


            //mPhotoView = (ImageView) view.findViewById(R.id.photos);
            //head_name=(TextView)view.findViewById(R.id.caption);
            //mFrameLayout = (FrameLayout) view.findViewById(R.id.fl);

           row_item_image = (ImageView) itemView.findViewById(R.id.picture);
            row_item_head = (AppCompatTextView) itemView.findViewById(R.id.row_head);
            row_item_date = (AppCompatTextView) itemView.findViewById(R.id.bulletin_date);
            row_item_contents = (AppCompatTextView) itemView.findViewById(R.id.bulletin_contents);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }


        // endregion


    }

}