package com.kenplayschool.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kenplayschool.R;
import com.kenplayschool.SectorialDetails;
import com.kenplayschool.app_util.CustomPreference;
import com.kenplayschool.data_model.SectoralModel;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.DataObjectHolder> {

    private Context context;
    private List<SectoralModel> sectoralModels = new ArrayList<>();

    public PerformanceAdapter() {
    }

    public PerformanceAdapter(List<SectoralModel> sectoralModels, Context context, RecyclerView mRecyclerView) {
        this.sectoralModels = sectoralModels;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.performance_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.title.setText(sectoralModels.get(position).getSector_name());
        holder.subtitle.setText(sectoralModels.get(position).getTotal_posts() + " posts");
        try {
            if (sectoralModels.get(position).getTotal_posts().equals("0")) {
                holder.sect_layout.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            } else {
                holder.sect_layout.setVisibility(View.VISIBLE);
                holder.divider.setVisibility(View.VISIBLE);
                holder.sect_layout.setOnClickListener(sendSectId(sectoralModels.get(position).getSector_name(), Integer.parseInt(sectoralModels.get(position).getSector_id())));
            }

        } catch (Exception e) {

        }

    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private IconTextView icon;
        private AppCompatTextView title;
        private AppCompatTextView subtitle;
        private View divider;
        private Context context;
        private LinearLayout sect_layout;

        public DataObjectHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            sect_layout = (LinearLayout) itemView.findViewById(R.id.view);

            //icon = (IconTextView) itemView.findViewById(R.id.icon);

            icon = (IconTextView) itemView.findViewById(R.id.icon);

            //  icon = (IconTextView) itemView.findViewById(R.id.icon);
            divider = (View) itemView.findViewById(R.id.divider);

            title = (AppCompatTextView) itemView.findViewById(R.id.title);
            subtitle = (AppCompatTextView) itemView.findViewById(R.id.subtitle);
            Log.i("LOG_TAG", "Adding Listener");


        }
    }

    private View.OnClickListener sendSectId(final String sector_name, final int sector_id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPreference.with(context).save("sector_id", sector_id);
                Intent intent = new Intent(context, SectorialDetails.class);
                intent.putExtra("sector_name", sector_name);
                intent.putExtra("sector_id", sector_id);
                intent.putExtra("pageFrom", "Sectoral");
                context.startActivity(intent);
                ((AppCompatActivity) context).finish();
            }
        };
    }

    @Override
    public int getItemCount() {
        return sectoralModels.size();
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

}