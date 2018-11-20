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
/**
 * Created by dr1 on 2017/7/12.
 */

public class NetWorkListAdapter extends BaseAdapter {

    Context mContext;

    LayoutInflater mLayoutInflater;

    public List<EntrySetBean> mList = new ArrayList<EntrySetBean>();

    public NetWorkListAdapter(Context context) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.shop_type_list_item1, null);
            holder = new ViewHolder();
            holder.shopType =  convertView .findViewById(R.id.shop_type_item_txt);
//            holder.itemSelect = convertView.findViewById(R.id.item_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        if(mList.get(position).isChoose){
////            holder.itemSelect.setVisibility(View.VISIBLE);
//            holder.shopType.setTextColor(mContext.getResources().getColor(R.color.green_00));
//        }else {
//            holder.shopType.setTextColor(mContext.getResources().getColor(R.color.grey_66));
////            holder.itemSelect.setVisibility(View.GONE);
//        }
        holder.shopType.setText(mList.get(position).name);
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
//        ImageView itemSelect;
    }
}
