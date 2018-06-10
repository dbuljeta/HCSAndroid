package com.example.daniel.hcs.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daniel.hcs.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
    private ArrayList<Pill> Pills;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder pillViewHolder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            pillViewHolder = new ViewHolder(convertView);
            convertView.setTag(pillViewHolder);
        }
        else{
            pillViewHolder = (ViewHolder) convertView.getTag();
        }
        Pill pill = this.Pills.get(position);
        pillViewHolder.tvPill.setText(pill.getName());
//        pillViewHolder.tvTimeIntake.setText(pill.tim());
        pillViewHolder.tvNumberOfIntakes.setText(String.valueOf(pill.getNumberOfIntakes()));
        return convertView;
    }

    public void insert(Pill pill) {
        this.Pills.add(pill);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView tvPill, tvTimeIntake, tvNumberOfIntakes;
        public ViewHolder(View bookView) {
            tvPill = (TextView) bookView.findViewById(R.id.tvPill);
            tvTimeIntake = (TextView) bookView.findViewById(R.id.tvTimeIntake);
            tvNumberOfIntakes = (TextView) bookView.findViewById(R.id.tvNumberOfIntakes);
        }
    }
}
