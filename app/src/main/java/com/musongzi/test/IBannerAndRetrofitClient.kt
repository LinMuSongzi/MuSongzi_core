package com.musongzi.test

import android.graphics.Bitmap
import com.musongzi.core.itf.IClient

interface IBannerAndRetrofitClient : IClient {
    fun setGrilPicture(decodeByteArray: Bitmap?)
}