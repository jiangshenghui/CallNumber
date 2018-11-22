package com.call.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    @BindView(R.id.radioBtnLocal)
    RadioButton radioLocal;
    @BindView(R.id.radioBtnOrigin)
    RadioButton radioOrigin;
    @BindView(R.id.tv_language_ku)
    TextView tvLanguageKu;
    @BindView(R.id.re_language)
    RelativeLayout reLanguage;

    @BindView(R.id.re_refresh)
    RelativeLayout reRefresh;
    private NetWorkListAdapter netListAdapter;

    private SpinerPopWindow mSpinerPopNet;


    public int setLayoutResID() {
        return R.layout.fragment_language;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        netListAdapter = new NetWorkListAdapter(getActivity());


         List<EntrySetBean> mList = new ArrayList<EntrySetBean>();

        EntrySetBean entrySet1 = new EntrySetBean();
        entrySet1.name = "中文女声语音库,能在WIN7系统上使用ScanSoft_MeiLing_ChineseMandarin";
        EntrySetBean entrySet2 = new EntrySetBean();
        entrySet2.name = "中文女声语音库,能在WIN7系统上使用ScanSoft_MeiLing_ChineseMandarin";
        mList.add(entrySet1);
        mList.add(entrySet2);
        netListAdapter.addData(mList);
        mSpinerPopNet = new SpinerPopWindow(getActivity(),itemClickListener,netListAdapter);
        mSpinerPopNet.setOnDismissListener(dismissListener);
    }

    @Override
    public void initListener() {

    }
    @OnClick({R.id.radioBtnLocal, R.id.radioBtnOrigin,R.id.re_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioBtnLocal:// 部门窗口
                radioLocal.setChecked(true);
                radioOrigin.setChecked(false);
                break;
            case R.id.radioBtnOrigin://部门网点
                radioLocal.setChecked(false);
                radioOrigin.setChecked(true);
//                setTextImage(R.drawable.icon_up);
                break;
            case R.id.re_language://部门网点
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
