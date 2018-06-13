package com.example.daniel.hcs.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daniel.hcs.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    private List<Pill> Pills;

    public CustomAdapter(List<Pill> pills) { Pills = pills; }

    @Override
    public int getCount() {
        return Pills.size();
    }

    @Override
    public Object getItem(int i) {
        return Pills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Pills.get(i).getId();
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
        pillViewHolder.tvNumberOfIntakes.setText(String.valueOf(pill.getNumberOfIntakes()));
        pillViewHolder.tvDescription.setText(String.valueOf(pill.getDescription()));

        return convertView;
    }

    public void insert(List<Pill> Pills) {
        this.Pills = Pills;
        Log.e("Pill", "NOTIFY " + Pills.size());
        this.notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView tvPill, tvDescription, tvNumberOfIntakes;

        public ViewHolder(View bookView) {
            tvPill = bookView.findViewById(R.id.tvPill);
            tvDescription = bookView.findViewById(R.id.tvDescription);
            tvNumberOfIntakes = bookView.findViewById(R.id.tvNumberOfIntakes);
        }
    }
}
