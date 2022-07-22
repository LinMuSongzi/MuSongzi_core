package com.musongzi.comment.describe

import android.os.Binder
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*** created by linhui * on 2022/7/21 */
@Parcelize
class ActivityDescribe(
    var activityName: String,
    var fragmentDescribes: List<Parcelable>? = null,
    var styleMessageInfo: Parcelable? = null,
    var datasBundle: Parcelable? = null
) : Binder(), Parcelable {


}