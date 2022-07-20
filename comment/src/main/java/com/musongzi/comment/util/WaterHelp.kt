package com.musongzi.comment.util

import android.content.Context
import android.graphics.Bitmap
import java.io.File

interface WaterHelp {
    fun waterMask(bitmap: Bitmap)
    fun waterMask(context: Context, path: String, contentSgFlag:String, resId:Int, watermarkResId:Int, outPutDir: File)
}