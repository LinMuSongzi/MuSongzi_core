package com.musongzi.core.base.bean

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.musongzi.core.R
import kotlinx.android.parcel.Parcelize

/*** created by linhui * on 2022/7/7 */
@Parcelize
data class StyleMessageDescribe @JvmOverloads constructor(
    var title: String? = null,
    var barColor: Int = R.color.bg_white,
    var rightText:String? = null,
    val isShowBar: Int = View.VISIBLE,
    val statusColor: Int = R.color.bg_white,
    val statusTextColorFlag: Int = 1
) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readInt()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//        parcel.writeInt(barColor)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<StyleMessageInfo> {
//        override fun createFromParcel(parcel: Parcel): StyleMessageInfo {
//            return StyleMessageInfo(parcel)
//        }
//
//        override fun newArray(size: Int): Array<StyleMessageInfo?> {
//            return arrayOfNulls(size)
//        }
//    }
}