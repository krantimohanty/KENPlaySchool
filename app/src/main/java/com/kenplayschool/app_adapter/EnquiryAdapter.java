package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kenplayschool.EnquiryDetailActivity;
import com.kenplayschool.R;
import com.kenplayschool.data_model.EnquiryModel;
import com.kenplayschool.app_widget.CircularImageView;

import java.util.List;

/**
 * Created on 2/4/2016.
 */
public class EnquiryAdapter extends RecyclerView.Adapter {


    private List<EnquiryModel> enquiryModels;
    Context context;
    RecyclerView mRecyclerView;

    public EnquiryAdapter(Context context, List<EnquiryModel> enquiryModels , RecyclerView mRecyclerView) {

         this.context=context;
         this.enquiryModels=enquiryModels;
         this.mRecyclerView=mRecyclerView;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enquiry_list_row, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        /*holder.head_name.setText(photoModels.get(position).getTitle());
        holder.head_name.setTextColor(Color.WHITE);*/
//        try {
//           Picasso.with(context)
//        .load(enquiryModels.get(position).getProfilePic())
//                .error(R.drawable.com_facebook_profile_picture_blank_portrait)
//                .into( holder.profile_pic);
//
//        }catch (Exception e){
//
//        }

        holder .row_item_no.setText(enquiryModels.get(position).getEnquiryNo());
        holder .row_item_name.setText(enquiryModels.get(position).getName());
//        holder.row_item_phoneno.setText(enquiryModels.get(position).getPhoneNo());
        holder.row_item_emailid.setText(enquiryModels.get(position).getEmailId());
//        holder.row_item_area.setText(enquiryModels.get(position).getArea());
//        holder.row_item_queries.setText(enquiryModels.get(position).getQueries());
//        holder.row_item_source.setText(enquiryModels.get(position).getSource());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EnquiryDetailActivity.class);
                intent.putExtra("person_name", enquiryModels.get(position).getName());
                intent.putExtra("enquiry_for", enquiryModels.get(position).getEnquiryType());
                intent.putExtra("enquiry_query", enquiryModels.get(position).getQueries());
                intent.putExtra("enquiry_phno", enquiryModels.get(position).getPhoneNo());
               intent.putExtra("enquiry_area", enquiryModels.get(position).getArea());
//                intent.putExtra("bulletin_image", enquiryModels.get(position).getBulletinImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return enquiryModels.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder  {

        // region Member Variables
        //private final ImageView mPhotoView;
        //private final TextView head_name;
        //private final FrameLayout mFrameLayout;
private ScrollView scrlvw;
        private ImageView row_item_image;
        private AppCompatTextView row_item_no;
        private AppCompatTextView row_item_name;
        private AppCompatTextView row_item_phoneno;
        private AppCompatTextView row_item_queries;
        private AppCompatTextView row_item_source;
        private AppCompatTextView row_item_emailid;
        private AppCompatTextView row_item_area;
        private LinearLayout event_row;
        private CardView cardview;
        private CircularImageView profile_pic;
        // endregion

        // region Constructors
        public ImageViewHolder(final View view) {
            super(view);


            //mPhotoView = (ImageView) view.findViewById(R.id.photos);
            //head_name=(TextView)view.findViewById(R.id.caption);
            //mFrameLayout = (FrameLayout) view.findViewById(R.id.fl);
            scrlvw=(ScrollView)view.findViewById(R.id.scrollview1);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            row_item_no = (AppCompatTextView) itemView.findViewById(R.id.enquiry_number);
            row_item_name = (AppCompatTextView) itemView.findViewById(R.id.enquiry_name);
//            row_item_phoneno = (AppCompatTextView) itemView.findViewById(R.id.enquiry_phno);
            row_item_emailid = (AppCompatTextView) itemView.findViewById(R.id.enquiry_emailid);
//            row_item_area = (AppCompatTextView) itemView.findViewById(R.id.enquiry_area);
//            row_item_queries = (AppCompatTextView) itemView.findViewById(R.id.enquiry_queries);
//            row_item_source = (AppCompatTextView) itemView.findViewById(R.id.enquiry_source);
//            profile_pic = (CircularImageView) itemView.findViewById(R.id.circleImage);
//            profile_pic.setBorderColor(getResources().getColor(R.color.grey));
//            profile_pic.setBorderWidth(10);

        }


        // endregion


    }

}