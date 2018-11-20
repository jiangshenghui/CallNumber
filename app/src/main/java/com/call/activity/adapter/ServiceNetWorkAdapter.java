package com.call.activity.adapter;

import android.view.View;
import android.widget.TextView;


import com.bg.baseutillib.base.BaseListAdapter;
import com.call.R;
import com.call.net.window.response.EntrySetBean;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceNetWorkAdapter extends BaseListAdapter<EntrySetBean> {

    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.tv_vip_desc)
    TextView tvDesc;

    private List<EntrySetBean> mDataList = new ArrayList<EntrySetBean>();

    public ServiceNetWorkAdapter() {

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
            tvDesc.setText(mDataList.get(position).name);
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

        private OnBtnClickListener mOnBtnClickListener;

        public interface OnBtnClickListener {
            void onDetails(int position);

            void onDoRent(int position);

            void onAgain(int position);

            void onComment(int position);

            void onDoing(int position);

            void onDoPay(int position);
        }

        public void setOnBtnClickListener(OnBtnClickListener listener) {
            mOnBtnClickListener = listener;
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
