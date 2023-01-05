package com.mszsupport.itf

import com.mszsupport.itf.IClient

interface INotifyDataSetChanged : IClient {
    fun notifyDataSetChanged()
    fun notifyDataSetChangedItem(postiont:Int)
}