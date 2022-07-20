package com.musongzi.comment.util

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import com.musongzi.core.util.ActivityThreadHelp

/*** created by linhui * on 2022/7/20 */
object ApkUtil {

    const val TAG = "ApkUtil_msz"

    fun getResUri(resId: Int): Uri {
        val r: Resources = ActivityThreadHelp.getCurrentApplication().resources
        val uri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(resId) + "/"
                    + r.getResourceTypeName(resId) + "/"
                    + r.getResourceEntryName(resId)
        )
        Log.i(ApkUtil.TAG, "getResUri: $uri")
        return uri
    }

    fun getResUriString(resId: Int): String {
        val r: Resources = ActivityThreadHelp.getCurrentApplication().resources
        return ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" +
                r.getResourcePackageName(resId) +
                "/" +
                r.getResourceTypeName(resId) +
                "/" +
                r.getResourceEntryName(resId)
    }

}