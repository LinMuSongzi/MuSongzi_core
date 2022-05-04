package com.musongzi.core.itf.holder

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.musongzi.core.itf.IClient
import com.musongzi.core.itf.IDisconnect
import com.musongzi.core.itf.INotifyDataSetChanged
import com.musongzi.core.itf.IWant

interface IHolderActivity : IHolderContext, IHolderArguments<Bundle>, INotifyDataSetChanged, IHolderViewModelProvider, IWant{
    fun getHolderActivity(): FragmentActivity?
    fun getClient(): IClient?
}