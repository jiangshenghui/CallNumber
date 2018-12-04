package com.call.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.call.R;
import com.call.RvBaseFragment;
import com.call.activity.adapter.NetWorkListAdapter;
import com.call.dialog.SpinerPopWindow;
import com.call.net.login.LoginDao;
import com.call.net.window.response.EntrySetBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 语言设置
 */
public class LanguageFragment extends RvBaseFragment {
    @BindView(R.id.tv_language_ku)
    TextView tvLanguageKu;

    @BindView(R.id.et_language_ku)
    public EditText et_language_ku;


    @BindView(R.id.re_language)
    RelativeLayout reLanguage;

    @BindView(R.id.re_refresh)
    RelativeLayout reRefresh;
    private NetWorkListAdapter netListAdapter;

    @BindView(R.id.radioBtnLocal)
    ImageView radioBtnLocal;

    @BindView(R.id.radioBtnOrigin)
    ImageView radioBtnOrigin;

    @BindView(R.id.ivDown)
    ImageView ivDown;

    private SpinerPopWindow mSpinerPopNet;

    private String chooseLanguage = "1";//1本地语音库 2 远程语音库
    private String voice = "0";
    private String ip = "";

    public int setLayoutResID() {
        return R.layout.fragment_language;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        netListAdapter = new NetWorkListAdapter(getActivity());

       chooseLanguage = SharedPreferencesUtil.readString("chooseLanguage");
       voice =   SharedPreferencesUtil.readString("voice");
       ip =   SharedPreferencesUtil.readString("ip");
        if(TextUtils.isEmpty(chooseLanguage)){
            chooseLanguage = "1";
            voice = "0";
        }
       if("1".equals(chooseLanguage)){
           radioBtnLocal.setImageResource(R.drawable.me_at);
           radioBtnOrigin.setImageResource(R.drawable.me_at_n);
           et_language_ku.setVisibility(View.GONE);
           tvLanguageKu.setVisibility(View.VISIBLE);
           ivDown.setVisibility(View.VISIBLE);
       }else {
           radioBtnLocal.setImageResource(R.drawable.me_at_n);
           radioBtnOrigin.setImageResource(R.drawable.me_at);
           tvLanguageKu.setVisibility(View.GONE);
           et_language_ku.setVisibility(View.VISIBLE);
           ivDown.setVisibility(View.GONE);
       }
       if("1".equals(chooseLanguage)){
          // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
           Log.d("jsh","voice:"+voice);
           if("0".equals(voice)){
               tvLanguageKu.setText("普通女声");
           }else {
               tvLanguageKu.setText("普通男声");
           }
           SharedPreferencesUtil.writeString("voice",voice);
       }else {
           if(!TextUtils.isEmpty(ip)){
               et_language_ku.setText(ip);
           }
       }
        List<EntrySetBean> mList = new ArrayList<EntrySetBean>();
        EntrySetBean entrySet1 = new EntrySetBean();
        entrySet1.name = "普通女声";
        entrySet1.voice = "0";
        EntrySetBean entrySet2 = new EntrySetBean();
        entrySet2.name = "普通男声";
        entrySet2.voice = "1";
        mList.add(entrySet1);
        mList.add(entrySet2);
        netListAdapter.addData(mList);
        mSpinerPopNet = new SpinerPopWindow(getActivity(),itemClickListener,netListAdapter);
        mSpinerPopNet.setOnDismissListener(dismissListener);
    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.llBtnLocal, R.id.llBtnOrigin,R.id.re_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBtnLocal://
                radioBtnLocal.setImageResource(R.drawable.me_at);
                radioBtnOrigin.setImageResource(R.drawable.me_at_n);
                SharedPreferencesUtil.writeString("chooseLanguage","1");
                et_language_ku.setVisibility(View.GONE);
                tvLanguageKu.setVisibility(View.VISIBLE);
                ivDown.setVisibility(View.VISIBLE);
                break;
            case R.id.llBtnOrigin://
                SharedPreferencesUtil.writeString("chooseLanguage","2");
                radioBtnLocal.setImageResource(R.drawable.me_at_n);
                radioBtnOrigin.setImageResource(R.drawable.me_at);
                tvLanguageKu.setVisibility(View.GONE);
                et_language_ku.setVisibility(View.VISIBLE);
                ivDown.setVisibility(View.GONE);
//                setTextImage(R.drawable.icon_up);
                break;
            case R.id.re_language://
                mSpinerPopNet.setWidth(reLanguage.getWidth());
                mSpinerPopNet.showAsDropDown(reLanguage);
//                setTextImage(R.drawable.icon_up);
                break;
        }
    }
    public LoginDao onCreateRequestData() {
        return new LoginDao();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopNet.dismiss();
            tvLanguageKu.setText(netListAdapter.mList.get(position).name);
            SharedPreferencesUtil.writeString("voice",netListAdapter.mList.get(position).voice);
        }
    };
    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

        }
    };
}
