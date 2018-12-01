package com.call.activity.service;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.fragment.LanguageFragment;
import com.call.fragment.QueueFragment;
import com.call.net.window.WindowDao;
import com.call.net.window.response.EntrySetBean;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 设置窗口
 */
public class SetServiceActivity extends RvBaseActivity {
    protected FragmentManager fragmentManager;
    private QueueFragment queueFragment;
    private LanguageFragment languageFragment;
    @BindView(R.id.btn_queue_set)
    Button btnQueue;
    @BindView(R.id.btn_language)
    Button btnLanguage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutResID() {
        return R.layout.activity_set_service;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initFrament();
    }
    private void initFrament(){
        queueFragment = new QueueFragment();
        languageFragment = new LanguageFragment();
        switchFragment(queueFragment).commit();
        //步骤一：添加一个FragmentTransaction的实例
//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        //步骤二：用add()方法加上Fragment的对象rightFragment
//        transaction.add(R.id.fragment_set,queueFragment);
//        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
//        transaction.commit();
    }
    private  Fragment  currentFragment;
    private FragmentTransaction switchFragment(Fragment targetFragment) {

             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

             if (!targetFragment.isAdded()) {
                //第一次使用switchFragment()时currentFragment为null，所以要判断一下
                 if (currentFragment != null) {
                    transaction.hide(currentFragment);
                 }
                 transaction.add(R.id.fragment_set, targetFragment, targetFragment.getClass().getName());
            } else {
                transaction
                     .hide(currentFragment)
                     .show(targetFragment);
             }
            currentFragment = targetFragment;
        return transaction;
    }


    @Override
    public void initListener() {

    }

    @Override
    public WindowDao onCreateRequestData() {
        return new WindowDao();
    }

    @OnClick({R.id.btn_queue_set,R.id.btn_language,R.id.btnSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_queue_set:
//                fragmentManager.beginTransaction().replace(R.id.fragment_set, queueFragment).commit();
                switchFragment(queueFragment).commit();
                btnQueue.setTextColor(getResources().getColor(R.color.color28));
                btnLanguage.setTextColor(getResources().getColor(R.color.color32));
                btnQueue.setBackgroundResource(R.color.white);
                btnLanguage.setBackgroundResource(R.color.transparent);
                break;
            case R.id.btn_language:
//                fragmentManager.beginTransaction().replace(R.id.fragment_set, languageFragment).commit();
                btnQueue.setTextColor(getResources().getColor(R.color.color32));
                btnLanguage.setTextColor(getResources().getColor(R.color.color28));
                switchFragment(languageFragment).commit();
                btnQueue.setBackgroundResource(R.color.transparent);
                btnLanguage.setBackgroundResource(R.color.white);
                break;
            case R.id.btnSure:
                Bundle bundle = new Bundle();
                boolean isChoose = false;
                List<EntrySetBean> mList = new ArrayList<EntrySetBean>();
                for(EntrySetBean eBean :queueFragment.queuetAdapter.mList){
                    if(eBean.isChoose){
                        isChoose = true;
                        mList.add(eBean);
                    }
                }
                if(!isChoose){
                    ToastUtil.showShortToast("请选择队列设置");
                    return;
                }
                if(queueFragment.tvWindows.getText().toString().trim().equals("请选择窗口")){
                    ToastUtil.showShortToast("请选择窗口");
                    return;
                }
                bundle.putSerializable("businessType",(Serializable) mList);
                bundle.putSerializable("netWorkName",queueFragment.tvNetwork.getText().toString());
                bundle.putSerializable("window",queueFragment.tvWindows.getText().toString());
                if(queueFragment.tvNetwork.getTag() != null){
                    bundle.putSerializable("depId",queueFragment.tvNetwork.getTag().toString());
                }
                if(queueFragment.tvWindows.getTag() != null){
                    bundle.putSerializable("windowId",queueFragment.tvWindows.getTag().toString());
                }
                Gson gson = new Gson();
                String json = gson.toJson(mList);
                SharedPreferencesUtil.writeString("alterSampleJson",json);
                startActivity(ServiceNetWorkActivity.class,bundle);

                if(languageFragment.et_language_ku !=null){
                    SharedPreferencesUtil.writeString("ip",languageFragment.et_language_ku.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}