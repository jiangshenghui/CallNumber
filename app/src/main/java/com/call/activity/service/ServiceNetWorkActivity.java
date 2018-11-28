package com.call.activity.service;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bg.baseutillib.base.BaseListAdapter;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.activity.adapter.NetWorkContentAdapter;
import com.call.activity.adapter.ServiceNetWorkAdapter;
import com.call.activity.adapter.WaitPersonAdapter;
import com.call.control.InitConfig;
import com.call.listener.UiMessageListener;
import com.call.net.login.request.CommonBody;
import com.call.net.login.request.ParamsSet;
import com.call.net.window.WindowDao;
import com.call.net.window.response.EntrySetBean;
import com.call.net.window.response.ServiceNetWorkBean;
import com.call.utils.AutoCheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    private static final String TEXT = "欢迎使用百度语音合成，请在代码中修改合成文本";


    // ================== 初始化参数设置开始 ==========================
    /**
     * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
     * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
     */
    protected String appId = "14944201";

    protected String appKey = "pnnyNtSgvCNx8jWDzsXgKOom";

    protected String secretKey = "NcVK10DGRb4gMGPweATO4Utoan6dfzDG";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.ONLINE;

    // ================选择TtsMode.ONLINE  不需要设置以下参数; 选择TtsMode.MIX 需要设置下面2个离线资源文件的路径
    private static final String TEMP_DIR = "/sdcard/baiduTTS"; // 重要！请手动将assets目录下的3个dat 文件复制到该目录

    // 请确保该PATH下有这个文件
    private static final String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat";

    // 请确保该PATH下有这个文件 ，m15是离线男声
    private static final String MODEL_FILENAME =
            TEMP_DIR + "/" + "bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat";

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    protected SpeechSynthesizer mSpeechSynthesizer;

    // =========== 以下为UI部分 ==================================================

    private Button mSpeak;

    private Button mStop;

