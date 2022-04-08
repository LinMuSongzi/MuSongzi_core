package com.musongzi.core.itf

interface INotifyDataSetChanged :IClient{
    fun notifyDataSetChanged()
    fun notifyDataSetChangedItem(postiont:Int)
}