package com.call.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseFragment;
import com.call.activity.adapter.NetWorkListAdapter;
import com.call.activity.adapter.QueuetAdapter;
import com.call.activity.adapter.WindowListAdapter;
import com.call.activity.service.ServiceNetWorkActivity;
import com.call.dialog.SpinerPopWindow;
import com.call.net.login.request.CommonBody;
import com.call.net.login.request.ParamsSet;
import com.call.net.window.WindowDao;
import com.call.net.window.response.EntrySetBean;
import com.call.net.window.response.ServiceNetWorkBean;
import com.call.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
/**
 * 队列设置Fragment
 */

public class QueueFragment extends RvBaseFragment {

    @BindView(R.id.tv_network)
    public TextView tvNetwork;
    @BindView(R.id.tv_windows)
    public  TextView tvWindows;
    @BindView(R.id.re_depart_windows_sa)
    RelativeLayout reDepartWindows;
    @BindView(R.id.re_depart_net_sa)
    RelativeLayout reDepartNet;
    @BindView(R.id.gv_queue)
    GridView girdQueue;


    private NetWorkListAdapter netListAdapter;
    private WindowListAdapter windowsListAdapter;
    private SpinerPopWindow mSpinerPopNet,mSpinerPopWindow;
    public   QueuetAdapter queuetAdapter;

    private String networkname = "";
    private String networkId ="";
    private String windowName ="";
    private String windowId = "";

    public int setLayoutResID() {
        return R.layout.fragment_queue;
    }
    private String json;
    List<EntrySetBean> alterSamples;
    @Override
    public void initData(Bundle savedInstanceState) {
        networkname =  SharedPreferencesUtil.readString("networkname");
        networkId = SharedPreferencesUtil.readString("networkid");

        windowName = SharedPreferencesUtil.readString("windowName");
        windowId = SharedPreferencesUtil.readString("windowId");
        json = SharedPreferencesUtil.readString("alterSampleJson");
        if(!StringUtils.isEmpty(json)){
            Gson gson = new Gson();
            Type type = new TypeToken<List<EntrySetBean>>(){}.getType();
            alterSamples = new ArrayList<EntrySetBean>();
            alterSamples = gson.fromJson(json, type);

        }
        netListAdapter = new NetWorkListAdapter(getActivity());
        windowsListAdapter = new WindowListAdapter(getActivity());
        queuetAdapter = new QueuetAdapter(getActivity());
        girdQueue.setAdapter(queuetAdapter);

        getDepartmentData();

        if(!TextUtils.isEmpty(networkname)){
            tvNetwork.setText(networkname);
            tvNetwork.setTag(networkId);
            getDepartmentWindow(networkId);
            getDepartmentQueue(networkId);
        }
        if(!TextUtils.isEmpty(windowName)){
            tvWindows.setText(windowName);
            tvWindows.setTag(windowId);
        }
        girdQueue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //                    for (EntrySetBean info: mList){
//                        info.isChoose = false;
//                    }
                queuetAdapter.mList.get(position).isChoose = !queuetAdapter.mList.get(position).isChoose;
                queuetAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.re_depart_windows, R.id.re_depart_net})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_depart_windows:// 部门窗口
                if(tvNetwork.getText().toString().trim().equals("请选择网点")){
                    ToastUtil.showShortToast("请选择网点");
                    return;
                }
                mSpinerPopWindow.setWidth(reDepartWindows.getWidth());
                mSpinerPopWindow.showAsDropDown(reDepartWindows);
                break;
            case R.id.re_depart_net://部门网点
                mSpinerPopNet.setWidth(reDepartNet.getWidth());
                mSpinerPopNet.showAsDropDown(reDepartNet);
//                setTextImage(R.drawable.icon_up);
                break;
        }
    }
    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        tvNetwork.setCompoundDrawables(null, null, drawable, null);
    }
    public WindowDao onCreateRequestData() {
        return new WindowDao();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void getDepartmentData(){
        CommonBody commonBody = new CommonBody();
        ((WindowDao)createRequestData).getServiceNetWork(getActivity(), commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null&& serviceNetWorkBean.entrySet != null && serviceNetWorkBean.entrySet.size() > 0) {
                    netListAdapter.addData(serviceNetWorkBean.entrySet);
                    mSpinerPopNet = new SpinerPopWindow(getActivity(),itemClickListener,netListAdapter);
                    mSpinerPopNet.setOnDismissListener(dismissListener);

                }
            }
            @Override
            public void onError(ApiException e) {
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });

    }
    private void getDepartmentWindow(String id){
        CommonBody commonBody = new CommonBody();
        ParamsSet paramsSet = new ParamsSet();
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        paramsSet.name = "depId";
        paramsSet.value = id;
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).getServiceWindows(getActivity(), commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null && serviceNetWorkBean.entrySet != null&& serviceNetWorkBean.entrySet.size() > 0) {//登录成功
                    windowsListAdapter.clearData();
                    windowsListAdapter.addData(serviceNetWorkBean.entrySet);
                    mSpinerPopWindow = new SpinerPopWindow(getActivity(),itemClickListener1,windowsListAdapter);
                    mSpinerPopWindow.setOnDismissListener(dismissListener1);
                }
            }
            @Override
            public void onError(ApiException e) {
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });

    }

    /***
     * 获取网点队列数
     * @param id
     */
    private void getDepartmentQueue(String id){
        CommonBody commonBody = new CommonBody();
        ParamsSet paramsSet = new ParamsSet();
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        paramsSet.name = "depId";
        paramsSet.value = id;
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).getDepartGroupList(getActivity(), commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null && serviceNetWorkBean.entrySet != null && serviceNetWorkBean.entrySet.size() > 0) {//登录成功
                    if(alterSamples != null && alterSamples.size() > 0){
                        for(EntrySetBean eSetBean:serviceNetWorkBean.entrySet){
                            for(EntrySetBean entrySetBean:alterSamples){
                                if(eSetBean.id.equals(entrySetBean.id)&& entrySetBean.isChoose){
                                    eSetBean.isChoose = true;
                                    break;
                                }
                            }
                        }
                    }
                    queuetAdapter.clearData();
                    queuetAdapter.addData(serviceNetWorkBean.entrySet);
                }
            }
            @Override
            public void onError(ApiException e) {
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopNet.dismiss();
            tvNetwork.setText(netListAdapter.mList.get(position).name);
            tvNetwork.setTag(netListAdapter.mList.get(position).id);
            SharedPreferencesUtil.writeString("networkname",netListAdapter.mList.get(position).name);
            SharedPreferencesUtil.writeString("networkid",netListAdapter.mList.get(position).id);
            getDepartmentWindow(netListAdapter.mList.get(position).id);
            getDepartmentQueue(netListAdapter.mList.get(position).id);
        }
    };
    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener1=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener1 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow.dismiss();
            tvWindows.setText(windowsListAdapter.mList.get(position).windowName);
            tvWindows.setTag(windowsListAdapter.mList.get(position).id);
            SharedPreferencesUtil.writeString("windowName",windowsListAdapter.mList.get(position).windowName);
            SharedPreferencesUtil.writeString("windowId",windowsListAdapter.mList.get(position).id);
        }
    };

}
