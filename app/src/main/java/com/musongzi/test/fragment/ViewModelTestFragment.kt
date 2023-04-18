package com.musongzi.test.fragment

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.style.*
import android.util.Log
import android.view.View.MeasureSpec
import com.musongzi.comment.ExtensionMethod.getDrawable
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
        val text = "热评vip果然很搞笑哈哈哈哈哈"


        val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        dataBinding.idContentIv.measure(w, w)

        Log.i(TAG, "initData: measuredHeight = ${dataBinding.idContentIv.measuredHeight} , 18dp = ${ScreenUtil.dp2px(18f)}px")

        dataBinding.idContentIv.text = SpannableString(text).let {

            var start = 0
            var end = 2
//
//            var span = BackDynamicDrawableSpan(
//                R.drawable.shape_red,
//                text.substring(start, end),
//                ScreenUtil.dp2px(5),
//                ScreenUtil.dp2px(18f),
//                dataBinding.idContentIv.measuredHeight
//            )
//
//            it.setSpan(span, start, end, SPAN_INCLUSIVE_EXCLUSIVE)

            start = 2
            end = 5
            var span2 = BackDynamicDrawableSpan(
                myDrawable = R.drawable.shape_gold.getDrawable(),
                myText = text.substring(start, end),
                paddingWith = ScreenUtil.dp2px(5),
                height = ScreenUtil.dp2px(18f),
                marginW = ScreenUtil.dp2px(5)
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
        var paddingWith: Int,
        height: Int,
        var marginW: Int
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

        private var mVerticalAlignment = ALIGN_BOTTOM

        override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
            textWidth = paint.measureText(myText).toInt()
            myWith = paddingWith * 2 + marginW * 2 + textWidth
            getCahceDrawable().setBounds(0, 0, textWidth + paddingWith * 2, myHeight)
            Log.i("initData", "getSize: textWidth = $textWidth , myWith = $myWith")
            return myWith
        }

        override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
            Log.i("initData", "initData draw: $text , start = $start , end = $end , x = $x , top = $top , y = $y , bottom = $bottom")
            Log.i("initData", "draw: paint.textSize = ${paint.textSize} , myText.size = ${paint.measureText(myText)}")
//            super.draw(canvas, text, start, end, x, top, y, bottom, paint)
            canvas.save()


            var transY: Int = bottom - getCahceDrawable().bounds.bottom
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= paint.fontMetricsInt.descent
            } else if (mVerticalAlignment == ALIGN_CENTER) {
                transY = top + (bottom - top) / 2 - getCahceDrawable().getBounds().height() / 2
            }
            canvas.translate(x + marginW, transY.toFloat())
            getCahceDrawable().draw(canvas)
            canvas.restore()


            canvas.save()
            canvas.drawText(myText, x + paddingWith + marginW, myHeight.toFloat(), paint)
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