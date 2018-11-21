package com.call.activity.service;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.fragment.LanguageFragment;
import com.call.fragment.QueueFragment;
import com.call.net.window.WindowDao;
import butterknife.BindView;
import butterknife.OnClick;

/***
 * 设置窗口
 */
public class SetServiceActivity extends RvBaseActivity {

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
//        //步骤一：添加一个FragmentTransaction的实例
//        fragmentManager =getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        //步骤二：用add()方法加上Fragment的对象rightFragment
//        queueFragment = new QueueFragment();
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

    @OnClick({R.id.btn_queue_set,R.id.btn_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_queue_set:
                switchFragment(queueFragment).commit();
                btnQueue.setTextColor(getResources().getColor(R.color.color28));
                btnLanguage.setTextColor(getResources().getColor(R.color.color32));
                btnQueue.setBackgroundResource(R.color.white);
                btnLanguage.setBackgroundResource(R.color.transparent);
                break;
            case R.id.btn_language:
                btnQueue.setTextColor(getResources().getColor(R.color.color32));
                btnLanguage.setTextColor(getResources().getColor(R.color.color28));
                switchFragment(languageFragment).commit();
                btnQueue.setBackgroundResource(R.color.transparent);
                btnLanguage.setBackgroundResource(R.color.white);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}