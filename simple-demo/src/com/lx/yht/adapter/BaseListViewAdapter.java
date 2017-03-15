package com.lx.yht.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    public List<T> dataList = new ArrayList<T>();
    protected Context context;

    public BaseListViewAdapter(Context context) {
        this.context = context;
    }

    public BaseListViewAdapter(Context context, List<T> data) {
        this(context);
        this.dataList = data;
    }

    public void addData(ArrayList<T> dataList) {
        for (T t : dataList) {
            this.dataList.add(t);
        }
        this.notifyDataSetChanged();
    }

    public void setData(ArrayList<T> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        try {
            return dataList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public T getItem(int position) {
        try {
            return dataList.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

