package com.musongzi.comment.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.ViewTarget
import com.musongzi.core.ExtensionCoreMethod.androidColorGet
import com.musongzi.core.util.ActivityThreadHelp
import com.musongzi.core.util.InjectionHelp
import com.musongzi.core.util.ScreenUtil.SCREEN_1_3_WDITH

/*** created by linhui * on 2022/7/20 */


var placeholderId: Int = 0;
var errorId: Int = 0;


fun RequestBuilder<Drawable>.placeholderRes(): RequestBuilder<Drawable> {
    return if (placeholderId != 0) {
        placeholder(placeholderId)
    } else {
        this
    }
}

fun RequestBuilder<Drawable>.errorRes(): RequestBuilder<Drawable> {
    return if (errorId != 0) {
        error(errorId)
    } else {
        this
    }
}

fun RequestBuilder<Drawable>.memoryCacheStrategy(): RequestBuilder<Drawable> {
    return this
}

fun RequestManager.loadByAny(res: Any): RequestBuilder<Drawable> {
    return when (res) {
        is Int -> {
            load(res)
        }
        is Uri -> {
            load(res)
        }
        is String -> {
            load(res)
        }
        else -> {
            load(res)
        }
    }
}

fun RequestBuilder<Drawable>.overrideInto(
    imageView: ImageView,
    conifgOverride: (() -> Pair<Int, Int>)? = null
): ViewTarget<ImageView, *> {
    return conifgOverride?.invoke()?.let {
        override(it.first, it.second).into(imageView)
    } ?: into(imageView)
}

fun ImageView.showImage(uri: Any?, conifgOverride: (() -> Pair<Int, Int>)? = null) {
    uri?.let {
        if(uri is Bitmap){
            setImageBitmap(uri)
            return
        }
        Glide.with(this).loadByAny(it).placeholderRes().errorRes().memoryCacheStrategy()
            .overrideInto(this, conifgOverride)
    }
}

@BindingAdapter("setTextColorRes")
fun setTextColorRes(textView: TextView, res: Int) {
    textView.setTextColor(res.androidColorGet())
}

@BindingAdapter("setTextColorRes2")
fun setTextColorRes2(textView: TextView, res: Int) {
    textView.setTextColor(res)
}


@BindingAdapter("setTextNormal")
fun setText(textView: TextView, str: CharSequence?) {
    textView.text = str
}

@BindingAdapter("setTextNormal")
fun setText(textView: TextView, res: Int) {
    textView.setText(res)
}

@BindingAdapter("imageLoadRect")
fun imageLoadRect(image: ImageView, uri: Any?) {
    image.showImage(uri) {
        SCREEN_1_3_WDITH to SCREEN_1_3_WDITH
    }
}

@BindingAdapter("viewVisibility")
fun viewVisibility(v: View, int: Int) {
    v.visibility = int
}

@BindingAdapter("viewVisibility")
fun viewVisibility(v: View, isShow: Boolean) {
    v.visibility = if (isShow) View.VISIBLE else View.GONE
}

@BindingAdapter("imageLoadNormal")
fun imageLoadNormal(image: ImageView, uri: Any?) {
    image.showImage(uri)
}

@BindingAdapter("imageSet")
fun imageSet(image: ImageView, b: Bitmap?) {
    image.showImage(b)
}


//@BindingAdapter(value = ["onClickClass", "onClickAction"])
//fun viewVisibility(v: View, click: String, action: String?) {
//    val click:View.OnClickListener? = InjectionHelp.injectOnClick(click,action)
//    v.setOnClickListener(click)
//}



