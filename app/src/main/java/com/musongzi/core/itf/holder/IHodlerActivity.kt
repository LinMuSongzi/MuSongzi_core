package com.musongzi.core.itf.holder

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.musongzi.core.itf.IClient

interface IHodlerActivity : IHodlerLifecycle, IHodlerContext, IHodlerArguments<Bundle>,
    IHolderViewModelProvider {
//    fun getHolderDataBinding():
    fun getHodlerActivity(): FragmentActivity?
    fun getClient(): IClient
}