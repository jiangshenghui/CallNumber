package com.call.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
/**
 * 队列设置Fragment
 */

public class QueueFragment extends RvBaseFragment {

    @BindView(R.id.tv_network)
    TextView tvNetwork;
    @BindView(R.id.tv_windows)
    TextView tvWindows;
    @BindView(R.id.re_depart_windows)
    RelativeLayout reDepartWindows;
    @BindView(R.id.re_depart_net)
    RelativeLayout reDepartNet;
    @BindView(R.id.gv_queue)
    GridView girdQueue;


    private NetWorkListAdapter netListAdapter;
    private WindowListAdapter windowsListAdapter;
    private SpinerPopWindow mSpinerPopNet,mSpinerPopWindow;
    private QueuetAdapter queuetAdapter;

    public int setLayoutResID() {
        return R.layout.fragment_queue;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        netListAdapter = new NetWorkListAdapter(getActivity());
        windowsListAdapter = new WindowListAdapter(getActivity());
        queuetAdapter = new QueuetAdapter(getActivity());
        girdQueue.setAdapter(queuetAdapter);

        getDepartmentData();



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
    @OnClick({R.id.re_depart_windows, R.id.re_depart_net,R.id.btnSure})
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
            case R.id.btnSure:
                Bundle bundle = new Bundle();
                boolean isChoose = false;
                 List<EntrySetBean> mList = new ArrayList<EntrySetBean>();
                 for(EntrySetBean eBean :queuetAdapter.mList){
                   if(eBean.isChoose){
                       isChoose = true;
                       mList.add(eBean);
                   }
                }
                if(!isChoose){
                    ToastUtil.showShortToast("请选择排队队列");
                    return;
                }
                bundle.putSerializable("businessType",(Serializable) mList);
                bundle.putSerializable("netWorkName",tvNetwork.getText().toString());
                if(tvNetwork.getTag() != null){
                    bundle.putSerializable("depId",tvNetwork.getTag().toString());
                }
                startActivity(ServiceNetWorkActivity.class,bundle);
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
        }
    };

}
