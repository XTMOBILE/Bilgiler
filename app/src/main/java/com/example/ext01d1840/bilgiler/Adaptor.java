package com.example.ext01d1840.bilgiler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by EXT01D1840 on 6/1/2016.
 */
public class Adaptor extends ArrayAdapter<String>{
    String[] namex = {};
    String[] divisionx ={};
    String[] statusx = {};
    String[] start_datex = {};
    String[] end_datex = {};
    Context c;
    LayoutInflater inflater;

    public Adaptor(Context context, String[] name,String[] division,String[] status,String[] start_date,String[] end_date) {
        super(context, R.layout.school_list,name);

        this.c = context;
        this.namex = name;
        this.divisionx = division;
        this.start_datex = start_date;
        this.statusx = status;
        this.end_datex = end_date;
    }

    public class ViewHolder{
        TextView nameTv;
        TextView divisionTv;
        TextView statusTv;
        TextView startDateTv;
        TextView endDateTv;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.school_list,null);
        }

        final ViewHolder holder =new ViewHolder();
        holder.nameTv = (TextView) convertView.findViewById(R.id.tvName);
        holder.divisionTv = (TextView) convertView.findViewById(R.id.tvDivision);
        holder.statusTv = (TextView) convertView.findViewById(R.id.tvStatus);
        holder.startDateTv = (TextView) convertView.findViewById(R.id.tvStartDate);
        holder.endDateTv = (TextView) convertView.findViewById(R.id.tvEndDate);


        holder.nameTv.setText(namex[position]);
        holder.divisionTv.setText(divisionx[position]);
        holder.startDateTv.setText(start_datex[position]);
        holder.statusTv.setText(statusx[position]);
        holder.endDateTv.setText(end_datex[position]);


        return convertView;
    }
}
