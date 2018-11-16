package com.bg.baseutillib.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bg.baseutillib.R;

import java.util.List;

/**
 * 列表基类适配器
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseListAdapter.BgViewHolder> {
    private List<T> mDataList;//数据源
    private LayoutInflater mInflater; //LayoutInflater
    public static final int TYPE_EMPTY = 9999;//空布局类型

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mDataList = setDataList();
    }

    @Override
    public BgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        if (viewType == TYPE_EMPTY) {
            return createEmptyViewHolder(parent);
        }

        int[] layoutsRes = setItemLayouts();
        if (layoutsRes == null) {
            return createEmptyViewHolder(parent);
        }

        return new BgViewHolder(mInflater.inflate(layoutsRes[viewType], parent, false));
    }

    @NonNull
    private BgViewHolder createEmptyViewHolder(ViewGroup parent) {
        int emptyDataItemLayoutRes = setEmptyDataItemLayout();
        if (emptyDataItemLayoutRes > 0) {
            return new BgViewHolder(mInflater.inflate(emptyDataItemLayoutRes, parent, false));
        } else {
            return new BgViewHolder(mInflater.inflate(R.layout.item_empty_prompt_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final BgViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_EMPTY) {
            return;
        }
        
        //item点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }

        bindData(getItemViewType(position), position, holder, mDataList);
    }

    /**
     * 设置item使用的布局
     *
     * @return
     */
    protected abstract int[] setItemLayouts();
    
    /**
     * 抽象设置布局类型的方法
     *
     * @param position 数据索引
     * @return 从1开始
     */
    public abstract int setItemViewType(int position);

    /**
     * 设置列表数据
     *
     * @return
     */
    protected abstract List<T> setDataList();

    /**
     * 绑定数据
     *
     * @param itemViewType 从0开始
     * @param position
     * @param viewHolder
     * @param dataList
     */
    protected abstract void bindData(int itemViewType, int position, BgViewHolder viewHolder, List<T> dataList);
    
    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.size() <= 0) {
            return 1;
        }

        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList == null || mDataList.size() <= 0) {
            return TYPE_EMPTY;
        }

        return setItemViewType(position);
    }
    
    /**
     * 设置没有数据时的布局
     *
     * @return 例如：R.layout.xxx
     */
    protected int setEmptyDataItemLayout() {
        return -1;
    }

    public static class BgViewHolder extends RecyclerView.ViewHolder {
        private View rootView;

        public BgViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
        }

        /**
         * 获取View
         *
         * @param viewId
         * @return
         */
        public View findViewById(int viewId) {
            return this.rootView.findViewById(viewId);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
