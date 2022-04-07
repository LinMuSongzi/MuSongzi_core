package com.musongzi.core.util;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.util.Log;

public class NoUnderlineSpan extends UnderlineSpan {

    private static final String TAG = "NoUnderlineSpan";
    int color = -1;//ActivityCompat.getColor(PlayMusicApplication.getInstance(), R.color.colorNewAccent);

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        Log.i(TAG, "updateDrawState: " + color);
//        if (color != -1) {
            ds.setColor(color);
//        }
        ds.setUnderlineText(false);
    }


}
