package com.musongzi.core.base.bean

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

/*** created by linhui * on 2022/7/7 */
class StyleMessageInfo @JvmOverloads constructor(var title:String? = null, var barColor:Int = Color.WHITE) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(barColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StyleMessageInfo> {
        override fun createFromParcel(parcel: Parcel): StyleMessageInfo {
            return StyleMessageInfo(parcel)
        }

        override fun newArray(size: Int): Array<StyleMessageInfo?> {
            return arrayOfNulls(size)
        }
    }
}