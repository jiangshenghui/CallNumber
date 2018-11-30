package com.call.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.call.R;
import com.call.net.window.response.EntrySetBean;
import java.util.ArrayList;
import java.util.List;

public class WindowListAdapter extends BaseAdapter {

    Context mContext;

    LayoutInflater mLayoutInflater;

    public List<EntrySetBean> mList = new ArrayList<EntrySetBean>();

    public WindowListAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        WindowListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.shop_type_list_item1, null);
            holder = new WindowListAdapter.ViewHolder();
            holder.shopType =  convertView .findViewById(R.id.shop_type_item_txt);
//            holder.itemSelect = convertView.findViewById(R.id.item_select);
            convertView.setTag(holder);
        } else {
            holder = (WindowListAdapter.ViewHolder) convertView.getTag();
        }
        holder.shopType.setText(mList.get(position).windowName);
        return convertView;
    }
    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<EntrySetBean> addData(List<EntrySetBean> list) {
        if (list != null && list.size() > 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
        return mList;
    }
    class ViewHolder {
        TextView shopType;
//        ImageView itemSelect;
    }
}
