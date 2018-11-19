package com.call.activity.service;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.bg.baseutillib.base.BaseListAdapter;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.activity.adapter.ServiceNetWorkAdapter;
import com.call.net.login.request.CommonBody;
import com.call.net.window.WindowDao;
import com.call.net.window.response.EntrySetBean;
import com.call.net.window.response.ServiceNetWorkBean;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/***
 * 服务窗口
 */
public class ServiceNetWorkActivity extends RvBaseActivity {


    @BindView(R.id.service_network_recycler)
    RecyclerView recyclerView;

    private ServiceNetWorkAdapter serviceNetWorkAdapter;
    private List<EntrySetBean> mDataList;

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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.GRAY);
//        }
        serviceNetWorkAdapter = new ServiceNetWorkAdapter();
        getServiceNetWork();
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                int count = state.getItemCount();

                if (count > 0) {
                    if(count>4){
                        count =4;
                    }
                    int realHeight = 0;
                    int realWidth = 0;
                    for(int i = 0;i < count; i++){
                        View view = recycler.getViewForPosition(0);
                        if (view != null) {
                            measureChild(view, widthSpec, heightSpec);
                            int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                            int measuredHeight = view.getMeasuredHeight();
                            realWidth = realWidth > measuredWidth ? realWidth : measuredWidth;
                            realHeight += measuredHeight;
                        }
                        setMeasuredDimension(realWidth, realHeight);
                    }
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
        serviceNetWorkAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("jsh","position:"+position);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", mDataList.get(position).getId());
//                bundle.putString("distance", mDataList.get(position).getDistance());
//                startActivity(CarDetailActivity.class, bundle);
            }
        });
    }
    private  void getServiceNetWork(){
        CommonBody commonBody = new CommonBody();
        ((WindowDao)createRequestData).getServiceNetWork(this, commonBody, new RxNetCallback<ServiceNetWorkBean>() {
            @Override
            public void onSuccess(ServiceNetWorkBean serviceNetWorkBean) {
                if (serviceNetWorkBean != null && serviceNetWorkBean.entrySet.size() > 0) {//登录成功
                    serviceNetWorkAdapter.addData(serviceNetWorkBean.entrySet);
                    recyclerView.setAdapter(serviceNetWorkAdapter);//设置Adapter
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

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {


        }
    }





    @Override
    protected void onResume() {
        super.onResume();

    }


}

