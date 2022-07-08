package com.musongzi.core.base.bean

import android.os.Parcel
import android.os.Parcelable

/*** created by linhui * on 2022/7/7 */
class FragmentEventInfo(val className:String,val sinfo:StyleMessageInfo) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
       parcel.readParcelable(StyleMessageInfo::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(className)
        parcel.writeParcelable(sinfo,0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FragmentEventInfo> {
        override fun createFromParcel(parcel: Parcel): FragmentEventInfo {
            return FragmentEventInfo(parcel)
        }

        override fun newArray(size: Int): Array<FragmentEventInfo?> {
            return arrayOfNulls(size)
        }
    }
}