package com.example.geekhub_3rd_homework;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RowAdapter extends ArrayAdapter<Article> {
    private static final String LOG_TAG = "ROW ADAPTER";
	private final Context context;
    private static  List<Article> articles;
    //private final ArrayList<String> dates;
//    private final ArrayList<String> titles;
//    private final ArrayList<String> dates;


    public RowAdapter(Context context, List<Article> OuterArticles) {
    	//Log.d(LOG_TAG, "Article is null ");
     //  int i = super.getCount();
    
       super(context, R.layout.listview_row, OuterArticles);
        this.context = context;
        this.articles = OuterArticles;
       // this.addAll(articles);
        Log.d(LOG_TAG, "RowAdapter constructor ");
    }

//    public RowAdapter(Context context, ArrayList<String> titles, ArrayList<String> dates, List<Article> OuterArticles) {
//        super(context, R.layout.listview_row, titles);
//         this.context = context;
//         this.titles = titles;
//         this.dates = dates;
//         this.articles = OuterArticles;
//     }
    
    
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    //	Log.d(LOG_TAG, " LayoutInflater inflater");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
       // Log.d(LOG_TAG, " View rowView; ");
       rowView = inflater.inflate(R.layout.listview_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) rowView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        Article article = articles.get(position);
        if (article!= null) {
      //  Log.d(LOG_TAG, "Article is OK "+Integer.toString(position));
        textView.setText(article.getTitle());
        textView2.setText(article.getPublished());
        }else
        {
       // 	Log.d(LOG_TAG, "Article is null "+Integer.toString(position));
        }
        
        return rowView;
    }
}