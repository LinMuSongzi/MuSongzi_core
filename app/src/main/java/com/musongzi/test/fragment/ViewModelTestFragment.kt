package com.musongzi.test.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.style.*
import android.util.Log
import android.view.View.MeasureSpec
import com.musongzi.comment.ExtensionMethod.getDrawable
import com.musongzi.comment.util.setTextColorRes
import com.musongzi.core.ExtensionCoreMethod.androidColorGet
import com.musongzi.core.annotation.CollecttionsEngine.B
import com.musongzi.core.base.fragment.ViewModelFragment
import com.musongzi.core.util.ScreenUtil
import com.musongzi.test.R
import com.musongzi.test.databinding.FragmentViewModelTestBinding
import com.musongzi.test.vm.ViewModelTestViewModel
import java.lang.ref.WeakReference

class ViewModelTestFragment : ViewModelFragment<ViewModelTestViewModel, FragmentViewModelTestBinding>() {

    companion object {
        fun newInstance() = ViewModelTestFragment()
    }

//    private val observer = object : Observable.OnPropertyChangedCallback() {
//        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//            dataBinding.idContentIv.text = "text = " + (sender as? ObservableField<*>)?.get()
//        }
//    }

    override fun initView() {
//        observer.onPropertyChanged(getViewModel().initFlag,0);
//        getViewModel().initFlag.addOnPropertyChangedCallback(observer)
        dataBinding.viewmodel = getViewModel()
    }

    override fun initEvent() {

    }

    var _sum = 0

    override fun initData() {
//        object : Runnable {
//            override fun run() {
//                if(!isAdded){
//                    return
//                }
//                getViewModel().stringText.set("${++_sum}")
//                dataBinding.idContentIv.postDelayed(this,100)
//            }
//
//        }.run()
        val text = "热评精选神回复果然很搞笑哈哈哈哈哈"


        val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        dataBinding.idContentIv.measure(w, w)
//        dataBinding.idContentIv.line

        Log.i(TAG, "initData: measuredHeight = ${dataBinding.idContentIv.measuredHeight} , 18dp = ${ScreenUtil.dp2px(18f)}px")

        dataBinding.idContentIv.text = SpannableString(text).let {

            var start = 0
            var end = 2
//
            var span = BackDynamicDrawableSpan(
                myDrawable = R.drawable.shape_reping.getDrawable(),
                myText = text.substring(start, end),
                paddingWidth = ScreenUtil.dp2px(5),
                height = ScreenUtil.dp2px(18f),
                marginWidth = ScreenUtil.dp2px(2),
                colorValue = R.color.reping.androidColorGet()
            )

            it.setSpan(span, start, end, SPAN_INCLUSIVE_EXCLUSIVE)

            start = 2
            end = 4
            var span2 = BackDynamicDrawableSpan(
                myDrawable = R.drawable.shape_jingxuan.getDrawable(),
                myText = text.substring(start, end),
                paddingWidth = ScreenUtil.dp2px(5),
                height = ScreenUtil.dp2px(18f),
                marginWidth = ScreenUtil.dp2px(2),
                colorValue = R.color.jingxuan.androidColorGet()
            )
            it.setSpan(span2, start, end, SPAN_INCLUSIVE_EXCLUSIVE)

            start = 4
            end = 7
            span2 = BackDynamicDrawableSpan(
                myDrawable = R.drawable.shape_shenhuifu.getDrawable(),
                myText = text.substring(start, end),
                paddingWidth = ScreenUtil.dp2px(5),
                height = ScreenUtil.dp2px(18f),
                marginWidth = ScreenUtil.dp2px(2),
                colorValue = R.color.shenhuifu.androidColorGet()
            )
            it.setSpan(span2, start, end, SPAN_INCLUSIVE_EXCLUSIVE)



            it
        }

    }

    /**
     * @param myDrawable 当前的背景
     * @param myText 要显示的文案
     * @param paddingWith 左右间距 比如值 10，那么等于左右间距为 10
     * @param size 文本的字体大小
     * @param height 文本高度
     */
    class BackDynamicDrawableSpan(
        var myDrawable: Drawable,
        var myText: String,
        height: Int,
        var paddingWidth: Int = ScreenUtil.dp2px(2),
        var marginWidth: Int = ScreenUtil.dp2px(1),
//        var paddingHeight: Int = ScreenUtil.dp2px(1),
//        var marginHeight: Int = ScreenUtil.dp2px(10),
        var textNewSize: Float = ScreenUtil.dp2px(10).toFloat(),
//        var height:Int
        var colorValue: Int = Color.BLACK
    ) :
        ReplacementSpan() {
        var myHeight = height
        var myWith = 0
        var textWidth = 0

        var weakReferenceCahe: WeakReference<Drawable?>? = null


        fun getCahceDrawable(): Drawable {
            return weakReferenceCahe?.get() ?: (myDrawable.apply {
                weakReferenceCahe = WeakReference(this)
            })
        }
//        android.text.style.DynamicDrawableSpan.ALIGN_BOTTOM

        private var mVerticalAlignment = ALIGN_CENTER
//        private var paint:Paint = Paint().apply {
//            color = colorValue
//            textSize = textNewSize
//        }

        override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
            textWidth = paint.apply {
                textSize = textNewSize
                color = colorValue
            }.measureText(myText).toInt()
            myWith = paddingWidth * 2 + marginWidth * 2 + textWidth
            getCahceDrawable().setBounds(0, 0, textWidth + paddingWidth * 2, myHeight)
            Log.i("initData", "getSize: textWidth = $textWidth , myWith = $myWith , paint = $paint")
            return myWith
        }

        override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
            Log.d("initData", "initData draw: $text , start = $start , end = $end , x = $x , top = $top , y = $y , bottom = $bottom")
            Log.d("initData", "draw: paint.textSize = ${paint.textSize} , myText.size = ${paint.measureText(myText)} , paint = $paint")
//            super.draw(canvas, text, start, end, x, top, y, bottom, paint)
            canvas.save()
//            canvas.clipBounds.set(0,0,textWidth + paddingWidth * 2,myHeight + paddingHeight * 2)

            var transY: Int = bottom - getCahceDrawable().bounds.bottom
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= paint.fontMetricsInt.descent
            } else if (mVerticalAlignment == ALIGN_CENTER) {
                transY = top + (bottom - top) / 2 - getCahceDrawable().bounds.height() / 2
            }
            canvas.translate(x + marginWidth, transY.toFloat())
            getCahceDrawable().draw(canvas)
            canvas.restore()


            canvas.save()
            canvas.drawText(myText, x + paddingWidth + marginWidth, myHeight - (myHeight.toFloat() - textNewSize)/2, paint)
            canvas.restore()
        }


        companion object {

            /**
             * A constant indicating that the bottom of this span should be aligned
             * with the bottom of the surrounding text, i.e., at the same level as the
             * lowest descender in the text.
             */
            const val ALIGN_BOTTOM = 0

            /**
             * A constant indicating that the bottom of this span should be aligned
             * with the baseline of the surrounding text.
             */
            const val ALIGN_BASELINE = 1

            /**
             * A constant indicating that this span should be vertically centered between
             * the top and the lowest descender.
             */
            const val ALIGN_CENTER = 2

        }
    }

}