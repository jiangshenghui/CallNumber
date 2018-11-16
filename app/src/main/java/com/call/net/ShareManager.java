package com.call.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.hx.zzp.R;
import com.hx.zzp.utils.AppConfig;
import com.hx.zzp.utils.AppUserData;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

/**
 * 分享管理 -> 小程序
 */
public class ShareManager {

    private static final String TAG = "ShareManager";
    /**
     * routeDetail （路线详情）
     * storyDetail （故事详情）
     * campDetail（营地详情）
     * rentDetail（租赁详情）
     * hotelDetail （酒店详情）
     * 路径  /pages/storyDetail/index? id=xxx & shareUserId=xxx
     */
    private IWXAPI api;
    public static final String SHARE_ROUTEDETAIL = "routeDetail";
    public static final String SHARE_STORYDETAIL = "storyDetail";
    public static final String SHARE_CAMPDETAIL = "campDetail";
    public static final String SHARE_RENTDETAIL = "rentDetail";
    public static final String SHARE_HOTELDETAIL = "hotelDetail";
    private static final int THUMB_SIZE = 150;
    private Context context;

    public ShareManager(Context context) {
        this.context = context;
        api = WXAPIFactory.createWXAPI(context, AppConfig.WX_ID);
    }

    /**
     * 分享到小程序
     *
     * @param title 小程序消息Title
     * @param desc  小程序消息Desc
     * @param file  小程序消息image
     * @param id    分享信息id
     * @param type  详情来源
     */
    public void shareToWxProgram(String title, String desc, File file, String id, String type, boolean isDetail) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_1c2aee0635c7";     // 小程序原始id
        if (isDetail) {
            miniProgramObj.path = "/pages/" + type + "/index?id=" + id;//小程序页面路径
        } else {
            miniProgramObj.path = "/pages/share/index?shareUserId=" + AppUserData.getInstance().getUserBean().getId();//小程序页面路径
        }
        Bitmap thumbBmp = null;

        if (!file.exists()) {
            thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.login_back);
        } else if (!isDetail) {
            try {
                File fileCs = new Compressor(context)
                        .setQuality(100)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(file);
                thumbBmp = BitmapFactory.decodeFile(fileCs.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            thumbBmp = BitmapFactory.decodeFile(file.getPath());
        }

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = desc;               // 小程序消息desc
        if (isDetail) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbBmp, 250, 200, true);
//            Bitmap scaledBitmap = BitMapUtil.zoomBitmap(thumbBmp,  250, 200);
            thumbBmp.recycle();
//            msg.thumbData = Util.bmpToByteArray(scaledBitmap, true);   // 小程序消息封面图片，小于128k
        } else {
//            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);   // 小程序消息封面图片，小于128k
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
