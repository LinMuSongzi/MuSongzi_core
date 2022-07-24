package com.musongzi.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;


/**
 * 功能：屏幕工具
 */
public class ScreenUtil {

    public static final String TAG = ScreenUtil.class.getSimpleName();

    public static final int SCREEN_1_2_WDITH = ScreenUtil.getScreenWidth();

    public static final int SCREEN_1_3_WDITH = ScreenUtil.getScreenWidth() / 3;

    public static final int SCREEN_1_4_WDITH = ScreenUtil.getScreenWidth() / 4;

    public static final int SCREEN_1_5_WDITH = ScreenUtil.getScreenWidth() / 5;


    /**
     * 功能：屏幕单位转换
     */
    public static int px2dp(float pxValue) {
        Context context = ActivityThreadHelp.getCurrentApplication();
        return px2dp(context, pxValue);
    }

    /**
     * 功能：屏幕单位转换
     */
    public static int dp2px(float dpValue) {
        Context context = ActivityThreadHelp.getCurrentApplication();
        return dp2px(context, dpValue);
    }

    /**
     * 功能：屏幕单位转换
     */
    public static float dp2px2(float dpValue) {
        Context context = ActivityThreadHelp.getCurrentApplication();
        return dp2px(context, dpValue);
    }

    public static int dp2px(String dpValue) {
        Context context = ActivityThreadHelp.getCurrentApplication();
        return dp2px(context, Float.parseFloat(dpValue));
    }

    // ---------------------------------------------------------------------------------

    /**
     * 功能：屏幕单位转换
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 功能：屏幕单位转换
     */
    @Deprecated
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

//    /**
//     * 功能：屏幕单位转换
//     */
//    @Deprecated
//    public static Float dp2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return dpValue * scale + 0.5f
//    }

    /**
     * 功能：屏幕单位转换
     */
    @Deprecated
    public static float dp2px2(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }


    // ---------------------------------------------------------------------------------

    /**
     * 功能：获得屏幕宽度（px）
     */
    @Deprecated
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 功能：获得屏幕高度（px）
     */
    public static int getScreenHeight() {
        WindowManager w = (WindowManager) ActivityThreadHelp.getCurrentApplication().getSystemService(Context.WINDOW_SERVICE);
        Display s = w.getDefaultDisplay();
        Log.i(TAG, "getScreenHeight: " + s.getHeight());
        return s.getHeight();
    }

    /**
     * 功能：获得屏幕宽度（px）
     */
    public static int getScreenWidth() {
        WindowManager w = (WindowManager) ActivityThreadHelp.getCurrentApplication().getSystemService(Context.WINDOW_SERVICE);
        Display s = w.getDefaultDisplay();
        Log.i(TAG, "getScreenWidth: " + s.getWidth());
        return s.getWidth();
    }

//	/**
//	 * 功能：获得屏幕宽度（px）
//	 */
//	public static int getScreenLocalWidth() {
//		return AppManager.getInstance().getApplication().getSeparate().getAppclicationWrapper().getScreenLocalWidth();
//	}
//
//	/**
//	 * 功能：获得屏幕高度（px）
//	 */
//	public static int getScreenHeight() {
//		return AppManager.getInstance().getApplication().getSeparate().getAppclicationWrapper().getScreenHeight();
//	}
//
//	/**
//	 * 功能：获得屏幕高度（px）
//	 */
//	public static int getScreenLocalHeight() {
//		return AppManager.getInstance().getApplication().getSeparate().getAppclicationWrapper().getScreenLocalHeight();
//	}

    // ---------------------------------------------------------------------------------

    /**
     * 获得屏幕比例
     */
    public static float getAreaOneRatio(Activity activity) {
//		Log.d(TAG, "getAreaOneRatio: // -------------------------------------------------------------");
        int[] size = getAreaOne(activity);
        int a = size[0];
        int b = size[1];
        float ratio = 0f;
        try {
            if (a > b) {
                ratio = (float) a / (float) b;
            } else {
                ratio = (float) b / (float) a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//		Log.d(TAG, "getAreaOneRatio: ratio=" + ratio);
        return ratio;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获得屏幕尺寸（宽 x 高）（px）
     */
    public static int[] getAreaOne(Activity activity) {
//		Log.d(TAG, "getAreaOne: // -------------------------------------------------------------");
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
//		Log.d(TAG, "getAreaOne: width=" + point.x);
//		Log.d(TAG, "getAreaOne: height=" + point.y);
        return new int[]{point.x, point.y};
    }

    /**
     * 获得应用区域尺寸（宽 x 高）（px）
     */
    public static int[] getAreaTwo(Activity activity) {
//		Log.d(TAG, "getAreaOne: // -------------------------------------------------------------");
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//		Log.d(TAG, "getAreaOne: width=" + rect.width());
//		Log.d(TAG, "getAreaOne: height=" + rect.height());
        return new int[]{rect.width(), rect.height()};
    }

    /**
     * 获得用户绘图区域尺寸（宽 x 高）（px）
     */
    public static int[] getAreaThree(Activity activity) {
//		Log.d(TAG, "getAreaOne: // -------------------------------------------------------------");
        Rect rect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
//		Log.d(TAG, "getAreaOne: width=" + rect.width());
//		Log.d(TAG, "getAreaOne: height=" + rect.height());
        return new int[]{rect.width(), rect.height()};
    }

//	public static int getStatusBarHeight() {
//		return getStatusBarHeight(AppManager.getInstance().getContext());
//	}

    public static int getStatusBarHeight() {
        int statusBarHeight = 0;
        Resources res = ActivityThreadHelp.getCurrentApplication().getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int getNavBarHeight() {
        int result = 0;
        int resourceId = ActivityThreadHelp.getCurrentApplication().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ActivityThreadHelp.getCurrentApplication().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
