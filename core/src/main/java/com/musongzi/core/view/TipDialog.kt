package com.musongzi.core.view

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.musongzi.core.R

class TipDialog(context: Context) : Dialog(context) {


    private var isInitView = false

    override fun show() {
        if (!isInitView) {
            beforeContentView()
            setContentView(R.layout.dialog_tip)
            afterContentView()
            isInitView = true
        }
        super.show()
    }

    protected fun beforeContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = window
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        setCanceledOnTouchOutside(true)
    }

    protected fun afterContentView() {
        val window = window
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val params = window!!.attributes
        params.width = display.width
        window.attributes = params
    }


}