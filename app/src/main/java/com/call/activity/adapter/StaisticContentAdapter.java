package com.call.activity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bg.baseutillib.base.BaseListAdapter;
import com.call.R;
import com.call.net.window.response.EntrySetBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaisticContentAdapter  extends BaseListAdapter<EntrySetBean> {

    @BindView(R.id.tvBusinessName)
    TextView tvBusinessName;
    @BindView(R.id.tvGroupName)
    TextView tvGroupName;
    @BindView(R.id.tvCompleteNumber)
    TextView tvCompleteNumber;
    @BindView(R.id.tvPassNumber)
    TextView tvPassNumber;

    public List<EntrySetBean> mDataList = new ArrayList<EntrySetBean>();

    private Context context;

    public StaisticContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected int[] setItemLayouts() {
        return new int[]{R.layout.item_statistics_content};
    }

    @Override
    public int setItemViewType(int position) {
        return 0;
    }

    @Override
    protected List<EntrySetBean> setDataList() {
        return mDataList;
    }

    @Override
    protected void bindData(int itemViewType, final int position, BgViewHolder viewHolder, List<EntrySetBean> dataList) {
        ButterKnife.bind(this, viewHolder.itemView);
        tvBusinessName.setText(mDataList.get(position).GroupName);
        tvGroupName.setText(mDataList.get(position).BusinessName);
        tvCompleteNumber.setText(mDataList.get(position).CompleteNumber+"");
        tvPassNumber.setText(mDataList.get(position).PassNumber+"");
        //item点击事件
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }

    }

    public void setData(List<EntrySetBean> data) {
        if (data != null && data.size() > 0) {
            mDataList.clear();
            mDataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public List<EntrySetBean> addData(List<EntrySetBean> list) {
        if (list != null && list.size() > 0) {
            mDataList.clear();
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
        return mDataList;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    BaseListAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(BaseListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
