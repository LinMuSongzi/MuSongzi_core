package com.musongzi.core.itf.holder

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.INotifyDataSetChanged

interface IHolderActivity : IHolderLifecycle, IHolderContext, IHolderArguments<Bundle>, INotifyDataSetChanged, IHolderViewModelProvider {
    fun getHodlerActivity(): FragmentActivity?
    fun getClient(): IClient
}