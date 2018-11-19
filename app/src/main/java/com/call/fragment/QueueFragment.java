package com.call.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.call.R;
import com.call.RvBaseFragment;
import com.call.net.window.WindowDao;
import butterknife.BindView;

/**
 * 队列设置Fragment
 */

public class QueueFragment extends RvBaseFragment {

    @BindView(R.id.gv_classification_adivice)
    ListView listView;
    @BindView(R.id.gv_queue)
    GridView gridView;

    public int setLayoutResID() {
        return R.layout.fragment_queue;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public void initListener() {

    }

    public WindowDao onCreateRequestData() {
        return new WindowDao();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void getDepartmentData(){


//        for(int i = 0;i<billTypes.length ;i++){
//            IndustryTypeInfo itemal= new IndustryTypeInfo();
//            if(getResources().getString(R.string.all).equals(billTypes[i])){
//                itemal.isChoose = true;
//                itemal.busicode = "4";
//            }else {
//                itemal.busicode = (i-1)+"";
//            }
//            itemal.businame = billTypes[i];
//            mList.add(itemal);
//        }
//        mAdapter = new ShopTypeListAdapter(getActivity(), mList);
//        listView.setAdapter(mAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
