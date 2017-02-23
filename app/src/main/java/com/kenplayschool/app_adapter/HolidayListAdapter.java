package com.kenplayschool.app_adapter;

/**
 * Created by suchismita.p on 5/5/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kenplayschool.R;
import com.kenplayschool.data_model.HolidayListModel;

import java.util.List;

//import com.babyloniaapp.data_model.HolidayList2Model;

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.MyViewHolder> {

    private List<HolidayListModel> holidayList;
//    private List<HolidayList2Model> holidayList2;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, day, holiday, month;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.row_item_date);
            day = (TextView) view.findViewById(R.id.row_item_day);
            holiday = (TextView) view.findViewById(R.id.row_item_name);
            month = (TextView) view.findViewById(R.id.row_item_head);
        }
    }


    public HolidayListAdapter(List<HolidayListModel> holidayList) {
        this.holidayList = holidayList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        HolidayListModel list=holidayList.get(position);
        holder.date.setText(list.getDate());
        holder.day.setText(list.getDay());
        holder.holiday.setText(list.getHoliday());
        holder.month.setText(list.getMonth());
    }

    @Override
    public int getItemCount() {
        return holidayList.size();
    }
}











