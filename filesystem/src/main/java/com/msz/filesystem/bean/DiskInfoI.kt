package com.msz.filesystem.bean

import android.os.Parcelable
import com.msz.filesystem.bean.IFile.Companion.DIR_TYPE
import com.msz.filesystem.bean.itf.IName
import kotlinx.android.parcel.Parcelize

@Parcelize
class DiskInfoI(override var name: String, override val path: String, override var token: String? = null) : ITokenInfo, IName, IFile, Parcelable {
    override var fileType: Int = DIR_TYPE
}