//    private TextView mShowText;

    protected Handler mainHandler;

    private String  chooseLanguage="1";//1本地 2远程

    private static final String DESC = "精简版合成，仅给出示例集成合成的调用过程。可以测试离线合成功能，首次使用请联网。\n"
            + "其中initTTS方法需要在新线程调用，否则引起UI阻塞。\n"
            + "纯在线请修改代码里ttsMode为TtsMode.ONLINE， 没有纯离线。\n"
            + "离线功能需要手动将assets目录下的资源文件复制到TEMP_DIR =/sdcard/baiduTTS \n"
            + "完整的SDK调用方式可以参见MainActivity\n\n";


    private static final String TAG = "MiniActivity";


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
        chooseLanguage = SharedPreferencesUtil.readString("chooseLanguage");
        initView();
        initPermission();
        initTTs();
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
                clientCallNext(entrySetBean.groupId,entrySetBean.windowId,entrySetBean.userId,entrySetBean.userName,entrySetBean.bespeakSort);
                break;
            case R.id.reReCall:
                entrySetBean = getEntrySetBean();
                if(entrySetBean == null){
//                    ToastUtil.showShortToast("请选择用户");
                    return;
                }
                clientReCall(entrySetBean.id,entrySetBean.userName,entrySetBean.bespeakSort);
                break;
            case R.id.re_back:
                finish();
                break;
            case R.id.btnSet:
                finish();
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
    private  void clientCallNext(String groupId, String windowId, String userId,final String userName, final String bespeakSort){
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
                    speak(userName,bespeakSort);
                }else{
                    ToastUtil.showShortToast("叫号失败");
                }
            }
            @Override
            public void onError(ApiException e) {
                ToastUtil.showShortToast("叫号失败");
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
    private  void clientReCall(String queueId ,final String userName, final String bespeakSort){
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
                    speak(userName,bespeakSort);
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



    /**
     * 注意此处为了说明流程，故意在UI线程中调用。
     * 实际集成中，该方法一定在新线程中调用，并且该线程不能结束。具体可以参考NonBlockSyntherizer的写法
     */
    private void initTTs() {
        LoggerProxy.printable(true); // 日志打印在logcat中
        boolean isMix = ttsMode.equals(TtsMode.MIX);
        boolean isSuccess;
        if (isMix) {
            // 检查2个离线资源是否可读
            isSuccess = checkOfflineResources();
            if (!isSuccess) {
                return;
            } else {
                print("离线资源存在并且可读, 目录：" + TEMP_DIR);
            }
        }
        // 日志更新在UI中，可以换成MessageListener，在logcat中查看日志
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler);

        // 1. 获取实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);

        // 2. 设置listener
        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);

        // 3. 设置appId，appKey.secretKey
        int result = mSpeechSynthesizer.setAppId(appId);
        checkResult(result, "setAppId");
        result = mSpeechSynthesizer.setApiKey(appKey, secretKey);
        checkResult(result, "setApiKey");

        // 4. 支持离线的话，需要设置离线模型
        if (isMix) {
            // 检查离线授权文件是否下载成功，离线授权文件联网时SDK自动下载管理，有效期3年，3年后的最后一个月自动更新。
            isSuccess = checkAuth();
            if (!isSuccess) {
                return;
            }
            // 文本模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
            // 声学模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        }

        // 5. 以下setParam 参数选填。不填写则默认值生效
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);

        // x. 额外 ： 自动so文件是否复制正确及上面设置的参数
        Map<String, String> params = new HashMap<>();
        // 复制下上面的 mSpeechSynthesizer.setParam参数
        // 上线时请删除AutoCheck的调用
        if (isMix) {
            params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
            params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        }
        InitConfig initConfig =  new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
            @Override
            /**
             * 开新线程检查，成功后回调
             */
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
                        print(message); // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }

        });

        // 6. 初始化
        result = mSpeechSynthesizer.initTts(ttsMode);
        checkResult(result, "initTts");

    }

    /**
     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
     *
     * @return
     */
    private boolean checkAuth() {
        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            print("【error】鉴权失败 errorMsg=" + errorMsg);
            return false;
        } else {
            print("验证通过，离线正式授权文件存在。");
            return true;
        }
    }

    /**
     * 检查 TEXT_FILENAME, MODEL_FILENAME 这2个文件是否存在，不存在请自行从assets目录里手动复制
     *
     * @return
     */
    private boolean checkOfflineResources() {
        String[] filenames = {TEXT_FILENAME, MODEL_FILENAME};
        for (String path : filenames) {
            File f = new File(path);
            if (!f.canRead()) {
                print("[ERROR] 文件不存在或者不可读取，请从assets目录复制同名文件到：" + path);
                print("[ERROR] 初始化失败！！！");
                return false;
            }
        }
        return true;
    }

    private void speak(String userName,String bespeakSort) {
        /* 以下参数每次合成时都可以修改
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
         *  设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5"); 设置合成的音量，0-9 ，默认 5
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5"); 设置合成的语速，0-9 ，默认 5
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5"); 设置合成的语调，0-9 ，默认 5
         *
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
         *  MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
         *  MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
         *  MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
         *  MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
         */

        if (mSpeechSynthesizer == null) {
            print("[ERROR], 初始化失败");
            return;
        }
        int result = mSpeechSynthesizer.speak("请"+userName+"到"+bespeakSort+"办理业务");
//        int result = mSpeechSynthesizer.speak(TEXT);
//        mShowText.setText("");
        print("合成并播放 按钮已经点击");
        checkResult(result, "speak");
    }

    private void stop() {
        print("停止合成引擎 按钮已经点击");
        int result = mSpeechSynthesizer.stop();
        checkResult(result, "stop");
    }

//    //  下面是UI部分
//
    private void initView() {
//        mSpeak = (Button) this.findViewById(R.id.speak);
//        mStop = (Button) this.findViewById(R.id.stop);
////        mShowText = (TextView) this.findViewById(R.id.showText);
////        mShowText.setText(DESC);
//        View.OnClickListener listener = new View.OnClickListener() {
//            public void onClick(View v) {
//                int id = v.getId();
//                switch (id) {
//                    case R.id.speak:
//                        speak("","");
//                        break;
//                    case R.id.stop:
//                        stop();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        mSpeak.setOnClickListener(listener);
//        mStop.setOnClickListener(listener);
        mainHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    print(msg.obj.toString());
                }
            }

        };
    }

    private void print(String message) {
        Log.i(TAG, message);
//        mShowText.append(message + "\n");
    }

    @Override
    protected void onDestroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
            print("释放资源成功");
        }
        super.onDestroy();
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            print("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    //  下面是android 6.0以上的动态授权

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }
}

