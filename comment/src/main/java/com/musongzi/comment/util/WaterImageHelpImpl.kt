package com.musongzi.comment.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.musongzi.comment.ExtensionMethod.toast
import com.musongzi.core.ExtensionCoreMethod.threadStart
import com.musongzi.core.ExtensionCoreMethod.androidColorGet
import java.io.File
import java.io.FileOutputStream

/**
 * 加水印
 */
class WaterImageHelpImpl : WaterHelp {

    companion object {
        const val TAG = "WaterImageHelpImpl"
    }

    override fun waterMask(bitmap: Bitmap) {

    }

    override fun waterMask(context:Context,path: String,contentSgFlag:String,resId:Int,watermarkResId:Int,outPutDir:File) {
        val water: (Bitmap) -> Bitmap = { bp ->
            val paint = Paint()
            paint.color = Color.WHITE
            paint.setShadowLayer(2f, 2f, 2f, resId.androidColorGet())
            val canvas = Canvas(bp);
            val hadW = bp.width * 0.01f
            val www1 = bp.width * 0.15f
            val sum1 = 0.5f
            var hhh1 = 1f
            var a = 0f
            while (www1 >= a) {
                hhh1 += sum1
                paint.textSize = hhh1
                a = paint.measureText(contentSgFlag)
            }
            val aaaaLength: Int = (paint.textSize * 1.5).toInt();
            val watermarkBtmap = Glide.with(context).asBitmap().load(watermarkResId)
                    .override(aaaaLength, aaaaLength).submit().get()

            val wText = a
            Log.i(TAG, ": $wText , ${paint.textSize}")

            val mix: Float = kotlin.math.abs(watermarkBtmap.height - paint.textSize) / 4

            val height = bp.height.toFloat() - (watermarkBtmap.height + hadW)

            canvas.drawText(contentSgFlag, 0, contentSgFlag.length, bp.width - wText - hadW, height + paint.textSize + mix, paint)
            canvas.drawBitmap(watermarkBtmap, bp.width - wText - hadW - watermarkBtmap.width, height, paint)

            bp
        }

        val run: () -> Unit = {
            Glide.with(context).asBitmap().load(path).submit().get().apply {
                water(this)
                val out = FileOutputStream(File(outPutDir, "w${System.currentTimeMillis()}.jpeg"))
                compress(Bitmap.CompressFormat.JPEG, 90, out)
                recycle()
                toast("已经保存到相册~")
            }
        }
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            threadStart {
                run.invoke()
            }
        } else {
            run.invoke()
        }
    }
}