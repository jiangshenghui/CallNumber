package com.call.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
/**
 * 微信回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_withdrawals_success);
//		api = WXAPIFactory.createWXAPI(this, ApiManager.APP_ID);
//		api.handleIntent(getIntent(), this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {

		String amount =  SharedPreferencesUtil.readString("amount");
		String vipLevel  = SharedPreferencesUtil.readString("vipLevel");
//		if (resp.errCode ==0 ) {
//			Intent intent = new Intent(this, PaySuccessActivity.class);
//			intent.putExtra("vipLevel",vipLevel);
//			intent.putExtra("amount",amount);
//			intent.putExtra("type","2");
//			startActivity(intent);
//		}else if(resp.errCode ==-2){
//			ToastUtil.showShortToast("取消支付");
//		}
		finish();
	}
}