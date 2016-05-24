package com.sdust.zhihudaily.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.util.DateUtils;

/**
 * Created by Kevin on 2015/8/9.
 */
public class DateViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvDate;
    public DateViewHolder(View itemView) {
        super(itemView);
        mTvDate = (TextView)itemView.findViewById(R.id.date);
    }

    public void bindDateView(String date) {
        mTvDate.setText(getDate(date, itemView.getContext()));
    }

    public static String getDate(String date, Context context) {
        if (DateUtils.isToday(date)) {
            return context.getResources().getString(R.string.recycler_item_main_today_hottest);
        } else {
            return DateUtils.getMainPageDate(date);
        }
    }
}
