package com.musongzi.core.base.bean

import android.os.Parcelable
import com.musongzi.core.base.business.BaseMapBusiness
import kotlinx.android.parcel.Parcelize

/*** created by linhui * on 2022/7/28 */
@Parcelize
class ActivityDescribe(var className:String,val parcelable: Parcelable? = null,val businessName:String? = null,
                       var intentFlag:Int? = null,var windowDescribe: WindowDescribe? = null) :Parcelable{
}