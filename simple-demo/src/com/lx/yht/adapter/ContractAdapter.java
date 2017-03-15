package com.lx.yht.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.yht.R;
import com.yunhetong.sdk.base.bean.YhtContract;

import java.util.List;


/**
 * author  : kangpeng on 2016/5/25 0025.
 * email   : kangpeng@yunhetong.net
 */
public class ContractAdapter extends BaseListViewAdapter<YhtContract> {

    public ContractAdapter(Context context, List<YhtContract> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_contract_list_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_contract_title);
            viewHolder.status = (TextView) convertView.findViewById(R.id.tv_contract_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final YhtContract beanData = getItem(position);
        if (!TextUtils.isEmpty(beanData.getTitle()) && !TextUtils.isEmpty(beanData.getStatus())) {
            viewHolder.title.setText(beanData.getTitle());
            viewHolder.status.setText(beanData.getStatus());
        } else {
            viewHolder.title.setText(String.valueOf(beanData.getId()));
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView title;
        public TextView status;
    }
}

