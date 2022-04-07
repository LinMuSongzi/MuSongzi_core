package com.musongzi.core.util;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;


import java.util.ArrayList;
import java.util.List;

/**
 * 功能：文本工具
 */
public class TextUtil {
    private static final int baseWidth = 750;

    /**
     * 功能：TextView显示颜色HTML
     *
     * @param text
     * @param color "#00b4ff"
     * @return
     */
    public static String toColor(String text, String color) {

        return "<font color=\"" + color + "\">" + text + "</font>";
        // <font color="123456">abc</font>
    }

    /**
     * 功能：TextView显示颜色斜体HTML
     *
     * @param text
     * @param color "#00b4ff"
     * @return
     */
    public static String toColorItalic(String text, String color) {
        return "<font color=\"" + color + "\" style=\"font-weight:bold;font-style:italic;\">" + text + "</font>";
    }

    /**
     * 功能：TextView显示颜色并下划线HTML
     *
     * @param text
     * @param color "#00b4ff"
     * @return
     */
    public static String toColorAndUnderLine(String text, String color) {
        return "<font color=\"" + color + "\">" + "<u>" + text + "</u>" + "</font>";
        // <font color="123456"><u>abc</u></font>
    }

    /**
     * 金额转换 1,000,000
     *
     * @param number
     * @return
     */
    public static String convertMoney(String number) {
        try {
            //是否有小数点
            String dian = null;
            int indexof = number.indexOf(".");
            if (indexof != -1) {
                dian = number.substring(indexof, number.length());
                number = number.substring(0, indexof);
            }
            //是否小于1000
            if (number.length() < 4) {
                if (StringUtil.isNull(dian)) {
                    return number;
                } else {
                    return number + dian;
                }
            }
            int count = 0;
            List<Character> list = new ArrayList<>();
            //从后面开始对比
            for (int i = number.length() - 1; i >= 0; i--) {
                if (count != 0 && count % 3 == 0) {
                    list.add(',');
                    count = 0;
                }
                count++;
                list.add(number.charAt(i));

            }
            StringBuilder rs = new StringBuilder();
            //对比完再倒置
            for (int i = list.size() - 1; i >= 0; i--) {
                rs.append(list.get(i));
            }
            if (!StringUtil.isNull(dian)) {
                rs.append(dian);
            }
            return rs.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            //若出现异常，则返回原值
            return number;
        }
    }

    /**
     * 设置文本某个字体的大小
     *
     * @param startP
     * @param endP
     * @param dp
     */
    public static Spannable setSizeText(Spannable spanned, int startP, int endP, int dp) {
        spanned.setSpan(new AbsoluteSizeSpan(ScreenUtil.dp2px(dp)), startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }

    /**
     * 设置文本背景
     *
     * @param txt
     * @param startP
     * @param endP
     * @param color
     */
    @Deprecated
    public static Spannable setBackgroundColorText(String txt, int startP, int endP, int color) {
        SpannableString spanned = new SpannableString(txt);
        spanned.setSpan(new BackgroundColorSpan(color), startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }


    /**
     * 设置文本某段字体颜色
     *
     * @param txt
     * @param startP
     * @param endP
     * @param color
     */
    @Deprecated
    public static Spannable setForegroundColorText(String txt, int startP, int endP, int color) {
        return setForegroundColorText(new SpannableString(txt), startP, endP, color);
    }

    /**
     * 设置文本某段字体颜色
     *
     * @param startP
     * @param endP
     * @param color
     */
    public static Spannable setForegroundColorText(Spannable spanned, int startP, int endP, int color) {
        spanned.setSpan(new ForegroundColorSpan(color), startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }

    /**
     * 下划线
     *
     * @param spanned
     * @param startP
     * @param endP
     * @return
     */
    public static Spannable setUnderlineSpanText(Spannable spanned, int startP, int endP) {
        spanned.setSpan(new UnderlineSpan(), startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }

    /**
     * 中划线，删除线
     *
     * @return
     */
    public static Spannable setStrikethroughSpan(Spannable spanned, int startP, int endP) {
        spanned.setSpan(new StrikethroughSpan(), startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }

    public static Spannable setStyleSpan(Spannable spanned, int startP, int endP, int mStyle) {
        spanned.setSpan(new StyleSpan(mStyle), startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }

    /**
     * 点击
     *
     * @param spanned
     * @param startP
     * @param endP
     * @param clickableSpan
     * @return
     */
    public static Spannable setSpanClick(Spannable spanned, int startP, int endP, ClickableSpan clickableSpan) {
        spanned.setSpan(clickableSpan, startP, endP, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }

    // abcdabcd   b  (1,2],
    public static CharSequence findStringMatchShowColor(String conten, String matchString, String color) {
        if (StringUtil.isNull(matchString)) {
            return conten;
        }

//        Spannable spannable = new SpannableString(conten);
//        List<Integer[]> list = new ArrayList<>();
//        int cacheIndex = -1;
//        int start = 0;
//        StringBuilder stringBuilder = new StringBuilder();
//        do {
//            cacheIndex = conten.indexOf(matchString, start);
//            if (cacheIndex >= 0) {
//
//                stringBuilder.append()
//
//                Integer[] integers = new Integer[]{cacheIndex, cacheIndex + matchString.length()};
//                list.add(integers);
//                start = integers[1];
//            }
//
//        } while (cacheIndex >= 0 || start >= conten.length());


        return Html.fromHtml(conten.replace(matchString, String.format("<font color=%s>%s</font>", color, matchString)));
    }

    /**
     * 去掉下划线
     * @param spannable
     * @param color
     * @return
     */
    public static Spannable NoUnderlineSpan(Spannable spannable, int color) {

        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        mNoUnderlineSpan.setColor(color);
        spannable.setSpan(mNoUnderlineSpan, 0, spannable.length(), Spannable.SPAN_MARK_MARK);
        return spannable;
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String capitalizationText(String str) {
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
