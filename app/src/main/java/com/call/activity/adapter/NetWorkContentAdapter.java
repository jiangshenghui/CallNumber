package com.call.activity.adapter;

import android.content.Context;
import android.util.Log;
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

public class NetWorkContentAdapter  extends BaseListAdapter<EntrySetBean> {

    @BindView(R.id.tvbespeakSort)
    TextView tvbespeakSort;
    @BindView(R.id.tvPerson)
    TextView tvPerson;
    @BindView(R.id.tvQueue)
    TextView tvQueue;
    @BindView(R.id.re_content_view)
    RelativeLayout reView;

    public List<EntrySetBean> mDataList = new ArrayList<EntrySetBean>();

    private Context context;

    public NetWorkContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected int[] setItemLayouts() {
        return new int[]{R.layout.item_queue_content};
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
        tvbespeakSort.setText(mDataList.get(position).bespeakSort);
        tvPerson.setText(mDataList.get(position).phone);
        tvQueue.setText("  "+mDataList.get(position).waitCount+"  ");
        if("5".equals(mDataList.get(position).queueState.trim())){//正在办理
            tvbespeakSort.setTextColor(context.getResources().getColor(R.color.color34));
            tvPerson.setTextColor(context.getResources().getColor(R.color.color34));
            tvPerson.setTextColor(context.getResources().getColor(R.color.color34));
        }else {
            tvbespeakSort.setTextColor(context.getResources().getColor(R.color.color3b));
            tvPerson.setTextColor(context.getResources().getColor(R.color.color3b));
            tvPerson.setTextColor(context.getResources().getColor(R.color.color3b));
        }
        if(position %2 ==0){
            reView.setBackgroundColor(context.getResources().getColor(R.color.colorf7));
        }else {
            reView.setBackgroundColor(context.getResources().getColor(R.color.colore3));

        }
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
