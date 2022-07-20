package com.musongzi.core.base.bean

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*** created by linhui * on 2022/7/7 */
@Parcelize
class FragmentEventInfo(val className: String, val sinfo: StyleMessageInfo?,val businessInfo:BusinessInfo? = null) : Parcelable {
    var tag = className + "_f";
}