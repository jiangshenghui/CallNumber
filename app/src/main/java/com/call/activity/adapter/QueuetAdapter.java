package com.call.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.call.R;
import com.call.net.window.response.EntrySetBean;

import java.util.ArrayList;
import java.util.List;

public class QueuetAdapter extends BaseAdapter {

    Context mContext;

    LayoutInflater mLayoutInflater;

    public List<EntrySetBean> mList = new ArrayList<EntrySetBean>();

    public QueuetAdapter(Context context) {
        super();
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        QueuetAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_queue_list, null);
            holder = new QueuetAdapter.ViewHolder();
            holder.shopType =  convertView .findViewById(R.id.shop_type_item_txt);
            holder.radioButton = convertView.findViewById(R.id.radioBtn);
            convertView.setTag(holder);

            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (EntrySetBean info: mList){
                        info.isChoose = false;
                    }
                    mList.get(position).isChoose = true;
                    notifyDataSetChanged();
                }
            });
        } else {
            holder = (QueuetAdapter.ViewHolder) convertView.getTag();
        }
        if(mList.get(position).isChoose){
            holder.radioButton.setChecked(true);
        }else {
            holder.radioButton.setChecked(false);
        }
        String groupname = mList.get(position).groupname;
        if(!TextUtils.isEmpty(groupname)){
          int index = groupname.indexOf("--");
          groupname = groupname.substring(index+2,groupname.length());
          holder.shopType.setText(groupname);
        }

        return convertView;
    }
    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<EntrySetBean> addData(List<EntrySetBean> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
        return mList;
    }
    class ViewHolder {
        TextView shopType;
        RadioButton radioButton;
    }
}
