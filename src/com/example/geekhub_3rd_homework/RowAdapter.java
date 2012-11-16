package com.example.geekhub_3rd_homework;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RowAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> titles;
    private final ArrayList<String> dates;

    public RowAdapter(Context context, ArrayList<String> titles, ArrayList<String> dates) {
       super(context, R.layout.listview_row, titles);
        this.context = context;
        this.titles = titles;
        this.dates = dates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = inflater.inflate(R.layout.listview_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) rowView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        textView.setText(titles.get(position));
        textView2.setText(dates.get(position));
 
        return rowView;
    }
}