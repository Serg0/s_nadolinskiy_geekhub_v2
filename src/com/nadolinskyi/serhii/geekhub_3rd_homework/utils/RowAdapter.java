package com.nadolinskyi.serhii.geekhub_3rd_homework.utils;

import java.util.List;

import com.nadolinskyi.serhii.geekhub_3rd_homework.R;
import com.nadolinskyi.serhii.geekhub_3rd_homework.R.id;
import com.nadolinskyi.serhii.geekhub_3rd_homework.R.layout;
import com.nadolinskyi.serhii.geekhub_3rd_homework.models.Article;

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
	private static List<Article> articles;

	public RowAdapter(Context context, List<Article> OuterArticles) {


		super(context, R.layout.listview_row, OuterArticles);
		this.context = context;
		this.articles = OuterArticles;
		Log.d(LOG_TAG, "RowAdapter constructor ");
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_row, parent, false);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.textView1);
		TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
		Article article = articles.get(position);
		if (article != null) {
			textView.setText(article.getTitle());
			textView2.setText(article.getPublished());
		} else {
		}

		return convertView;
	}
}