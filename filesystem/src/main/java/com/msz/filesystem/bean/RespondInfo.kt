package com.msz.filesystem.bean

data class RespondInfo<T>(var code: Int, var msg: String, var data: T? = null)