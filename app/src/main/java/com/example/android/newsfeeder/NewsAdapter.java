package com.example.android.newsfeeder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0 , news);
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent)
    {
        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items,parent,false);
        }

        News currentNews = getItem(position);

        TextView headingView = (TextView) listItemView.findViewById(R.id.heading);
        headingView.setText(currentNews.getHeading());

        TextView bodyView = (TextView) listItemView.findViewById(R.id.body);
        bodyView.setText(currentNews.getBody());

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(currentNews.getTime());

        return listItemView;
    }
}
