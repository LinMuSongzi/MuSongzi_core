package com.msz.filesystem.bean

import com.msz.filesystem.bean.TokenInfo

class FileInfo(
    val name: String,
    val path: String,
    val fileType:Int,
    val cover: String? = null,
    val createTime: Long? = null,
    val fileFormat: String? = null,
    val allName: String? = null,
    token: String
) : TokenInfo(token)
