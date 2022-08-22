package com.musongzi.core;

import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;


import com.musongzi.core.util.ActivityThreadHelp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 功能：URL工具
 */
public class URLUtil {

     

    private static final String TAG = "URLUtil";
    @Deprecated
    public static final String PACKAGE_PROVIDER = "com.musongzi.test.provider";

    /**
     * 功能：URL是否合法
     */
    public static boolean isURL(String url) {

        try {
            URL mURL = new URL(url);
            System.out.println("url 正确");
            return true;
        } catch (MalformedURLException e) {
            System.out.println("url 不可用");
            return false;
        }
    }

    public static Uri fileUri(String path) {
        return fileUri(new File(path));
    }

    public static Uri fileUri(File file) {
        Uri uri;
//        Log.i(TAG, "fileUri: " + file.getAbsolutePath());
        if (Build.VERSION.SDK_INT >= 24 && file != null
                && !file.isDirectory()) {
            uri = FileProvider.getUriForFile(ActivityThreadHelp.getCurrentApplication()
                    , PACKAGE_PROVIDER, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static Uri fileUri2(String filePath) {
        Uri uri;
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= 28 && file != null
                && !file.isDirectory()) {
            uri = FileProvider.getUriForFile(ActivityThreadHelp.getCurrentApplication()
                    , PACKAGE_PROVIDER, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static Uri navitRes(int res) {
        return Uri.parse("android.resource://" + ActivityThreadHelp.getCurrentApplication().getPackageName() + "/" + res);
    }



    public void shareWechat(){

//        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
//        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
//        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
//        miniProgramObj.userName = "gh_d43f693ca31f";     // 小程序原始id
//        miniProgramObj.path = "/pages/media";            //小程序页面路径
//        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
//        msg.title = "小程序消息Title";                    // 小程序消息title
//        msg.description = "小程序消息Desc";               // 小程序消息desc
//        msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
//        api.sendReq(req);

    }

}
