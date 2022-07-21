package com.musongzi.core.base.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*** created by linhui * on 2022/7/7 */
@Parcelize
open class FragmentDescribe(val className: String, val sinfo: StyleMessageDescribe?, val businessInfo:BusinessInfo? = null) : Parcelable {
    var tag = className + "_f";
}