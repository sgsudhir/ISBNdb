package com.isbndb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isbndb.R;

/**
 * Created by Sgsudhir on 2/28/2016.
 */
public class ListViewAdapter extends BaseAdapter {
    int typeOfElements;
    String[] titles;
    String[] titleTwo;
    String[] titleThree;
    Context context;
    LayoutInflater layoutInflater;
    public ListViewAdapter(int typeOfElements, String[] titles, String[] titleTwo, String[] titleThree, Context context) {
        this.typeOfElements = typeOfElements;
        this.titles = titles;
        this.titleTwo = titleTwo;
        this.titleThree = titleThree;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.titles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        TextView textViewFirst, textViewSecond, textViewThird;
        if (this.typeOfElements == 3) {
            row = layoutInflater.inflate(R.layout.list_view_single_row_three_elements, parent, false);
            textViewFirst = (TextView) row.findViewById(R.id.text_view_title_one_single_row_three_elements);
            textViewSecond = (TextView) row.findViewById(R.id.text_view_title_two_single_row_three_elements);
            textViewThird = (TextView) row.findViewById(R.id.text_view_title_three_single_row_three_elements);
            textViewFirst.setText(this.titles[position]);
            textViewSecond.setText(this.titleTwo[position]);
            textViewThird.setText(this.titleThree[position]);
            return row;
        } else if (this.typeOfElements ==2) {
            row = layoutInflater.inflate(R.layout.list_view_single_row_two_elements, parent, false);
            textViewFirst = (TextView) row.findViewById(R.id.text_view_title_one_single_row_two_elements);
            textViewSecond = (TextView) row.findViewById(R.id.text_view_title_two_single_row_two_elements);
            textViewFirst.setText(this.titles[position]);
            textViewSecond.setText(this.titleTwo[position]);
            return row;
        } else if (this.typeOfElements ==1) {
            row = layoutInflater.inflate(R.layout.list_view_single_row_one_elements, parent, false);
            textViewFirst = (TextView) row.findViewById(R.id.text_view_title_one_single_row_one_element);
            textViewFirst.setText(this.titles[position]);
            return row;
        }
        return null;
    }
}
