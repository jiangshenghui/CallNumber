package com.bg.baseutillib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.bg.baseutillib.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/2/28.
 * <p>
 * 自定义WebView控件
 * 用于加载HTML与H5，附加图片点击事件
 * HTML自定义样式
 */

public class BgWebView extends WebView implements View.OnTouchListener {

    //通过点击图片获取到图片地址，存进list用于点击
    private List<String> imgUrl = new ArrayList<>();

    private float x, y;

    public BgWebView(Context context) {
        super(context);
        initWebview();
    }

    public BgWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebview();
    }

    public BgWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebview();
    }

    /**
     * 加载H5链接
     *
     * @param url 网址
     */
    public void loadH5Url(String url) {
        this.loadUrl(url);
    }

    /**
     * 加载HTML内容
     *
     * @param content 内容数据
     */
    public void loadHtmlUrl(String content) {
        String linkCss = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} " +
                "body{word-wrap:break-word;font-size:16px;padding:0px;margin:0px} " +
                "p{padding:0px;margin:0px;font-size:16px;color:#333333;line-height:1.3;} " +
                "img{padding:0px;margin-left:0px;margin-right:0px;margin-top:10px;margin-bottom:10px;max-width:100%; width:auto; height:auto;}</style>" +
                "</head>";
        String html = "<html><header>" + linkCss + "</header>" + content + "</body></html>";
        this.loadData(html, "text/html;charset=utf-8", "uft-8");
    }

    /**
     * 初始化
     */
    private void initWebview() {
//        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
//        webView.getSettings().setBuiltInZoomControls(true);//支持缩放，必须要设置，否则不能缩放    是否显示缩放按钮，默认false
//        webView.getSettings().setDisplayZoomControls(false);//是否显示缩放按钮，默认true，兼容到3.0以上

        //单屏显示
        this.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        this.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        this.getSettings().setDefaultFontSize((int) getResources().getDimension(R.dimen.y36));

        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.getSettings().setAppCacheEnabled(false);//是否使用缓存
        this.getSettings().setDomStorageEnabled(true);//DOM Storage
//        String user_agent =
//                "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en) AppleWebKit/124 (KHTML, like Gecko) Safari/125.1";
//        webView.getSettings().setUserAgentString(user_agent);//代理

        //设置WebView
        this.getSettings().setJavaScriptEnabled(true);//支持JS
        this.addJavascriptInterface(new JsInterface(getContext()), "imageClick"); //JS交互
        this.setOnTouchListener(this);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        //如果不设置setWebViewClient()就会打开手机的浏览器加载网页
        this.setWebChromeClient(new WebChromeClient() {
            //进度更新时会触发
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d("THOMAS", "===========onProgressChanged: newProgress : " + newProgress);

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("ThomasDebug", "onJsAlert : " + message);
                return super.onJsAlert(view, url, message, result);
            }
        });

        //在apk内加载网页
        this.setWebViewClient(new WebViewClient() {
            //加载结束时触发
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("ThomasDebug", "onPageFinished: ");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("ThomasDebug", "shouldOverrideUrlLoading : " + url);
                view.loadUrl(url);
                return true;
            }
        });
    }

    class JsInterface {
        Context context;

        public JsInterface(Context context) {
            this.context = context;
        }

        //查看图片url
        @JavascriptInterface
        public void click(String url) {
            imgUrl.clear();
            imgUrl.add(url);
//            ImagePagerActivity.startActivity(getContext(), imgUrl, 0, true, true);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //通过wenview的touch来响应web上的图片点击事件
        float density = getResources().getDisplayMetrics().density; //屏幕密度
        float touchX = event.getX() / density;  //必须除以屏幕密度
        float touchY = event.getY() / density;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = touchX;
            y = touchY;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            float dx = Math.abs(touchX - x);
            float dy = Math.abs(touchY - y);
            if (dx < 10.0 / density && dy < 10.0 / density) {
                clickImage(touchX, touchY);
            }
        }
        return false;
    }

    private void clickImage(float touchX, float touchY) {
        //通过触控的位置来获取图片URL
        String js = "javascript:(function(){" +
                "var  obj=document.elementFromPoint(" + touchX + "," + touchY + ");"
                + "if(obj.src!=null){" + " window.imageClick.click(obj.src);}" +
                "})()";
        this.loadUrl(js);
    }
}
