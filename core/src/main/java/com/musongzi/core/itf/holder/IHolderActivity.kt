package com.musongzi.core.itf.holder

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IWant

interface IHolderActivity : IHolderContext, IHolderArguments<Bundle>, IHolderProviderContext, IWant{
    fun getHolderActivity(): FragmentActivity?
    fun getClient(): IClient?

}