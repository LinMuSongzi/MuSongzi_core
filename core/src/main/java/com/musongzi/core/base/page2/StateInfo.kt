package com.musongzi.core.base.page2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateInfo @JvmOverloads constructor(val page: Int, val pageSize: Int, val state: Int, var id: Long? = null) : Parcelable