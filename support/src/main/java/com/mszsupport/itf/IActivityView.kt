package com.mszsupport.itf

import androidx.fragment.app.FragmentActivity
import com.mszsupport.itf.holder.IHolderContext
import com.mszsupport.itf.holder.IHolderViewModelProvider

interface IActivityView : IHolderContext, INotifyDataSetChanged,
    IHolderViewModelProvider, IWant {
    fun getHolderActivity(): FragmentActivity?
    fun getClient(): IClient?
}