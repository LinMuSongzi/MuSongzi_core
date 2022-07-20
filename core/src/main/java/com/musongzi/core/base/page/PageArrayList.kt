package com.musongzi.core.base.page

import android.util.Log
//import com.musongzi.core.ExtensionMethod.isDebug

/**
 *
 */
class PageArrayList<T> : ArrayList<T>() {

    private var enablePrintLog = true//isDebug()

    companion object {
        const val TAG = "PageArrayList"
    }

    override fun add(element: T): Boolean {
        return super.add(element)
        if (enablePrintLog) {
            Log.i(TAG, "add: element = $element")
        }
    }

    override fun add(index: Int, element: T) {
        super.add(index, element)
        if (enablePrintLog) {
            Log.i(TAG, "add: index = $index , element = $element")
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return super.addAll(elements)
        if (enablePrintLog) {
            Log.i(TAG, "addAll: ${elements.size}")
        }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return super.addAll(index, elements)
        if (enablePrintLog) {
            Log.i(TAG, "addAll: index = $index , ${elements.size}")
        }
    }


}