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

    private String depId;//网点Id

    private String groupId;//队列ID

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
        recyclerViewContent.setAdapter(netWorkContentAdapter);//设置Adapter
        recyclerViewContent.setLayoutManager(new LinearLayoutManager(this) );
        if(mList != null && mList.size() > 0){
            groupId  = mList.get(0).id;
            getGroupQueueList(groupId);
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
                groupId = mList.get(position).id;
                getGroupQueueList(groupId);
            }
        });
        netWorkContentAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (EntrySetBean info: netWorkContentAdapter.mDataList){
                    info.isChoose = false;
                }
                netWorkContentAdapter.mDataList.get(position).isChoose = !netWorkContentAdapter.mDataList.get(position).isChoose;
                netWorkContentAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取队列排队列表
     * @param id
     */
    private  void getGroupQueueList(String id){
        CommonBody commonBody = new CommonBody();
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "groupId";
        paramsSet.value = id;
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).getGroupQueueList(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null && "0".equals(serviceNetWorkBean.status) &&serviceNetWorkBean.entrySet  != null && serviceNetWorkBean.entrySet.size() > 0) {
                    mDataList = serviceNetWorkBean.entrySet;
                    netWorkContentAdapter.addData(serviceNetWorkBean.entrySet);
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

    @OnClick({R.id.reTongji,R.id.reJiesan,R.id.rePause,R.id.reGuohao,R.id.reNext,R.id.reReCall,R.id.re_back
       ,R.id.btnSet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reTongji:
                Bundle bundle = new Bundle();
                bundle.putSerializable("depId",depId);
                startActivity(StatisticsActivity.class,bundle);
                break;
            case R.id.reJiesan:
                EntrySetBean entrySetBean = getEntrySetBean();
                if(entrySetBean == null){
                    ToastUtil.showShortToast("暂无排队人解散");
                    return;
                }
                clientDissolutionQueue(groupId);
                break;
            case R.id.rePause:
                entrySetBean = getEntrySetBean();
                if(entrySetBean == null){
//                    ToastUtil.showShortToast("请选择用户");
                    return;
                }
                clientPauseCall(entrySetBean.id);//队列id
                break;
            case R.id.reGuohao:
                 entrySetBean = getEntrySetBean();
                if(entrySetBean == null){
//                    ToastUtil.showShortToast("请选择用户");
                    return;
                }
                clientPassCall(entrySetBean.id,entrySetBean.groupId);
                break;
            case R.id.reNext:
                entrySetBean = getEntrySetBean();
                if(entrySetBean == null){
//                    ToastUtil.showShortToast("请选择用户");
                    return;
                }
                clientCallNext(entrySetBean.groupId,entrySetBean.windowId,entrySetBean.userId);
                break;
            case R.id.reReCall:
                entrySetBean = getEntrySetBean();
                if(entrySetBean == null){
//                    ToastUtil.showShortToast("请选择用户");
                    return;
                }
                clientReCall(entrySetBean.id);
                break;
            case R.id.re_back:
                finish();
                break;
            case R.id.btnSet:
                startActivity(MiniActivity.class);
                break;
        }
    }
    private EntrySetBean getEntrySetBean(){
        EntrySetBean entrySetBean = null;
        if( netWorkContentAdapter.mDataList != null &&  netWorkContentAdapter.mDataList.size() > 0){
            return  netWorkContentAdapter.mDataList.get(0);
        }
        return entrySetBean;
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    /***
     * 解散
     * @param groupId
     */
    private  void clientDissolutionQueue(String groupId){
        CommonBody commonBody = new CommonBody();
        String groupIds = "";
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "groupId";
        paramsSet.value = groupId;//队列id
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).clientDissolutionQueue(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null &&"0".equals(serviceNetWorkBean.status)) {
                   ToastUtil.showShortToast("解散成功");
                }else{
                    ToastUtil.showShortToast("解散失败");
                }
            }
            @Override
            public void onError(ApiException e) {
                ToastUtil.showShortToast("解散失败");
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }
    /***
     * 暂停
     * @param queueId
     */
    private  void clientPauseCall(String queueId){
        CommonBody commonBody = new CommonBody();
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "queueId";
        paramsSet.value = queueId;//队列id
        paramsSetList.add(paramsSet);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).clientPauseCall(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null &&"0".equals(serviceNetWorkBean.status)) {
                    ToastUtil.showShortToast("操作成功");
                }else{
                    ToastUtil.showShortToast("操作失败");
                }
            }
            @Override
            public void onError(ApiException e) {
                ToastUtil.showShortToast("操作失败");
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }
    /***
     * 过号
     * @param queueId
     */
    private  void clientPassCall(String queueId,String groupId){
        CommonBody commonBody = new CommonBody();
        String groupIds = "";
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "queueId";
        paramsSet.value = queueId;//队列id

        ParamsSet paramsSet1 = new ParamsSet();
        paramsSet1.name = "groupId";
        paramsSet1.value = groupId;//网点id
        paramsSetList.add(paramsSet);
        paramsSetList.add(paramsSet1);
        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).clientPassCall(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null &&"0".equals(serviceNetWorkBean.status)) {
                    ToastUtil.showShortToast("操作成功");
                }else{
                    ToastUtil.showShortToast("操作失败");
                }
            }
            @Override
            public void onError(ApiException e) {
                ToastUtil.showShortToast("操作失败");
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }

    /***
     * 下一位
     * @param groupId
     */
    private  void clientCallNext(String groupId,String windowId,String userId){
        CommonBody commonBody = new CommonBody();
        String groupIds = "";
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();

        ParamsSet paramsWindow = new ParamsSet();
        paramsWindow.name = "groupId";
        paramsWindow.value = groupId;//队列id

        ParamsSet paramsSetGroup = new ParamsSet();
        paramsSetGroup.name = "windowId";
        paramsSetGroup.value = windowId;//网点id

        ParamsSet paramsSetUser = new ParamsSet();
        paramsSetUser.name = "userId";
        paramsSetUser.value = userId;//网点id

        paramsSetList.add(paramsWindow);
        paramsSetList.add(paramsSetGroup);
        paramsSetList.add(paramsSetUser);

        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).clientCallNext(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null &&"0".equals(serviceNetWorkBean.status)) {
                    ToastUtil.showShortToast("操作成功");
                }else{
                    ToastUtil.showShortToast("操作失败");
                }
            }
            @Override
            public void onError(ApiException e) {
                ToastUtil.showShortToast("操作成功");
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }
    /***
     * 重呼
     * @param queueId
     */
    private  void clientReCall(String queueId ){
        CommonBody commonBody = new CommonBody();
        String groupIds = "";
        List<ParamsSet> paramsSetList = new ArrayList<ParamsSet>();
        ParamsSet paramsSet = new ParamsSet();
        paramsSet.name = "queueId";
        paramsSet.value = queueId;//队列id
        paramsSetList.add(paramsSet);

        commonBody.paramsSet = paramsSetList;
        ((WindowDao)createRequestData).clientReCall(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null &&"0".equals(serviceNetWorkBean.status)) {
                    ToastUtil.showShortToast("重呼成功");
                }else{
                    ToastUtil.showShortToast("重呼失败");
                }
            }
            @Override
            public void onError(ApiException e) {
                ToastUtil.showShortToast("重呼失败");
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }

}

