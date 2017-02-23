package com.kenplayschool.app_adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kenplayschool.R;

import java.util.ArrayList;

/**
 * Created by Kranti on 22/3/2016.
 */
public class LocationSpinnerAdapter extends ArrayAdapter<String> {

    Context ctx;
    ArrayList<String> spinnerdata;

    public LocationSpinnerAdapter(Context ctx, int txtViewResourceId, ArrayList<String> spinnerdata) {
        super(ctx, txtViewResourceId, spinnerdata);
        this.ctx = ctx;
        this.spinnerdata = spinnerdata;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mySpinner = inflater.inflate(R.layout.spinner_text, parent, false);
        AppCompatTextView main_text = (AppCompatTextView) mySpinner.findViewById(R.id.spinner_text);
        main_text.setText(spinnerdata.get(position));
        return mySpinner;
    }
}


