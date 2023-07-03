package com.msz.filesystem.bean

import com.msz.filesystem.bean.TokenInfo
import com.msz.filesystem.bean.itf.IName

class DiskInfo(override var name: String, val path: String, token: String?) : TokenInfo(token), IName