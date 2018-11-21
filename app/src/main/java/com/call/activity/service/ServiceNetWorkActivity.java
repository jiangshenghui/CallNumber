package com.call.activity.service;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.bg.baseutillib.base.BaseListAdapter;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.activity.adapter.NetWorkContentAdapter;
import com.call.activity.adapter.ServiceNetWorkAdapter;
import com.call.activity.adapter.WaitPersonAdapter;
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
 * 服务窗口
 */
public class ServiceNetWorkActivity extends RvBaseActivity {


    @BindView(R.id.tvNetWorkName)
    TextView tvNetWorkName;

    private ServiceNetWorkAdapter serviceNetWorkAdapter;
    private List<EntrySetBean> mDataList;

    private String netWorkName;
    public List<EntrySetBean> mList;

    @BindView(R.id.bussiness_recycler)
    RecyclerView recyclerViewHead;
    @BindView(R.id.content_recycler)
    RecyclerView recyclerViewContent;

    @BindView(R.id.wait_recycler)
    RecyclerView waitRecycler;

    private NetWorkContentAdapter netWorkContentAdapter;

    private WaitPersonAdapter waitPersonAdapter;

    private String depId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayoutResID() {
        return R.layout.activity_service_network;
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
        tvNetWorkName.setText(netWorkName);
        serviceNetWorkAdapter = new ServiceNetWorkAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHead.setLayoutManager(linearLayoutManager);
        if(mList != null && mList.size() > 0){
            mList.get(0).isHeadChoose = true;
        }
        serviceNetWorkAdapter.addData(mList);
        recyclerViewHead.setAdapter(serviceNetWorkAdapter);

        netWorkContentAdapter = new NetWorkContentAdapter(this);
        recyclerViewContent.setLayoutManager(new LinearLayoutManager(this) );
        if(mList != null && mList.size() > 0){
            getServiceNetWork(mList.get(0).id);
        }

        waitPersonAdapter = new WaitPersonAdapter(this);
        waitRecycler.setLayoutManager(new LinearLayoutManager(this) );

        getWaitNumber();
        serviceNetWorkAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (EntrySetBean info: serviceNetWorkAdapter.mDataList){
                        info.isHeadChoose = false;
               }
                serviceNetWorkAdapter.mDataList.get(position).isHeadChoose = true;
                serviceNetWorkAdapter.notifyDataSetChanged();
                getServiceNetWork(mList.get(position).id);
            }
        });
    }
    private  void getServiceNetWork(String id){
        CommonBody commonBody = new CommonBody();

        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
//        for (int i = 0; i < mList.size(); i++) {
//            if(mList.get(i).isChoose){
//                ParamsSet paramsSet = new ParamsSet();
//                paramsSet.name = "groupId";
//                paramsSet.value = mList.get(i).id;
//                paramsSetList.add(paramsSet);
//            }
//        }
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "groupId";
        paramsSet.value = id;
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).getGroupQueueList(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null  &&serviceNetWorkBean.entrySet  != null && serviceNetWorkBean.entrySet.size() > 0) {
                    mDataList = serviceNetWorkBean.entrySet;
                    netWorkContentAdapter.addData(serviceNetWorkBean.entrySet);
                    recyclerViewContent.setAdapter(netWorkContentAdapter);//设置Adapter
                }else {
                    netWorkContentAdapter.clearData();
                }
            }
            @Override
            public void onError(ApiException e) {
                netWorkContentAdapter.clearData();
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }
    private  void getWaitNumber(){
        CommonBody commonBody = new CommonBody();
        String groupIds = "";
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).isChoose){
                groupIds += mList.get(i).id+"#";
            }
        }
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "groupIds";
        paramsSet.value = groupIds;
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).getGroupWaitNumber(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null &&serviceNetWorkBean.entrySet  != null && serviceNetWorkBean.entrySet.size() > 0) {
                    waitPersonAdapter.addData(serviceNetWorkBean.entrySet);
                    waitRecycler.setAdapter(waitPersonAdapter);//设置Adapter
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
    public void initListener() {

    }

    @Override
    public WindowDao onCreateRequestData() {
        return new WindowDao();
    }

    @OnClick({R.id.reTongji,R.id.reJiesan,R.id.rePause,R.id.reGuohao,R.id.reNext,R.id.reReCall,R.id.re_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reTongji:
                Bundle bundle = new Bundle();
                bundle.putSerializable("depId",depId);
                startActivity(StatisticsActivity.class,bundle);
                break;
            case R.id.reJiesan:
                break;
            case R.id.rePause:
                break;
            case R.id.reGuohao:
                break;
            case R.id.reNext:
                break;
            case R.id.reReCall:
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

