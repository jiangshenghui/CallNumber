package com.call.activity.service;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.activity.adapter.StaisticContentAdapter;
import com.call.activity.adapter.WindowListAdapter;
import com.call.dialog.SpinerPopWindow;
import com.call.net.login.request.CommonBody;
import com.call.net.login.request.ParamsSet;
import com.call.net.window.WindowDao;
import com.call.net.window.response.EntrySetBean;
import com.call.net.window.response.ServiceNetWorkBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
/***
 * 统计
 */
public class StatisticsActivity extends RvBaseActivity {

    private List<EntrySetBean> mDataList;

    private String netWorkName;
    public List<EntrySetBean> mList;

    @BindView(R.id.content_staistics_recycler)
    RecyclerView recyclerViewContent;
    @BindView(R.id.tvWindow)
    TextView tvWindow;
    @BindView(R.id.re_depart_windows)
    RelativeLayout reDepartWindows;


    private StaisticContentAdapter staisticContentAdapter;

   private String depId = "";

    private SpinerPopWindow mSpinerPopWindow;

    private WindowListAdapter windowsListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayoutResID() {
        return R.layout.activity_statistics;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent().getSerializableExtra("businessType") != null) {
            mList = (List<EntrySetBean>)getIntent().getExtras().get("businessType");
        }
        if (getIntent().getSerializableExtra("netWorkName") != null) {
            netWorkName =  getIntent().getStringExtra("netWorkName");
        }
        if (getIntent().getSerializableExtra("depId") != null) {
            depId =  getIntent().getStringExtra("depId");
        }
        staisticContentAdapter = new StaisticContentAdapter(this);
        recyclerViewContent.setLayoutManager(new LinearLayoutManager(this) );
        windowsListAdapter = new WindowListAdapter(this);
        getDepartmentWindow(depId);
    }

    /**
     * 获得窗口统计
     * @param id
     */
    private  void getWindowHandleStatictics(String id,String depId){
        CommonBody commonBody = new CommonBody();

        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "windowId";
        paramsSet.value = id;
        ParamsSet paramsSet1 = new ParamsSet();
        paramsSet1.name = "depId";
        paramsSet1.value = depId;
        paramsSetList.add(paramsSet);
        paramsSetList.add(paramsSet1);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).getWindowHandleStatictics(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null  &&serviceNetWorkBean.entrySet  != null && serviceNetWorkBean.entrySet.size() > 0) {
                    mDataList = serviceNetWorkBean.entrySet;
                    staisticContentAdapter.addData(serviceNetWorkBean.entrySet);
                    recyclerViewContent.setAdapter(staisticContentAdapter);//设置Adapter
                }else {
                    staisticContentAdapter.clearData();
                }
            }
            @Override
            public void onError(ApiException e) {
                staisticContentAdapter.clearData();
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
        ((WindowDao)createRequestData).getServiceWindows(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null && serviceNetWorkBean.entrySet != null&& serviceNetWorkBean.entrySet.size() > 0) {//登录成功
                    windowsListAdapter.clearData();
                    windowsListAdapter.addData(serviceNetWorkBean.entrySet);
                    mSpinerPopWindow = new SpinerPopWindow(StatisticsActivity.this,itemClickListener,windowsListAdapter);
                    mSpinerPopWindow.setOnDismissListener(dismissListener);
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
    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
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
            mSpinerPopWindow.dismiss();
            tvWindow.setText(windowsListAdapter.mList.get(position).windowName);
            tvWindow.setTag(windowsListAdapter.mList.get(position).id);
            getWindowHandleStatictics(windowsListAdapter.mList.get(position).id,depId);
        }
    };
    @Override
    public void initListener() {

    }

    @Override
    public WindowDao onCreateRequestData() {
        return new WindowDao();
    }

    @OnClick({R.id.re_depart_windows,R.id.re_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_depart_windows:// 部门窗口
                mSpinerPopWindow.setWidth(reDepartWindows.getWidth());
                mSpinerPopWindow.showAsDropDown(reDepartWindows);
                break;
            case R.id.re_back:
                finish();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}

