package com.call.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.bg.baseutillib.view.LayoutRaidoGroup;
import com.call.R;
import com.call.activity.adapter.NetWorkListAdapter;
import com.call.activity.adapter.WindowListAdapter;


public class SpinerPopWindow extends PopupWindow {
    private LayoutInflater inflater;
    private ListView mListView;
    private NetWorkListAdapter mAdapter;
    private WindowListAdapter windowListAdapter;
    public SpinerPopWindow(Context context, AdapterView.OnItemClickListener clickListener,NetWorkListAdapter mAdapter) {
        super(context);
        inflater=LayoutInflater.from(context);
//        this.list=list;
        this.mAdapter = mAdapter;
        init(clickListener);
    }
    public SpinerPopWindow(Context context, AdapterView.OnItemClickListener clickListener,WindowListAdapter mAdapter) {
        super(context);
        inflater=LayoutInflater.from(context);
        this.windowListAdapter = mAdapter;
        init(clickListener);
    }
    private void init(AdapterView.OnItemClickListener clickListener){
        View view = inflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LayoutRaidoGroup.LayoutParams.WRAP_CONTENT);
        setHeight(LayoutRaidoGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView =  view.findViewById(R.id.listview);
        if(mAdapter != null){
            mListView.setAdapter(mAdapter);
        }
        if(windowListAdapter != null){
            mListView.setAdapter(windowListAdapter);
        }
        mListView.setOnItemClickListener(clickListener);
    }
}