package com.musongzi.comment.viewmodel.itf

import androidx.lifecycle.Observer
import com.musongzi.comment.bean.FileBean
import com.musongzi.core.itf.IAgent
import com.musongzi.core.itf.holder.IHolderViewModelProvider

interface IPhotoHandlerViewModel : IAgent,IHolderViewModelProvider{
    fun observerChange(observer: Observer<Int>)
    fun handlerCamera(b: Boolean)
    fun chooseFile(it: FileBean, b: Boolean):Boolean
    fun chooseFile(it: FileBean, b: Boolean, b1: Boolean):Boolean
    fun buildNormalData()
    fun chooseImages():MutableList<FileBean>
    fun simpleImages():MutableList<FileBean>
}
