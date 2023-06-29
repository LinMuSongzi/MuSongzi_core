package com.musongzi.core.base.bean

import android.os.Parcelable
import com.musongzi.core.ExtensionCoreMethod.isHeatDarkMode
import kotlinx.android.parcel.Parcelize

/**
 * 填充ui的对象数据
 */
@Parcelize
class DefaultShowInfo(
    @Deprecated("仅作为自我标识,具体没有作用")
    var state: Int,//
    /**
     * 长度为2的本地资源图片。0 标识正常模式，1 标识暗黑模式
     */
    var centerRes: IntArray,
    /**
     * 文案提示（文案为空的时候不显示textview ui）
     */
    var tip: CharSequence? = null,
    /**
     * 文案的颜色提示，0 标识正常模式，1 标识暗黑模式
     */
    var tipColor: IntArray? = null,
    /**
     * 用来触发最基本的按钮点击事件
     * 但是如果你有自己处理方式，这里将失去作用
     */
    var buttonClickAction: Int? = null,
    /**
     * 按钮的文案，文案为空的时候不显示按钮
     */
    var buttonText: String? = null,
    /**
     * 按钮的颜色，0 标识正常模式，1 标识暗黑模式
     */
    var buttonColor: IntArray? = null,
    /**
     * 按钮的背景，drawable资源。0 标识正常模式，1 标识暗黑模式
     */
    var buttonBg: IntArray? = null
) : Parcelable {


    fun getRealTipColor(): Int? {
        if (tipColor == null) {
            return null
        }
        return if (!isHeatDarkMode) tipColor!![0] else tipColor!![1]
    }

    fun getRealCenterRes(): Int {
        return if (!isHeatDarkMode) centerRes[0] else centerRes[1]
    }

    fun getRealButtonColor(): Int? {
        if (buttonColor == null) {
            return null
        }
        return if (!isHeatDarkMode) buttonColor!![0] else buttonColor!![1]
    }

    fun getRealButtonBg(): Int? {
        if (buttonBg == null) {
            return null
        }
        return if (!isHeatDarkMode) buttonBg!![0] else buttonBg!![1]
    }


    companion object {
        fun CharSequence.asDefaultShowInfo(): DefaultShowInfo {
            return DefaultShowInfo(
                state = 1, centerRes = TODO()//intArrayOf(R.mipmap.empty_list_light, R.mipmap.empty_list_dark), tip = this
            )
        }
    }


}