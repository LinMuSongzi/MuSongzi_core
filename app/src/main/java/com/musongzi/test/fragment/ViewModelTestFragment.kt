package com.musongzi.test.fragment

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE
import android.text.style.*
import android.util.Log
import android.view.View.MeasureSpec
import com.musongzi.comment.ExtensionMethod.getDrawable
import com.musongzi.core.ExtensionCoreMethod.androidColorGet
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
        dataBinding.idDra.background = object : Drawable() {
            val b = BitmapFactory.decodeResource(requireActivity().application.resources, R.mipmap.ic_normal_man_hear)

            var rotate = 0f

            val p = Paint().apply {
                color = Color.parseColor("#a4fa9b")
                style = Paint.Style.FILL
                isAntiAlias = true
                textSize = ScreenUtil.dp2px(17f).toFloat()
            }

            private val sModes = arrayOf<Xfermode>(
                PorterDuffXfermode(PorterDuff.Mode.CLEAR),
                PorterDuffXfermode(PorterDuff.Mode.SRC),
                PorterDuffXfermode(PorterDuff.Mode.DST),
                PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
                PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                PorterDuffXfermode(PorterDuff.Mode.DST_IN),
                PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
                PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
                PorterDuffXfermode(PorterDuff.Mode.XOR),
                PorterDuffXfermode(PorterDuff.Mode.DARKEN),
                PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
                PorterDuffXfermode(PorterDuff.Mode.SCREEN)
            )

            val cp = Paint().apply {
                color = Color.parseColor("#98c61f")
                textSize = ScreenUtil.dp2px(17f).toFloat()
                style = Paint.Style.FILL
                isAntiAlias = true
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER);
            }

            var index = 0

            override fun draw(canvas: Canvas) {
                // 创建一个Bitmap对象
                // 创建一个Bitmap对象
                val bitmap = b

// 创建一个BitmapShader对象

// 创建一个BitmapShader对象
                val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

// 创建一个Paint对象，并将其Shader属性设置为上面创建的BitmapShader对象

// 创建一个Paint对象，并将其Shader属性设置为上面创建的BitmapShader对象
                val paint = Paint()
                paint.shader = shader

// 计算圆形的半径

// 计算圆形的半径
                val radius = Math.min(bitmap.width, bitmap.height) / 2

// 创建一个Canvas对象，并在其上绘制一个圆形

// 创建一个Canvas对象，并在其上绘制一个圆形
//                val canvas = Canvas()
//                canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), paint)

                canvas.drawRoundRect(
                    RectF(0f, 0f, b.width.toFloat()/2, b.height.toFloat()),
                    20f,
                    20f,
                    paint
                )

            }

            override fun setAlpha(alpha: Int) {

            }

            override fun setColorFilter(colorFilter: ColorFilter?) {
            }

            override fun getOpacity() = PixelFormat.UNKNOWN

        }
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
        val text = "依然坚持做自己想做的事情，成为自己想成为的人，是啊，无论多么不平凡的生命，最终都要归于平凡的柴米油盐；无论生命中有多少波澜壮阔，我们最迷恋的，始终是包裹在烟火人事里，平凡琐碎的温暖和感动。正如朴"
        //"树的《平凡之路》，“我曾经跨过山和大海，也穿过人山人海，我曾经拥有着的一切，转眼都飘散如烟，我曾经失落失望失掉所有方向，直到看见平凡才是唯一的答案。" +
//                "依然坚持做自己想做的事情，成为自己想成为的人，是啊，无论多么不平凡的生命，最终都要归于平凡的柴米油盐；无论生命中有多少波澜壮阔，我们最迷恋的，始终是包裹在烟火人事里，平凡琐碎的温暖和感动。正如朴树的《平凡之路》，“我曾经跨过山和大海，也穿过人山人海，我曾经拥有着的一切，转眼都飘散如烟，我曾经失落失望失掉所有方向，直到看见平凡才是唯一的答案。" +
//                "依然坚持做自己想做的事情，成为自己想成为的人，是啊，无论多么不平凡的生命，最终都要归于平凡的柴米油盐；无论生命中有多少波澜壮阔，我们最迷恋的，始终是包裹在烟火人事里，平凡琐碎的温暖和感动。正如朴树的《平凡之路》，“我曾经跨过山和大海，也穿过人山人海，我曾经拥有着的一切，转眼都飘散如烟，我曾经失落失望失掉所有方向，直到看见平凡才是唯一的答案。" +
//                "依然坚持做自己想做的事情，成为自己想成为的人，是啊，无论多么不平凡的生命，最终都要归于平凡的柴米油盐；无论生命中有多少波澜壮阔，我们最迷恋的，始终是包裹在烟火人事里，平凡琐碎的温暖和感动。正如朴树的《平凡之路》，“我曾经跨过山和大海，也穿过人山人海，我曾经拥有着的一切，转眼都飘散如烟，我曾经失落失望失掉所有方向，直到看见平凡才是唯一的答案。"


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


            start = 14
            end = 45
            span2 = BackDynamicDrawableSpan(
                myDrawable = R.drawable.shape_jingxuan.getDrawable(),
                myText = text.substring(start, end),
                paddingWidth = ScreenUtil.dp2px(5),
                height = ScreenUtil.dp2px(18f),
                marginWidth = ScreenUtil.dp2px(2),
                colorValue = R.color.jingxuan.androidColorGet()
            )
            it.setSpan(span2, start, end, SPAN_INCLUSIVE_EXCLUSIVE)

            start = 66
            end = 71
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
            Log.d("initData", "initData draw: start = $start , end = $end , x = $x , top = $top , y = $y , bottom = $bottom")
//            Log.d("initData", "draw: paint.textSize = ${paint.textSize} , myText.size = ${paint.measureText(myText)} , paint = $paint")
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
            canvas.drawText(myText, x + paddingWidth + marginWidth, myHeight - (myHeight.toFloat() - textNewSize) / 2, paint)
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