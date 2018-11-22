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

public class StaisticHeadAdapter extends BaseListAdapter<EntrySetBean> {

    @BindView(R.id.tv_vip_desc)
    TextView tvDesc;

    @BindView(R.id.re_view)
    RelativeLayout reView;

    public List<EntrySetBean> mDataList = new ArrayList<EntrySetBean>();

    private Context context;

    public StaisticHeadAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected int[] setItemLayouts() {
        return new int[]{R.layout.item_partner};
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
        String groupname = mDataList.get(position).name;
        tvDesc.setText(groupname);
        reView.setBackgroundColor(context.getResources().getColor(R.color.color34));
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
